package com.wubaoguo.springboot.app.service;

import com.wubaoguo.springboot.jwt.JwtAuthentication;

public interface JwtAuthService {

    JwtAuthentication login(JwtAuthentication auth);

}
