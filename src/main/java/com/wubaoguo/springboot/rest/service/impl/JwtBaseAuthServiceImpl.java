package com.wubaoguo.springboot.rest.service.impl;

import com.wubaoguo.springboot.core.bean.AuthBean;
import com.wubaoguo.springboot.core.bean.LoginParam;
import org.springframework.stereotype.Service;

import com.wubaoguo.springboot.rest.service.JwtAuthService;

/**
 * Description: 基础登录验证
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2023-3-9 20:19
 */
@Service
public class JwtBaseAuthServiceImpl implements JwtAuthService {

    @Override
    public AuthBean login(LoginParam loginParam) {
        //todo: 登录验证逻辑
        AuthBean auth = new AuthBean();
        return auth;
    }


}
