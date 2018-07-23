package com.wubaoguo.springboot.rest.service.impl;

import org.springframework.stereotype.Service;

import com.wubaoguo.springboot.rest.service.JwtAuthService;
import com.wubaoguo.springboot.jwt.JwtAuthentication;

@Service
public class JwtBaseAuthServiceImpl implements JwtAuthService{

    @Override
    public JwtAuthentication login(JwtAuthentication auth) {
        return null;
    }


}
