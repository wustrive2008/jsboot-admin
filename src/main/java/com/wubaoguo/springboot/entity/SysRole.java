package com.wubaoguo.springboot.entity;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import com.wubaoguo.springboot.core.dao.jdbc.bean.BaseBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 管理员角色
 */
@SuppressWarnings("unchecked")
public class SysRole implements BaseBean {

	public final static Map<String, String> KEYS = new HashMap<String, String>();
	private Map<String, Object> BEAN_VALUES = null;
	
	static {
		KEYS.put("id", "String");
		KEYS.put("name", "String");
		KEYS.put("code", "String");
		KEYS.put("is_activity", "Integer");
		KEYS.put("sort", "Integer");
	}
	public Map<String, String> getColumnMap(){
		return KEYS;
	}
	
	private String id;
	private Boolean isSetted_id = false;
	
	private String name;
	private Boolean isSetted_name = false;
	private String code;
	private Boolean isSetted_code = false;
	private Integer is_activity;
	private Boolean isSetted_is_activity = false;
	private Integer sort;
	private Boolean isSetted_sort = false;

	private void initBeanValues(){
		BEAN_VALUES = new HashMap<String, Object>();
		BEAN_VALUES.put("id",id);
			BEAN_VALUES.put("name", null);
			BEAN_VALUES.put("code", null);
			BEAN_VALUES.put("is_activity", null);
			BEAN_VALUES.put("sort", null);
	}
	
	public SysRole() {
		initBeanValues();
	}
	
	public SysRole(String id) {
		super();
		this.id = id;
		isSetted_id=true;
		initBeanValues();
		BEAN_VALUES.put("id",id);
	}

    /**
     * 获取ID
     */
	public String getId() {
		return this.id;
	}
	/**
     * 设置ID
     */
	public SysRole setId(String id) {
		this.id = id;
		this.isSetted_id = true;
		BEAN_VALUES.put("id",id);
		return this;
	}
	
	@Override
	public String getUpdateSql() {
		StringBuffer sBuffer = new StringBuffer("update sys_role set ");
			if (isSetted_name) {
				sBuffer.append("name=:name,");
			}
			if (isSetted_code) {
				sBuffer.append("code=:code,");
			}
			if (isSetted_is_activity) {
				sBuffer.append("is_activity=:is_activity,");
			}
			if (isSetted_sort) {
				sBuffer.append("sort=:sort,");
			}
		String sql = sBuffer.toString();
		return StringUtils.removeEnd(sql, ",") + " where id=:id";
	}
	
	
	@Override
	public String getInsertSql() {
		StringBuffer sBuffer = new StringBuffer("insert into sys_role set ");
		StringBuffer fileds = new StringBuffer("id=:id,");
		if(BEAN_VALUES!=null){
			  	 if(BEAN_VALUES.get("name")!=null){
					fileds.append("name=:name,");
				  }
			  	 if(BEAN_VALUES.get("code")!=null){
					fileds.append("code=:code,");
				  }
			  	 if(BEAN_VALUES.get("is_activity")!=null){
					fileds.append("is_activity=:is_activity,");
				  }
			  	 if(BEAN_VALUES.get("sort")!=null){
					fileds.append("sort=:sort,");
				  }
		}
		sBuffer.append(StringUtils.removeEnd(fileds.toString(), ","));
		return sBuffer.toString();
	}
	

		/**
		 * @return the name 角色名称<BR/>
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param  name to name 角色名称 set
		 */
		public SysRole setName(String name) {
			this.name = name;
			this.isSetted_name = true;
			BEAN_VALUES.put("name",name);
			return this;
		}
		/**
		 * @return the code 角色编码<BR/>
		 */
		public String getCode() {
			return code;
		}
		/**
		 * @param  code to code 角色编码 set
		 */
		public SysRole setCode(String code) {
			this.code = code;
			this.isSetted_code = true;
			BEAN_VALUES.put("code",code);
			return this;
		}
		/**
		 * @return the is_activity 角色活动状态 1启用2停用<BR/>
		 */
		public Integer getIs_activity() {
			return is_activity;
		}
		/**
		 * @param  is_activity to is_activity 角色活动状态 1启用2停用 set
		 */
		public SysRole setIs_activity(Integer is_activity) {
			this.is_activity = is_activity;
			this.isSetted_is_activity = true;
			BEAN_VALUES.put("is_activity",is_activity);
			return this;
		}
		/**
		 * @return the sort 排序<BR/>
		 */
		public Integer getSort() {
			return sort;
		}
		/**
		 * @param  sort to sort 排序 set
		 */
		public SysRole setSort(Integer sort) {
			this.sort = sort;
			this.isSetted_sort = true;
			BEAN_VALUES.put("sort",sort);
			return this;
		}

