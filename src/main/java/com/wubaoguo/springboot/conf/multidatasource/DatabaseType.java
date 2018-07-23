package com.wubaoguo.springboot.conf.multidatasource;

/**
 * Description:列出数据源类型
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 16:30
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public enum DatabaseType {

    master("write"), slave("read");

    private String name;

    DatabaseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DatabaseType{" +
                "name='" + name + '\'' +
                '}';
    }
}
