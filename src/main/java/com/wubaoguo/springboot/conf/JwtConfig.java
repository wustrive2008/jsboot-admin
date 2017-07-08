package com.wubaoguo.springboot.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wubaoguo.springboot.jwt.filter.JwtAuthorizeFilter;

@Configuration
public class JwtConfig {
    
    /**
     * app 登录URI
     */
    public static final String LOGIN_URL = "/app/login";
    
    /**
     * app 可不登录URI
     */
    public static final String[] OPEN_URL = new String[]{"/app/open"};
    
    @Bean  
    public FilterRegistrationBean basicFilterRegistrationBean(){  
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
        JwtAuthorizeFilter filter = new JwtAuthorizeFilter();  
        registrationBean.setFilter(filter);  
          
        List<String> urlPatterns = new ArrayList<>();  
        urlPatterns.add("/app/*");  
          
        registrationBean.setUrlPatterns(urlPatterns);  
        return registrationBean;  
    }  
}
