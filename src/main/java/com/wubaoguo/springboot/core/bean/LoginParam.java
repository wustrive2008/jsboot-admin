package com.wubaoguo.springboot.core.bean;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: 基础登录参数
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2023-3-9 20:25
 */
@Data
public class LoginParam {
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public LoginParam(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
