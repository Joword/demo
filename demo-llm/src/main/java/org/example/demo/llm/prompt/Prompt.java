package org.example.demo.llm.prompt;

import com.alibaba.fastjson.JSONObject;

import org.example.demo.llm.model.PromptValue;
import org.example.demo.llm.model.SendMessage;
import org.example.demo.llm.model.SystemPromptEnum;
import org.example.demo.llm.utils.AssemblyUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joword
 * @date: 2023/11/30 21:38
 * @version: 0.1.0
 * @description: make prompt engineer
 */
@Component
public class Prompt {
    
    public static String buildOrigin(String question){
        SendMessage sendMessage = new SendMessage();
        PromptValue systemPrompt = new PromptValue();
        PromptValue userPrompt = new PromptValue();
        List<JSONObject> jsonObjects = new ArrayList<>();
        systemPrompt.setRole("system");
        systemPrompt.setContent(SystemPromptEnum.ASSISTANT.toString());
        userPrompt.setRole("user");
        userPrompt.setContent(question);
        jsonObjects.add(JSONObject.parseObject(JSONObject.toJSONString(systemPrompt)));
        jsonObjects.add(JSONObject.parseObject(JSONObject.toJSONString(userPrompt)));
        sendMessage.setMessages(jsonObjects);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(sendMessage));
        return JSONObject.toJSONString(jsonObject).replace("\"","\\\"").replace("\\\\","\\");
    }

    public static String buildCheck(String question){
        SendMessage sendMessage = new SendMessage();
        PromptValue systemPrompt = new PromptValue();
        PromptValue userPrompt = new PromptValue();
        List<JSONObject> jsonObjects = new ArrayList<>();
        systemPrompt.setRole("system");
        systemPrompt.setContent(AssemblyUtils.toCheck(question));
        jsonObjects.add(JSONObject.parseObject(JSONObject.toJSONString(systemPrompt)));
        sendMessage.setMessages(jsonObjects);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(sendMessage));
        return JSONObject.toJSONString(jsonObject).replace("\"","\\\"");
    }
    
    public static String build(String question){
        SendMessage sendMessage = new SendMessage();
        PromptValue systemPrompt = new PromptValue();
        PromptValue userPrompt = new PromptValue();
        List<JSONObject> jsonObjects = new ArrayList<>();
        systemPrompt.setRole("system");
        systemPrompt.setContent(AssemblyUtils.toRole());
        userPrompt.setRole("user");
        userPrompt.setContent(question);
        jsonObjects.add(JSONObject.parseObject(JSONObject.toJSONString(systemPrompt)));
        jsonObjects.add(JSONObject.parseObject(JSONObject.toJSONString(userPrompt)));
        sendMessage.setMessages(jsonObjects);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(sendMessage));
        return JSONObject.toJSONString(jsonObject).replace("\"","\\\"");
    }
    
    public static String buildCheckSummary(String question){
        SendMessage sendMessage = new SendMessage();
        PromptValue systemPrompt = new PromptValue();
        PromptValue userPrompt = new PromptValue();
        List<JSONObject> jsonObjects = new ArrayList<>();
        systemPrompt.setRole("system");
        systemPrompt.setContent(AssemblyUtils.toCheckSummary(question));
        jsonObjects.add(JSONObject.parseObject(JSONObject.toJSONString(systemPrompt)));
        sendMessage.setMessages(jsonObjects);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(sendMessage));
        return JSONObject.toJSONString(jsonObject).replace("\"","\\\"");
        
    }
    
    public static String buildSummary(String question){
        SendMessage sendMessage = new SendMessage();
        PromptValue systemPrompt = new PromptValue();
        PromptValue userPrompt = new PromptValue();
        List<JSONObject> jsonObjects = new ArrayList<>();
        systemPrompt.setRole("system");
        systemPrompt.setContent(AssemblyUtils.toSummary());
        userPrompt.setRole("user");
        userPrompt.setContent(question);
        jsonObjects.add(JSONObject.parseObject(JSONObject.toJSONString(systemPrompt)));
        jsonObjects.add(JSONObject.parseObject(JSONObject.toJSONString(userPrompt)));
        sendMessage.setMessages(jsonObjects);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(sendMessage));
        return JSONObject.toJSONString(jsonObject).replace("\"","\\\"").replace("\\\\","\\");
    }
    
    public static String buildChat(String question){
        SendMessage sendMessage = new SendMessage();
        PromptValue systemPrompt = new PromptValue();
        PromptValue userPrompt = new PromptValue();
        List<JSONObject> jsonObjects = new ArrayList<>();
        systemPrompt.setRole("system");
        systemPrompt.setContent(SystemPromptEnum.PAPERPARSER.toString());
        userPrompt.setRole("user");
        userPrompt.setContent(AssemblyUtils.toChat(question));
        jsonObjects.add(JSONObject.parseObject(JSONObject.toJSONString(systemPrompt)));
        jsonObjects.add(JSONObject.parseObject(JSONObject.toJSONString(userPrompt)));
        sendMessage.setMessages(jsonObjects);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(sendMessage));
        return JSONObject.toJSONString(jsonObject).replace("\"","\\\"");
    }
}
