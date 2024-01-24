package org.example.demo.llm.client;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSONObject;
import org.example.demo.llm.conversation.OpenAIResponse;
import org.example.demo.llm.model.Parameters;
import org.example.demo.llm.utils.OpenAIUtils;
import org.springframework.stereotype.Component;

/**
 * @author Joword
 * @date: 2023/11/29 18:45
 * @version: 0.1.0
 * @description: chat azure openai api
 */
@Component
public class ChatClient {
    
    public static OpenAIResponse toBashChat(Parameters parameters){
        Process process = null;
        OpenAIResponse openAIResponse = null;
        try {
            process = Runtime.getRuntime().exec("curl "+ OpenAIUtils.toGenerate(parameters));
            String output = IoUtil.read(process.getInputStream(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(output);
            openAIResponse = OpenAIUtils.getResponse(jsonObject);
            return openAIResponse;
        } catch (Exception e) {
            openAIResponse = new OpenAIResponse();
            openAIResponse.setTotal("数据异常，请重新提问");
            openAIResponse.setChoicesMessageContent("Error");
            return openAIResponse;
        }
    }
    
}
