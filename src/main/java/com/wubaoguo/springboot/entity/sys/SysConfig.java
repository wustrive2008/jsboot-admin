package com.wubaoguo.springboot.entity.sys;

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
 * 
 */
@SuppressWarnings("unchecked")
public class SysConfig implements BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7031607188344176768L;
	public final static Map<String, String> KEYS = new HashMap<String, String>();
	private Map<String, Object> BEAN_VALUES = null;
	
	static {
		KEYS.put("id", "String");
		KEYS.put("item_key", "String");
		KEYS.put("item_value", "String");
		KEYS.put("sys_damin_id", "String");
	}
	public Map<String, String> getColumnMap(){
		return KEYS;
	}
	
	private String id;
	private Boolean isSetted_id = false;
	
	private String item_key;
	private Boolean isSetted_item_key = false;
	private String item_value;
	private Boolean isSetted_item_value = false;
	private String sys_damin_id;
	private Boolean isSetted_sys_damin_id = false;

	private void initBeanValues(){
		BEAN_VALUES = new HashMap<String, Object>();
		BEAN_VALUES.put("id",id);
			BEAN_VALUES.put("item_key", null);
			BEAN_VALUES.put("item_value", null);
			BEAN_VALUES.put("sys_damin_id", null);
	}
	
	public SysConfig() {
		initBeanValues();
	}
	
	public SysConfig(String id) {
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
	public SysConfig setId(String id) {
		this.id = id;
		this.isSetted_id = true;
		BEAN_VALUES.put("id",id);
		return this;
	}
	
	@Override
	public String getUpdateSql() {
		StringBuffer sBuffer = new StringBuffer("update sys_config set ");
			if (isSetted_item_key) {
				sBuffer.append("item_key=:item_key,");
			}
			if (isSetted_item_value) {
				sBuffer.append("item_value=:item_value,");
			}
			if (isSetted_sys_damin_id) {
				sBuffer.append("sys_damin_id=:sys_damin_id,");
			}
		String sql = sBuffer.toString();
		return StringUtils.removeEnd(sql, ",") + " where id=:id";
	}
	
	
	@Override
	public String getInsertSql() {
		StringBuffer sBuffer = new StringBuffer("insert into sys_config set ");
		StringBuffer fileds = new StringBuffer("id=:id,");
		if(BEAN_VALUES!=null){
			  	 if(BEAN_VALUES.get("item_key")!=null){
					fileds.append("item_key=:item_key,");
				  }
			  	 if(BEAN_VALUES.get("item_value")!=null){
					fileds.append("item_value=:item_value,");
				  }
			  	 if(BEAN_VALUES.get("sys_damin_id")!=null){
					fileds.append("sys_damin_id=:sys_damin_id,");
				  }
		}
		sBuffer.append(StringUtils.removeEnd(fileds.toString(), ","));
		return sBuffer.toString();
	}
	

		/**
		 * @return the item_key 系统配置项key值<BR/>
		 */
		public String getItem_key() {
			return item_key;
		}
		/**
		 * @param  item_key to item_key 系统配置项key值 set
		 */
		public SysConfig setItem_key(String item_key) {
			this.item_key = item_key;
			this.isSetted_item_key = true;
			BEAN_VALUES.put("item_key",item_key);
			return this;
		}
		/**
		 * @return the item_value 配置开启值 Y开启 N关闭<BR/>
		 */
		public String getItem_value() {
			return item_value;
		}
		/**
		 * @param  item_value to item_value 配置开启值 Y开启 N关闭 set
		 */
		public SysConfig setItem_value(String item_value) {
			this.item_value = item_value;
			this.isSetted_item_value = true;
			BEAN_VALUES.put("item_value",item_value);
			return this;
		}
		/**
		 * @return the sys_damin_id 管理员主键<BR/>
		 */
		public String getSys_damin_id() {
			return sys_damin_id;
		}
		/**
		 * @param  sys_damin_id to sys_damin_id 管理员主键 set
		 */
		public SysConfig setSys_damin_id(String sys_damin_id) {
			this.sys_damin_id = sys_damin_id;
			this.isSetted_sys_damin_id = true;
			BEAN_VALUES.put("sys_damin_id",sys_damin_id);
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
		public SysConfig getInstanceById() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("获取Bean时ID不能为空");
			}
			return dao.queryForBean("select * from " + getTableName() + " where id=:id", BEAN_VALUES, this);
		}
	
		
		
		@Override
		public SysConfig queryForBean() {
			StringBuffer sBuffer = new StringBuffer("select * from sys_config where ");
			if(isSetted_id){
				sBuffer.append("id=:id and ");
			}
				if (isSetted_item_key) {
					sBuffer.append("item_key=:item_key and ");
				}
				if (isSetted_item_value) {
					sBuffer.append("item_value=:item_value and ");
				}
				if (isSetted_sys_damin_id) {
					sBuffer.append("sys_damin_id=:sys_damin_id and ");
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
				if (isSetted_item_key) {
					sBuffer.append("item_key=:item_key and ");
				}
				if (isSetted_item_value) {
					sBuffer.append("item_value=:item_value and ");
				}
				if (isSetted_sys_damin_id) {
					sBuffer.append("sys_damin_id=:sys_damin_id and ");
				}
			
			String sql = sBuffer.toString();
			return StringUtils.removeEnd(sql, " and ");
		
		}
	
		@Override
		public String getTableName() {
			return "sys_config";
		}
		
		
		public Map<String, Object> getBeanValues(){
			return this.BEAN_VALUES;
		}
	
		@Override
		public SysConfig insert() {
			if (StringUtils.isBlank(id)) {
				this.setId(IdUtil.randomUUID());
			}
			dao.execute(getInsertSql(),BEAN_VALUES);
			return this;
		}
	
		@Override
		public SysConfig update() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("更新Bean时ID不能为空");
			}
			dao.execute(getUpdateSql(),BEAN_VALUES);
			return this;
		}  
		
		public SysConfig insertOrUpdate(){
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
			
			return dao.queryForMap("select * from sys_config where id=:id",BEAN_VALUES);
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
		
		public SysConfig newInstance(){
			return new SysConfig();
		}
		
		private static class Mapper implements RowMapper<SysConfig> {
			public SysConfig mapRow(ResultSet rs, int rownum) throws SQLException {
				SysConfig bean = new SysConfig();
				Object id = rs.getObject("id");
				bean.setId(Convert.toStr(id));
				bean.BEAN_VALUES.put("id",id);
				Object obj = null;
					obj = rs.getObject("item_key");
					bean.setItem_key(Convert.toStr(obj));
				bean.BEAN_VALUES.put("item_key",obj);
					obj = rs.getObject("item_value");
					bean.setItem_value(Convert.toStr(obj));
				bean.BEAN_VALUES.put("item_value",obj);
					obj = rs.getObject("sys_damin_id");
					bean.setSys_damin_id(Convert.toStr(obj));
				bean.BEAN_VALUES.put("sys_damin_id",obj);
				return bean;
			}
    	}	
		
		public RowMapper<SysConfig> newMapper(){
			return new SysConfig.Mapper();
		}

}