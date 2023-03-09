package com.wubaoguo.springboot.conf;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
* @ClassName: FreeMarkerConfig
* @Description: freemarker配置
* @author wustrive
* @date 2017年3月9日 下午9:19:06 
*
 */
@Configuration
public class FreeMarkerConfig {
	@Autowired  
    protected freemarker.template.Configuration configuration;
	
	//ftl渲染视图
	@Autowired  
    protected org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver resolver;  
	
	//jsp渲染视图
	//@Autowired
    //protected org.springframework.web.servlet.view.InternalResourceViewResolver springResolver;
	
	public static Version version = new Version("2.3.21"); //FreeMarker版本号  
	
	
	@PostConstruct
	public void setSharedVariable() throws TemplateException, IOException {
	    configuration = getConfiguration();
		configuration.setDateFormat("yyyy/MM/dd");
		configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");

		BeansWrapper wrapper = new DefaultObjectWrapperBuilder(version).build();
		TemplateHashModel staticModels = wrapper.getStaticModels();
		configuration.setSharedVariable("StringUtil",staticModels.get("org.apache.commons.lang.StringUtils"));
		configuration.setSharedVariable("DateUtil",staticModels.get("com.wubaoguo.springboot.util.DateUtil"));
		configuration.setSharedVariable("ConvertUtil",staticModels.get("cn.hutool.core.convert.Convert"));
		
		configuration.setSharedVariable("Constants",staticModels.get("com.wubaoguo.springboot.constant.ShiroConstants"));
		
		configuration.setSetting("template_update_delay", "1");
        configuration.setSetting("default_encoding", "UTF-8");
        
        //解析jsp文件
		/*
        springResolver.setPrefix("/WEB-INF/view/");
        springResolver.setSuffix(".jsp");  
        springResolver.setOrder(1);
        */
        
        //解析后缀为html的
        resolver.setSuffix(".html"); 
        resolver.setCache(false);
        resolver.setRequestContextAttribute("request");
        resolver.setOrder(0);  
	}
	
	public freemarker.template.Configuration getConfiguration(){
        //禁止本地化寻找
	    configuration.setLocalizedLookup(false);
	    configuration.setDefaultEncoding("utf-8");
	    configuration.setClassicCompatible(true);
        //使用[#]替代<#ftl>
	    configuration.setTagSyntax(freemarker.template.Configuration.SQUARE_BRACKET_TAG_SYNTAX);
        
        return configuration;
	}
}
