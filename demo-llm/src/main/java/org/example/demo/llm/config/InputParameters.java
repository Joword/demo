package org.example.demo.llm.config;

import org.example.demo.llm.model.Parameters;
import org.example.demo.llm.prompt.Prompt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Joword
 * @date: 2023/11/30 21:14
 * @version: 0.1.0
 * @description: openai parameters
 */
@Configuration
public class InputParameters {
    @Bean
    public Parameters inputOpenai(){
        Parameters parameters = new Parameters();
        parameters.setEndpoint("https://bgioaistudio.openai.azure.com/");
        parameters.setKey("6d93e436f3274457a46daebd59f03969");
        parameters.setModel("gpt-35-turbo");
        parameters.setVersion("2023-07-01-preview");
        parameters.setPrompt(Prompt.build("Hi, there!"));
        return parameters;
    }    
    @Bean
    public Parameters inputOpenai432k(){
        Parameters parameters = new Parameters();
        parameters.setEndpoint("https://bgioaistudio.openai.azure.com/");
        parameters.setKey("6d93e436f3274457a46daebd59f03969");
        parameters.setModel("gpt-4-32k");
        parameters.setVersion("2023-03-15-preview");
        parameters.setPrompt(Prompt.build("Hi, there!"));
        return parameters;
    }
    @Bean
    public Parameters inputOpenAI35(){
        Parameters parameters = new Parameters();
        parameters.setEndpoint("https://bgioaistudio.openai.azure.com/");
        parameters.setKey("6d93e436f3274457a46daebd59f03969");
        parameters.setModel("gpt-35-16k");
        parameters.setVersion("2023-03-15-preview");
        parameters.setPrompt(Prompt.build("Hi, there!"));
        return parameters;
    }
    @Bean
    public Parameters inputOpenAI4(){
        Parameters parameters = new Parameters();
        parameters.setEndpoint("https://bgioaistudio.openai.azure.com/");
        parameters.setKey("6d93e436f3274457a46daebd59f03969");
        parameters.setModel("gpt-4");
        parameters.setVersion("2023-03-15-preview");
        parameters.setPrompt(Prompt.build("Hi, there!"));
        return parameters;
    }
    
}
