package com.wubaoguo.springboot.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.wubaoguo.springboot.filter.SubThreadContent;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
    
    @Bean
    public FilterRegistrationBean getDemoFilter(){
        SubThreadContent threadContent = new SubThreadContent();
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        registrationBean.setFilter(threadContent);
        List<String> urlPatterns=new ArrayList<String>();
        urlPatterns.add("/*");//拦截路径，可以添加多个
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
