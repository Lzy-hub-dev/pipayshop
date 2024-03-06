package com.example.pipayshopapi;

import org.mybatis.spring.annotation.MapperScan;
// import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wzx
 */
@SpringBootApplication
@EnableRabbit
@MapperScan("com.example.pipayshopapi.mapper")
@EnableScheduling

public class PipayshopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PipayshopApiApplication.class, args);
        System.out.println("           _                        __\n" +
                "    ____  (_)___  ____ ___  _______/ /_  ____  ____\n" +
                "   / __ \\/ / __ \\/ __ `/ / / / ___/ __ \\/ __ \\/ __ \\\n" +
                "  / /_/ / / /_/ / /_/ / /_/ (__  ) / / / /_/ / /_/ /\n" +
                " / .___/_/ .___/\\__,_/\\__, /____/_/ /_/\\____/ .___/\n" +
                "/_/     /_/          /____/                /_/\n");
        System.out.println("pipayshop is running");
    }

}
