package com.wubaoguo.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wubaoguo.springboot.redis.StringRedisCache;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/redis")
public class RedisTestController {
	
	@Autowired
	private StringRedisCache stringRedisCache;
	
	@ApiOperation(value="数据存储到redis", notes="将参数存储到redis")
    @RequestMapping(value={"put/{key}:{value}"}, method=RequestMethod.GET)
    public String put(@PathVariable String key,@PathVariable String value) {
		stringRedisCache.addStringToJedis(key,value,-1);
        return "Success";
    }
}
