package com.wubaoguo.springboot.core.bean;

import java.io.Serializable;

/**
 *
 * @Description: 基础认证bean
 * @author wubaoguo
 * @mail: wustrive2008@gmail.com
 * @date: 2017年3月14日 下午2:10:41
 * @version: v0.0.1
 */
public class AuthBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String userId;  //用户Id
    private String username; //用户名称
    private String password; //用户密码
    private Boolean isAuth;  //是否已认证

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Boolean isAuth) {
        this.isAuth = isAuth;
    }
    
    public  <T extends AuthBean> T copy(T bean) {
         bean.setUsername(getUsername());
         bean.setPassword(getPassword());
         bean.setUserId(getUserId());
         bean.setIsAuth(getIsAuth());
         return bean;
     }
    
}
