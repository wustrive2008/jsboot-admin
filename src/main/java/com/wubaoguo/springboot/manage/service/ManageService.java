package com.wubaoguo.springboot.manage.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wustrive.java.common.secret.MD5Encrypt;
import org.wustrive.java.common.util.StringUtil;
import org.wustrive.java.core.bean.CurrentRole;
import org.wustrive.java.core.request.BaseState;
import org.wustrive.java.dao.jdbc.dao.BaseDao;

import com.google.common.collect.ImmutableMap;
import com.wubaoguo.springboot.constant.ShiroConstants;
import com.wubaoguo.springboot.entity.SysAdmin;
import com.wubaoguo.springboot.entity.SysResources;
import com.wubaoguo.springboot.manage.domain.SearchResults;

@Service
public class ManageService {
		
	@Autowired
	BaseDao baseDao;
	
	@SuppressWarnings("unchecked")
	public List<SysResources> findSysResourcesByRole(String roleCode, String userId) {
		Object current_resource = SecurityUtils.getSubject().getSession().getAttribute(ShiroConstants.SESSION_CURRENT_RESOURCE+roleCode);
		if(current_resource != null && current_resource instanceof List) {
			return (List<SysResources>) current_resource;
		}
		if(StringUtil.isNotBlank(roleCode)) {
			StringBuilder querySql = new StringBuilder("SELECT re.* FROM sys_admin a, sys_admin_role r, sys_role_resources rrl, sys_resources re ");
			querySql.append("WHERE rrl.role_code = r.`code` AND re.is_activity = 1 AND rrl.resources_id = re.id AND a.id = r.admin_id ");
			querySql.append("AND (r.`code` =:role_code OR r.code = 'all') ");
			querySql.append("AND a.id = :user_id ");
			querySql.append("ORDER BY re.sort ASC");
			List<SysResources> list = baseDao.queryForBeanList(querySql.toString(), ImmutableMap.of("role_code", roleCode, "user_id", userId), new SysResources());
			// 调整数据结构移除主要list中子节点数据 全部子节点临时存储集合
			List<SysResources> allChildrens = new ArrayList<SysResources>();
			for(SysResources parent : list) {
				List<SysResources> childrens = new ArrayList<SysResources>();
				for(SysResources children : list) {
					if(parent.getId().equals(children.getGroup_id())) {
						childrens.add(children);
						allChildrens.add(children);
					}
				}
				// 整理子节点到父节点list结构中
				parent.setChildrens(childrens);
			}
			// 子节点数据移除
			list.removeAll(allChildrens);
			SecurityUtils.getSubject().getSession().setAttribute(ShiroConstants.SESSION_CURRENT_RESOURCE, list);
			return list;
		} else {
			return null;
		}
	}
	
	public List<SearchResults> searchSysResource(String keywords) {
		CurrentRole currentRole = ShiroConstants.getCurrentRoles().get(0);
		Map<String, Object> paramMap = new HashMap<String, Object>(3);
		StringBuilder querySql = new StringBuilder("SELECT re.* FROM sys_admin a, sys_admin_role r, sys_role_resources rrl, sys_resources re ");
		querySql.append("WHERE rrl.role_code = r.`code` AND re.is_activity = 1 AND rrl.resources_id = re.id AND a.id = r.admin_id ");
		querySql.append("AND (r.`code` =:role_code OR r.code = 'all') ");
		querySql.append("AND re.uri != 'none' ");
		querySql.append("AND a.id = :user_id ");
		querySql.append("AND re.`menu_name` like :keywords ");
		paramMap.put("keywords", "%" + keywords + "%");
		paramMap.put("role_code", currentRole.getCode());
		paramMap.put("user_id", ShiroConstants.getCurrentUser().getUserId());
		List<SysResources> sysResourcess = baseDao.queryForBeanList(querySql.toString(), paramMap, new SysResources());
		List<SearchResults> rList = new ArrayList<SearchResults>();
		for(SysResources sysResources : sysResourcess) {
			StringBuilder details = new StringBuilder();
			// 不查询父节点菜单
			if("root".equals(sysResources.getGroup_id())) {
				details.append("上级菜单[root]");
			} else {
				String parent_name = new SysResources().setId(sysResources.getGroup_id()).queryForBean().getMenu_name();
				details.append("上级菜单 [" + parent_name + "]");
			}
			rList.add(new SearchResults(sysResources.getMenu_name(), sysResources.getUri(), details.toString()));
		}
		return rList;
	}
	
	/**
	 * 修改用户密码
	 * @param account 账号
	 * @param pwd 明文新密码
	 * @param oldpwd 明文旧密码
	 */
	public BaseState updateUserPwd(String account, String pwd, String oldpwd){
		if(StringUtil.isBlank(account)) {
			return new BaseState(BaseState.S_AUTH_ERROR,"账号不正确");
		}
		if(StringUtil.isBlank(pwd)) {
			return new BaseState(BaseState.S_AUTH_ERROR,"新密码未正确填写");
		}
		if(StringUtil.isBlank(oldpwd)) {
			return new BaseState(BaseState.S_AUTH_ERROR,"旧密码未正确填写");
		}
		
		SysAdmin baseUser = new SysAdmin().setAccount(account).queryForBean();
		if(baseUser==null || StringUtil.isBlank(baseUser.getId())) {
			return new BaseState(BaseState.S_AUTH_ERROR,"帐号不存在");
		}
		if(ShiroConstants.IS_ACTIVITY_NO.equals(baseUser.getIs_activity())) {
			return new BaseState(BaseState.S_AUTH_ERROR,"当前登录账号已被禁用");
		}
		
		String passwordMD5 = MD5Encrypt.MD5Encode(oldpwd);
		// 验证旧密码是否正确
		if(baseUser.getPassword().equals(passwordMD5)) {
			// 验证成功，在验证新密码
			String sql="update sys_admin set password=:pwd where account=:account";
			Map<String,String> paramMap=new HashMap<String,String>(2);
			paramMap.put("pwd", MD5Encrypt.MD5Encode(pwd));
			paramMap.put("account", account);
			baseDao.execute(sql, paramMap);
			return new BaseState();
		} else {
			return new BaseState(BaseState.S_AUTH_ERROR,"旧密码错误");
		}
		
	}
}
