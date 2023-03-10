package com.wubaoguo.springboot.rest.service;

import com.wubaoguo.springboot.core.bean.AuthBean;
import com.wubaoguo.springboot.core.bean.LoginParam;
import com.wubaoguo.springboot.core.exception.LoginSecurityException;

public interface JwtAuthService {

    AuthBean login(LoginParam loginParam) throws LoginSecurityException;

}
