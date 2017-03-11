package com.wubaoguo.springboot.jwt;

import com.nimbusds.jose.JWSObject;
import com.wubaoguo.springboot.util.JWTUtil;


public class JWTUser {
	private String userId;
	private String deviceId;  
	private String authType;

	public JWTUser() {}
	
	public JWTUser(JWSObject jwsObject) {
		this.userId = null != JWTUtil.getValue(jwsObject, "userId") ? JWTUtil.getValue(jwsObject, "userId").toString() : null;
		this.deviceId = null != JWTUtil.getValue(jwsObject, "deviceId") ? JWTUtil.getValue(jwsObject, "deviceId").toString() : null;
		this.authType = null != JWTUtil.getValue(jwsObject, "authType") ? JWTUtil.getValue(jwsObject, "authType").toString() : null;
	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
