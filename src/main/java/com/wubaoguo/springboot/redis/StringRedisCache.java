package com.wubaoguo.springboot.redis;

import com.wubaoguo.springboot.common.redis.support.RedisCacheSupport;
import org.springframework.stereotype.Component;

@Component
public class StringRedisCache extends RedisCacheSupport {

	@Override
	public int getDBIndex() {
		return 0;
	}

}
