package org.example.demo.manager.websocket;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.llm.client.ChatClient;
import org.example.demo.llm.conversation.OpenAIResponse;
import org.example.demo.llm.model.Parameters;
import org.example.demo.llm.prompt.Prompt;
import org.example.demo.llm.utils.OpenAIUtils;
import org.example.demo.manager.api.Message;
import org.example.demo.manager.config.WebSocketContext;
import org.example.demo.manager.model.AuthRequest;
import org.example.demo.manager.model.Response;
import org.example.demo.manager.utils.ServerEncoder;
import org.example.demo.manager.utils.WebSocketUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.example.demo.manager.api.MessageHandler;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Joword
 * @date: 2023/12/1 17:49
 * @version: 1.0.0
 * @description:
 */
@Slf4j
@Controller
@ServerEndpoint(value = "/msg",encoders = ServerEncoder.class)
public class WebsocketService implements InitializingBean {
    
    private static final Map<String, MessageHandler> HANDLERS = new HashMap<>();
    
    private static final List<JSONObject> messageLists = new ArrayList<>();

    @Autowired
    private ApplicationContext applicationContext;
    
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        log.info("[onOpen][session({}) 接入]", session);
        List<String> accessTokenValues = session.getRequestParameterMap().get("accessToken");
        String accessToken = !CollectionUtils.isEmpty(accessTokenValues) ? accessTokenValues.get(0) : null;
        // 创建 AuthRequest 消息类型
        AuthRequest authRequest = new AuthRequest().setAccessToken(accessToken);
        // 获得消息处理器
        MessageHandler<AuthRequest> messageHandler = HANDLERS.get(AuthRequest.TYPE);
        if (messageHandler == null) {
            log.error("[onOpen][认证消息类型，不存在消息处理器]");
            return;
        }
        messageHandler.execute(session, authRequest);
    }
    
    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("[onOpen][session({}) 接收到一条消息({})]", session, message);
        Response response = JSONObject.toJavaObject(JSONObject.parseObject(message), Response.class);
        if ("J".equals(response.getTag())){
            JSONObject messageObj = response.getContent();
            Parameters parameters = null;
            Parameters checkParameters = null;
            Parameters checkSummaryParameter = null;
            Parameters reParameters = null;
            OpenAIResponse openAICheckResponse = null;
            OpenAIResponse openAIResponse = null;
            OpenAIResponse openAICheckSummaryResponse = null;
            OpenAIResponse handlerExceptionResponse = null;
            try {
                MessageHandler messageHandler = HANDLERS.get(AuthRequest.TYPE);
                AuthRequest authRequest = new AuthRequest();
                authRequest.setAccessToken(response.getSessionId());
                /* 添加判断 */
                checkParameters = OpenAIUtils.isCheck();
                checkParameters.setPrompt(Prompt.buildCheck(messageObj.getString("question")));
                openAICheckResponse = WebSocketContext.getContext().getBean(ChatClient.class).toBashChat(checkParameters);
                /* 正式提问 */
                if ("数据异常，请重新提问".equals(openAICheckResponse.getTotal())){
                    log.error("提问有误，请重新发送!");
                    authRequest.setMsg("提问有误，请重新发送");
                } else {
                    Matcher checkMatcher = Pattern.compile("(?i)positive").matcher(openAICheckResponse.getChoicesMessageContent());
                    /* 添加prompt，重要！ */
                    if (checkMatcher.find()){
                        checkSummaryParameter = OpenAIUtils.isCheckSummary();
                        checkSummaryParameter.setPrompt(Prompt.buildCheckSummary(messageObj.getString("question")));
                        openAICheckSummaryResponse = WebSocketContext.getContext().getBean(ChatClient.class).toBashChat(checkSummaryParameter);
                        Matcher yesMatcher = Pattern.compile("(?i)positive|same|similar").matcher(openAICheckSummaryResponse.getChoicesMessageContent());
                        if (yesMatcher.find()) {
                            parameters = OpenAIUtils.isSummary(response.getModel());
                            parameters.setPrompt(Prompt.buildSummary(messageObj.getString("question")));
                        } else {
                            /* 选择使用gpt-3.5/gpt-3.5-16k/gpt-4/gpt4-32k */
                            parameters = OpenAIUtils.isChatting();
                            parameters.setPrompt(Prompt.buildChat(messageObj.getString("question")));
                        }
                    } else {
                        parameters = OpenAIUtils.isChatting();
                        parameters.setPrompt(Prompt.build(messageObj.getString("question")));
                    }
                    openAIResponse = WebSocketContext.getContext().getBean(ChatClient.class).toBashChat(parameters);
                    authRequest.setMsg(openAIResponse.getChoicesMessageContent());
                }
                messageHandler.execute(session, (Message) authRequest);
            } catch (Exception e) {
                CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.getCloseCode(1006),"Model exception!");
                this.onClose(session, closeReason);
                try {
                    session.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    @OnClose
    public void onClose(Session session, CloseReason closeReason){
        log.info("[onClose][session({}) 连接关闭。关闭原因是({})}]", session, closeReason);
        WebSocketUtils.rmSession(session);
    }
    
    @OnError
    public void onError(Session session, Throwable throwable){
        log.info("[onClose][session({}) 发生异常]", session, throwable);
    }
    
    @Override
    public void afterPropertiesSet() {
        applicationContext.getBeansOfType(MessageHandler.class).values()
                .forEach(messageHandler -> HANDLERS.put(messageHandler.getType(), messageHandler));
        log.info("[afterPropertiesSet][消息处理器数量：{}]", HANDLERS.size());
    }
    
    private Class<? extends Message> getMessageClass(MessageHandler handler){
        // 获得 Bean 对应的 Class 类名。因为有可能被 AOP 代理过。
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(handler);
        // 获得接口的 Type 数组
        Type[] interfaces = targetClass.getGenericInterfaces();
        Class<?> superclass = targetClass.getSuperclass();
        while ((Objects.isNull(interfaces) || 0 == interfaces.length) && Objects.nonNull(superclass)) { // 此处，是以父类的接口为准
            interfaces = superclass.getGenericInterfaces();
            superclass = targetClass.getSuperclass();
        }
        if (Objects.nonNull(interfaces)) {
            // 遍历 interfaces 数组
            for (Type type : interfaces) {
                // 要求 type 是泛型参数
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    // 要求是 MessageHandler 接口
                    if (Objects.equals(parameterizedType.getRawType(), MessageHandler.class)) {
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        // 取首个元素
                        if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
                            return (Class<Message>) actualTypeArguments[0];
                        } else {
                            throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));
                        }
                    }
                }
            }
        }
        throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));
    }
}
