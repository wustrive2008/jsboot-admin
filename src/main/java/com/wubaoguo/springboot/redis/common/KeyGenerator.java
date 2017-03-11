package com.wubaoguo.springboot.redis.common;
/**
 * @Title: KeyGenerator.java
 * @ClassName: org.vcxx.common.redis.KeyGenerator
 * @Description: key创建
 *
 * Copyright  2015-2016 维创盈通 - Powered By 研发中心 V1.0.0
 * @author Zhaoqt
 * @date Jan 29, 2016 11:17:16 AM
 */
public enum KeyGenerator {

	NOTICE_KEY("notice_"),
	/** session共享 */
	SEESION_KEY("session_"),
	/** 查询效率不高的业务数据cache，频繁度高的查询 */
	BUSINESS_KEY("business_"),
	/** 活动数据 */
	SUBJECT_KEY("subject_"),
	
	TOKEN_SECRET("token_secret_"),
	
	BASE_SERVICE_KEY("base_service_"),// 基础服务key
	
	RESUBMIT_TOKEN("resubmit_token_");

	private final String prefix;

	private KeyGenerator(String prefix) {
		this.prefix = prefix;
	}

	public String getKey(Object subKey) {
		return this.prefix + subKey;
	}
}
