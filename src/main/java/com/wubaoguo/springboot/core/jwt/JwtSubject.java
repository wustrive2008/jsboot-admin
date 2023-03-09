package com.wubaoguo.springboot.core.jwt;

import com.nimbusds.jose.JWSObject;
import com.wubaoguo.springboot.constant.JwtConstants;
import com.wubaoguo.springboot.core.bean.AuthBean;
import com.wubaoguo.springboot.core.filter.ThreadContentFilter;
import org.apache.commons.lang3.StringUtils;

/**
 * Description: jwt对象
 * 
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:15
 */
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
    
    public static AuthBean getJwtUser() {
        return ThreadContentFilter.getData(JwtConstants.THREAD_CURRENT_USER);
    }
    
    public static boolean isLogin() {
        // 直接进行非空验证.后期做修改
        AuthBean jwtUser = getJwtUser();
        if(jwtUser != null && StringUtils.isNotBlank(jwtUser.getUserId())) {
            return true;
        }
        return false;
    }
}
