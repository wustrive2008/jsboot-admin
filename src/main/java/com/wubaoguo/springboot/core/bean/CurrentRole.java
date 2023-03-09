package com.wubaoguo.springboot.core.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Description: 
 * 
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2023-3-9 20:29
 * 
 */
@Data
public class CurrentRole implements Serializable{
    private static final long serialVersionUID = -8436272707319248710L;

    private String name;
    private String code;

}
