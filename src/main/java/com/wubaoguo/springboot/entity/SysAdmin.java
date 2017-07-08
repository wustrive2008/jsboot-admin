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
 * 后台管理员表
 */
@SuppressWarnings("unchecked")
public class SysAdmin implements BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5137957282828882736L;
	public final static Map<String, String> KEYS = new HashMap<String, String>();
	private Map<String, Object> BEAN_VALUES = null;
	
	static {
		KEYS.put("id", "String");
		KEYS.put("name", "String");
		KEYS.put("phone_number", "String");
		KEYS.put("account", "String");
		KEYS.put("password", "String");
		KEYS.put("is_activity", "Integer");
		KEYS.put("add_time", "Long");
	}
	public Map<String, String> getColumnMap(){
		return KEYS;
	}
	
	private String id;
	private Boolean isSetted_id = false;
	
	private String name;
	private Boolean isSetted_name = false;
	private String phone_number;
	private Boolean isSetted_phone_number = false;
	private String account;
	private Boolean isSetted_account = false;
	private String password;
	private Boolean isSetted_password = false;
	private Integer is_activity;
	private Boolean isSetted_is_activity = false;
	private Long add_time;
	private Boolean isSetted_add_time = false;

	private void initBeanValues(){
		BEAN_VALUES = new HashMap<String, Object>();
		BEAN_VALUES.put("id",id);
			BEAN_VALUES.put("name", null);
			BEAN_VALUES.put("phone_number", null);
			BEAN_VALUES.put("account", null);
			BEAN_VALUES.put("password", null);
			BEAN_VALUES.put("is_activity", null);
			BEAN_VALUES.put("add_time", null);
	}
	
	public SysAdmin() {
		initBeanValues();
	}
	
	public SysAdmin(String id) {
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
	public SysAdmin setId(String id) {
		this.id = id;
		this.isSetted_id = true;
		BEAN_VALUES.put("id",id);
		return this;
	}
	
	@Override
	public String getUpdateSql() {
		StringBuffer sBuffer = new StringBuffer("update sys_admin set ");
			if (isSetted_name) {
				sBuffer.append("name=:name,");
			}
			if (isSetted_phone_number) {
				sBuffer.append("phone_number=:phone_number,");
			}
			if (isSetted_account) {
				sBuffer.append("account=:account,");
			}
			if (isSetted_password) {
				sBuffer.append("password=:password,");
			}
			if (isSetted_is_activity) {
				sBuffer.append("is_activity=:is_activity,");
			}
			if (isSetted_add_time) {
				sBuffer.append("add_time=:add_time,");
			}
		String sql = sBuffer.toString();
		return StringUtils.removeEnd(sql, ",") + " where id=:id";
	}
	
	
	@Override
	public String getInsertSql() {
		StringBuffer sBuffer = new StringBuffer("insert into sys_admin set ");
		StringBuffer fileds = new StringBuffer("id=:id,");
		if(BEAN_VALUES!=null){
			  	 if(BEAN_VALUES.get("name")!=null){
					fileds.append("name=:name,");
				  }
			  	 if(BEAN_VALUES.get("phone_number")!=null){
					fileds.append("phone_number=:phone_number,");
				  }
			  	 if(BEAN_VALUES.get("account")!=null){
					fileds.append("account=:account,");
				  }
			  	 if(BEAN_VALUES.get("password")!=null){
					fileds.append("password=:password,");
				  }
			  	 if(BEAN_VALUES.get("is_activity")!=null){
					fileds.append("is_activity=:is_activity,");
				  }
			  	 if(BEAN_VALUES.get("add_time")!=null){
					fileds.append("add_time=:add_time,");
				  }
		}
		sBuffer.append(StringUtils.removeEnd(fileds.toString(), ","));
		return sBuffer.toString();
	}
	

		/**
		 * @return the name 管理员名称<BR/>
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param  the name to name 管理员名称 set
		 */
		public SysAdmin setName(String name) {
			this.name = name;
			this.isSetted_name = true;
			BEAN_VALUES.put("name",name);
			return this;
		}
		/**
		 * @return the phone_number 手机号码<BR/>
		 */
		public String getPhone_number() {
			return phone_number;
		}
		/**
		 * @param  the phone_number to phone_number 手机号码 set
		 */
		public SysAdmin setPhone_number(String phone_number) {
			this.phone_number = phone_number;
			this.isSetted_phone_number = true;
			BEAN_VALUES.put("phone_number",phone_number);
			return this;
		}
		/**
		 * @return the account 管理员帐号<BR/>
		 */
		public String getAccount() {
			return account;
		}
		/**
		 * @param  the account to account 管理员帐号 set
		 */
		public SysAdmin setAccount(String account) {
			this.account = account;
			this.isSetted_account = true;
			BEAN_VALUES.put("account",account);
			return this;
		}
		/**
		 * @return the password 管理员登录密码，MD5加密串<BR/>
		 */
		public String getPassword() {
			return password;
		}
		/**
		 * @param  the password to password 管理员登录密码，MD5加密串 set
		 */
		public SysAdmin setPassword(String password) {
			this.password = password;
			this.isSetted_password = true;
			BEAN_VALUES.put("password",password);
			return this;
		}
		/**
		 * @return the is_activity 1启用，2作废<BR/>
		 */
		public Integer getIs_activity() {
			return is_activity;
		}
		/**
		 * @param  the is_activity to is_activity 1启用，2作废 set
		 */
		public SysAdmin setIs_activity(Integer is_activity) {
			this.is_activity = is_activity;
			this.isSetted_is_activity = true;
			BEAN_VALUES.put("is_activity",is_activity);
			return this;
		}
		/**
		 * @return the add_time 添加时间<BR/>
		 */
		public Long getAdd_time() {
			return add_time;
		}
		/**
		 * @param  the add_time to add_time 添加时间 set
		 */
		public SysAdmin setAdd_time(Long add_time) {
			this.add_time = add_time;
			this.isSetted_add_time = true;
			BEAN_VALUES.put("add_time",add_time);
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
		public SysAdmin getInstanceById() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("获取Bean时ID不能为空");
			}
			return dao.queryForBean("select * from " + getTableName() + " where id=:id", BEAN_VALUES, this);
		}
	
		
		
		@Override
		public SysAdmin queryForBean() {
			StringBuffer sBuffer = new StringBuffer("select * from sys_admin where ");
			if(isSetted_id){
				sBuffer.append("id=:id and ");
			}
				if (isSetted_name) {
					sBuffer.append("name=:name and ");
				}
				if (isSetted_phone_number) {
					sBuffer.append("phone_number=:phone_number and ");
				}
				if (isSetted_account) {
					sBuffer.append("account=:account and ");
				}
				if (isSetted_password) {
					sBuffer.append("password=:password and ");
				}
				if (isSetted_is_activity) {
					sBuffer.append("is_activity=:is_activity and ");
				}
				if (isSetted_add_time) {
					sBuffer.append("add_time=:add_time and ");
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
				if (isSetted_phone_number) {
					sBuffer.append("phone_number=:phone_number and ");
				}
				if (isSetted_account) {
					sBuffer.append("account=:account and ");
				}
				if (isSetted_password) {
					sBuffer.append("password=:password and ");
				}
				if (isSetted_is_activity) {
					sBuffer.append("is_activity=:is_activity and ");
				}
				if (isSetted_add_time) {
					sBuffer.append("add_time=:add_time and ");
				}
			
			String sql = sBuffer.toString();
			return StringUtils.removeEnd(sql, " and ");
		
		}
	
		@Override
		public String getTableName() {
			return "sys_admin";
		}
		
		
		public Map<String, Object> getBeanValues(){
			return this.BEAN_VALUES;
		}
	
		@Override
		public SysAdmin insert() {
			if (StringUtils.isBlank(id)) {
				this.setId(StringUtil.getUUID());
			}
			dao.execute(getInsertSql(),BEAN_VALUES);
			return this;
		}
	
		@Override
		public SysAdmin update() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("更新Bean时ID不能为空");
			}
			dao.execute(getUpdateSql(),BEAN_VALUES);
			return this;
		}  
		
		public SysAdmin insertOrUpdate(){
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
			
			return dao.queryForMap("select * from sys_admin where id=:id",BEAN_VALUES);
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
		
		public SysAdmin newInstance(){
			return new SysAdmin();
		}
		
		private static class Mapper implements RowMapper<SysAdmin> {
			public SysAdmin mapRow(ResultSet rs, int rownum) throws SQLException {
				SysAdmin bean = new SysAdmin();
				Object id = rs.getObject("id");
				bean.setId(ConvertUtil.obj2Str(id));
				bean.BEAN_VALUES.put("id",id);
				Object obj = null;
					obj = rs.getObject("name");
					bean.setName(ConvertUtil.obj2Str(obj));
				bean.BEAN_VALUES.put("name",obj);
					obj = rs.getObject("phone_number");
					bean.setPhone_number(ConvertUtil.obj2Str(obj));
				bean.BEAN_VALUES.put("phone_number",obj);
					obj = rs.getObject("account");
					bean.setAccount(ConvertUtil.obj2Str(obj));
				bean.BEAN_VALUES.put("account",obj);
					obj = rs.getObject("password");
					bean.setPassword(ConvertUtil.obj2Str(obj));
				bean.BEAN_VALUES.put("password",obj);
						obj = rs.getInt("is_activity");
					bean.setIs_activity(ConvertUtil.obj2Integer(obj));
				bean.BEAN_VALUES.put("is_activity",obj);
					obj = rs.getObject("add_time");
					bean.setAdd_time(ConvertUtil.obj2Long(obj));
				bean.BEAN_VALUES.put("add_time",obj);
				return bean;
			}
    	}	
		
		public RowMapper<SysAdmin> newMapper(){
			return new SysAdmin.Mapper();
		}

}