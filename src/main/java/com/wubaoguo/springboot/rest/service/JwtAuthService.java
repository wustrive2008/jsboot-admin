package com.wubaoguo.springboot.rest.service;

import com.wubaoguo.springboot.jwt.JwtAuthentication;

public interface JwtAuthService {

    JwtAuthentication login(JwtAuthentication auth);

}
