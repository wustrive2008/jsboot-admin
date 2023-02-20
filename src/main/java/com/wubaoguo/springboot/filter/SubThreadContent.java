package com.wubaoguo.springboot.filter;


import com.google.common.collect.ImmutableSet;
import com.wubaoguo.springboot.core.filter.ThreadContentFilter;
import org.apache.commons.lang.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: ThreadLocal 解决线程变量副本问题
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:12
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class SubThreadContent extends ThreadContentFilter {

    /**
     * 静态文件后缀名
     */
    public static ImmutableSet<String> ignoreSuffix = new ImmutableSet.Builder<String>().add("jpg", "jpeg", "ico", "txt", "doc", "ppt", "xls", "pdf", "gif", "png",
            "bmp", "css", "js", "swf", "flv", "mp3", "htc", "woff", "woff2", "ttf").build();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        if (isStaticFile(request)) {
            filterChain.doFilter(request, response);
        } else {
            SubThreadContent.setThreadObject(new ThreadObject((HttpServletRequest) request, (HttpServletResponse) response));
            filterChain.doFilter(request, response);
            SubThreadContent.setThreadObject(null);
        }
    }

    /**
     * 判断请求的是否是静态文件
     *
     * @param request
     * @return
     */
    private boolean isStaticFile(ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            String uri = ((HttpServletRequest) request).getRequestURI();
            String suffix = StringUtils.substringAfterLast(uri, ".").toLowerCase();
            return ignoreSuffix.contains(suffix);
        }
        // request 无法解析时 暂时定为静态文件处理
        return true;
    }

}
