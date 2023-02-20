package com.wubaoguo.springboot.common.redis.impl;

import com.wubaoguo.springboot.common.redis.support.RedisCacheSupport;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * Description: 请求限制缓存 使用redis实现分布式限流
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2017/11/4 12:09
 */
@Component
public class ReqLimitCache extends RedisCacheSupport {

    private static final String CATALOG = "reqlimit:";

    public long increment(String key, int expire) {
        Jedis jedis = null;
        long count = 1;
        try {
            jedis = this.getJedis();
            jedis.select(getDBIndex());
            count = jedis.incrBy(CATALOG + key, 1);
            if (count == 1) {
                jedis.expire(CATALOG + key, expire);
            }
        } finally {
            this.close(jedis);
        }
        return count;
    }

    @Override
    public int getDBIndex() {
        return 2;
    }
}
