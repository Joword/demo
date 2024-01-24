package org.example.demo.llm.model;

import lombok.Data;

/**
 * @author Joword
 * @date: 2023/11/27 16:23
 * @version: 0.1.0
 * @description: parameters
 */
@Data
public class Parameters {
    private String key;
    private String endpoint;
    private String model;
    private String version;
    private String prompt;
}
