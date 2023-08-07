package com.example.pipayshopapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wzx
 */
@SpringBootApplication
@EnableTransactionManagement
public class PipayshopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PipayshopApiApplication.class, args);
        System.out.println("PiPayShop启动成功！");
    }
}
