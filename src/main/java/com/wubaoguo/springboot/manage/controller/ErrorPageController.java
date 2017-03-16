package com.wubaoguo.springboot.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorPageController {

	@RequestMapping(value = { "404" })
	public String err404() {
		return "/common/error/404";
	}
	
	@RequestMapping(value = { "403" })
    public String err403() {
        return "/common/error/403";
    }
	
	@RequestMapping(value = { "500" })
    public String err500() {
        return "/common/error/500";
    }
}
