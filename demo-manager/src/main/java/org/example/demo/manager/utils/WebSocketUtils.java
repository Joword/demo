package org.example.demo.manager.utils;


import com.bgi.aigi.manager.api.Message;
import com.bgi.aigi.manager.model.AuthRequest;
import com.bgi.aigi.manager.model.ConvertResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Joword
 * @date: 2023/10/10 16:27
 * @version: 0.1.0
 * @description: websocket utils
 */
@Component
public class WebSocketUtils {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketUtils.class);

    private static final Map<Session, String> SESSION_USER_MAP = new ConcurrentHashMap<>();

    private static final Map<String, Session> USER_SESSION_MAP = new ConcurrentHashMap<>();

    private static <T extends Message> ConvertResponse buildTextMessage(String type, T message){
        ConvertResponse msg = new ConvertResponse();
        msg.setCode(1000);
        msg.setMsg("success");
        msg.setBody(((AuthRequest) message).getMsg());
        return msg;
    }

    private static void sendTextMessage(Session session, ConvertResponse textMessage){
        if (session==null){
            logger.error("[sendTextMessage][session is null]");
            return;
        }
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic==null){
            logger.error("[sendTextMessage][session 的 为 null]");
            return;
        }
        try {
            basic.sendObject(textMessage);
        } catch (IOException e) {
            logger.error("[sendTextMessage][session({}) sendmsg{}] exception!",session, textMessage, e);
        } catch (EncodeException e) {
            logger.error("[sendTextMessage][session({}) sendmsg{}] exception!",session, textMessage, e);
        }

    }

    public static void addSession(Session session, String user){
        USER_SESSION_MAP.put(user, session);
        SESSION_USER_MAP.put(session, user);
    }

    public static void rmSession(Session session){
        String user = SESSION_USER_MAP.remove(session);
        if (user!=null && user.length()>0){
            USER_SESSION_MAP.remove(user);
        }
    }
    
    public static <T extends Message> void send(Session session, String type, T message){
        ConvertResponse textMessage = buildTextMessage(type, message);
        sendTextMessage(session, textMessage);
    }

    public static <T extends Message> boolean send(String user, String type, T message){
        Session session = USER_SESSION_MAP.get(user);
        if (session==null){
            logger.error("[send][user({}) has no session]", user);
            return false;
        }
        send(session, type, message);
        return true;
    }
    
    
}
