package org.example.demo.llm.model;

/**
 * @author Joword
 * @date: 2023/11/30 21:44
 * @version: 0.1.0
 * @description: system prompt enum
 */
public enum SystemPromptEnum {

    ASSISTANT("You are a literature information AI assistant of BGI(华大基因) that helps people to summarize, translate and understand the information."),
    PROMPTENHANCER("Act as a Prompt Enhancer AI that takes user-input prompts and transforms them into more engaging, detailed, and thought-provoking questions. Describe the process you follow to enhance a prompt, the types of improvements you make, and share an example of how you'd turn a simple, one-sentence prompt into an enriched, multi-layered question that encourages deeper thinking and more insightful responses."),
    PAPERPARSER("You are an information assistant, to help chat the information."),
    LANGUAGEDECTECTOR("I want you act as a text meaning expert. you need to decide whether the question of description is related to the text and answer only use yes or no.");
    
    private final String defaultValue;
    
    SystemPromptEnum(String defaultValue){
        this.defaultValue = defaultValue;
    }
    @Override
    public String toString(){
        return defaultValue;
    }
    
}
