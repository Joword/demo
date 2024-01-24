package org.example.demo.manager.model;


import org.example.demo.manager.api.Message;

/**
 * @author Joword
 * @date: 2023/12/1 17:53
 * @version: 0.1.0
 * @description: user request
 */

public class AuthRequest implements Message {
    public static final String TYPE = "AUTH_REQUEST";

    public String getAccessToken() {
        return accessToken;
    }

    public AuthRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    private String accessToken;

    public String getMsg() {
        return msg;
    }

    public AuthRequest setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    private String msg;
    
    
}
