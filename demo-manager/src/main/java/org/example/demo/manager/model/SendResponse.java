package org.example.demo.manager.model;

import com.bgi.aigi.manager.api.Message;

/**
 * @author Joword
 * @date: 2023/12/1 20:32
 * @version:
 * @description:
 */
public class SendResponse implements Message {
    
    public static final String TYPE = "SEND_RESPONSE";

    public String getSessionId() {
        return sessionId;
    }

    public SendResponse setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public SendResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SendResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    private String sessionId;
    
    private Integer code;
    
    private String message;

    public String getAccessToken() {
        return accessToken;
    }

    public SendResponse setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    private String accessToken;
    
    
    
}
