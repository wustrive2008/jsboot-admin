package com.wubaoguo.springboot.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description: webconfig
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:10
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    /**
     * 默认首页
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/manage/login.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
