package com.wubaoguo.springboot.filter;


import com.wubaoguo.springboot.constant.ShiroConstants;
import com.wubaoguo.springboot.core.request.StateMap;
import com.wubaoguo.springboot.core.request.ViewResult;
import com.wubaoguo.springboot.util.WebUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: 管理后台表单提交请求拦截
 * 
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:11
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class AjaxFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {
            if (WebUtil.isAjaxRequest(httpRequest)) {
                WebUtil.write(
                        ViewResult.newInstance()
                                .state(StateMap.S_AUTH_TIMEOUT, "您尚未登录或登录时间过长,请重新登录!").json(),
                        httpResponse);
            } else {
                return super.onAccessDenied(httpRequest, httpResponse);
            }
        } else {
            if (WebUtil.isAjaxRequest(httpRequest)) {
                WebUtil.write(
                        ViewResult.newInstance().state(StateMap.S_AUTH_ERROR, "您没有足够的权限执行该操作!")
                                .json(), httpResponse);
            } else {
                String unauthorizedUrl = ShiroConstants.SHIRO_UNAUTH_URL;
                if (StringUtils.hasText(unauthorizedUrl)) {
                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
                } else {
                    WebUtils.toHttp(response).sendError(StateMap.S_AUTH_ERROR);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
            return true;// 已经登录过
        }
        return super.onPreHandle(request, response, mappedValue);
    }

}
