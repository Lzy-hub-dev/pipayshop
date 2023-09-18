package com.example.pipayshopapi.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private RedisTemplate<String, T> redisTemplate;

    public static RedisTemplate redis;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

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
    public void set(String key, String value) {
        try {
            // 设置序列化
            stringRedisTemplate.opsForValue().set(key, value, Constants.CHECK_CODE_VALID_TIME, TimeUnit.SECONDS);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取指定 key 的值
     */
    public String get(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除指定 key 的值
     */
    public void del(String key){
        try {
            stringRedisTemplate.delete(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
