package org.example.demo.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.UUID;

/**
 * @author Joword
 * @date: 2024/2/16 17:16
 * @version:
 * @description:
 */
@Slf4j
public class RedisLockUtils {
    
    private static RedisScript<String> scriptLock = new DefaultRedisScript<>(RedisKeys.LUA_SCRIPT_LOCK,String.class);
    private static RedisScript<String> scriptUnLock = new DefaultRedisScript<>(RedisKeys.LUA_SCRIPT_UNLOCK,String.class);

    /** to get a key
     * @param redisTemplate RedisTemplate 
     * @param key String
     * @return lock()
     */
    public static RedisLock lock(RedisTemplate<String, String> redisTemplate, String key){
        return lock(redisTemplate,key,RedisKeys.DEFAULT_LOCK_TIME_OUT,RedisKeys.DEFAULT_TRY_LOCK_TIME_OUT);
    }

    /** to get a key in a lock time
     * @param redisTemplate RedisTemplate
     * @param key String
     * @param lockTime long
     * @return lock()
     */
    public static RedisLock lock(RedisTemplate<String, String> redisTemplate, String key, long lockTime){
        return lock(redisTemplate, key , lockTime, RedisKeys.DEFAULT_TRY_LOCK_TIME_OUT);
    }

    /** release the lock
     * @param redisTemplate RedisTemplate 
     * @param key String
     * @param lockTime long
     * @param lockTimeTry long
     * @return RedisLock
     */
    public static RedisLock lock(RedisTemplate<String, String> redisTemplate, String key, long lockTime, long lockTimeTry){
        long timeStamp = System.currentTimeMillis();
        try {
            key = key + ".lock";
            UUID uuid = UUID.randomUUID();
            while (lockTimeTry == 0 || (System.currentTimeMillis() - timeStamp) < lockTimeTry) {
                String result = redisTemplate.execute(scriptLock, redisTemplate.getStringSerializer(), redisTemplate.getStringSerializer(), Collections.singletonList(key), uuid.toString(),  String.valueOf(lockTime));
                if (result != null && result.equals(RedisKeys.CONSTANT_OK)) {
                    return new RedisLock(key, uuid, lockTime);
                } else {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        log.error("Fail to get lock key:{},lockTimeout:{},tryLockTimeout:{}", key, lockTime, lockTimeTry,e);
                        Thread.currentThread().interrupt();
                    }
                }
            }
            log.error("Fail to get lock key:{},lockTimeout:{},tryLockTimeout:{}", key, lockTime, lockTimeTry);
        } catch (Exception e) {
            log.error("RedisLock lock error",e);
        } finally {
            long tick = System.currentTimeMillis() - timeStamp;
        }
        return null;
    }
    
    public static void unLock(RedisTemplate<String, String> redisTemplate, RedisLock lock){
        long timeStamp = System.currentTimeMillis();
        try {
            String result = redisTemplate.execute(scriptUnLock, redisTemplate.getStringSerializer(), redisTemplate.getStringSerializer(), Collections.singletonList(lock.getKey()), lock.getUuid().toString());
            log.info("{},release the lock, status：{}",lock.getKey(),result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("RedisLockUtils unLock error", e);
        } finally {
            long tick = System.currentTimeMillis() - timeStamp;
            if (tick > RedisKeys.TIME){
                log.error("Unlock time: " + tick + "ms, key: " + lock.getKey());
            }
        }
    }
}
