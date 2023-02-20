package com.wubaoguo.springboot.manage.controller;

import com.wubaoguo.springboot.core.request.ViewResult;
import com.wubaoguo.springboot.manage.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage/sysconfig")
public class SysConfigController {

	@Autowired
	SysConfigService sysConfigService;
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public String currentConfig(String item_key, String value) {
		boolean flag = sysConfigService.save(item_key, value);
		if(flag) {
			// 更新 session 范围数据
			sysConfigService.initSysConfigToSession(null);
		}
		return ViewResult.newInstance().success().json();
	}
}
