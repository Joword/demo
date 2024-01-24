package org.example.demo.manager.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Joword
 * @date: 2024/1/24 21:27
 * @version: 1.0.0
 * @description: websocket context
 */
@Component
public class WebSocketContext implements ApplicationContextAware {


    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext(){
        return context;
    }

    public Object getBean(String name){
        return context.getBean(name);
    }

    public <T> T getBean(String name, Class<T> clazz){
        return context.getBean(name, clazz);
    }

    public <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }
}
