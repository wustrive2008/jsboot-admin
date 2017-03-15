package com.wubaoguo.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorPageController {

	@RequestMapping(value = { "404" })
	public String err404() {
		return "/common/404";
	}
	
	@RequestMapping(value = { "403" })
    public String err403() {
        return "/common/403";
    }
	
	@RequestMapping(value = { "500" })
    public String err500() {
        return "/common/500";
    }
}
