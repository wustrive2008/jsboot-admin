package com.wubaoguo.springboot.manage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wustrive.java.core.request.ViewResult;

import com.wubaoguo.springboot.entity.SysDictionary;
import com.wubaoguo.springboot.manage.service.SysDictionaryService;

/**
 * @Title: SysDictionaryController.java
 * @ClassName: com.vcxx.manage.controller.SysDictionaryController
 * @Description: 系统数据字典
 *
 * Copyright  2015-2016 维创盈通 - Powered By 研发中心 V1.0.0
 * @author zhaoqt
 * @date Apr 7, 2016 7:05:01 PM
 */
@Controller
@RequestMapping("/manage/sysdictionary")
public class SysDictionaryController {
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String form(@PathVariable("id") String id, ModelMap map) {
		SysDictionary sysDictionary = new SysDictionary();
		if(!"none".equals(id)) {
			sysDictionary = sysDictionary.setId(id).queryForBean();
		}
		map.addAttribute("entity", sysDictionary);
		return "/manage/dictionary/form";
	} 
	
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String main(ModelMap map) {
		return "/manage/dictionary/main";
	}
	
	@RequestMapping(value="/table", method=RequestMethod.GET)
	public String findSysDictionary(ModelMap map) {
		map.put("entitys", sysDictionaryService.findSysDictionary());
		return "/manage/dictionary/table";
	}
	
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public String save(SysDictionary sysDictionary) {
		return ViewResult.newInstance().state(sysDictionaryService.saveSysDictionary(sysDictionary)).json();
	} 
	
	@ResponseBody
	@RequestMapping(value = "/inline", method=RequestMethod.POST)
	public String update(String id, String column, String value) {
		return sysDictionaryService.updateSysDictionary(id, column, value);
	}
}
