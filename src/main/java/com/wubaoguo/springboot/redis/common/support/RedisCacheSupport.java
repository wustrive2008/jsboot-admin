package com.wubaoguo.springboot.redis.common.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisException;

import com.wubaoguo.springboot.redis.common.HessianSerializer;
import com.wubaoguo.springboot.redis.common.client.JedisClient;
import com.wubaoguo.springboot.redis.common.client.JedisClientFactory;

public abstract class RedisCacheSupport {

	private static final Logger logger = LoggerFactory.getLogger(RedisCacheSupport.class);
	
	private JedisClient client;
	 
	 
	public RedisCacheSupport() {
		client = JedisClientFactory.getJedisClient();
	}
	/**
	 * 具体实现类配置 数据库编号
	 * 
	 * @return
	 */
	public abstract int getDBIndex();
	
	
	@SuppressWarnings({ "deprecation"})
	public Jedis getJedis() {
        Jedis jedis = null;
        try {
           jedis = client.getJedis();
        } catch (JedisException e) {
        	logger.warn("failed:jedisPool getResource.");
            if(jedis!=null){
            	client.returnBrokenResource(jedis);
            }
        }
        return jedis;
    }
	
	/**
	 * 添加字符串 到 redis 数据库
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public String addStringToJedis(String key, String value, int expire) {
		Jedis jedis = null;
	    String lastVal = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            lastVal = jedis.getSet(key, value);
            if(expire > 0){
                jedis.expire(key, expire);
            }
        } finally {
           client.close(jedis);
        }
        return lastVal;
	}
	
	/**
	 * 重设指定key 过期时间
	 * 
	 * @param key
	 * @param expire 单位秒
	 * @return
	 */
	public Long expire(String key, int expire) {
		Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            if(expire > 0){
            	return jedis.expire(key, expire);
            }
        } finally {
           client.close(jedis);
        }
        return 0L;
	}
	
	/**
	 * 多元素字符串 追加到 redis
	 * 
	 * @param batchData
	 * @param expire
	 */
	public void addStringToJedis(Map<String,String> batchData, int expire) {
        Jedis jedis = null;
        try {
        	boolean isExpire = expire > 0;
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            Pipeline pipeline = jedis.pipelined();
            for(Map.Entry<String,String> element: batchData.entrySet()){
                if(isExpire){
                    pipeline.setex(element.getKey(), expire, element.getValue());
                }else{
                    pipeline.set(element.getKey(), element.getValue());
                }
            }
            pipeline.sync();
        }  finally {
           client.close(jedis);
        }
	}
	
	/**
	 * list to redis
	 * 
	 * @param key
	 * @param list
	 * @param expire
	 */
	public void addListToJedis(String key, List<String> list, int expire) {
        if (list != null && list.size() > 0) {
            Jedis jedis = null;
            try {
                jedis = this.getJedis();
                jedis.select(getDBIndex());
                if (jedis.exists(key)) {
                    jedis.del(key);
                }
                for (String aList : list) {
                    jedis.rpush(key, aList);
                }
                if (expire > 0){
                    jedis.expire(key, expire);
                }
            }  finally {
            	client.close(jedis);
            }
        }
    }
    
    /**
     * array to redis set
     * 
     * @param key
     * @param value
     */
    public void addToSetJedis(String key, String[] value) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            jedis.sadd(key, value);
        } finally {
        	client.close(jedis);
        }
    }
    /**
     * remove key value at set 
     * 
     * @param key
     * @param value
     */
    public void removeSetJedis(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            jedis.srem(key, value);
        }  finally {
        	client.close(jedis);
        }
    }
    
    /**
     * push String To List
     * 
     * @param key
     * @param data
     * @param expire
     */
    public void pushStringToListJedis(String key, String data, int expire) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            jedis.rpush(key, data);
            if(expire > 0){
                jedis.expire(key,expire);
            }
        }  finally {
        	client.close(jedis);
        }
    }
    
    /**
     * push data to list
     * 
     * @param key
     * @param batchData
     * @param expire
     */
    public void pushDataToListJedis(String key,List<String> batchData, int expire) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            jedis.del(key);
            jedis.lpush(key, batchData.toArray(new String[batchData.size()]));
            if(expire > 0)
                jedis.expire(key, expire);
        } finally {
        	client.close(jedis);
        }
    }
    
    /**
     * 删除list中的元素
     * 
     * @param key
     * @param values
     */
    public void deleteDataFromListJedis(String key, List<String> values) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            Pipeline pipeline = jedis.pipelined();
            if(values != null && !values.isEmpty()){
                for (String val : values){
                    pipeline.lrem(key, 0, val);
                }
            }
            pipeline.sync();
        } finally {
        	client.close(jedis);
        }
    }
    
    /**
     * add hashMap to hash
     * 
     * @param key
     * @param map
     * @param expire
     */
    public void addHashMapToJedis(String key, Map<String, String> map, int expire) {
        Jedis jedis = null;
        if (map != null && map.size() > 0) {
            try {
                jedis = this.getJedis();
                jedis.select(getDBIndex());
                jedis.hmset(key, map);
                if (expire > 0)
                    jedis.expire(key, expire);
            } finally {
            	client.close(jedis);
            }
        }
    }
    
    /**
     * hset 存储字符串
     * 
     * @param key
     * @param field
     * @param value
     * @param expire
     */
    public void addHashMapToJedis(String key, String field, String value, int expire) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            if (jedis != null) {
                jedis.select(getDBIndex());
                jedis.hset(key, field, value);
                if (expire > 0)
                	jedis.expire(key, expire);
            }
        } finally {
        	client.close(jedis);
        }
    }
    
    /**
     * hincrBy  更新
     * 
     * @param key
     * @param incrementField
     * @param incrementValue
     * @param dateField
     * @param dateValue
     */
    public void updateHashMapToJedis(String key, String incrementField, long incrementValue, String dateField, String dateValue) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            jedis.hincrBy(key, incrementField, incrementValue);
            jedis.hset(key, dateField, dateValue);
        } finally {
        	client.close(jedis);
        }
    }
    
    /**
     * 根据指定key 返回 字符串类型
     * 
     * @param key
     * @return
     */
    public String getStringFromJedis(String key, int expire) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            if (jedis.exists(key)) {
                value = jedis.get(key);
                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
                // value 非空 重置时间
                if(value != null && expire > 0) {
                	jedis.expire(key, expire);
                }
            }
        } finally {
        	client.close(jedis);
        }
        return value;
    }
    
    public String getStringFromJedis(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            if (jedis.exists(key)) {
                value = jedis.get(key);
                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
            }
        } finally {
        	client.close(jedis);
        }
        return value;
    }
    
    
    /**
     * mget
     * 
     * @param keys
     * @return
     */
    public List<String> getStringFromJedis(String[] keys) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            return jedis.mget(keys);
        }  finally {
        	client.close(jedis);
        }
    }
    
    /**
     * lrange
     * 
     * @param key
     * @return
     */
    public List<String> getListFromJedis(String key) {
        List<String> list = null;
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            if (jedis.exists(key)) {
                list = jedis.lrange(key, 0, -1);
            }
        } finally {
        	client.close(jedis);
        }
        return list;
    }
    
    /**
     * smembers
     * 
     * @param key
     * @return
     */
    public Set<String> getSetFromJedis(String key) {
        Set<String> list = null;
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            if (jedis.exists(key)) {
                list = jedis.smembers(key);
            }
        } finally {
        	client.close(jedis);
        }
        return list;
    }
    
    /**
     * hgetAll
     * 
     * @param key
     * @return
     */
    public Map<String, String> getHashMapFromJedis(String key) {
        Map<String, String> hashMap = null;
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            hashMap = jedis.hgetAll(key);
        } finally {
        	client.close(jedis);
        }
        return hashMap;
    }
    
    /**
     * hget
     * 
     * @param key
     * @param field
     * @return
     */
    public String getHashMapValueFromJedis(String key, String field) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            if (jedis != null) {
                jedis.select(getDBIndex());
                if (jedis.exists(key)) {
                    value = jedis.hget(key, field);
                }
            }
        } finally {
        	client.close(jedis);
        }
        return value;
    }
    
    /**
     * incr
     * 
     * @param identifyName
     * @return
     */
    public Long getIdentifyId(String identifyName) {
        Jedis jedis = null;
        Long identify = null;
        try {
            jedis = this.getJedis();
            if (jedis != null) {
                jedis.select(getDBIndex());
                identify = jedis.incr(identifyName);
                if(identify == 0){
                    return jedis.incr(identifyName);
                }else {
                    return identify;
                }
            }
        } finally {
        	client.close(jedis);
        }
        return null;
    }
    
    /**
     * 删除某db的某个key值
     * 
     * @param key
     * @return
     */
    public Long delKeyFromJedis(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            return jedis.del(key);
        }  finally {
        	client.close(jedis);
        }
    }
    
    /**
     * flushDB
     * 
     * @param dbIndex
     */
    public void flushDBFromJedis(int dbIndex) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(dbIndex);
            jedis.flushDB();
        } finally {
        	client.close(jedis);
        }
    }
    
    /**
     * exists key
     * 
     * @param key
     * @return
     */
    public boolean existKey(String key) {
        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            return jedis.exists(key);
        }  finally {
        	client.close(jedis);
        }
    }
    
    /**
     * DB size
     * 
     * @return
     */
    public Long dbSize() {
    	 Jedis jedis = null;
         try {
             jedis = this.getJedis();
             jedis.select(getDBIndex());
             return jedis.dbSize();
         }  finally {
         	client.close(jedis);
         }
    }
    
    public void close(Jedis resource) {
    	resource.close();
    }
    
    @SuppressWarnings("deprecation")
	public <T extends Serializable>  void setObject(String key, T obj) {
    	Jedis jedis = getJedis();
        try {
        	 jedis.select(getDBIndex());
			jedis.set(key.getBytes(), HessianSerializer.serialize(obj));
		} catch (Exception e) {
			client.returnBrokenResource(jedis);
        } finally {
        	client.returnResource(jedis);
        }
    }
    
    @SuppressWarnings("deprecation")
    public <T extends Serializable> T getObject(String key) {
    	  Jedis jedis = getJedis();
          T t = null;
          try {
        	  jedis.select(getDBIndex());
              byte[] result = jedis.get(key.getBytes()); 
              if(result != null){
                  t = HessianSerializer.deserialize(result);
              }
          } catch (Exception e) {
        	  client.returnBrokenResource(jedis);
          } finally {
        	  client.returnResource(jedis);
          }
          return t; 
    }
}
