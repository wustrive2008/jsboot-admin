package com.wubaoguo.springboot.conf.multidatasource;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:动态处理数据源，根据命名区分
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 16:44
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("execution(* com.wubaoguo.springboot.dao.*.*(..))")//切点
    public void aspect() {

    }

    @Before("aspect()")
    public void before(JoinPoint point) { //在指定切点的方法之前执行
        String className = point.getTarget().getClass().getName();
        String method = point.getSignature().getName();
        String args = StringUtils.join(point.getArgs(), ",");
        logger.info("className:{}, method:{}, args:{} ", className, method, args);
        try {
            for (DatabaseType type : DatabaseType.values()) {
                List<String> values = DynamicDataSource.METHOD_TYPE_MAP.get(type);
                for (String key : values) {
                    if (method.startsWith(key)) {
                        logger.info(">>{} 方法使用的数据源为:{}<<", method, key);
                        DatabaseContextHolder.setDatabaseType(type);
                        DatabaseType types = DatabaseContextHolder.getDatabaseType();
                        logger.info(">>{}方法使用的数据源为:{}<<", method, types);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
