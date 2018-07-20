package com.wubaoguo.springboot.manage.controller.commond;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * 后台管理模块查询条件缓存
 * Description:
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2017/8/10 9:47
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class QueryCommondSession {


    public static DgztcBaseCommond getQueryCommond(String key) {
        return (DgztcBaseCommond) getSession().getAttribute(key);
    }

    public static void putQueryCommond(String key,DgztcBaseCommond commond){
        getSession().setAttribute(key,commond);
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }


    /**
     * 验证是否使用缓存的查询条件，满足以下条件使用缓存条件
     * 1. 不是点击查询按钮
     * 2. 新的查询条件为空
     * 3. 第一页数据
     * @param key
     * @param commond
     * @return
     */
    public static DgztcBaseCommond validateCommond(String key,DgztcBaseCommond commond){
        if(null == commond.getNewQuery() && commond.isNull() && commond.getPageNumber() < 2){
            DgztcBaseCommond cacheCommond = QueryCommondSession.getQueryCommond(key);
            if(null != cacheCommond){
                commond = cacheCommond;
            }
        }
        return commond;
    }
}
