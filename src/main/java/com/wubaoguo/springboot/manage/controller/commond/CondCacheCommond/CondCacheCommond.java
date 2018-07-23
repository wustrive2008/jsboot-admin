package com.wubaoguo.springboot.manage.controller.commond.CondCacheCommond;

import lombok.Data;
import org.wustrive.java.dao.jdbc.BaseCommond;

/**
 * Description: 缓存条件对象 用户缓存查询条件
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:15
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Data
public class CondCacheCommond extends BaseCommond {
    private static final long serialVersionUID = 1L;

    private Integer newQuery;

    public boolean isNull() {
        return false;
    }
}
