package com.wubaoguo.springboot.jwt;

import org.wustrive.java.core.bean.AuthBean;

import com.nimbusds.jose.JWSObject;
import com.wubaoguo.springboot.util.JWTUtil;

/**
 *
 * Description: jwt 授权信息
 * 
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/21 9:01
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class JwtAuthentication extends AuthBean{
    
    private String deviceId;  //设备id
    
    private String authType;  //认证类型
    
    private String accessToken;  //jwt授权token
    
    
    public JwtAuthentication() {}
    
    public JwtAuthentication(JWSObject jwsObject) {
        //this.userId = null != JWTUtil.getValue(jwsObject, "userId") ? JWTUtil.getValue(jwsObject, "userId").toString() : null;
        this.deviceId = null != JWTUtil.getValue(jwsObject, "deviceId") ? JWTUtil.getValue(jwsObject, "deviceId").toString() : null;
        this.authType = null != JWTUtil.getValue(jwsObject, "authType") ? JWTUtil.getValue(jwsObject, "authType").toString() : null;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }
    
}
