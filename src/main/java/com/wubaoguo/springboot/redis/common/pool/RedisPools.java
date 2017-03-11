package com.wubaoguo.springboot.redis.common.pool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.wubaoguo.springboot.redis.common.conf.RedisConfig;



/**
 * @Title: RedisPools.java
 * @ClassName: org.vcxx.common.redis.pool.RedisPools
 * @Description: redis连接池
 *
 * Copyright  2015-2016 维创盈通 - Powered By 研发中心 V1.0.0
 * @author Zhaoqt
 * @date Jan 29, 2016 10:49:49 AM
 */
public class RedisPools {

	 private static final Logger logger = LoggerFactory.getLogger(RedisPools.class);
	 
	 /** key为文件名，value为分库的pool */
	 private Map<String, JedisPool> pools = new ConcurrentHashMap<String, JedisPool>();
	 
	 private static Lock lock = new ReentrantLock();
	 
     private RedisPools() {
     }
     
     public static RedisPools getInstance() {
    	 return PoolInstance.instance;
	 }

     /**
      * 获取redis配置文件
      * @param filepath
      * @return
      */
     public JedisPool getPool(String filepath) {
         JedisPool pool = pools.get(filepath);

         if (pool == null) {
             lock.lock();
             try {
                 RedisConfig config = loadConfig(filepath);
                 if (config != null) {
                     pool = new JedisPool(config.getPoolConfig(), config.getHost(), config.getPort(), config.getTimeout(), config.getPassword());
                     pools.put(filepath, pool);
                 } else {
                     throw new RuntimeException(
                         "Can't parse ["
                                 + filepath
                                 + "] files, please check whether there is any file or the format is correct");
                 }
             } finally {
                 lock.unlock();
             }
         }

         return pool;
     }
     
     
     /**
      * 加载redis配置
      * @param filepath
      * @return
      */
     protected static RedisConfig loadConfig(String filepath) {
         try {
            return JSON.parseObject(getConfigContent(filepath), RedisConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
     }
     
     /**
      * 读取redis配置文件内容
      * @param filepath
      * @return
     * @throws IOException 
      */
     public static String getConfigContent(String filepath) throws IOException{
         InputStreamReader inputStreamReader = new InputStreamReader(RedisPools.class.getClassLoader().getResourceAsStream(filepath));
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
         StringBuilder stringBuilder = new StringBuilder();  
         String line;
         while ((line=bufferedReader.readLine())!=null){  
             stringBuilder.append(line);  
         }  
         bufferedReader.close();  
         inputStreamReader.close();  
         return stringBuilder.toString();
         
     }
     
     public void destroy(){
         for (JedisPool pool : pools.values()) {
             if(pool != null){
                 try {
                     pool.destroy();
                 } catch (Exception e) {
                     logger.error("destroy pool error!", e);
                 }
             }
         }
     }

     static class PoolInstance {
         private static RedisPools instance = new RedisPools();
     }
}
