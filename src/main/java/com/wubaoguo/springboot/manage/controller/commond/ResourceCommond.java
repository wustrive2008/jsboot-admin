package com.wubaoguo.springboot.manage.controller.commond;


import com.wubaoguo.springboot.manage.controller.commond.CondCacheCommond.CondCacheCommond;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.List;

/**
 * Description: 系统用户-角色-权限 管理模块查询条件
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/20 11:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceCommond extends CondCacheCommond {

    //菜单

    private String menuName;  //菜单名称

    private String parentMenu; //父级菜单id

    //管理员

    private String roleCode;  //角色代码

    private String name;     //用户姓名

    private String phone;    //用户手机号

    private String state;  //账号状态


    @Override
    public List<String> getLikes() {
        return Arrays.asList("menuName", "roleCode");
    }
}
