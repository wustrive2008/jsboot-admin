package com.wubaoguo.springboot.redis;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.wubaoguo.springboot.exception.BusinessException;
import com.wubaoguo.springboot.redis.common.HessianSerializer;
import com.wubaoguo.springboot.redis.common.support.RedisCacheSupport;


public abstract class AbstractRedisManagerSupport extends RedisCacheSupport {
	
	public boolean put(String key, CharSequence seq) throws BusinessException {
		addStringToJedis(prefix() + key, String.valueOf(seq), expire());
		return true;
	}
	
	public boolean put(String key, Number number) throws BusinessException {
		return put(key, String.valueOf(number));
	}

	public boolean put(String key, Object value) throws BusinessException {
		if(!(value instanceof Serializable))
			throw new BusinessException("not serializable object");
		Jedis jedis = this.getJedis();
		try {
			jedis.select(getDBIndex());
			String result = jedis.set((prefix() + key).getBytes(), HessianSerializer.serialize(value));
			jedis.expire(key, expire());
			return "ok".equals(result);
		} catch (IOException e) {
			throw new BusinessException(e.getMessage());
		} finally {
			this.close(jedis);
		}
	}

	public Object get(String key) throws BusinessException {
		Jedis jedis = this.getJedis();
		try {
			jedis.select(getDBIndex());
			String value = jedis.get((prefix() + key));
			if(null == value){
			    return null;
			}
			return value;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		} finally {
			this.close(jedis);
		}
	}
	
	public String getString(String key) {
		return getStringFromJedis(prefix() + key);
	}
	
	public Number getNumber(String key) {
		String value = getString(key);
		if(value == null)
			return 0;
		return new BigDecimal(value);
	}

	public boolean remove(String key) {
		if (this.delKeyFromJedis(prefix() + key) > 0) {
			return true;
		}
		return false;
	}

	public boolean removeAll()  {
		Jedis jedis =  this.getJedis();
		try {
			jedis.select(getDBIndex());
			Set<String> keys = jedis.keys(prefix() + "*");
			for(String key : keys) {
				jedis.del(key);
			}
		} finally {
			this.close(jedis);
		}
		return true;
	}

	public boolean containsKey(String key) throws BusinessException {
		return this.existKey(prefix() + key);
	}

	protected abstract int expire();
	
	protected abstract String prefix();
}
