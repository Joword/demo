package org.example.demo.manager.api;

import javax.websocket.Session;

/**
 * @author Joword
 * @date: 2023/10/10 16:54
 * @version: 0.1.0
 * @description: websocket message handler
 */
public interface MessageHandler<T extends Message> {
    
    /** to do message
     * @param session websocket session
     * @param message msg
     */
    void execute(Session session, T message);
    

    /**
     * @return msg type 
     */
    String getType();
}
