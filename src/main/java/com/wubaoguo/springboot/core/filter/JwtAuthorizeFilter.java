package com.wubaoguo.springboot.core.filter;

import com.nimbusds.jose.JWSObject;
import com.wubaoguo.springboot.constant.JwtConstants;
import com.wubaoguo.springboot.core.bean.AuthBean;
import com.wubaoguo.springboot.core.exception.BusinessException;
import com.wubaoguo.springboot.core.exception.LoginSecurityException;
import com.wubaoguo.springboot.core.request.StateMap;
import com.wubaoguo.springboot.core.request.ViewResult;
import com.wubaoguo.springboot.core.util.WebUtil;
import com.wubaoguo.springboot.core.util.JWTUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: jwt过滤器
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2023-3-9 14:27
 */
@WebFilter(urlPatterns = "/rest/*")
public class JwtAuthorizeFilter implements Filter {

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
            String token = httpRequest.getHeader("accessToken");

            if (StringUtils.isBlank(token)) {
                WebUtil.write(viewResult.fail("header中token不能为空").json(), httpResponse);
                return;
            }
            //不强制登录接口
            if (StringUtils.isBlank(token) && isOpenUri(uri)) {
                chain.doFilter(httpRequest, httpResponse);
                return;
            }

            // 授权验证
            AuthBean auth = new AuthBean();
            auth.setAccessToken(token);
            if (isAuthentication(auth)) {
                chain.doFilter(httpRequest, httpResponse);
            } else {
                WebUtil.write(viewResult.state(StateMap.S_AUTH_ERROR, "认证失败，请重新登录").json(), httpResponse);
            }
        } catch (LoginSecurityException e) {
            e.printStackTrace();
            WebUtil.write(viewResult.state(e.getState(), e.getMessage()).json(), httpResponse);
        }

    }

    /**
     * 是否是开放url
     *
     * @param uri
     * @return
     */
    private boolean isOpenUri(String uri) {
        for (String str : JwtConstants.OPEN_URL) {
            if (uri.contains(str)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    private boolean isAuthentication(AuthBean auth) throws LoginSecurityException {
        try {
            String token = auth.getAccessToken();

            JWSObject jwsObject = JWTUtil.decrypt(token);
            // 验证 token 信息是否有效 并验证 token 是否过期
            if (JWTUtil.verify(jwsObject)) {
                if (!JWTUtil.exp(jwsObject)) {
                    // token 信息过期，需客户端重新获取
                    throw new LoginSecurityException(StateMap.S_AUTH_TIMEOUT, "登录已过期，请重新登录");
                }
                // 添加用户信息到当前线程
                ThreadContentFilter.addData(JwtConstants.THREAD_CURRENT_USER, auth);
                return true;
            }
        } catch (BusinessException e) {
            throw new LoginSecurityException(e.getState(), e.getMessage());
        }
        // 验证无效token
        return false;
    }
}
