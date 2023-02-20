package com.wubaoguo.springboot.rest.controller;

import com.google.common.collect.ImmutableMap;
import com.wubaoguo.springboot.core.request.ViewResult;
import com.wubaoguo.springboot.dao.SysAdminMapper;
import com.wubaoguo.springboot.entity.SysAdmin;
import com.wubaoguo.springboot.rest.service.MyBatisTestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:mybatis示例
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:52
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@RestController
@RequestMapping("/mybatis")
public class MyBatisTestController {

    @Autowired
    MyBatisTestService myBatisTestService;

    @Autowired
    SysAdminMapper sysAdminMapper;

    @ApiOperation(value = "mybatis简单操作示例-根据id获取用户信息", notes = "mybatis简单操作示例-根据id获取用户信息")
    @RequestMapping(value = {"getUserById/{id}"}, method = RequestMethod.GET)
    public String getUserById(@PathVariable String id) {
        SysAdmin admin = myBatisTestService.getUserById(id);
        return ViewResult.newInstance().setData(admin.getBeanValues()).json();
    }

    @ApiOperation(value = "mybatis复杂操作示例-根据id获取用户角色名称", notes = "mybatis复杂操作示例-根据id获取用户角色名称")
    @RequestMapping(value = {"getRoleNameById/{id}"}, method = RequestMethod.GET)
    public String getRoleNameById(@PathVariable String id) {
        String roleName = myBatisTestService.getRoleNameById(id);
        return ViewResult.newInstance().setData(ImmutableMap.of("roleName", roleName)).json();
    }

    @ApiOperation(value = "mybatis-Mapper示例-根据id查询实体", notes = "mybatis-Mapper示例-根据id查询实体")
    @RequestMapping(value = {"selectByPrimaryKey/{id}"}, method = RequestMethod.GET)
    public String selectByPrimaryKey(@PathVariable String id) {
        com.wubaoguo.springboot.model.SysAdmin admin = sysAdminMapper.selectByPrimaryKey(id);
        return ViewResult.newInstance().setData(ImmutableMap.of("name", admin.getName())).json();
    }
}
