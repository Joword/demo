package org.example.demo.commons.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * @author Joword
 * @date: 2023/10/9 18:00
 * @version: 0.1.0
 * @description: timed task
 */
@Configuration
@EnableScheduling
public class ScheduleTask {
    
    @Scheduled(cron = "0 22 2 28-31 * ?")
    private void toStartTasks(){
        final Calendar c = Calendar.getInstance();
        if (c.get(Calendar.DATE)==c.getActualMaximum(Calendar.DATE)){
            System.out.println("执行定时任务时间："+ LocalDateTime.now());
        }
    }
    
}
