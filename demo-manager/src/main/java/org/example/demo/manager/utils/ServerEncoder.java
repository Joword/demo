package org.example.demo.manager.utils;

import com.bgi.aigi.manager.model.ConvertResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author Joword
 * @date: 2023/12/7 10:44
 * @version: 0.1.0
 * @description: encoder
 */
@Slf4j
public class ServerEncoder implements Encoder.Text<ConvertResponse> {

    @Override
    public String encode(ConvertResponse object) {
        try {
            JsonMapper jsonMapper = new JsonMapper();
            return jsonMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Encoding Exception!");
            return null;
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
