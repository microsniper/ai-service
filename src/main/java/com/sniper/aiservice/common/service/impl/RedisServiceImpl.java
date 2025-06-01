package com.sniper.aiservice.common.service.impl;

import com.sniper.aiservice.common.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author sniper
 * @Date 2024/4/29 11:10 AM
 **/
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key,value,time);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean tryLock(String lockKey, String requestId, long expireTime, TimeUnit timeUnit) {
        // 使用 SETNX + 过期时间（原子操作）
        Boolean result = redisTemplate.opsForValue().setIfAbsent(
                lockKey,
                requestId,
                expireTime,
                timeUnit
        );
        return result != null && result;
    }

    public boolean unlock(String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Long result = redisTemplate.execute(
                new DefaultRedisScript<>(script, Long.class),
                Collections.singletonList(lockKey),
                requestId
        );
        return result != null && result == 1L;
    }
}
