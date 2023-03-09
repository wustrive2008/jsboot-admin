package com.wubaoguo.springboot.core.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Description: 
 * 
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2023-3-9 20:23
 * 
 */
@Data
public class CurrentUser extends AuthBean implements Serializable{
	private static final long serialVersionUID = -7919201192414048616L;

	private String id;
	private String name;
	private String phoneNumber;
	private String account;
	private Integer adminType;

}
