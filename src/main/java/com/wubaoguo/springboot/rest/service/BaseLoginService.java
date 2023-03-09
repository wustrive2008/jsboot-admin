package com.wubaoguo.springboot.rest.service;

import com.nimbusds.jose.KeyLengthException;
import com.wubaoguo.springboot.core.bean.AuthBean;
import com.wubaoguo.springboot.core.bean.LoginParam;
import com.wubaoguo.springboot.core.util.JWTUtil;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2023-3-9 14:45
 */
@Service
public class BaseLoginService {
    @Autowired
    JwtAuthService jwtBaseAuthServiceImpl;

    public Map<String, Object> login(LoginParam auth) {
        AuthBean authBean = jwtBaseAuthServiceImpl.login(auth);
        Map<String, Object> res = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", authBean.getUserId());
        res.put("userId", authBean.getUserId());
        try {
            String accessToken = JWTUtil.encrypt(jsonObject);
            res.put("Authorization", accessToken);
        } catch (KeyLengthException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
