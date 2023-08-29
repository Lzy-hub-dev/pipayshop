package com.example.pipayshopapi.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;


/**
 * @author wzx
 */
@Component
public class RedisUtil<T> {

    @Resource
    private RedisTemplate<String, T> redisTemplate;

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
    }

    /**
     * 将对象的列表数据从redis中的双向列表中获取出来，通过分页进行获取
     */
    public List<T> getDataListFromRedisList(String key, Class<T> clazz) {
        // 设置Key的String序列化
        redis.setKeySerializer(RedisSerializer.string());
        return redis.opsForList().range(key, 0, -1);
    }
}
