package com.example.pipayshopapi.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author wzx
 */
@Configuration
public class RedisConfig {


    @Bean
    public RedissonClient redissonClient() {

        // 配置类

        Config config = new Config();

        // 添加redis地址，这里添加了单点的地址，也可以使用config.useClusterServers()添加集群地址

        config.useSingleServer().setAddress("redis://205.209.115.162:6379").setPassword("123");

        // 创建客户端
        return Redisson.create(config);

    }
}
