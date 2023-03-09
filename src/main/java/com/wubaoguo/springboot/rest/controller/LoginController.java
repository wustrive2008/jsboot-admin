package com.wubaoguo.springboot.rest.controller;

import com.wubaoguo.springboot.core.bean.LoginParam;
import com.wubaoguo.springboot.core.request.ViewResult;
import com.wubaoguo.springboot.rest.service.BaseLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Description:前端登录接口
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2023-3-9 14:43
 */
@RestController
@RequestMapping("/rest/login")
public class LoginController {

    @Autowired
    BaseLoginService baseLoginService;

    @RequestMapping(value = "/baseLogin", method = RequestMethod.POST)
    public String appLogin(LoginParam loginParam) {
        Map<String, Object> loginRes = baseLoginService.login(loginParam);
        return ViewResult.newInstance().success().setData(loginRes).json();
    }

}
