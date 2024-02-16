package org.example.demo.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.SneakyThrows;

import java.util.*;

/**
 * @author Joword
 * @date: 2024/2/16 10:44
 * @version: 0.1.0
 * @description: 熔断器是否开启，若开启切换线程
 */
public class Passport implements RequestInterceptor {
    
    private Boolean hystirx;
    
    @SneakyThrows
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Map<String, Collection<String>> headers = new HashMap<>();
        if (!hystirx){
            headers.put("userId",Collections.singleton(""));
        }
        requestTemplate.headers(headers);
    }
}
