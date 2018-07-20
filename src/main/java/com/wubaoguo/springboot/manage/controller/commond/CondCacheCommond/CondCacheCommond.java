package com.wubaoguo.springboot.manage.controller.commond.CondCacheCommond;

import org.wustrive.java.dao.jdbc.BaseCommond;

public class CondCacheCommond extends BaseCommond {
    private static final long serialVersionUID = 1L;

    private Integer newQuery;

    public Integer getNewQuery() {
        return newQuery;
    }

    public void setNewQuery(Integer newQuery) {
        this.newQuery = newQuery;
    }

    public boolean isNull(){
        return false;
    }
}
