package com.wubaoguo.springboot.core.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Description: 基础登录参数
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2023-3-9 20:25
 */
@Data
@AllArgsConstructor
public class LoginParam {
    private String username;
    private String password;
}
