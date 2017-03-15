package com.wubaoguo.springboot.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage")
public class ManageController {
	
	//首页
	@RequestMapping(value="index")
	public String index() {
		return "manage/login";
	}
	
	//默认首页
    @RequestMapping(value="")
    public String index2() {
        return "manage/login";
    }
	
	//登录
	@RequestMapping(value="login")
	public String login() {
		return "manage/login";
	}
}
