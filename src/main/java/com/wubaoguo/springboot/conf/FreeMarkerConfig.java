package com.wubaoguo.springboot.conf;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.Version;

/**
 * freemarker配置
* @ClassName: FreeMarkerConfig 
* @Description: TODO(这里用一句话描述这个类的作用) 
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
	@Autowired  
    protected org.springframework.web.servlet.view.InternalResourceViewResolver springResolver;  
	
	public static Version version = new Version("2.3.21"); //FreeMarker版本号  
	
	
	@PostConstruct
	public void setSharedVariable() throws TemplateException, IOException {
		configuration.setDateFormat("yyyy/MM/dd");
		configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
		
		
		BeansWrapper wrapper = new DefaultObjectWrapperBuilder(version).build();
		TemplateHashModel staticModels = wrapper.getStaticModels();
		configuration.setSharedVariable("StringUtil",
				(TemplateHashModel) staticModels.get("com.wubaoguo.springboot.util.StringUtil"));
		configuration.setSharedVariable("DateUtil",
				(TemplateHashModel) staticModels.get("com.wubaoguo.springboot.util.DateUtil"));
		configuration.setSharedVariable("ConvertUtil",
				(TemplateHashModel) staticModels.get("com.wubaoguo.springboot.util.ConvertUtil"));
		
		configuration.setSetting("template_update_delay", "1");  
        configuration.setSetting("default_encoding", "UTF-8");  
        
        //configuration.setServletContextForTemplateLoading(servletContext, "/WEB-INF/ftl/");
        
        //解析前缀后XXX路径下的jsp文件 
        springResolver.setPrefix("/WEB-INF/view/");  
        springResolver.setSuffix(".jsp");  
        springResolver.setOrder(1);  
        
        //解析后缀为html的
        resolver.setSuffix(".html"); 
        resolver.setCache(false);
        resolver.setRequestContextAttribute("request");
        resolver.setOrder(0);  
	}
	
	public freemarker.template.Configuration getConfiguration(){
		return configuration;
	}
}
