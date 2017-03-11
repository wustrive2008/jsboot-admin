package com.wubaoguo.springboot.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesConfig {
	private static Properties properties;
	private static Logger logger = LoggerFactory.getLogger(PropertiesConfig.class);

	/**
	 * 获取Properties
	 * 
	 * @Title: getProperties
	 * @return
	 */
	public static Properties getProperties() {
		// 生成输入流
		InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
		// 生成properties对象
		properties = new Properties();
		try {
			properties.load(ins);
		} catch (Exception e) {
			logger.info("ExceptionContent:::", e);
		} finally {
			IOUtils.closeQuietly(ins);
		}
		return properties;
	}

	public static String getProperty(String key) {
		if (properties == null) {
			properties = getProperties();
		}
		return properties.getProperty(key);
	}
}
