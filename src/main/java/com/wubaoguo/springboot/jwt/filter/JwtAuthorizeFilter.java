package com.wubaoguo.springboot.jwt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wubaoguo.springboot.conf.JwtConfig;
import com.wubaoguo.springboot.exception.LoginSecurityException;
import com.wubaoguo.springboot.jwt.JwtAuthentication;
import com.wubaoguo.springboot.jwt.JwtAuthorizingRealm;
import com.wubaoguo.springboot.response.StateMap;
import com.wubaoguo.springboot.response.ViewResult;
import com.wubaoguo.springboot.util.WebUtil;

public class JwtAuthorizeFilter implements Filter{

    @Autowired
    private JwtAuthorizingRealm jwtAuthorizingRealm;
    
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();
        ViewResult viewResult = ViewResult.newInstance();
        try {
            //请求终端标识，唯一设备号
            String deviceId = httpRequest.getHeader("deviceId");
            //认证类型 用以扩展多终端认证
            String authType = httpRequest.getHeader("authType");
            
            if(StringUtils.isBlank(deviceId) || StringUtils.isBlank(authType)){
                WebUtil.write(viewResult.fail("请求头信息不全").json(), httpResponse);
                return;
            }
            
            //登录请求，进行登录验证
            if (uri.contains(JwtConfig.LOGIN_URL)) {
                String username = httpRequest.getParameter("username");
                String password = httpRequest.getParameter("password");
                if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
                    WebUtil.write(viewResult.fail("登录参数错误").json(), httpResponse);
                    return;
                }
                JwtAuthentication jwtAuth = new JwtAuthentication();
                jwtAuth.setUsername(username);
                jwtAuth.setPassword(password);
                
                // 执行底层认证
                viewResult = jwtAuthorizingRealm.doAuthentication(jwtAuth);
                
                // 登录状态返回
                WebUtil.write(viewResult.json(), httpResponse);
            } else {
                // 请求地址 非登录地址 进行 hander token 验证
                String accessToken = httpRequest.getHeader("Authorization");
                
                //不强制登录接口
                if(StringUtils.isBlank(accessToken) && isOpenUri(uri)){
                    chain.doFilter(httpRequest, httpResponse);
                    return;
                }
                
                // 授权验证
                JwtAuthentication auth = new JwtAuthentication();
                auth.setAccessToken(accessToken);
                auth.setDeviceId(deviceId);
                auth.setAuthType(authType);
                if(jwtAuthorizingRealm.isAuthentication(auth)){
                    chain.doFilter(httpRequest, httpResponse);
                }else{
                    WebUtil.write(viewResult.state(StateMap.S_403,"认证失败，请重新登录").json(), httpResponse);
                }
            }
        } catch (LoginSecurityException e) {
            e.printStackTrace();
            WebUtil.write(viewResult.state(e.getState(),e.getMessage()).json(), httpResponse);
        }
        
    }

    /**
     * 是否是开放url
     * @param uri
     * @return
     */
    private boolean isOpenUri(String uri) {
        for (String str : JwtConfig.OPEN_URL) {
            if(uri.contains(str)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

}
