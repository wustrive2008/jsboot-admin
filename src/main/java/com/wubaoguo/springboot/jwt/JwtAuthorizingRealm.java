package com.wubaoguo.springboot.jwt;

import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.wubaoguo.springboot.exception.BusinessException;
import com.wubaoguo.springboot.exception.LoginSecurityException;
import com.wubaoguo.springboot.filter.ThreadContent;
import com.wubaoguo.springboot.redis.JwtSubjectCache;
import com.wubaoguo.springboot.response.StateMap;
import com.wubaoguo.springboot.response.ViewResult;
import com.wubaoguo.springboot.service.JwtAuthService;
import com.wubaoguo.springboot.util.JWTUtil;

@Component
public class JwtAuthorizingRealm {
    
    @Autowired
    private JwtSubjectCache jwtSubjectCache;
    
    
    @Autowired
    private JwtAuthService jwtAuthService;
    
    
    /**
     * 登录认证  app 具体认证策略交给 具体业务实现
     * @param appAuthentication
     * @return
     */
    public ViewResult doAuthentication(JwtAuthentication auth) throws LoginSecurityException {
        ViewResult viewResult = ViewResult.newInstance();
        String authType = auth.getAuthType();
        if(StringUtils.isBlank(authType)) {
            throw new LoginSecurityException(StateMap.S_IS_NULL, "http Header中authType不存在");
        }
        //调用对应的登录处理方法 进行登录验证
        auth  = jwtAuthService.login(auth);
        if(auth.isAuth()) {
            try {
                //使用jwt加密得到token格式为A.B.C
                String accessToken = encrypt(auth);
                Map<String,Object> res = new HashMap<String,Object>();
                res.put("Authorization", accessToken);
                //APP用户缓存 key:userId+authType 用于限制多终端登录
                jwtSubjectCache.put(auth.getUserId() + auth.getAuthType(), auth.getDeviceId());
                return viewResult.setData(res).success();
            } catch(BusinessException e) {
                throw new LoginSecurityException(StateMap.S_ERROR, "未知错误,请联系管理员");
            }
        }
        return viewResult.state(StateMap.S_200,"登录失败,请稍后重试");
    }
    
    /**
     *  做进行验证成功 ，服务器进行 token 验证
     * 
     *  默认解密 采用jwt方法 若切换其他加密策略 重写此方法
     * 
     * @param appAuthentication
     * @return
     * @throws LoginSecurityException
     */
    public boolean isAuthentication(JwtAuthentication auth) throws LoginSecurityException {
        try {
            String token = auth.getAccessToken();
            String deviceId = auth.getDeviceId();
            String authType = auth.getAuthType();
            
            JWSObject jwsObject = JWTUtil.decrypt(token);
            // 验证 token 信息是否有效 并验证 token 是否过期
            if(JWTUtil.verify(jwsObject)) {
                
                // 验证 终端标识是否与deviceId 标识匹配
                if(!deviceId.equals(JWTUtil.getValue(jwsObject, "deviceId"))) {
                    // 终端标识与deviceId不匹配，验证失败。返回
                    return false;
                }
                // 验证终端authType是否有效
                if(!authType.equals(JWTUtil.getValue(jwsObject, "authType"))) {
                    // 终端authType 匹配失效验证失败 
                    return false;
                }
                
                if(!JWTUtil.exp(jwsObject)) {
                    // token 信息过期，需客户端重新获取
                    throw new LoginSecurityException(StateMap.S_403, "登录已过期，请重新登录");
                }
                
                 //判断用户是否已换设备登录
                if(!isAuthOfDevice(auth)){
                    throw new LoginSecurityException(StateMap.S_403, "该账号已在其他设备登录,请重新登录");
                }
                
                // 添加用户信息到当前线程
                JWTUser jwtUser = new JWTUser(jwsObject);
                ThreadContent.addData(JwtConstants.THREAD_CURRENT_USER, jwtUser);
                return true;
            } 
        } catch (BusinessException e) {
            throw new LoginSecurityException(e.getState(), e.getMessage());
        }
        // 验证无效token
        return false;
    }
    
    
    /**
     * 判断该设备是否是最新授权设备
     * @param auth
     * @return
     * @throws BusinessException 
     */
    private boolean isAuthOfDevice(JwtAuthentication auth) throws BusinessException {
        Object deviceId = jwtSubjectCache.get(auth.getUserId()+auth.getAuthType());
        if(null != deviceId && deviceId.toString().equals(auth.getDeviceId())){
            return true;
        }
        return false;
    }

    /**
     * jwt 加密 
     * @param auth
     * @return
     * @throws LoginSecurityException
     */
    public String encrypt(JwtAuthentication auth) throws LoginSecurityException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", auth.getUserId());
        jsonObject.put("deviceId", auth.getDeviceId());
        jsonObject.put("authType", auth.getAuthType());
        try {
            return JWTUtil.encrypt(jsonObject);
        } catch (JOSEException e) {
            throw new LoginSecurityException(StateMap.S_IS_FORMAT, "authentication fail");
        }
    }
    
    
    

}
