package com.wubaoguo.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorPageController {

	@RequestMapping(value = { "404" })
	public String put() {
		return "404";
	}
}
