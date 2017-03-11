package com.wubaoguo.springboot.redis.common.client;


/**
 * @Title: JedisClientFactory.java
 * @ClassName: org.vcxx.common.redis.client.JedisClientFactory
 * @Description: JedisClient 工厂
 *
 * Copyright  2015-2016 维创盈通 - Powered By 研发中心 V1.0.0
 * @author Zhaoqt
 * @date Jan 29, 2016 10:32:56 AM
 */
public class JedisClientFactory {

    private static final String DEFAULT_FILE = "jedis.json";

    public static JedisClient getJedisClient() {
        return getJedisClient(DEFAULT_FILE);
    }

    public static JedisClient getJedisClient(String filename) {
        return new JedisClient(filename);
    }
}
