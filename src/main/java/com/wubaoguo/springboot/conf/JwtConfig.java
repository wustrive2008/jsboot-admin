package com.wubaoguo.springboot.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wubaoguo.springboot.jwt.filter.JwtAuthorizeFilter;

/**
 * Description: jwt配置
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:09
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Configuration
public class JwtConfig {

    /**
     * rest 登录URI
     */
    public static final String LOGIN_URL = "/rest/login";

    /**
     * rest 可不登录URI
     */
    public static final String[] OPEN_URL = new String[]{"/rest/open"};

    @Bean
    public FilterRegistrationBean basicFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JwtAuthorizeFilter filter = new JwtAuthorizeFilter();
        registrationBean.setFilter(filter);

        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/rest/*");

        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}
