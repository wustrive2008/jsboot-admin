package com.wubaoguo.springboot.conf;

import com.wubaoguo.springboot.redis.lock.DistributedLock;
import com.wubaoguo.springboot.redis.lock.RedisDistributedLock;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Description:
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/8/4 12:03
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class DistributedLockAutoConfiguration {

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public DistributedLock redisDistributedLock(RedisTemplate<Object, Object> redisTemplate){
        return new RedisDistributedLock(redisTemplate);
    }
}
