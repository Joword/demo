package org.example.demo.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 
 */
@Slf4j
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"org.example.demo"})
public class DemoApplication 
{
    public static void main( String[] args ) {
        SpringApplication.run(DemoApplication.class,args);
        System.out.println( "DemoApplication is activated" );
    }
}
