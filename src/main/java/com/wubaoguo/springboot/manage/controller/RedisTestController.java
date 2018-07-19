package com.wubaoguo.springboot.manage.controller;

import com.wubaoguo.springboot.manage.service.RedisTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import com.wubaoguo.springboot.redis.StringRedisCache;

import io.swagger.annotations.ApiOperation;
import org.wustrive.java.core.request.ViewResult;

@CacheConfig(cacheNames = "user")
@RestController
@RequestMapping("/redis")
public class RedisTestController {

    @Autowired
    private StringRedisCache stringRedisCache;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTestService redisTestService;

    @ApiOperation(value = "数据存储到redis-外置redis", notes = "将数据存储到redis")
    @RequestMapping(value = {"put/{key}:{value}"}, method = RequestMethod.GET)
    public String putValue(@PathVariable String key, @PathVariable String value) {
        stringRedisCache.addStringToJedis(key, value, -1);
        return "Success";
    }

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

    @ApiOperation(value = "redis数据库缓存", notes = "缓存数据")
    @RequestMapping(value = {"getUsers"}, method = RequestMethod.GET)
    public String cacheSql() {
        return redisTestService.getUsers();
    }


}
