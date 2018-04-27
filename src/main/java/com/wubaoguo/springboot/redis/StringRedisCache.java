package com.wubaoguo.springboot.redis;

import org.springframework.stereotype.Component;
import org.wustrive.java.common.redis.support.RedisCacheSupport;

@Component
public class StringRedisCache extends RedisCacheSupport {

	@Override
	public int getDBIndex() {
		return 0;
	}

}
