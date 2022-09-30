package com.bjtu.sdtest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.bjtu.sdtest.mapper"})
public class SdTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdTestApplication.class, args);
    }

}
