package org.example.demo.llm.conversation;

import lombok.Data;

/**
 * @author Joword
 * @date: 2023/12/1 15:07
 * @version: 0.1.0
 * @description: api response data
 */
@Data
public class OpenAIResponse {
    private String total;
    private String id;
    private String object;
    private String created;
    private Integer usageCompletionTokens;
    private Integer usagePromptTokens;
    private Integer usageTotalTokens;
    private String model;
    private String choicesFinishReason;
    private Integer choicesIndex;
    private String choicesMessageRole;
    private String choicesMessageContent;
    private String error;
    private String message;
    private String code;
}
