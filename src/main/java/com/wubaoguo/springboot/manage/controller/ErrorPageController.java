package com.wubaoguo.springboot.manage.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 错误页面
 * @Description: TODO
 * @author wubaoguo
 * @mail: wustrive2008@gmail.com
 * @date: 2017年3月17日 上午9:59:55
 * @version: v0.0.1
 */
@Controller
public class ErrorPageController implements ErrorController {
    private static final String ERROR_PATH = "/error";  
    
	@RequestMapping(value = ERROR_PATH)
	public String err404(HttpServletResponse response) {
	    switch(response.getStatus()){
	        case 404:
	            return "/common/error/404";
	        case 403:
	            return "/common/error/403";
	        default:
	            return "/common/error/500";
	    }
	}
	
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
