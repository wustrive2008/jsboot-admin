package com.wubaoguo.springboot.core.dao.jdbc.dao;

import cn.hutool.core.convert.Convert;
import com.wubaoguo.springboot.core.dao.jdbc.SqlParameter;
import com.wubaoguo.springboot.core.dao.jdbc.bean.BaseBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings("all")
@Component
public class BaseDao {
	
    @Autowired
	private JdbcTemplate jdbcTemplate;

    @Autowired
	private NamedParameterJdbcTemplate paraJdbcTemplate;
	

	/**
	 * GG_USER user = baseDao.queryForBean("select * from GG_USER where id=:id", new GG_USER().setId('aasaaa'));
	 * @param <T>
	 * @param sql
	 * @param bean
	 * @return
	 */
	public <T> T queryForBean(String sql, BaseBean bean) {
		List<T> list = this.paraJdbcTemplate.query(sql, bean.getBeanValues(), bean.newMapper());
		if (list == null || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	/**
	 * 
	 * GG_USER user = baseDao.queryForBean("select * from GG_USER where id=:id",
				new SqlParameter("id","1dcd7d8483c2434dabbb142a76003df9"),
				new GG_USER());
	 * @param <T>
	 * @param sql
	 * @param param
	 * @param bean
	 * @return
	 */
	public <T> T queryForBean(String sql, Map param, BaseBean bean) {
		List<T> list = this.paraJdbcTemplate.query(sql, param, bean.newMapper());
		if (list == null || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	
	public <T> List<T> queryForBeanList(String sql, Map param, BaseBean bean) {
		return this.paraJdbcTemplate.query(sql, param, bean.newMapper());
	}
	
	public <T> List<T> queryForBeanMapperList(String sql, RowMapper<T> rowMapper) {
		return this.paraJdbcTemplate.query(sql, rowMapper);
	}
	
	/**
	 * 只提供了对bean中set方法的注入
	 * @param bean
	 * @param map
	 * @return
	 */
	private BaseBean map2Bean(BaseBean bean, Map map){
		Iterator iterator = map.keySet().iterator();
		Class<?> c = bean.getClass();
		Method[] methods = c.getDeclaredMethods();
		try {
			while (iterator.hasNext()) {
				String key = iterator.next().toString();
				for (Method method : methods) {
					if (StringUtils.equals(method.getName().toLowerCase(), "set"+key.toLowerCase())) {
						String parameterType = method.getParameterTypes()[0].getSimpleName();
						if (StringUtils.equals("String", parameterType)) {
							method.invoke(bean, Convert.toStr(map.get(key)));
						} else if (StringUtils.equals("Integer", parameterType)) {
							method.invoke(bean, Convert.toInt(map.get(key)));
						} else if (StringUtils.equals("Long", parameterType)) {
							method.invoke(bean, Convert.toLong(map.get(key)));
						} else if (StringUtils.equals("Double", parameterType)) {
							method.invoke(bean, Convert.toDouble(map.get(key)));
						} else {
							System.out.println(method.getName() + "方法的参数类型为：" + parameterType + "该类型暂时没有处理");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("org.vcxx.common.utils.ConvertUtil.map2Bean(BaseBean, Map)方法执行失败");
		}
		return bean;
	}
	
	public <T> T queryForList(String sql,BaseBean baseBean){
		List<Map> list = this.queryForList(sql, baseBean.getBeanValues());
		if (list != null && list.size() > 0) {
			List<BaseBean> beanList = new ArrayList<BaseBean>();
			for (Map map : list) {
				beanList.add(map2Bean(baseBean.newInstance(), map));
			}
			return (T)beanList;
		}
		return (T)new ArrayList<BaseBean>();
	}
	
	
	/**
	 * @param sql
	 * @param param
	 * @return
	 */
	public List<Map> queryForList(String sql,Map param) {
		List<Map<String,Object>> list = this.paraJdbcTemplate.queryForList(sql, param);
		if (list == null) {
			list = new ArrayList<Map<String,Object>>();
		}
		return (List)list;
	}
	
	public List<Map<String,Object>> queryForListMap(String sql,Map param) {
		List<Map<String,Object>> list = this.paraJdbcTemplate.queryForList(sql, param);
		if (list == null) {
			list = new ArrayList<Map<String,Object>>();
		}
		return list;
	}
	
	public List<Map<String,Object>> queryForListLimit(String sql,Map param,Integer pagenum,Integer pagesize) {
		if(pagenum==null||pagenum<1){
			pagenum=1;
		}
		if(pagesize==null||pagesize<0){
			pagesize=1;
		}
		
		param.put("pagenum", (pagenum-1)*pagesize);
		param.put("pagesize", pagesize);
		
		sql+=" limit :pagenum,:pagesize ";
		
		List<Map<String,Object>> list = this.paraJdbcTemplate.queryForList(sql, param);
		if (list == null) {
			list = new ArrayList<Map<String,Object>>(1);
		}
		return list;
	}
	
	public List<Map> queryForList(String sql) {
		List<Map<String,Object>> list = this.jdbcTemplate.queryForList(sql);
		if (list == null) {
			list = new ArrayList<Map<String,Object>>();
		}
		return (List)list;
	}
	
	
	

	
	public Map queryForMap(String sql,Map paramMap){
		List<Map> list  = queryForList(sql, paramMap);
		if (list == null || list.size() == 0) {
			return  new HashMap(0);
		}
		return list.get(0);
	}
	
	
	public Map queryForMap(String sql){
		Map map = this.queryForMap(sql,new SqlParameter());
		if (map == null) {
			map = new HashMap();
		}
		return map;
	}
	
	public Map<String, Object> queryForObjectMap(String sql){
		Map map = this.queryForMap(sql,new SqlParameter());
		if (map == null) {
			map = new HashMap<String, Object>(0);
		}
		return map;
	}
	
	public Map<String, Object> queryForObjectMap(String sql, Map paramMap){
		List<Map> list  = queryForList(sql, paramMap);
		if (list == null || list.size() == 0) {
			return  new HashMap<String, Object>(0);
		}
		return list.get(0);
	}
	
	public String queryForString(String sql,Map paramMap){
		Map map = queryForMap(sql, paramMap);
		if (map == null || map.isEmpty()) {
			return null;
		} else {
			return Convert.toStr(map.get(map.keySet().iterator().next()));
		}
	}
	
	public String queryForString(String sql){
		Map map = queryForMap(sql);
		if (map == null || map.isEmpty()) {
			return null;
		} else {
			return Convert.toStr(map.get(map.keySet().iterator().next()));
		}
	}
	
	
	public Integer queryForInteger(String sql){
		Map map = queryForMap(sql);
		if (map == null || map.isEmpty()) {
			return null;
		} else {
			return Convert.toInt(map.get(map.keySet().iterator().next()));
		}
	}
	
	public Long queryForLong(String sql){
		Map map = queryForMap(sql);
		if (map == null || map.isEmpty()) {
			return null;
		} else {
			return Convert.toLong(map.get(map.keySet().iterator().next()));
		}
	}
	
	public Double queryForDouble(String sql){
		Map map = queryForMap(sql);
		if (map == null || map.isEmpty()) {
			return null;
		} else {
			return Convert.toDouble(map.get(map.keySet().iterator().next()));
		}
	}
	
	public Double queryForDouble(String sql,Map paramMap){
		Map map = queryForMap(sql,paramMap);
		if (map == null || map.isEmpty()) {
			return null;
		} else {
			return Convert.toDouble(map.get(map.keySet().iterator().next()));
		}
	}
	
	public Long queryForLong(String sql,Map paramMap){
		Map map = queryForMap(sql,paramMap);
		if (map == null || map.isEmpty()) {
			return null;
		} else {
			return Convert.toLong(map.get(map.keySet().iterator().next()));
		}
	}
	
	
	public Integer queryForInteger(String sql,Map paramMap){
		Map map = queryForMap(sql,paramMap);
		if (map == null || map.isEmpty()) {
			return null;
		} else {
			return Convert.toInt(map.get(map.keySet().iterator().next()));
		}
	}
	
	public Map<String, Object> queryForMap(String sql, Object... args) {
		try {
			return jdbcTemplate.queryForMap(sql, args);
		} catch (EmptyResultDataAccessException e) {
			return new HashMap<String, Object>(0);
		}
	}
	
	public List<Map<String, Object>> queryForList(String sql, Object... args) {
		return jdbcTemplate.queryForList(sql, args);
	}
	
	public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) {
		try {
			return jdbcTemplate.queryForObject(sql, args, requiredType);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * @param sql
	 * @param param
	 */
	public Integer execute(String sql,Map paramMap) {  
		SqlParameterSource source = new MapSqlParameterSource(paramMap);
		int r = this.paraJdbcTemplate.update(sql, source);
		return r;
	}
	
	/**
	 * 在执行insert操作之后获取最新的ID
	 * @return
	 */
	public int getCurrentPK(){
		return queryForInteger("SELECT @@IDENTITY");
	}
	
	
	public void executeBatch(String sql,List<SqlParameter> paramList){
		if (paramList == null || paramList.size() == 0) {
			return;
		}
		Map[] maps = new Map[paramList.size()];
		for (int i = 0; i < paramList.size(); i++) {
			maps[i] = paramList.get(i);
		}
		this.paraJdbcTemplate.batchUpdate(sql, maps);
	}
	
	public void executeBatch(List<String> sqlList){
		String[] sqls = new String[sqlList.size()];
		for (int i = 0; i < sqlList.size(); i++) {
			sqls[i] = sqlList.get(i);
		}
		this.jdbcTemplate.batchUpdate(sqls);
	}
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public NamedParameterJdbcTemplate getParaJdbcTemplate() {
		return paraJdbcTemplate;
	}

	public <T> List<T> queryForList(String sql, Map<String, ?> paramMap, Class<T> elementType) {
		return paraJdbcTemplate.queryForList(sql, paramMap, elementType);
	}
	
	public void setParaJdbcTemplate(NamedParameterJdbcTemplate paraJdbcTemplate) {
		this.paraJdbcTemplate = paraJdbcTemplate;
	}

}
