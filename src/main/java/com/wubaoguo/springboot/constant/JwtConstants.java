package com.wubaoguo.springboot.constant;

/**
 * Description: jwt相关常量配置
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2023-3-9 14:24
 */
public class JwtConstants {

    /**
     * 线程共享Map中的key
     */
    public static final String THREAD_CURRENT_USER = "currentUser";

    /**
     * 登录接口地址
     */
    public static final String LOGIN_URL = "/rest/login";

    /**
     * 免验证路由配置
     */
    public static final String[] OPEN_URL = new String[]{"/rest/open"};
}
