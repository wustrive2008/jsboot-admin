package com.wubaoguo.springboot.rest.controller;

import com.google.common.collect.Maps;
import com.wubaoguo.springboot.core.request.ViewResult;
import com.wubaoguo.springboot.core.util.JWTUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Description:
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2023-3-10 9:11
 */
@RestController
@RequestMapping("/rest/user")
public class UserController {

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public String appLogin() {
        Map<String, Object> res = Maps.newHashMap();
        res.put("userId", JWTUtil.getJwtUser().getUserId());
        return ViewResult.newInstance().success().setData(res).json();
    }
}
