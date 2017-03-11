package com.wubaoguo.springboot.redis.common.client;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.wubaoguo.springboot.redis.common.pool.RedisPools;


/**
 * @Title: JedisClient.java
 * @ClassName: org.vcxx.common.redis.client.JedisClient
 * @Description: jedis客户端
 *
 * Copyright  2015-2016 维创盈通 - Powered By 研发中心 V1.0.0
 * @author Zhaoqt
 * @date Jan 29, 2016 10:44:08 AM
 */
public class JedisClient {
    
    private JedisPool pool;
    
    protected JedisClient(String configFilePath){
        pool = RedisPools.getInstance().getPool(configFilePath);
    }
    
    /**
     * 获取jedis对象
     * @return
     */
    public Jedis getJedis(){
        return pool.getResource();
    }
    
    /**
     * 每次使用完Jedis必须返还
     *  @see {@link org.vcxx.common.redis.client.JedisClient#close(Jedis)}
     *  
     * @param resource
     */
    @Deprecated 
    public void returnResource(Jedis resource){
        pool.returnResource(resource);
    }
    
    /**
     * 当获取对象出错时必须returnBrokenResource
     *  @see {@link org.vcxx.common.redis.client.JedisClient#close(Jedis)}
     *  
     * @param resource
     */
    @Deprecated
    public void returnBrokenResource(Jedis resource){
        pool.returnBrokenResource(resource);
    }
    
    public void close(Jedis resource) {
    	resource.close();
    }
}
