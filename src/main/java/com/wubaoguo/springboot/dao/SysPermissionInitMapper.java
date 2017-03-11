package com.wubaoguo.springboot.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wubaoguo.springboot.entity.SysPermissionInit;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author z77z
 * @since 2017-02-16
 */
public interface SysPermissionInitMapper extends BaseMapper<SysPermissionInit> {

	List<SysPermissionInit> selectAll();

}