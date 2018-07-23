package com.wubaoguo.springboot.conf.multidatasource;

/**
 * Description:保存一个线程安全的DatabaseType容器
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 16:32
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class DatabaseContextHolder {

    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseType type) {
        contextHolder.set(type);
    }

    public static DatabaseType getDatabaseType() {
        return contextHolder.get();
    }
}
