package com.wubaoguo.springboot.core.reqlimit.annotation;

import com.wubaoguo.springboot.common.redis.impl.ReqLimitCache;
import com.wubaoguo.springboot.core.filter.ThreadContentFilter;
import com.wubaoguo.springboot.core.reqlimit.exception.RequestLimitException;
import com.wubaoguo.springboot.core.request.ViewResult;
import com.wubaoguo.springboot.util.WebUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Description: 请求频率限制
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2017/11/4 12:08
 */
@Aspect
@Component
public class RequestLimitContract {
    private static final Logger log = LoggerFactory.getLogger(RequestLimitContract.class);

    @Autowired
    ReqLimitCache reqLimitCache;

    @Before("@annotation(limit)")
    public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) throws RequestLimitException {
        OutputStream writer = null;
        try {
            HttpServletRequest request = ThreadContentFilter.request();
            HttpServletResponse response = ThreadContentFilter.response();

            String ip = WebUtil.getClientIp(request);
            String url = request.getRequestURL().toString();
            String key = "req_limit_".concat(url).concat(ip);
            long count = reqLimitCache.increment(key, limit.time());
            if (count > limit.count()) {
                writer = response.getOutputStream();
                log.warn("用户IP[" + ip + "]在" + limit.time() + "秒内,访问地址[" + url + "]超过了限定的次数[" + limit.count() + "],访问临时受限!");
                String res = ViewResult.newInstance().fail("操作太快了,请稍后再试").json();
                response.setContentType("application/json");
                writer.write(res.getBytes("utf-8"));
                writer.flush();
            }
        } catch (Exception e) {
            log.error("接口ip限制发生异常: ", e);
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error("关闭response时异常: ", e);
                }
            }
        }
    }


}
