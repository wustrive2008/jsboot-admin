package com.wubaoguo.springboot.mail;

import java.io.File;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.wubaoguo.springboot.Application;
import com.wubaoguo.springboot.conf.FreeMarkerConfig;

import freemarker.template.Template;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class MailTest {
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private FreeMarkerConfig freeMarkerConfigurer;
	
	@Test
	public void sendSimpleMail() throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("18695850831@126.com");
		message.setTo("1083030443@qq.com");
		message.setSubject("主题：springboot-简单邮件");
		message.setText("测试邮件内容");
		mailSender.send(message);
	}
	
	@Test
	public void sendAttachmentsMail() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("18695850831@126.com");
		helper.setTo("1083030443@qq.com");
		helper.setSubject("主题：springboot-有附件");
		helper.setText("有附件的邮件");
		FileSystemResource file = new FileSystemResource(new File("/Users/wubaoguo/github/spring-boot-restful/log4j.properties"));
		helper.addAttachment("附件-1", file);
		helper.addAttachment("附件-2", file);
		mailSender.send(mimeMessage);
	}
	
	@Test
	public void sendTemplateMail() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("18695850831@126.com");
		helper.setTo("1083030443@qq.com");
		helper.setSubject("主题：springboot-模板邮件");
		Map<String, Object> model = new HashedMap();
		model.put("username", "张三");
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate("template.ftl"); 
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		helper.setText(text, true);
		mailSender.send(mimeMessage);
	}
	
}
