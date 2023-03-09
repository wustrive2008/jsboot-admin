package com.wubaoguo.springboot.core.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 解决前端分离 ajax请求跨域问题
 * @author wubaoguo
 * @mail: wustrive2008@gmail.com
 * @date: 2017年4月18日 下午9:03:03
 * @version: v0.0.1
 */
@WebFilter(urlPatterns = "/*")
public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Headers", "accessToken, deviceId, authType, Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(req, res);
        
    }

    @Override
    public void destroy() {
        
    }

}
