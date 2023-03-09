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
 * 角色资源管理表
 */
@SuppressWarnings("unchecked")
public class SysRoleResources implements BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9054978751922104559L;
	public final static Map<String, String> KEYS = new HashMap<String, String>();
	private Map<String, Object> BEAN_VALUES = null;
	
	static {
		KEYS.put("id", "String");
		KEYS.put("role_code", "String");
		KEYS.put("resources_id", "String");
	}
	public Map<String, String> getColumnMap(){
		return KEYS;
	}
	
	private String id;
	private Boolean isSetted_id = false;
	
	private String role_code;
	private Boolean isSetted_role_code = false;
	private String resources_id;
	private Boolean isSetted_resources_id = false;

	private void initBeanValues(){
		BEAN_VALUES = new HashMap<String, Object>();
		BEAN_VALUES.put("id",id);
			BEAN_VALUES.put("role_code", null);
			BEAN_VALUES.put("resources_id", null);
	}
	
	public SysRoleResources() {
		initBeanValues();
	}
	
	public SysRoleResources(String id) {
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
	public SysRoleResources setId(String id) {
		this.id = id;
		this.isSetted_id = true;
		BEAN_VALUES.put("id",id);
		return this;
	}
	
	@Override
	public String getUpdateSql() {
		StringBuffer sBuffer = new StringBuffer("update sys_role_resources set ");
			if (isSetted_role_code) {
				sBuffer.append("role_code=:role_code,");
			}
			if (isSetted_resources_id) {
				sBuffer.append("resources_id=:resources_id,");
			}
		String sql = sBuffer.toString();
		return StringUtils.removeEnd(sql, ",") + " where id=:id";
	}
	
	
	@Override
	public String getInsertSql() {
		StringBuffer sBuffer = new StringBuffer("insert into sys_role_resources set ");
		StringBuffer fileds = new StringBuffer("id=:id,");
		if(BEAN_VALUES!=null){
			  	 if(BEAN_VALUES.get("role_code")!=null){
					fileds.append("role_code=:role_code,");
				  }
			  	 if(BEAN_VALUES.get("resources_id")!=null){
					fileds.append("resources_id=:resources_id,");
				  }
		}
		sBuffer.append(StringUtils.removeEnd(fileds.toString(), ","));
		return sBuffer.toString();
	}
	

		/**
		 * @return the role_code 角色编码<BR/>
		 */
		public String getRole_code() {
			return role_code;
		}
		/**
		 * @param  role_code to role_code 角色编码 set
		 */
		public SysRoleResources setRole_code(String role_code) {
			this.role_code = role_code;
			this.isSetted_role_code = true;
			BEAN_VALUES.put("role_code",role_code);
			return this;
		}
		/**
		 * @return the resources_id 资源主键<BR/>
		 */
		public String getResources_id() {
			return resources_id;
		}
		/**
		 * @param  resources_id to resources_id 资源主键 set
		 */
		public SysRoleResources setResources_id(String resources_id) {
			this.resources_id = resources_id;
			this.isSetted_resources_id = true;
			BEAN_VALUES.put("resources_id",resources_id);
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
		public SysRoleResources getInstanceById() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("获取Bean时ID不能为空");
			}
			return dao.queryForBean("select * from " + getTableName() + " where id=:id", BEAN_VALUES, this);
		}
	
		
		
		@Override
		public SysRoleResources queryForBean() {
			StringBuffer sBuffer = new StringBuffer("select * from sys_role_resources where ");
			if(isSetted_id){
				sBuffer.append("id=:id and ");
			}
				if (isSetted_role_code) {
					sBuffer.append("role_code=:role_code and ");
				}
				if (isSetted_resources_id) {
					sBuffer.append("resources_id=:resources_id and ");
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
				if (isSetted_role_code) {
					sBuffer.append("role_code=:role_code and ");
				}
				if (isSetted_resources_id) {
					sBuffer.append("resources_id=:resources_id and ");
				}
			
			String sql = sBuffer.toString();
			return StringUtils.removeEnd(sql, " and ");
		
		}
	
		@Override
		public String getTableName() {
			return "sys_role_resources";
		}
		
		
		public Map<String, Object> getBeanValues(){
			return this.BEAN_VALUES;
		}
	
		@Override
		public SysRoleResources insert() {
			if (StringUtils.isBlank(id)) {
				this.setId(IdUtil.randomUUID());
			}
			dao.execute(getInsertSql(),BEAN_VALUES);
			return this;
		}
	
		@Override
		public SysRoleResources update() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("更新Bean时ID不能为空");
			}
			dao.execute(getUpdateSql(),BEAN_VALUES);
			return this;
		}  
		
		public SysRoleResources insertOrUpdate(){
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
			
			return dao.queryForMap("select * from sys_role_resources where id=:id",BEAN_VALUES);
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
		
		public SysRoleResources newInstance(){
			return new SysRoleResources();
		}
		
		private static class Mapper implements RowMapper<SysRoleResources> {
			public SysRoleResources mapRow(ResultSet rs, int rownum) throws SQLException {
				SysRoleResources bean = new SysRoleResources();
				Object id = rs.getObject("id");
				bean.setId(Convert.toStr(id));
				bean.BEAN_VALUES.put("id",id);
				Object obj = null;
					obj = rs.getObject("role_code");
					bean.setRole_code(Convert.toStr(obj));
				bean.BEAN_VALUES.put("role_code",obj);
					obj = rs.getObject("resources_id");
					bean.setResources_id(Convert.toStr(obj));
				bean.BEAN_VALUES.put("resources_id",obj);
				return bean;
			}
    	}	
		
		public RowMapper<SysRoleResources> newMapper(){
			return new SysRoleResources.Mapper();
		}

}