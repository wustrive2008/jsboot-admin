package com.wubaoguo.springboot.manage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wustrive.java.core.bean.CurrentUser;
import org.wustrive.java.core.exception.BusinessException;

import com.wubaoguo.springboot.constant.ShiroConstants;
import com.wubaoguo.springboot.manage.service.ManageService;

@Controller
@RequestMapping("/manage")
public class ManageController {
    
    @Autowired
    private ManageService manageService;
	
	//首页
	@RequestMapping(value="index")
	public String index() {
		return "redirect:/manage/login";
	}
	
	//默认首页
    @RequestMapping(value="")
    public String index2() {
        return "redirect:/manage/login";
    }
	
	//登录页
	@RequestMapping(value="login")
	public String login() {
		return "/manage/login";
	}
	
	//登录请求
    @RequestMapping(value="login",method=RequestMethod.POST)
    public String postLogin() {
        return "/manage/login";
    }
    
    @RequestMapping(value = "/home", method=RequestMethod.GET)
    public String home(HttpServletRequest request, ModelMap map) {
        return "/manage/home";
    }
    
    /**
     * 根据指定角色编码获取惨菜单资源项
     * 
     * @param roleCode
     * @param map
     * @param request
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/menu/{roleCode}", method=RequestMethod.GET)
    public String menu(@PathVariable("roleCode") String roleCode, ModelMap map) {
        CurrentUser currentUser = ShiroConstants.getCurrentUser();
        map.addAttribute("menus", manageService.findSysResourcesByRole(roleCode, currentUser.getId()));
        return "/manage/menu";
    }
    
    /**
     * 登出页面
     * 
     * @return
     */
    @RequestMapping(value = "/loginout", method=RequestMethod.GET)
    public String logout() {
        return "/manage/login";
    }
}
