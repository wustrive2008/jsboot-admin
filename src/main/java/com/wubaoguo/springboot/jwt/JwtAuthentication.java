package com.wubaoguo.springboot.jwt;

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
public class JwtAuthentication {
    private String userId;
    private String username;
    private String password;
    
    private String deviceId;  //设备id
    
    private String authType;  //认证类型
    
    private boolean isAuth;  //是否已经认证
    
    private String accessToken;  //jwt授权token

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

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean isAuth) {
        this.isAuth = isAuth;
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
