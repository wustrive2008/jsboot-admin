package com.wubaoguo.springboot.manage.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wustrive.java.common.util.ConvertUtil;
import org.wustrive.java.core.request.ViewResult;

import com.wubaoguo.springboot.constant.ShiroConstants;
import com.wubaoguo.springboot.entity.SysAdmin;
import com.wubaoguo.springboot.entity.SysResources;
import com.wubaoguo.springboot.entity.SysRole;
import com.wubaoguo.springboot.manage.service.ResourceService;

@Controller
@RequestMapping("/manage/resource")
public class ResourceController {

	@Autowired
	ResourceService resourceService;
	
	/**
	 * 菜单主页
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/menumain", method=RequestMethod.GET)
	public String menuMain(ModelMap map) {
		map.put("one_level_menus", resourceService.findSysResource("root"));
		return "/manage/resource/menumain";
	}
	
	/**
	 * 菜单主表格
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/menutable", method=RequestMethod.GET)
	public String menutable(ModelMap map) {
		map.put("datas", resourceService.findSysResource());
		return "/manage/resource/menutable";
	}
	
	/**
	 * 保存 菜单
	 * 
	 * @param sysResources
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/menumain", method=RequestMethod.POST)
	public String addMenuMain(SysResources sysResources) {
		return ViewResult.newInstance().state(resourceService.saveSysResource(sysResources)).json();
	}
	
	/**
	 * 预添加菜单 表单
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/menuform", method=RequestMethod.GET)
	public String menuForm(ModelMap map) {
		map.put("one_level_menus", resourceService.findSysResource("root"));
		return "/manage/resource/menuform";
	}
	/**
	 * 预编辑form 表单
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/menuform/{id}", method=RequestMethod.GET)
	public String getMenuForm(@PathVariable("id") String id, ModelMap map) {
		map.put("one_level_menus", resourceService.findSysResource("root"));
		map.put("data", resourceService.getSysResource(id));
		return "/manage/resource/menuform";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/menuinline", method=RequestMethod.POST)
	public String update(String id, String column, String value) {
		return resourceService.updateSysResources(id, column, value);
	}
	
	/**
	 * 角色主页
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/rolemain", method=RequestMethod.GET)
	public String roleMain(ModelMap map) {
		return "/manage/resource/rolemain";
	}
	
	/**
	 * 角色 列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/roletable", method=RequestMethod.GET)
	public String roletable(ModelMap map) {
		map.put("datas", resourceService.findSysRole());
		return "/manage/resource/roletable";
	}
	/**
	 * 添加角色
	 * 
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rolemain", method=RequestMethod.POST)
	public String addRoleMain(SysRole sysRole, String[] resourceIds) {
		return ViewResult.newInstance().state(resourceService.saveSysRole(sysRole, resourceIds)).json();
	}
	
	/**
	 * 预添加菜单 表单
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/roleform", method=RequestMethod.GET)
	public String roleForm(ModelMap map) {
		map.put("sysResourcesList", resourceService.findSysResourceZtree());
		return "/manage/resource/roleform";
	}
	
	/**
	 * 预添加菜单 表单
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/roleform/{id}", method=RequestMethod.GET)
	public String getRoleForm(@PathVariable("id") String id, ModelMap map) {
		Map<String, Object> roleMap = resourceService.getSysRole(id);
		map.put("data", roleMap);
		map.put("selectList", resourceService.findSysRoleResourcesByRoleCode(ConvertUtil.obj2Str(roleMap.get("code"))));
		map.put("sysResourcesList", resourceService.findSysResourceZtree());
		return "/manage/resource/roleform";
	}
	
	@ResponseBody
	@RequestMapping(value = "/roleinline", method=RequestMethod.POST)
	public String roleinline(String id, String column, String value) {
		return resourceService.updateSysRole(id, column, value);
	}
	
	/**
	 * 角色主页
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/adminmain", method=RequestMethod.GET)
	public String adminmain() {
		return "/manage/resource/adminmain";
	}
	
	@ResponseBody
	@RequestMapping(value = "/adminmain", method=RequestMethod.POST)
	public String addAdminmain(SysAdmin sysAdmin, String[] roleCodes) {
		return ViewResult.newInstance().state(resourceService.saveSysAdmin(sysAdmin, roleCodes)).json();
	}
	
	/**
	 * 管理员
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/admintable", method=RequestMethod.GET)
	public String admintable(ModelMap map) {
		map.put("datas", resourceService.findSysAdmin());
		return "/manage/resource/admintable";
	}
	
	@RequestMapping(value = "/adminform", method=RequestMethod.GET)
	public String adminform(ModelMap map) {
		map.put("roles", resourceService.findActivitySysRole());
		return "/manage/resource/adminform";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/adminreset/{id}", method=RequestMethod.POST)
	public String adminreset(@PathVariable("id") String id, String password) {
		return ViewResult.newInstance().state(resourceService.doResetAdminPassword(id, password)).json();
	}
	
	@RequestMapping(value = "/adminform/{id}", method=RequestMethod.GET)
	public String getAdminform(@PathVariable("id") String id, ModelMap map) {
		map.put("data", resourceService.getSysAdmin(id));
		map.put("roles", resourceService.findActivitySysRole());
		return "/manage/resource/adminform";
	}
	
	@RequestMapping(value = "/rolechange/{roleCode}", method=RequestMethod.GET)
	public String roleChange(@PathVariable("roleCode") String roleCode) {
		ShiroConstants.getSession().setAttribute(ShiroConstants.SESSION_CURRENT_ROLE, roleCode);
		return "redirect:/manage/home";
	}
}
