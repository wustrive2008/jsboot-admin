package com.wubaoguo.springboot.util;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import net.minidev.json.JSONObject;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

public class JWTUtil {
    
    @Value("${jwt.secret}")
    private static String JWT_SECRET;
    
    @Value("${jwt.exp}")
    private static int JWT_EXP;
    
    
    /**
     * 验证jwsObject 是否有效
     * 
     * @param jwsObject
     * @return
     * @throws BusinessException
     */
    public static boolean verify(JWSObject jwsObject) {
         try {
            MACVerifier macVerifier =  new MACVerifier(JWT_SECRET.getBytes());
            return macVerifier.verify(jwsObject.getHeader(), jwsObject.getSigningInput(), jwsObject.getSignature());
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 服务器端解密 jwtTemplate
     * 
     * @param jwtTemplate
     * @return
     * @throws BusinessException
     */
    public static JWSObject decrypt(String jwtTemplate){
        try {
            return JWSObject.parse(jwtTemplate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * accessToken 加密
     * 
     * @param jsonObject
     * @return
     * @throws BusinessException
     */
    public static String encrypt(JSONObject jsonObject) throws KeyLengthException {
        //设置token过期时间
        jsonObject.put("exp",DateUtil.getAfterHourDate(new Date(), JWT_EXP).getTime());
        
        Payload payload = new Payload(jsonObject);
            
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).
                               contentType("jwt").
                               build();
        
        // Create JWS object
        JWSObject jwsObject = new JWSObject(header, payload);
        
        // Create HMAC signer     
        JWSSigner signer = new MACSigner(JWT_SECRET.getBytes());
        
        try {
            jwsObject.sign(signer);
        } catch (JOSEException e) {
            System.err.println("Couldn't sign JWS object: " + e.getMessage());
        }  
        
        return jwsObject.serialize();
    }
    
    /**
     * accessToken 加密
     * 
     * @param jsonObject 用户信息
     * @param jti 终端标识
     * @return
     * @throws BusinessException
     */
    public static String encrypt(JSONObject jsonObject, String jti) throws JOSEException {
        jsonObject.put("jti", jti); // 终端标识 
        return encrypt(jsonObject);
    }
    
    
    /**
     * 验证 exp 是否过期  过期返回 true 未过期返回 false
     * 
     * @param jwsObject
     * @return
     */
    public synchronized static boolean exp(JWSObject jwsObject) {
        String exp = getValue(jwsObject, "exp").toString();
        if(StringUtil.isNotBlank(exp)) {
            if(Long.valueOf(exp) <= System.currentTimeMillis()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 重置jwt 过期时间返回新的 jwt encrypt
     * 
     * @param jwsObject
     * @return
     * @throws KeyLengthException 
     */
    public synchronized static String reExp(String jwtTemplate) throws KeyLengthException {
        if(StringUtil.isNotBlank(jwtTemplate)) {
            return encrypt(decrypt(jwtTemplate).getPayload().toJSONObject());
        } 
        return null;
    }
    
    /**
     * 获取 jwsObject  value
     *  
     * @param jwsObject
     * @param key
     * @return
     */
    public static Object getValue(JWSObject jwsObject, String key) {
        Payload payload = jwsObject.getPayload();
        JSONObject jsonObjcet = payload.toJSONObject();
        if(jsonObjcet.containsKey(key)) {
            return jsonObjcet.get(key);
        }
        return null;
    }

}