		/**
		 * 使用ID删除Bean<BR/>
		 */
		public void deleteById() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("删除bean时ID不能为空");
			}
			dao.execute("delete from " + getTableName() + " where id = :id", BEAN_VALUES);
		}
	
		@Override
		public SysRole getInstanceById() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("获取Bean时ID不能为空");
			}
			return dao.queryForBean("select * from " + getTableName() + " where id=:id", BEAN_VALUES, this);
		}
	
		
		
		@Override
		public SysRole queryForBean() {
			StringBuffer sBuffer = new StringBuffer("select * from sys_role where ");
			if(isSetted_id){
				sBuffer.append("id=:id and ");
			}
				if (isSetted_name) {
					sBuffer.append("name=:name and ");
				}
				if (isSetted_code) {
					sBuffer.append("code=:code and ");
				}
				if (isSetted_is_activity) {
					sBuffer.append("is_activity=:is_activity and ");
				}
				if (isSetted_sort) {
					sBuffer.append("sort=:sort and ");
				}
			String sql = sBuffer.toString();
			sql = StringUtils.removeEnd(sql, " and ");
			return dao.queryForBean(sql,this);
		}
		
		@Override
		public String getParameter(){
			StringBuffer sBuffer = new StringBuffer("");
			if(isSetted_id){
				sBuffer.append("id=:id and ");
			}
				if (isSetted_name) {
					sBuffer.append("name=:name and ");
				}
				if (isSetted_code) {
					sBuffer.append("code=:code and ");
				}
				if (isSetted_is_activity) {
					sBuffer.append("is_activity=:is_activity and ");
				}
				if (isSetted_sort) {
					sBuffer.append("sort=:sort and ");
				}
			
			String sql = sBuffer.toString();
			return StringUtils.removeEnd(sql, " and ");
		
		}
	
		@Override
		public String getTableName() {
			return "sys_role";
		}
		
		
		public Map<String, Object> getBeanValues(){
			return this.BEAN_VALUES;
		}
	
		@Override
		public SysRole insert() {
			if (StringUtils.isBlank(id)) {
				this.setId(IdUtil.randomUUID());
			}
			dao.execute(getInsertSql(),BEAN_VALUES);
			return this;
		}
	
		@Override
		public SysRole update() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("更新Bean时ID不能为空");
			}
			dao.execute(getUpdateSql(),BEAN_VALUES);
			return this;
		}  
		
		public SysRole insertOrUpdate(){
			if (StringUtils.isNotBlank(id)) {
				return update();
			} else {
				return insert();
			}
		}
		
		/**
		 * 通过ID获取该条信息的Map结构
		 */
		public Map<String, Object> getBeanMapById() {
			
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("ID不能为空!");
			}
			
			return dao.queryForMap("select * from sys_role where id=:id",BEAN_VALUES);
		}

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer("[");
			for (Iterator<String> iterator = KEYS.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				sb.append(key+"=" + BEAN_VALUES.get(key)+",");
			}
			sb.append("]");
			return sb.toString();
		}
		
		public SysRole newInstance(){
			return new SysRole();
		}
		
		private static class Mapper implements RowMapper<SysRole> {
			public SysRole mapRow(ResultSet rs, int rownum) throws SQLException {
				SysRole bean = new SysRole();
				Object id = rs.getObject("id");
				bean.setId(Convert.toStr(id));
				bean.BEAN_VALUES.put("id",id);
				Object obj = null;
					obj = rs.getObject("name");
					bean.setName(Convert.toStr(obj));
				bean.BEAN_VALUES.put("name",obj);
					obj = rs.getObject("code");
					bean.setCode(Convert.toStr(obj));
				bean.BEAN_VALUES.put("code",obj);
						obj = rs.getInt("is_activity");
					bean.setIs_activity(Convert.toInt(obj));
				bean.BEAN_VALUES.put("is_activity",obj);
						obj = rs.getObject("sort");
					bean.setSort(Convert.toInt(obj));
				bean.BEAN_VALUES.put("sort",obj);
				return bean;
			}
    	}	
		
		public RowMapper<SysRole> newMapper(){
			return new SysRole.Mapper();
		}

}