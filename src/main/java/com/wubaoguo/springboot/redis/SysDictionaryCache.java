package com.wubaoguo.springboot.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wustrive.java.common.util.ConvertUtil;
import org.wustrive.java.common.util.StringUtil;
import org.wustrive.java.dao.jdbc.BaseCommond;
import org.wustrive.java.dao.jdbc.dao.QuerySupport;
import org.wustrive.java.redis.support.RedisCacheSupport;

import redis.clients.jedis.Jedis;

import com.google.common.collect.ImmutableMap;
import com.wubaoguo.springboot.entity.SysDictionary;

@Component
public class SysDictionaryCache extends RedisCacheSupport{

	private static Logger logger = LoggerFactory.getLogger(SysDictionaryCache.class);
	
	public static final String KEY_CONTENT_PREFIX = "dic:";
	
	public static final String KEY_DIC_VERSION = "dic_version";
	
	@Autowired
	QuerySupport querySupport;
	
	@Override
	public int getDBIndex() {
		return 1;
	}
	
	
	/**
	 * 查询 全部数据此单那，map 字典 标识，值  字典内容
	 * 
	 * @return
	 */
	public List<Map<String, String>> getAllDic() {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.select(getDBIndex());
			Set<String> keys = jedis.keys(KEY_CONTENT_PREFIX + "*");
			List<Map<String, String>> rList = new ArrayList<Map<String,String>>(keys.size());
			for(String key : keys) {
				Map<String, String> map = jedis.hgetAll(key);
				rList.add(map);
			}
			return rList;
		} finally {
			if(jedis != null) {
				close(jedis);
			}
		}
	}
	
	/***
	 *  
	 * 
	 * @return
	 */
	public List<Map<String, Map<String, String>>> getAlliosDic() {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.select(getDBIndex());
			Set<String> keys = jedis.keys(KEY_CONTENT_PREFIX + "*");
			List<Map<String, Map<String, String>>> rList = new ArrayList<Map<String, Map<String, String>>>(keys.size());
			for(String key : keys) {
				Map<String, String> map = jedis.hgetAll(key);
				rList.add(ImmutableMap.of(map.get("tag_code"), map));
			}
			return rList;
		} finally {
			if(jedis != null) {
				close(jedis);
			}
		}
	}
	
	/**
	 * 同步单个 数据词典数据
	 * 
	 * @param sysDictionary
	 */
	public void sync(SysDictionary sysDictionary) {
		if(sysDictionary != null && StringUtil.isNotBlank(sysDictionary.getTag_code())) {
			logger.info("同步数据词典...[" + sysDictionary.getTag_code() + "]");
			Jedis jedis = null;
			try {
				jedis = this.getJedis();
				jedis.select(getDBIndex());
				// 重设 数据词典当前版本
				setVersion(jedis);
				// 删除 就key
				jedis.del(KEY_CONTENT_PREFIX + sysDictionary.getTag_code());
				
				// 添加 新key
				jedis.hmset(KEY_CONTENT_PREFIX + sysDictionary.getTag_code(),
						ImmutableMap.of(
								"tag_code", sysDictionary.getTag_code(),
								"res_code", sysDictionary.getRes_code(),
								"title", sysDictionary.getTitle(),
								"content", sysDictionary.getContent(),
								"description", sysDictionary.getDescription()));
			} finally {
				if(jedis != null) {
					close(jedis);
				}
			}
			logger.info("同步数据词典...[" + sysDictionary.getTag_code() + "]完成");
		}
	}
	
	
	/**
	 * 校验 指定版本是否为当前 数据词典 当前版本
	 * 
	 * @param version
	 * @return
	 */
	public boolean isCurrentVersion(String version) {
		if(!StringUtil.isBlank(version)) {
			return version.equals(getStringFromJedis(KEY_DIC_VERSION));
		}
		return false;
	}
	
	/**
	 * 数据词典当前版本
	 * 
	 * @return
	 */
	public String getVersion() {
		return getStringFromJedis(KEY_DIC_VERSION);
	}
	
	/**
	 * 重置 数据词典版本
	 * 
	 * @param jedis
	 */
	public void setVersion(Jedis jedis) {
		// 缓存  版本 控制，这里直接选择当前时间
		if(jedis.exists(KEY_DIC_VERSION)) {
			Integer version = Integer.valueOf(jedis.get(KEY_DIC_VERSION));
			jedis.set(KEY_DIC_VERSION, String.valueOf(version + 1));
		} else {
			jedis.set(KEY_DIC_VERSION, "1");
		}
	}
	
	/**
	 * 清除  数据词典缓存记录
	 * 
	 */
	public void clean() {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.select(getDBIndex());
			Set<String> keys = jedis.keys(KEY_CONTENT_PREFIX + "*");
			for(String key : keys) {
				jedis.del(key);
			}
		} finally {
			if(jedis != null) {
				close(jedis);
			}
		}
	}
}
