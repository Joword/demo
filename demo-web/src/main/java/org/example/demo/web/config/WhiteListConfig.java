package org.example.demo.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Joword
 * @date: 2024/2/16 13:45
 * @version: 0.1.0
 * @description: white list config
 */
@Setter
@Getter
@Component
@Configurable
@PropertySource(value = {"classpath:application.yml"},encoding = "utf-8")
@ConfigurationProperties(prefix = "server.white-list")
public class WhiteListConfig {

    /**
     * white list container
     */
    private List<String> list;

    /**
     * @return obtain the white list
     */
    public List<String> getWhiteList() {return this.list;}

    /**
     * a matcher of anywhere
     */
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * @param path String
     *
     * @return matcher
     */
    public Boolean matcher(String path){
        boolean result = false;
        if (!CollectionUtils.isEmpty(list)){
            for (String item : list){
                result = antPathMatcher.match(item,path);
                if (result){
                    break;
                }
            }
        }
        return result;
    }
    
}
