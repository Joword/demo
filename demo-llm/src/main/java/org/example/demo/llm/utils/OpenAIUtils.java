package org.example.demo.llm.utils;

import com.alibaba.fastjson.JSONObject;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import org.example.demo.llm.config.InputParameters;
import org.example.demo.llm.conversation.OpenAIResponse;
import org.example.demo.llm.model.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Joword
 * @date: 2023/11/27 16:17
 * @version: 0.1.0
 * @description: 
 */
@Component
public class OpenAIUtils {
    
    private static InputParameters inputParameters;
    
    @Autowired
    private void setInputParameters(InputParameters parameters){
        OpenAIUtils.inputParameters = parameters;
    }

    public static Parameters isCheck(){return inputParameters.inputOpenAI4();}

    public static Parameters isCheckSummary(){ return inputParameters.inputOpenAI35(); }
    
    public static Parameters isModel(String modelName){
        switch (modelName){
            case "gpt-35-turbo":
                return inputParameters.inputOpenai();
            case "gpt-35-16k":
                return inputParameters.inputOpenAI35();
            case "gpt-4":
                return inputParameters.inputOpenAI4();
            case "gpt-4-32k":
                return inputParameters.inputOpenai432k();
            default:
                Parameters para = new Parameters();
                para.setVersion("None");
                return para;
        }
    }

    public static Parameters isChatting(){
        return inputParameters.inputOpenAI35();
    }
    
    public static Parameters isSummary(String modelName){
        switch (modelName){
            case "gpt-35-turbo":
            case "gpt-35-16k":
                return inputParameters.inputOpenAI35();
            case "gpt-4":
                return inputParameters.inputOpenAI4();
            case "gpt-4-32k":
                return inputParameters.inputOpenai432k();
            default:
                Parameters para = new Parameters();
                para.setVersion("None");
                return para;
        }
    }
    
    public static int countTokens(String content){
        EncodingRegistry registry = Encodings.newLazyEncodingRegistry();
        Encoding encoding = registry.getEncoding(EncodingType.CL100K_BASE);
        return encoding.countTokens(content);
    }
    
    public static String toGenerate(Parameters parameters){
        StringBuffer para = new StringBuffer();
        if ("gpt-35-turbo".equals(parameters.getModel())){
            para.append(parameters.getEndpoint()).append("openai/deployments/").append("bgioaigpt/").append("chat/completions?api-version=").append(parameters.getVersion()).append(" ").append("-H \"Content-Type:application/json\"").append(" ").append("-H \"api-key:").append(parameters.getKey()).append("\"").append(" ").append("-d ").append("\"").append(parameters.getPrompt()).append("\"");
        }
        else if ("gpt-35-16k".equals(parameters.getModel())){
            para.append(parameters.getEndpoint()).append("openai/deployments/").append("bgioaigpt-16k/").append("chat/completions?api-version=").append(parameters.getVersion()).append(" ").append("-H \"Content-Type:application/json\"").append(" ").append("-H \"api-key:").append(parameters.getKey()).append("\"").append(" ").append("-d ").append("\"").append(parameters.getPrompt()).append("\"");
        }
        else if ("gpt-4".equals(parameters.getModel())){
            para.append(parameters.getEndpoint()).append("openai/deployments/").append("GPT4/").append("chat/completions?api-version=").append(parameters.getVersion()).append(" ").append("-H \"Content-Type:application/json\"").append(" ").append("-H \"api-key:").append(parameters.getKey()).append("\"").append(" ").append("-d ").append("\"").append(parameters.getPrompt()).append("\"");
        }
        else if ("gpt-4-32k".equals(parameters.getModel())){
            para.append(parameters.getEndpoint()).append("openai/deployments/").append("GPT4-32k/").append("chat/completions?api-version=").append(parameters.getVersion()).append(" ").append("-H \"Content-Type:application/json\"").append(" ").append("-H \"api-key:").append(parameters.getKey()).append("\"").append(" ").append("-d ").append("\"").append(parameters.getPrompt()).append("\"");
        }
        return para.toString();
    }
    
    public static OpenAIResponse getErrorResponse(JSONObject apiData){
        OpenAIResponse openAIResponse = new OpenAIResponse();
        openAIResponse.setCode(apiData.getJSONObject("error").getJSONObject("message").getString("status"));
        openAIResponse.setError(apiData.getString("error"));
        openAIResponse.setMessage(apiData.getJSONObject("error").getJSONObject("message").getString("message"));
        return openAIResponse;
    }
    
    public static OpenAIResponse getResponse(JSONObject apiData){
        OpenAIResponse openAIResponse = new OpenAIResponse();
        openAIResponse.setTotal(JSONObject.toJSONString(apiData));
        openAIResponse.setId(apiData.getString("id"));
        openAIResponse.setCreated(apiData.getString("created"));
        openAIResponse.setObject(apiData.getString("object"));
        openAIResponse.setModel(apiData.getString("model"));
        openAIResponse.setUsageCompletionTokens(JSONObject.parseObject(JSONObject.parseObject(apiData.getString("usage")).toString()).getInteger("completion_tokens"));
        openAIResponse.setUsagePromptTokens(JSONObject.parseObject(JSONObject.parseObject(apiData.getString("usage")).toString()).getInteger("prompt_tokens"));
        openAIResponse.setUsageTotalTokens(JSONObject.parseObject(JSONObject.parseObject(apiData.getString("usage")).toString()).getInteger("total_tokens"));
        openAIResponse.setChoicesFinishReason(JSONObject.parseObject(JSONObject.parseArray(apiData.getString("choices")).get(0).toString()).getString("finish_reason"));
        openAIResponse.setChoicesIndex(JSONObject.parseObject(JSONObject.parseArray(apiData.getString("choices")).get(0).toString()).getInteger("index"));
        openAIResponse.setChoicesMessageRole(JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseArray(apiData.getString("choices")).get(0).toString()).getString("message")).getString("role"));
        openAIResponse.setChoicesMessageContent(JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseArray(apiData.getString("choices")).get(0).toString()).getString("message")).getString("content"));
        if (apiData.getJSONObject("error")!=null){
            openAIResponse.setError(apiData.getString("error"));
            openAIResponse.setCode(apiData.getJSONObject("error").getString("status"));
            openAIResponse.setMessage(apiData.getJSONObject("error").getString("message"));
        }
        return openAIResponse;
    }
    
}
