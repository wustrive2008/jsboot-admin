package com.wubaoguo.springboot.manage.controller;

import com.wubaoguo.springboot.constant.ShiroConstants;
import com.wubaoguo.springboot.core.bean.CurrentUser;
import com.wubaoguo.springboot.core.request.ViewResult;
import com.wubaoguo.springboot.manage.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Description: 主页功能controller
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:07
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Controller
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private ManageService manageService;

    //首页
    @RequestMapping(value = "index")
    public String index() {
        return "redirect:/manage/login";
    }

    //默认首页
    @RequestMapping(value = "")
    public String index2() {
        return "redirect:/manage/login";
    }

    //登录页
    @RequestMapping(value = "login")
    public String login() {
        return "/manage/login";
    }

    //登录请求
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String postLogin() {
        return "/manage/login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap map) {
        CurrentUser currentUser = ShiroConstants.getCurrentUser();
        map.addAttribute("menus", manageService.findSysResourcesByRole(ShiroConstants.getCurrentRoleCode(), currentUser.getId()));
        return "/manage/home";
    }

    /**
     * 跳转到黑板
     */
    @RequestMapping("/blackboard")
    public String blackboard() {
        return "/manage/blackboard";
    }

    /**
     * 根据指定角色编码获取惨菜单资源项
     *
     * @param roleCode
     * @param map
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/menu/{roleCode}", method = RequestMethod.GET)
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
    @RequestMapping(value = "/loginout", method = RequestMethod.GET)
    public String logout() {
        return "/manage/login";
    }

    /**
     * 密码更新页面
     *
     * @return
     */
    @RequestMapping(value = "/updatepwdMain", method = RequestMethod.GET)
    public String updatepwdPage() {
        return "/manage/updatepwdMain";
    }

    /**
     * 修改用户自己的密码
     *
     * @param pwd
     * @param oldpwd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatepwd", method = RequestMethod.POST)
    public String updateUserPwd(String pwd, String oldpwd) {
        return ViewResult.newInstance()
                .state(manageService.updateUserPwd(ShiroConstants.getCurrentUser().getAccount(), pwd, oldpwd))
                .json();
    }

    /**
     * 锁屏页面
     *
     * @return
     */
    @RequestMapping(value = "/lockscreen", method = RequestMethod.GET)
    public String lockscreen(ModelMap map) {
        map.addAttribute("username", ShiroConstants.getCurrentUser().getAccount());
        return "/manage/lockscreen";
    }
}
