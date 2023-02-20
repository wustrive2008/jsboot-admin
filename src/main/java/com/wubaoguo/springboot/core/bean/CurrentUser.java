package com.wubaoguo.springboot.core.bean;

import java.io.Serializable;

/**
 * 
 * @Description: 当前用户
 * @author wubaoguo
 * @mail: wustrive2008@gmail.com
 * @date: 2017年3月27日 下午7:05:57
 * @version: v0.0.1
 */
public class CurrentUser extends AuthBean implements Serializable{
	private static final long serialVersionUID = -7919201192414048616L;

	private String id;
	private String name;
	private String phoneNumber;
	private String account;
	private Integer adminType;
	
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public Integer getAdminType() {
        return adminType;
    }
    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }
	
}
