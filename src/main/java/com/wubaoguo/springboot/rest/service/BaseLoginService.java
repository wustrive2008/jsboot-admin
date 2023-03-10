package com.wubaoguo.springboot.rest.service;

import com.nimbusds.jose.KeyLengthException;
import com.wubaoguo.springboot.core.bean.AuthBean;
import com.wubaoguo.springboot.core.bean.LoginParam;
import com.wubaoguo.springboot.core.exception.LoginSecurityException;
import com.wubaoguo.springboot.core.request.ViewResult;
import com.wubaoguo.springboot.core.util.JWTUtil;
import com.wubaoguo.springboot.core.util.WebUtil;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public Map<String, Object> login(LoginParam param) throws IOException {
        AuthBean authBean = null;
        ViewResult viewResult = ViewResult.newInstance();
        try {
            authBean = jwtBaseAuthServiceImpl.login(param);
        } catch (LoginSecurityException e) {
            e.printStackTrace();
            WebUtil.write(viewResult.state(e.getState(), e.getMessage()).json(), param.getResponse());
        }
        Map<String, Object> res = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", authBean.getUserId());
        res.put("userId", authBean.getUserId());
        try {
            String accessToken = JWTUtil.encrypt(jsonObject);
            res.put("accessToken", accessToken);
        } catch (KeyLengthException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
