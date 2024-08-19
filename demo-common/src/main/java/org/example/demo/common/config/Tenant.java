package org.example.demo.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

public class Tenant implements RequestInterceptor {

    @Value("${tenant.username}")
    private String userName;
    
    @Value("${tenant.password}")
    private String password;
    
    @Value("...")
    private String hystrix;
    
    @SneakyThrows
    @Override
    public void apply(RequestTemplate requestTemplate) {
        
        List<String> authorizationList = new ArrayList<>();

        //生成basic
        String basicOrigin = String.format("%s:%s",userName,password);
        authorizationList.add("basic "+ basicOrigin);
        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put("Authorization", authorizationList);
        
        requestTemplate.headers(headers);
        
    }
}
