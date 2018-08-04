package com.wubaoguo.springboot.redis.lock;

import org.springframework.stereotype.Component;

/**
 * Description: 分布式锁
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/8/4 12:04
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public interface DistributedLock {

    public static final long TIMEOUT_MILLIS = 30000;

    public static final int RETRY_TIMES = Integer.MAX_VALUE;

    public static final long SLEEP_MILLIS = 500;

    public boolean lock(String key);

    public boolean lock(String key, int retryTimes);

    public boolean lock(String key, int retryTimes, long sleepMillis);

    public boolean lock(String key, long expire);

    public boolean lock(String key, long expire, int retryTimes);

    public boolean lock(String key, long expire, int retryTimes, long sleepMillis);

    public boolean releaseLock(String key);
}
