package com.wubaoguo.springboot.rest.service;

import com.wubaoguo.springboot.entity.SysAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * Description:mybatis示例-注解
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:52
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Mapper
@Component
public interface MyBatisTestService {

    @Select("SELECT * FROM sys_admin WHERE id = #{id}")
    SysAdmin getUserById(@Param("id") String id);

    @Select("SELECT c.name FROM sys_admin a,sys_admin_role b,sys_role c where a.id =b.admin_id and b.code = c.code and a.id = #{id}")
    String getRoleNameById(String id);
}
