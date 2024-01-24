package org.example.demo.manager.model;

import com.bgi.aigi.manager.api.Message;

/**
 * @author Joword
 * @date: 2023/12/1 18:05
 * @version: 0.1.0
 * @description: auth response
 */
public class AuthResponse implements Message {
    
    public static final String TYPE = "AUTH_RESPONSE";

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public AuthResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public AuthResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    private Integer code;
    
    private String message;
    
}
