package com.wubaoguo.springboot.rest.service;

import com.wubaoguo.springboot.entity.SysAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.klock.annotation.Klock;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.wustrive.java.core.request.ViewResult;
import org.wustrive.java.dao.jdbc.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/19 9:40
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Service
public class RedisTestService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    BaseDao baseDao;


    @Klock
    @Cacheable(cacheNames = {"userList3"}, keyGenerator = "wiselyKeyGenerator")
    public String getUsers() {
        String sql = "select * from sys_admin";
        System.out.println("查询数据库");
        List<Map<String, Object>> list = baseDao.queryForListMap(sql, null);
        return ViewResult.newInstance().setRows(list).json();
    }

    /**
     * @param id
     * @return
     */
    @Cacheable("sysadmin")
    public SysAdmin getUser(String id) {
        System.out.println("查询数据库");
        return new SysAdmin(id).queryForBean();
    }

}
