package com.wubaoguo.springboot.jwt;

import org.apache.commons.lang3.StringUtils;

import com.nimbusds.jose.JWSObject;
import com.wubaoguo.springboot.filter.ThreadContent;

public class JwtSubject {
    private JWSObject jwsObject;

    public JwtSubject() {}
    
    
    public JwtSubject(JWSObject jwsObject) {
        this.jwsObject = jwsObject;
    }
    
    /**
     * @return the jwsObject
     */
    public JWSObject getJwsObject() {
        return jwsObject;
    }

    /**
     * @param jwsObject the jwsObject to set
     */
    public void setJwsObject(JWSObject jwsObject) {
        this.jwsObject = jwsObject;
    }
    
    public static JWTUser getJwtUser() {
        return ThreadContent.getData(JwtConstants.THREAD_CURRENT_USER);
    }
    
    public static boolean isLogin() {
        // 直接进行非空验证.后期做修改
        JWTUser jwtUser = getJwtUser();
        if(jwtUser != null && StringUtils.isNotBlank(jwtUser.getUserId())) {
            return true;
        }
        return false;
    }
}
