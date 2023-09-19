package com.example.pipayshopapi.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author wzx
 */

@Component
public class RedisUtil<T> {

    @Resource
    private RedisTemplate redisTemplate;

    public static RedisTemplate redis;

    @PostConstruct
    public void getRedisTemplate() {
        redis = this.redisTemplate;
    }

    /**
     * 将对象的列表数据存入redis中的双向列表中
     */
    public void savaDataListToRedisList(String key, List<T> list) {
        // 设置Key的String序列化
        redis.setKeySerializer(RedisSerializer.string());
        redis.opsForList().leftPushAll(key, list);
        redis.expire(key, Constants.REGION_VALID_TIME, TimeUnit.SECONDS);
    }

    /**
     * 将对象的列表数据从redis中的双向列表中获取出来，通过分页进行获取
     */
    public List<T> getDataListFromRedisList(String key) {
        // 设置Key的String序列化
        redis.setKeySerializer(RedisSerializer.string());
        return redis.opsForList().range(key, 0, -1);
    }




    /**
     * 设置指定 key 的值
     */
    public void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value, Long.parseLong(Constants.CHECK_CODE_VALID_TIME), TimeUnit.SECONDS);
    }

    /**
     * 获取指定 key 的值
     */
    public T get(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key)
    {
        return redisTemplate.delete(key);
    }

    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }
}
