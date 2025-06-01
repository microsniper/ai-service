package com.sniper.aiservice.common.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    /**
     * 保存属性
     */
    void set(String key, Object value, long time);
    /**
     * 保存属性
     */
    void set(String key, Object value);

    /**
     * 获取属性
     */
    Object get(String key);

    public boolean tryLock(String lockKey, String requestId, long expireTime, TimeUnit timeUnit);

    public boolean unlock(String lockKey, String requestId);
}
