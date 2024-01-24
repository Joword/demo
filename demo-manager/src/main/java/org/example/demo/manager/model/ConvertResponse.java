package org.example.demo.manager.model;

import lombok.Data;

/**
 * @author Joword
 * @date: 2023/12/7 14:45
 * @version: 0.1.0
 * @description:
 */
@Data
public class ConvertResponse {
    private int code;
    private String msg;
    private String body;
}
