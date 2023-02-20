package com.wubaoguo.springboot.dao.jdbc.dao;

import com.wubaoguo.springboot.dao.jdbc.BaseCommond;
import com.wubaoguo.springboot.dao.jdbc.bean.BaseBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuerySupport {

    @Autowired
	private BaseDao baseDao;
	
	public List<Map<String, Object>> find(String sql, Map<String, Object> parames, BaseCommond commond) {
		String querySql = sql;
		if(StringUtils.isNotEmpty(commond.getOrderBy())){
			querySql += " order by " +commond.getOrderBy()+" ";
		}
		if(commond.isPager()){
			String sqlCount = "select count(*) from ("+ sql +" ) as count_info ";
			commond.setTotalCount(baseDao.queryForInteger(sqlCount,parames));
			querySql +=  " limit "+commond.getLimitStart()+","+commond.getLimitEnd();
		}
	    return baseDao.queryForListMap(querySql, parames);
	}
	
	public <T> List<T> find(String sql, BaseBean bean, BaseCommond commond) throws Exception {
		String querySql = sql;
		
		if(StringUtils.isNotEmpty(commond.getOrderBy())){
			querySql += " order by " +commond.getOrderBy()+" ";
		}
		
		if(commond.isPager()){
			String sqlCount = "select count(*) from ("+ sql +" ) as count_info ";
			commond.setTotalCount(baseDao.queryForInteger(sqlCount,bean.getBeanValues()));
			querySql +=  " limit "+commond.getLimitStart()+","+commond.getLimitEnd();
		}
	    return baseDao.queryForList(querySql,bean);
	}

	public <T> List<T> find(BaseBean bean, BaseCommond commond) throws Exception {
		String sql = "select * from "+bean.getTableName();
		if(StringUtils.isNotEmpty(bean.getParameter())){
			sql += " where " + bean.getParameter();
		}
		return find(sql, bean, commond);
		
	}
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	public Map<String, Object> parames() {
		return new HashMap<String, Object>();
	}
}
