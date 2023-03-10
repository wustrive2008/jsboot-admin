package com.wubaoguo.springboot.rest.service.impl;

import com.wubaoguo.springboot.core.bean.AuthBean;
import com.wubaoguo.springboot.core.bean.LoginParam;
import com.wubaoguo.springboot.core.exception.LoginSecurityException;
import com.wubaoguo.springboot.core.request.StateMap;
import com.wubaoguo.springboot.rest.service.JwtAuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
    public AuthBean login(LoginParam loginParam) throws LoginSecurityException {
        if (StringUtils.isAnyBlank(loginParam.getUsername(), loginParam.getPassword())) {
            throw new LoginSecurityException(StateMap.S_AUTH_ERROR, "账号密码不能为空");
        }
        //todo: 登录验证逻辑
        AuthBean auth = new AuthBean();
        auth.setIsAuth(true);
        auth.setUserId("11111");
        return auth;
    }

}
