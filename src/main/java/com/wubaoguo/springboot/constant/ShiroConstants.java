package com.wubaoguo.springboot.constant;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.wustrive.java.core.bean.CurrentRole;
import org.wustrive.java.core.bean.CurrentUser;

/**
 * Description: shiro相关常量
 * 
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:11
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class ShiroConstants {
    
    public static final String SESSION_CURRENT_RESOURCE = "current_esource";
    
    public static final Integer IS_ACTIVITY_YES = 1; // 正常
    
    public static final Integer IS_ACTIVITY_NO = 2; // 作废  删除
    
    public static final String SESSION_CURRENT_USER = "currentUser";
    
    public static final String SESSION_CURRENT_ROLES = "currentRoles";
    
    public static final String SESSION_CURRENT_ROLE = "current_role";
    
    public static final String THREAD_CURRENT_USER = "currentUser";
    
    public static final String SESSION_TOKEN = "token";
    
    public static final String SESSION_SYS_CONFIG = "sys_config";
    
    public static final String SHIRO_UNAUTH_URL = "/manage/login";
    
    public static Long currentTimeSecond() {
        return  System.currentTimeMillis() / 1000;
    }
    
    public static CurrentUser getCurrentUser() {
        return (CurrentUser)SecurityUtils.getSubject().getSession().getAttribute(ShiroConstants.SESSION_CURRENT_USER);
    }
    
    @SuppressWarnings("unchecked")
    public static List<CurrentRole> getCurrentRoles() {
        return (List<CurrentRole>)SecurityUtils.getSubject().getSession().getAttribute(ShiroConstants.SESSION_CURRENT_ROLES);
    }
    
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }
    
    public static String getCurrentRoleCode() {
        return (String)getSession().getAttribute(SESSION_CURRENT_ROLE);
    }
}
