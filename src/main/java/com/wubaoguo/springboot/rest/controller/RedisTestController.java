package com.wubaoguo.springboot.rest.controller;

import com.wubaoguo.springboot.core.request.ViewResult;
import com.wubaoguo.springboot.rest.service.RedisTestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CacheConfig(cacheNames = "user")
@RestController
@RequestMapping("/open/redis")
public class RedisTestController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTestService redisTestService;


    @ApiOperation(value = "数据存储到redis-内置redis", notes = "将数据存储到redis")
    @RequestMapping(value = {"add/{key}:{value}"}, method = RequestMethod.GET)
    public String add(@PathVariable String key, @PathVariable String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        return "Success";
    }

    @ApiOperation(value = "redis数据库缓存", notes = "缓存数据")
    @RequestMapping(value = {"getUser/{id}"}, method = RequestMethod.GET)
    public String cacheSql(@PathVariable String id) {
        return ViewResult.newInstance().setData(redisTestService.getUser(id).getBeanValues()).json();
    }

    @RequestMapping(value = {"getUsers"}, method = RequestMethod.GET)
    public String cacheSql() {
        return redisTestService.getUsers();
    }


}
