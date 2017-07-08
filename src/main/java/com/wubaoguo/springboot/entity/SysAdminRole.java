package com.wubaoguo.springboot.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.wustrive.java.common.util.ConvertUtil;
import org.wustrive.java.common.util.StringUtil;
import org.wustrive.java.dao.jdbc.bean.BaseBean;

/**
 * 管理员角色
 */
@SuppressWarnings("unchecked")
public class SysAdminRole implements BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5150042652592955454L;
	public final static Map<String, String> KEYS = new HashMap<String, String>();
	private Map<String, Object> BEAN_VALUES = null;
	
	static {
		KEYS.put("id", "String");
		KEYS.put("admin_id", "String");
		KEYS.put("code", "String");
	}
	public Map<String, String> getColumnMap(){
		return KEYS;
	}
	
	private String id;
	private Boolean isSetted_id = false;
	
	private String admin_id;
	private Boolean isSetted_admin_id = false;
	private String code;
	private Boolean isSetted_code = false;

	private void initBeanValues(){
		BEAN_VALUES = new HashMap<String, Object>();
		BEAN_VALUES.put("id",id);
			BEAN_VALUES.put("admin_id", null);
			BEAN_VALUES.put("code", null);
	}
	
	public SysAdminRole() {
		initBeanValues();
	}
	
	public SysAdminRole(String id) {
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
	public SysAdminRole setId(String id) {
		this.id = id;
		this.isSetted_id = true;
		BEAN_VALUES.put("id",id);
		return this;
	}
	
	@Override
	public String getUpdateSql() {
		StringBuffer sBuffer = new StringBuffer("update sys_admin_role set ");
			if (isSetted_admin_id) {
				sBuffer.append("admin_id=:admin_id,");
			}
			if (isSetted_code) {
				sBuffer.append("code=:code,");
			}
		String sql = sBuffer.toString();
		return StringUtils.removeEnd(sql, ",") + " where id=:id";
	}
	
	
	@Override
	public String getInsertSql() {
		StringBuffer sBuffer = new StringBuffer("insert into sys_admin_role set ");
		StringBuffer fileds = new StringBuffer("id=:id,");
		if(BEAN_VALUES!=null){
			  	 if(BEAN_VALUES.get("admin_id")!=null){
					fileds.append("admin_id=:admin_id,");
				  }
			  	 if(BEAN_VALUES.get("code")!=null){
					fileds.append("code=:code,");
				  }
		}
		sBuffer.append(StringUtils.removeEnd(fileds.toString(), ","));
		return sBuffer.toString();
	}
	

		/**
		 * @return the admin_id <BR/>
		 */
		public String getAdmin_id() {
			return admin_id;
		}
		/**
		 * @param  the admin_id to admin_id  set
		 */
		public SysAdminRole setAdmin_id(String admin_id) {
			this.admin_id = admin_id;
			this.isSetted_admin_id = true;
			BEAN_VALUES.put("admin_id",admin_id);
			return this;
		}
		/**
		 * @return the code 角色编码<BR/>
		 */
		public String getCode() {
			return code;
		}
		/**
		 * @param  the code to code 角色编码 set
		 */
		public SysAdminRole setCode(String code) {
			this.code = code;
			this.isSetted_code = true;
			BEAN_VALUES.put("code",code);
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
		public SysAdminRole getInstanceById() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("获取Bean时ID不能为空");
			}
			return dao.queryForBean("select * from " + getTableName() + " where id=:id", BEAN_VALUES, this);
		}
	
		
		
		@Override
		public SysAdminRole queryForBean() {
			StringBuffer sBuffer = new StringBuffer("select * from sys_admin_role where ");
			if(isSetted_id){
				sBuffer.append("id=:id and ");
			}
				if (isSetted_admin_id) {
					sBuffer.append("admin_id=:admin_id and ");
				}
				if (isSetted_code) {
					sBuffer.append("code=:code and ");
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
				if (isSetted_admin_id) {
					sBuffer.append("admin_id=:admin_id and ");
				}
				if (isSetted_code) {
					sBuffer.append("code=:code and ");
				}
			
			String sql = sBuffer.toString();
			return StringUtils.removeEnd(sql, " and ");
		
		}
	
		@Override
		public String getTableName() {
			return "sys_admin_role";
		}
		
		
		public Map<String, Object> getBeanValues(){
			return this.BEAN_VALUES;
		}
	
		@Override
		public SysAdminRole insert() {
			if (StringUtils.isBlank(id)) {
				this.setId(StringUtil.getUUID());
			}
			dao.execute(getInsertSql(),BEAN_VALUES);
			return this;
		}
	
		@Override
		public SysAdminRole update() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("更新Bean时ID不能为空");
			}
			dao.execute(getUpdateSql(),BEAN_VALUES);
			return this;
		}  
		
		public SysAdminRole insertOrUpdate(){
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
			
			return dao.queryForMap("select * from sys_admin_role where id=:id",BEAN_VALUES);
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
		
		public SysAdminRole newInstance(){
			return new SysAdminRole();
		}
		
		private static class Mapper implements RowMapper<SysAdminRole> {
			public SysAdminRole mapRow(ResultSet rs, int rownum) throws SQLException {
				SysAdminRole bean = new SysAdminRole();
				Object id = rs.getObject("id");
				bean.setId(ConvertUtil.obj2Str(id));
				bean.BEAN_VALUES.put("id",id);
				Object obj = null;
					obj = rs.getObject("admin_id");
					bean.setAdmin_id(ConvertUtil.obj2Str(obj));
				bean.BEAN_VALUES.put("admin_id",obj);
					obj = rs.getObject("code");
					bean.setCode(ConvertUtil.obj2Str(obj));
				bean.BEAN_VALUES.put("code",obj);
				return bean;
			}
    	}	
		
		public RowMapper<SysAdminRole> newMapper(){
			return new SysAdminRole.Mapper();
		}

}