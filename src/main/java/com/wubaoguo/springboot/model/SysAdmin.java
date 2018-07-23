package com.wubaoguo.springboot.model;

import lombok.Data;

@Data
public class SysAdmin {
    private String id;

    private String name;

    private String phoneNumber;

    private String account;

    private Integer onlineState;

    private String nickName;

    private String password;

    private String departId;

    private Integer isActivity;

    private Long addTime;

}