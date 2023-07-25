package com.example.pipayshopapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
public class PipayshopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PipayshopApiApplication.class, args);
        System.out.println("PiPayShop启动成功！");
    }

}
