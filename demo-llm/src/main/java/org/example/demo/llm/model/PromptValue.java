package org.example.demo.llm.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Joword
 * @date: 2023/11/30 21:38
 * @version: 0.1.0
 * @description: prompt value
 */
@Data
public class PromptValue implements Serializable {
    private String role;
    private String content;
}
