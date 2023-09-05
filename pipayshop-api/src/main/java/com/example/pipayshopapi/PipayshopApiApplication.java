package com.example.pipayshopapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * @author wzx
 */
@SpringBootApplication
@EnableRabbit
@MapperScan("com.example.pipayshopapi.mapper")
public class PipayshopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PipayshopApiApplication.class, args);
        System.out.println("PiPayShop启动成功！");
    }



}
