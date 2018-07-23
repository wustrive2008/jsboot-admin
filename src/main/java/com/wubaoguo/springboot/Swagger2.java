package com.wubaoguo.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * Description: 接口文档
 * 
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 10:52
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
	
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wubaoguo.springboot.rest.controller"))
                .paths(PathSelectors.any())
                .build();
    }
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("jsboot-admin构建RESTful APIs")
                .description("详细内容关注：https://github.com/wustrive2008")
                .termsOfServiceUrl("https://github.com/wustrive2008")
                .contact(new Contact("wubaoguo","https://github.com/wustrive2008/jsboot-admin","wustrive_2008@126.com"))
                .version("1.0")
                .build();
    }
}
