package org.example.demo.manager.websocket;

import com.bgi.aigi.manager.api.MessageHandler;
import com.bgi.aigi.manager.model.AuthRequest;
import com.bgi.aigi.manager.model.AuthResponse;
import com.bgi.aigi.manager.utils.WebSocketUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.Session;

/**
 * @author Joword
 * @date: 2023/12/1 18:07
 * @version: 0.1.0
 * @description: auth message
 */
@Component
public class AuthMessageHandler implements MessageHandler<AuthRequest> {
    @Override
    public void execute(Session session, AuthRequest message) {
        if (StringUtils.isEmpty(message.getAccessToken())){
            WebSocketUtils.send(session, AuthResponse.TYPE, new AuthResponse().setCode(1).setMessage("认证 accessToken 未传入"));
        }
        WebSocketUtils.addSession(session, message.getAccessToken());
        WebSocketUtils.send(session, AuthResponse.TYPE, message);
    }
    @Override
    public String getType() {
        return AuthRequest.TYPE;
    }
}
