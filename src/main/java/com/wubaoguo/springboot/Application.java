package com.wubaoguo.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;

@ComponentScan(basePackages={"com.wubaoguo.springboot","org.wustrive.java.dao.jdbc.dao"})  

@SpringBootApplication 
@EnableCaching
public class Application {
	
	public static void main(String[] args){  
        SpringApplication.run(Application.class, args);
    }  
	
	
	/**
	 * 指定错误页面 Java 8的lambda表达式方式
	* @Title: containerCustomizer 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param  参数说明 
	* @return EmbeddedServletContainerCustomizer    返回类型 
	* @throws
	 */
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

	   return (container -> {
	        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401");
	        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
	        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");

	        container.addErrorPages(error401Page, error404Page, error500Page);
	   });
	}
}
