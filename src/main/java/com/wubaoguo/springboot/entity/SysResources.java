package com.wubaoguo.springboot.entity;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import com.wubaoguo.springboot.dao.jdbc.bean.BaseBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 管理员资源表
 */
@SuppressWarnings("unchecked")
public class SysResources implements BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1030412062898651592L;
	public final static Map<String, String> KEYS = new HashMap<String, String>();
	private Map<String, Object> BEAN_VALUES = null;
	
	static {
		KEYS.put("id", "String");
		KEYS.put("menu_name", "String");
		KEYS.put("uri", "String");
		KEYS.put("is_activity", "Integer");
		KEYS.put("group_id", "String");
		KEYS.put("sort", "Integer");
		KEYS.put("icon", "String");
		KEYS.put("code", "String");
	}
	
	private List<SysResources> childrens;
	
	public Map<String, String> getColumnMap(){
		return KEYS;
	}
	
	private String id;
	private Boolean isSetted_id = false;
	
	private String menu_name;
	private Boolean isSetted_menu_name = false;
	private String uri;
	private Boolean isSetted_uri = false;
	private Integer is_activity;
	private Boolean isSetted_is_activity = false;
	private String group_id;
	private Boolean isSetted_group_id = false;
	private Integer sort;
	private Boolean isSetted_sort = false;
	private String icon;
	private Boolean isSetted_icon = false;
	private String code;
	private Boolean isSetted_code = false;

	private void initBeanValues(){
		BEAN_VALUES = new HashMap<String, Object>();
		BEAN_VALUES.put("id",id);
			BEAN_VALUES.put("menu_name", null);
			BEAN_VALUES.put("uri", null);
			BEAN_VALUES.put("is_activity", null);
			BEAN_VALUES.put("group_id", null);
			BEAN_VALUES.put("sort", null);
			BEAN_VALUES.put("icon", null);
			BEAN_VALUES.put("code", null);
	}
	
	public SysResources() {
		initBeanValues();
	}
	
	public SysResources(String id) {
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
	public SysResources setId(String id) {
		this.id = id;
		this.isSetted_id = true;
		BEAN_VALUES.put("id",id);
		return this;
	}
	
	@Override
	public String getUpdateSql() {
		StringBuffer sBuffer = new StringBuffer("update sys_resources set ");
			if (isSetted_menu_name) {
				sBuffer.append("menu_name=:menu_name,");
			}
			if (isSetted_uri) {
				sBuffer.append("uri=:uri,");
			}
			if (isSetted_is_activity) {
				sBuffer.append("is_activity=:is_activity,");
			}
			if (isSetted_group_id) {
				sBuffer.append("group_id=:group_id,");
			}
			if (isSetted_sort) {
				sBuffer.append("sort=:sort,");
			}
			if (isSetted_icon) {
				sBuffer.append("icon=:icon,");
			}
			if (isSetted_code) {
				sBuffer.append("code=:code,");
			}
		String sql = sBuffer.toString();
		return StringUtils.removeEnd(sql, ",") + " where id=:id";
	}
	
	
	@Override
	public String getInsertSql() {
		StringBuffer sBuffer = new StringBuffer("insert into sys_resources set ");
		StringBuffer fileds = new StringBuffer("id=:id,");
		if(BEAN_VALUES!=null){
			  	 if(BEAN_VALUES.get("menu_name")!=null){
					fileds.append("menu_name=:menu_name,");
				  }
			  	 if(BEAN_VALUES.get("uri")!=null){
					fileds.append("uri=:uri,");
				  }
			  	 if(BEAN_VALUES.get("is_activity")!=null){
					fileds.append("is_activity=:is_activity,");
				  }
			  	 if(BEAN_VALUES.get("group_id")!=null){
					fileds.append("group_id=:group_id,");
				  }
			  	 if(BEAN_VALUES.get("sort")!=null){
					fileds.append("sort=:sort,");
				  }
			  	 if(BEAN_VALUES.get("icon")!=null){
					fileds.append("icon=:icon,");
				  }
			  	 if(BEAN_VALUES.get("code")!=null){
					fileds.append("code=:code,");
				  }
		}
		sBuffer.append(StringUtils.removeEnd(fileds.toString(), ","));
		return sBuffer.toString();
	}
	

		/**
		 * @return the menu_name 菜单名称，菜单显示名称<BR/>
		 */
		public String getMenu_name() {
			return menu_name;
		}
		/**
		 * @param  menu_name to menu_name 菜单名称，菜单显示名称 set
		 */
		public SysResources setMenu_name(String menu_name) {
			this.menu_name = menu_name;
			this.isSetted_menu_name = true;
			BEAN_VALUES.put("menu_name",menu_name);
			return this;
		}
		/**
		 * @return the uri 菜单uri地址<BR/>
		 */
		public String getUri() {
			return uri;
		}
		/**
		 * @param  uri to uri 菜单uri地址 set
		 */
		public SysResources setUri(String uri) {
			this.uri = uri;
			this.isSetted_uri = true;
			BEAN_VALUES.put("uri",uri);
			return this;
		}
		/**
		 * @return the is_activity 活动状态 1 活动 2 作废<BR/>
		 */
		public Integer getIs_activity() {
			return is_activity;
		}
		/**
		 * @param  is_activity to is_activity 活动状态 1 活动 2 作废 set
		 */
		public SysResources setIs_activity(Integer is_activity) {
			this.is_activity = is_activity;
			this.isSetted_is_activity = true;
			BEAN_VALUES.put("is_activity",is_activity);
			return this;
		}
		/**
		 * @return the group_id 资源组主键<BR/>
		 */
		public String getGroup_id() {
			return group_id;
		}
		/**
		 * @param  group_id to group_id 资源组主键 set
		 */
		public SysResources setGroup_id(String group_id) {
			this.group_id = group_id;
			this.isSetted_group_id = true;
			BEAN_VALUES.put("group_id",group_id);
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
		public SysResources setSort(Integer sort) {
			this.sort = sort;
			this.isSetted_sort = true;
			BEAN_VALUES.put("sort",sort);
			return this;
		}
		/**
		 * @return the icon 显示图标<BR/>
		 */
		public String getIcon() {
			return icon;
		}
		/**
		 * @param  icon to icon 显示图标 set
		 */
		public SysResources setIcon(String icon) {
			this.icon = icon;
			this.isSetted_icon = true;
			BEAN_VALUES.put("icon",icon);
			return this;
		}
		/**
		 * @return the code 资源编码<BR/>
		 */
		public String getCode() {
			return code;
		}
		/**
		 * @param  code to code 资源编码 set
		 */
		public SysResources setCode(String code) {
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
		public SysResources getInstanceById() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("获取Bean时ID不能为空");
			}
			return dao.queryForBean("select * from " + getTableName() + " where id=:id", BEAN_VALUES, this);
		}
	
		
		
		@Override
		public SysResources queryForBean() {
			StringBuffer sBuffer = new StringBuffer("select * from sys_resources where ");
			if(isSetted_id){
				sBuffer.append("id=:id and ");
			}
				if (isSetted_menu_name) {
					sBuffer.append("menu_name=:menu_name and ");
				}
				if (isSetted_uri) {
					sBuffer.append("uri=:uri and ");
				}
				if (isSetted_is_activity) {
					sBuffer.append("is_activity=:is_activity and ");
				}
				if (isSetted_group_id) {
					sBuffer.append("group_id=:group_id and ");
				}
				if (isSetted_sort) {
					sBuffer.append("sort=:sort and ");
				}
				if (isSetted_icon) {
					sBuffer.append("icon=:icon and ");
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
				if (isSetted_menu_name) {
					sBuffer.append("menu_name=:menu_name and ");
				}
				if (isSetted_uri) {
					sBuffer.append("uri=:uri and ");
				}
				if (isSetted_is_activity) {
					sBuffer.append("is_activity=:is_activity and ");
				}
				if (isSetted_group_id) {
					sBuffer.append("group_id=:group_id and ");
				}
				if (isSetted_sort) {
					sBuffer.append("sort=:sort and ");
				}
				if (isSetted_icon) {
					sBuffer.append("icon=:icon and ");
				}
				if (isSetted_code) {
					sBuffer.append("code=:code and ");
				}
			
			String sql = sBuffer.toString();
			return StringUtils.removeEnd(sql, " and ");
		
		}
	
		@Override
		public String getTableName() {
			return "sys_resources";
		}
		
		
		public Map<String, Object> getBeanValues(){
			return this.BEAN_VALUES;
		}
	
		@Override
		public SysResources insert() {
			if (StringUtils.isBlank(id)) {
				this.setId(IdUtil.randomUUID());
			}
			dao.execute(getInsertSql(),BEAN_VALUES);
			return this;
		}
	
		@Override
		public SysResources update() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("更新Bean时ID不能为空");
			}
			dao.execute(getUpdateSql(),BEAN_VALUES);
			return this;
		}  
		
		public SysResources insertOrUpdate(){
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
			
			return dao.queryForMap("select * from sys_resources where id=:id",BEAN_VALUES);
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
		
		public SysResources newInstance(){
			return new SysResources();
		}
		
		private static class Mapper implements RowMapper<SysResources> {
			public SysResources mapRow(ResultSet rs, int rownum) throws SQLException {
				SysResources bean = new SysResources();
				Object id = rs.getObject("id");
				bean.setId(Convert.toStr(id));
				bean.BEAN_VALUES.put("id",id);
				Object obj = null;
					obj = rs.getObject("menu_name");
					bean.setMenu_name(Convert.toStr(obj));
				bean.BEAN_VALUES.put("menu_name",obj);
					obj = rs.getObject("uri");
					bean.setUri(Convert.toStr(obj));
				bean.BEAN_VALUES.put("uri",obj);
						obj = rs.getInt("is_activity");
					bean.setIs_activity(Convert.toInt(obj));
				bean.BEAN_VALUES.put("is_activity",obj);
					obj = rs.getObject("group_id");
					bean.setGroup_id(Convert.toStr(obj));
				bean.BEAN_VALUES.put("group_id",obj);
						obj = rs.getObject("sort");
					bean.setSort(Convert.toInt(obj));
				bean.BEAN_VALUES.put("sort",obj);
					obj = rs.getObject("icon");
					bean.setIcon(Convert.toStr(obj));
				bean.BEAN_VALUES.put("icon",obj);
					obj = rs.getObject("code");
					bean.setCode(Convert.toStr(obj));
				bean.BEAN_VALUES.put("code",obj);
				return bean;
			}
    	}	
		
		public RowMapper<SysResources> newMapper(){
			return new SysResources.Mapper();
		}

		public List<SysResources> getChildrens() {
			return childrens;
		}

		public void setChildrens(List<SysResources> childrens) {
			this.childrens = childrens;
		}
		
}