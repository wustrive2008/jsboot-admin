package com.wubaoguo.springboot.dao;

import com.wubaoguo.springboot.model.SysAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Description: mybaits之Mapper-xml方式示例
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 15:41
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Mapper
public interface SysAdminMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysAdmin record);

    int insertSelective(SysAdmin record);

    SysAdmin selectByPrimaryKey(@Param("id") String id);

    int updateByPrimaryKeySelective(SysAdmin record);

    int updateByPrimaryKey(SysAdmin record);

}