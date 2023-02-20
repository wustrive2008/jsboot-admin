package com.wubaoguo.springboot.common.redis.client;

/**
 * 
 * @author wubaoguo
 * @mail: wustrive2008@gmail.com
 * @date: 2017年3月11日 下午4:11:14
 * @version: v0.0.1
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
