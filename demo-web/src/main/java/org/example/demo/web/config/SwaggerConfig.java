package org.example.demo.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joword
 * @date: 2024/2/16 16:26
 * @version: 0.1.0
 * @description: swagger ui config
 */
@Slf4j
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    
    public Docket createRestApi(){
        RequestParameter parameter = new RequestParameterBuilder()
                .name("ticket")
                .description("user ticket")
                .in(ParameterType.HEADER)
                .required(false)
                .build();
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(parameter);
        return new Docket(DocumentationType.OAS_30)
                .globalRequestParameters(parameters)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.example.demo.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Demo supply chain platform")
                .description("RESTFUL api")
                .contact(new Contact("platform","","23089538@qq.com"))
                .version("demo-v1.0")
                .build();
    }
}
