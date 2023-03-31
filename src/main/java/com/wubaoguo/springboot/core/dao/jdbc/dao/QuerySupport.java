package com.wubaoguo.springboot.core.dao.jdbc.dao;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.wubaoguo.springboot.core.dao.jdbc.BaseCommond;
import com.wubaoguo.springboot.core.dao.jdbc.bean.BaseBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuerySupport {

    @Autowired
    private BaseDao baseDao;

    public List<Map<String, Object>> find(String sql, Map<String, Object> parames, BaseCommond commond) {
        String querySql = sql;
        if (StringUtils.isNotEmpty(commond.getOrderBy())) {
            querySql += " order by " + commond.getOrderBy() + " ";
        }
        if (commond.isPager()) {
            String sqlCount = "select count(*) from (" + sql + " ) as count_info ";
            commond.setTotalCount(baseDao.queryForInteger(sqlCount, parames));
            querySql += " limit " + commond.getLimitStart() + "," + commond.getLimitEnd();
        }
        return baseDao.queryForListMap(querySql, parames);
    }

    public <T> List<T> find(String sql, BaseBean bean, BaseCommond commond) throws Exception {
        String querySql = sql;

        if (StringUtils.isNotEmpty(commond.getOrderBy())) {
            querySql += " order by " + commond.getOrderBy() + " ";
        }

        if (commond.isPager()) {
            String sqlCount = "select count(*) from (" + sql + " ) as count_info ";
            commond.setTotalCount(baseDao.queryForInteger(sqlCount, bean.getBeanValues()));
            querySql += " limit " + commond.getLimitStart() + "," + commond.getLimitEnd();
        }
        return baseDao.queryForList(querySql, bean);
    }

    public <T> List<T> find(BaseBean bean, BaseCommond commond) throws Exception {
        String sql = "select * from " + bean.getTableName();
        if (StringUtils.isNotEmpty(bean.getParameter())) {
            sql += " where " + bean.getParameter();
        }
        return find(sql, bean, commond);

    }

    /**
     * 条件查询封装
     *
     * @param tableName
     * @param columns
     * @param commond
     * @param orderByStr
     * @return
     */
    public List<Map<String, Object>> conditionQuery(String tableName, List<String> columns, BaseCommond commond, String orderByStr) {
        StringBuilder sql = new StringBuilder("SELECT ");
        Map<String, Object> param = Maps.newHashMap();
        if (columns.isEmpty()) {
            sql.append("*");
        } else {
            sql.append(String.join(",", columns));
        }
        sql.append(" FROM ").append(tableName).append(" WHERE 1=1 ");

        Class<?> commondClass = commond.getClass();
        Field[] fields = commondClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();   // 获取属性的名字
            Object value;   // 获取属性的值
            try {
                value = field.get(commond);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            sql.append(" AND ").append(StrUtil.toUnderlineCase(name));
            if (commond.getLikes().contains(name)) {
                sql.append(" like ").append(":").append(name);
                param.put(name, "%" + value + "%");
            } else {
                sql.append(" = ").append(value);
                param.put(name, value);
            }
        }
        //排序
        if (StringUtils.isNotEmpty(orderByStr)) {
            sql.append(orderByStr);
        }
        return find(sql.toString(), param, commond);
    }

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }


    public Map<String, Object> parames() {
        return new HashMap<String, Object>();
    }
}
