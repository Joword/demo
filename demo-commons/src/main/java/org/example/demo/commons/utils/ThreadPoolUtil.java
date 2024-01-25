package org.example.demo.commons.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author Joword
 * @date: 2023/10/9 17:53
 * @version: 0.1.0
 * @description: thread pool 
 */
@Configuration
public class ThreadPoolUtil {

    /**有界
     * @return ExecutorService 
     */
    @Bean
    public ExecutorService threadPool(){
        int keepAliveTime = 300;
        int corePoolSize = (int) (Runtime.getRuntime().availableProcessors() / (1 - 0.9));
        ExecutorService pool = new ThreadPoolExecutor(corePoolSize, corePoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(corePoolSize*5), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        return pool;
    }
    
}
