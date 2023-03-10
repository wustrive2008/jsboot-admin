package com.wubaoguo.springboot.core.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wubaoguo
 * @Description: 基础认证bean
 * @mail: wustrive2008@gmail.com
 * @date: 2017年3月14日 下午2:10:41
 * @version: v0.0.1
 */
@Data
public class AuthBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userId;  //用户Id
    private String accessToken; //token
    private Boolean isAuth;  //是否已认证


    public <T extends AuthBean> T copy(T bean) {
        bean.setUserId(getUserId());
        bean.setAccessToken(getAccessToken());
        bean.setIsAuth(getIsAuth());
        return bean;
    }
}
