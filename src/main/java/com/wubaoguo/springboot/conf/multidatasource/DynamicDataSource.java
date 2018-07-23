package com.wubaoguo.springboot.conf.multidatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 动态数据源-在项目运行时根据相应key值切换到对应的数据源DataSource上
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 16:33
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    static final Map<DatabaseType, List<String>> METHOD_TYPE_MAP = new HashMap<>();

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        DatabaseType type = DatabaseContextHolder.getDatabaseType();
        logger.info("====================dataSource ==========" + type);
        return type;
    }

    void setMethodType(DatabaseType type, String content) {
        List<String> list = Arrays.asList(content.split(","));
        METHOD_TYPE_MAP.put(type, list);
    }
}
