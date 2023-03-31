package com.wubaoguo.springboot.manage.controller.commond.CondCacheCommond;

import com.wubaoguo.springboot.core.dao.jdbc.BaseCommond;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Description: 缓存条件对象 用户缓存查询条件
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 11:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class CondCacheCommond extends BaseCommond {
    private static final long serialVersionUID = 1L;

    private Integer newQuery;

    public boolean isNull() {
        return false;
    }

    public abstract List<String> getLikes();
}
