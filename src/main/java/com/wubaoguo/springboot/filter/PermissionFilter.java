package com.wubaoguo.springboot.filter;

import com.wubaoguo.springboot.constant.ShiroConstants;
import com.wubaoguo.springboot.manage.service.ManageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Description:页面访问权限控制
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2017/9/6 16:07
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class PermissionFilter implements Filter {

    private String redirectUrl = "";

    @Autowired
    private ManageService manageService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //获取请求url
        String uri = httpRequest.getRequestURI();
        if (isStaticFile(uri)) {
            chain.doFilter(request, response);
        }
        String roleCode = ShiroConstants.getCurrentRoleCode();
        if (uri.lastIndexOf("home.html") > 0 || (uri.lastIndexOf("main.html") < 0 && uri.lastIndexOf("table") < 0) || manageService.hasPermission(roleCode, uri)) {
            chain.doFilter(request, response);
        } else {
            //WebUtils.issueRedirect(request, response, redirectUrl);
            String loginoutUrl = ((HttpServletRequest) request).getContextPath() + this.redirectUrl;
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<script>");
            out.println("window.open ('" + loginoutUrl + "', '_top') ");
            out.println("</script>");
            out.println("</html>");
        }
    }

    /**
     * 判断是否为静态文件
     *
     * @param uri
     * @return
     */
    private boolean isStaticFile(String uri) {
        String suffix = StringUtils.substringAfterLast(uri, ".").toLowerCase();
        return SubThreadContent.ignoreSuffix.contains(suffix);
    }

    @Override
    public void destroy() {

    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
