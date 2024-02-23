package org.example.demo.common.redis;

/**
 * @author Joword
 * @date: 2024/2/16 17:12
 * @version: 0.1.0
 * @description: the public redis key
 */
public interface RedisKeys {

    /**
     * save the public redis key
     */
    String USER_ID_LIST = "userIdList";

    /**
     * set default time
     */
    final long DEFAULT_LOCK_TIME_OUT = 6000;
    /**
     * 争抢锁的超时时间，单位毫秒，0代表永不超时（一直抢到锁为止）
     */
    final long DEFAULT_TRY_LOCK_TIME_OUT = 5000;
    final String LUA_SCRIPT_LOCK = "return redis.call('SET', KEYS[1], ARGV[1], 'NX', 'PX', ARGV[2]) ";
    final String LUA_SCRIPT_UNLOCK =
            "if (redis.call('GET', KEYS[1]) == ARGV[1]) then "
                    + "return tostring(redis.call('DEL',KEYS[1]))"
                    + "else " + "return '0' " + "end";
    final int TIME = 1000;
    final String CONSTANT_OK = "OK";
}
