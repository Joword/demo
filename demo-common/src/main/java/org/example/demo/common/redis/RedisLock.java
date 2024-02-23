package org.example.demo.common.redis;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Joword
 * @date: 2024/2/16 17:12
 * @version: 0.1.0
 * @description: reids 分布式锁
 */
@Getter
@Setter
public class RedisLock {
    private String key;
    private final UUID uuid;
    private long lockTime;
    
    public RedisLock(String key, UUID uuid, long lockTime){
        this.key = key;
        this.uuid = uuid;
        this.lockTime = lockTime;
    }
    
}
