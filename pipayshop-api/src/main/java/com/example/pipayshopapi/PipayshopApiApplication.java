package com.example.pipayshopapi;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wzx
 */
@SpringBootApplication
@EnableRabbit
public class PipayshopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PipayshopApiApplication.class, args);
        System.out.println("PiPayShop启动成功！");
    }
}
