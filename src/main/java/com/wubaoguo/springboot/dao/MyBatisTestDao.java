package com.wubaoguo.springboot.dao;

import com.wubaoguo.springboot.entity.SysAdmin;
import org.apache.ibatis.annotations.*;

/**
 * Description: mybatis示例-注解
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 17:09
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Mapper
public interface MyBatisTestDao {

    @Select("SELECT * FROM sys_admin WHERE id = #{id}")
    SysAdmin getUserById(@Param("id") String id);

    @Select("SELECT c.name FROM sys_admin a,sys_admin_role b,sys_role c where a.id =b.admin_id and b.code = c.code and a.id = #{id}")
    String getRoleNameById(String id);

    @Insert("insert into sys_admin(id,name,phone_number) values(#{uuid},#{name},#{phone_number})")
    Integer addSysAdmin(@Param("uuid") String uuid, @Param("name") String name, @Param("phone_number") String phoneNumber);
}
