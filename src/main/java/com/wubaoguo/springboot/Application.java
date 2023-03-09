package com.wubaoguo.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Description: jsboot-admin springboot快速开发脚手架
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2023-3-9 12:57
 *
 */
@SpringBootApplication
@EnableCaching
@ServletComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
