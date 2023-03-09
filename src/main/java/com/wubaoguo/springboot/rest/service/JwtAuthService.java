package com.wubaoguo.springboot.rest.service;

import com.wubaoguo.springboot.core.bean.AuthBean;
import com.wubaoguo.springboot.core.bean.LoginParam;

public interface JwtAuthService {

    AuthBean login(LoginParam auth);

}
