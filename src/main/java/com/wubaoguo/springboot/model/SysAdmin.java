package com.wubaoguo.springboot.model;

import lombok.Data;

/**
 * Description: mubatis实体示例
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 15:41
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
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