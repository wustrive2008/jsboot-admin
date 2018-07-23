package com.wubaoguo.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.wubaoguo.springboot", "org.wustrive.java.dao.jdbc.dao"})
@MapperScan(basePackages = {"com.wubaoguo.springboot.rest.service", "com.wubaoguo.springboot.dao"})
@SpringBootApplication
@EnableCaching
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
