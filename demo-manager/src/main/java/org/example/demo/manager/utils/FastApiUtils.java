package org.example.demo.manager.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Joword
 * @date: 2023/8/4 10:16
 * @version: 0.1.0
 * @description: a component about entity
 */
@Component
public class FastApiUtils {

    public static HttpEntity<MultiValueMap<String, Object>> buildEntity() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);
        return entity;
    }

}
