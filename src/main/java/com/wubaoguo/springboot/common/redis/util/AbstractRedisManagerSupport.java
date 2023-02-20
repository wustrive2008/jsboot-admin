package com.wubaoguo.springboot.common.redis.util;

import com.wubaoguo.springboot.common.redis.HessianSerializer;
import com.wubaoguo.springboot.common.redis.support.RedisCacheSupport;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;


public abstract class AbstractRedisManagerSupport extends RedisCacheSupport {
	
	public boolean put(String key, CharSequence seq) {
		addStringToJedis(prefix() + key, String.valueOf(seq), expire());
		return true;
	}
	
	public boolean put(String key, Number number) {
		return put(key, String.valueOf(number));
	}

	public boolean put(String key, Object value) {
		if(!(value instanceof Serializable))
			return false;
		Jedis jedis = this.getJedis();
		try {
			jedis.select(getDBIndex());
			String result = jedis.set((prefix() + key).getBytes(), HessianSerializer.serialize(value));
			jedis.expire(key, expire());
			return "ok".equals(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.close(jedis);
		}
		return false;
	}

	public Object get(String key) {
		Jedis jedis = this.getJedis();
		try {
			jedis.select(getDBIndex());
			String value = jedis.get((prefix() + key));
			if(null == value){
			    return null;
			}
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close(jedis);
		}
		return null;
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

	public boolean containsKey(String key) {
		return this.existKey(prefix() + key);
	}

	protected abstract int expire();
	
	protected abstract String prefix();
}
