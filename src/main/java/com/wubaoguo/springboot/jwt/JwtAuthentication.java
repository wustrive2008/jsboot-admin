package com.wubaoguo.springboot.jwt;

import org.wustrive.java.core.bean.AuthBean;

import com.nimbusds.jose.JWSObject;
import com.wubaoguo.springboot.util.JWTUtil;

/**
 * jwt 授权信息 
 * @Title: JwtAuthentication.java
 * @ClassName: com.wubaoguo.springboot.jwt.JwtAuthentication
 * @Description: TODO
 *
 * Copyright  2015-2017 维创盈通 - Powered By 研发中心 V1.0.0
 * @author wustrive
 * @date 2017年3月10日 下午5:57:56
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
