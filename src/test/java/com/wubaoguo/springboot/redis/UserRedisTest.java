package com.wubaoguo.springboot.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wubaoguo.springboot.Application;
import com.wubaoguo.springboot.bean.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class UserRedisTest {
	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	@Test
	public void test() throws Exception {
		// 保存对象
		User user = new User("1","超人");
		redisTemplate.opsForValue().set(user.getId(), user);
		user = new User("2","蝙蝠侠");
		redisTemplate.opsForValue().set(user.getId(), user);
		user = new User("3","蜘蛛侠");
		redisTemplate.opsForValue().set(user.getId(), user);
		Assert.assertEquals("超人", redisTemplate.opsForValue().get("1").getName());
		Assert.assertEquals("蝙蝠侠", redisTemplate.opsForValue().get("2").getName());
		Assert.assertEquals("蜘蛛侠", redisTemplate.opsForValue().get("3").getName());
	}
}
