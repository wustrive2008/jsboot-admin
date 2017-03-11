package com.wubaoguo.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wubaoguo.springboot.dao.SysPermissionInitMapper;
import com.wubaoguo.springboot.entity.SysPermissionInit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author z77z
 * @since 2017-02-16
 */
@Service
public class SysPermissionInitService extends ServiceImpl<SysPermissionInitMapper, SysPermissionInit>{
	
	@Autowired
	SysPermissionInitMapper sysPermissionInitMapper;
	
	public List<SysPermissionInit> selectAll() {
		return sysPermissionInitMapper.selectAll();
	}
}
