package com.wubaoguo.springboot.core.bean;

import java.io.Serializable;

/**
 * 
 * @Description: 当前角色
 * @author wubaoguo
 * @mail: wustrive2008@gmail.com
 * @date: 2017年3月27日 下午7:06:04
 * @version: v0.0.1
 */
public class CurrentRole implements Serializable{
    private static final long serialVersionUID = -8436272707319248710L;

    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
