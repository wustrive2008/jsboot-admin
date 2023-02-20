package com.wubaoguo.springboot.redis;

import com.wubaoguo.springboot.common.redis.util.AbstractRedisManagerSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtSubjectCache extends AbstractRedisManagerSupport {

    @Value("${jwt.exp}")
    private int jwtExp;
    
    @Override
    protected int expire() {
        Integer expire_hour = 30;
        expire_hour = Integer.valueOf(jwtExp) * 2 ;
        return expire_hour * 24 * 60 * 60;
    }

    @Override
    protected String prefix() {
        return "subject:";
    }

    @Override
    public int getDBIndex() {
        return 3;
    }
}
