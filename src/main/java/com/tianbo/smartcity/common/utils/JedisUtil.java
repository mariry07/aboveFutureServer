package com.tianbo.smartcity.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis 工具类
 *
 * @author yxk
 */
@Component
public class JedisUtil {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";

    /**
     * redis服务
     */
    @Autowired
    private JedisPool jedisPool;

    /**
     * set原子操作
     *
     * @param key        键
     * @param value      值
     * @param expireTime 超期时间
     * @return 是否set成功
     */
    public boolean setNX(String key, String value, int expireTime) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.set(key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        return LOCK_SUCCESS.equals(result);
    }

}
