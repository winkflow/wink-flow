package com.wink;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wink.dao")
public class WinkServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WinkServiceApplication.class, args);
    }
}