package com.wubaoguo.springboot.conf;

import java.util.ArrayList;
import java.util.List;

import com.wubaoguo.springboot.filter.CORSFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wubaoguo.springboot.filter.SubThreadContent;

/**
 * Description: 过滤器配置
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:10
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean getDemoFilter() {
        SubThreadContent threadContent = new SubThreadContent();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(threadContent);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");//拦截路径，可以添加多个
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    /**
     * 前端跨域处理过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean getCorsRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CORSFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(2);
        return registration;
    }

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
