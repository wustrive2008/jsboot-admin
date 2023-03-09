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
 * 数据词典
 */
@SuppressWarnings("unchecked")
public class SysDictionary implements BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3295396175205858793L;
	public final static Map<String, String> KEYS = new HashMap<String, String>();
	private Map<String, Object> BEAN_VALUES = null;
	
	static {
		KEYS.put("id", "String");
		KEYS.put("title", "String");
		KEYS.put("tag_code", "String");
		KEYS.put("description", "String");
		KEYS.put("res_code", "String");
		KEYS.put("content", "String");
		KEYS.put("add_time", "Long");
	}
	public Map<String, String> getColumnMap(){
		return KEYS;
	}
	
	private String id;
	private Boolean isSetted_id = false;
	
	private String title;
	private Boolean isSetted_title = false;
	private String tag_code;
	private Boolean isSetted_tag_code = false;
	private String description;
	private Boolean isSetted_description = false;
	private String res_code;
	private Boolean isSetted_res_code = false;
	private String content;
	private Boolean isSetted_content = false;
	private Long add_time;
	private Boolean isSetted_add_time = false;

	private void initBeanValues(){
		BEAN_VALUES = new HashMap<String, Object>();
		BEAN_VALUES.put("id",id);
			BEAN_VALUES.put("title", null);
			BEAN_VALUES.put("tag_code", null);
			BEAN_VALUES.put("description", null);
			BEAN_VALUES.put("res_code", null);
			BEAN_VALUES.put("content", null);
			BEAN_VALUES.put("add_time", null);
	}
	
	public SysDictionary() {
		initBeanValues();
	}
	
	public SysDictionary(String id) {
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
	public SysDictionary setId(String id) {
		this.id = id;
		this.isSetted_id = true;
		BEAN_VALUES.put("id",id);
		return this;
	}
	
	@Override
	public String getUpdateSql() {
		StringBuffer sBuffer = new StringBuffer("update sys_dictionary set ");
			if (isSetted_title) {
				sBuffer.append("title=:title,");
			}
			if (isSetted_tag_code) {
				sBuffer.append("tag_code=:tag_code,");
			}
			if (isSetted_description) {
				sBuffer.append("description=:description,");
			}
			if (isSetted_res_code) {
				sBuffer.append("res_code=:res_code,");
			}
			if (isSetted_content) {
				sBuffer.append("content=:content,");
			}
			if (isSetted_add_time) {
				sBuffer.append("add_time=:add_time,");
			}
		String sql = sBuffer.toString();
		return StringUtils.removeEnd(sql, ",") + " where id=:id";
	}
	
	
	@Override
	public String getInsertSql() {
		StringBuffer sBuffer = new StringBuffer("insert into sys_dictionary set ");
		StringBuffer fileds = new StringBuffer("id=:id,");
		if(BEAN_VALUES!=null){
			  	 if(BEAN_VALUES.get("title")!=null){
					fileds.append("title=:title,");
				  }
			  	 if(BEAN_VALUES.get("tag_code")!=null){
					fileds.append("tag_code=:tag_code,");
				  }
			  	 if(BEAN_VALUES.get("description")!=null){
					fileds.append("description=:description,");
				  }
			  	 if(BEAN_VALUES.get("res_code")!=null){
					fileds.append("res_code=:res_code,");
				  }
			  	 if(BEAN_VALUES.get("content")!=null){
					fileds.append("content=:content,");
				  }
			  	 if(BEAN_VALUES.get("add_time")!=null){
					fileds.append("add_time=:add_time,");
				  }
		}
		sBuffer.append(StringUtils.removeEnd(fileds.toString(), ","));
		return sBuffer.toString();
	}
	

		/**
		 * @return the title 词典标题<BR/>
		 */
		public String getTitle() {
			return title;
		}
		/**
		 * @param  title to title 词典标题 set
		 */
		public SysDictionary setTitle(String title) {
			this.title = title;
			this.isSetted_title = true;
			BEAN_VALUES.put("title",title);
			return this;
		}
		/**
		 * @return the tag_code 字典 编码 唯一标识<BR/>
		 */
		public String getTag_code() {
			return tag_code;
		}
		/**
		 * @param  tag_code to tag_code 字典 编码 唯一标识 set
		 */
		public SysDictionary setTag_code(String tag_code) {
			this.tag_code = tag_code;
			this.isSetted_tag_code = true;
			BEAN_VALUES.put("tag_code",tag_code);
			return this;
		}
		/**
		 * @return the description 描述简介<BR/>
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param  description to description 描述简介 set
		 */
		public SysDictionary setDescription(String description) {
			this.description = description;
			this.isSetted_description = true;
			BEAN_VALUES.put("description",description);
			return this;
		}
		/**
		 * @return the res_code 资源组标识<BR/>
		 */
		public String getRes_code() {
			return res_code;
		}
		/**
		 * @param  res_code to res_code 资源组标识 set
		 */
		public SysDictionary setRes_code(String res_code) {
			this.res_code = res_code;
			this.isSetted_res_code = true;
			BEAN_VALUES.put("res_code",res_code);
			return this;
		}
		/**
		 * @return the content 字典显示内容<BR/>
		 */
		public String getContent() {
			return content;
		}
		/**
		 * @param  content to content 字典显示内容 set
		 */
		public SysDictionary setContent(String content) {
			this.content = content;
			this.isSetted_content = true;
			BEAN_VALUES.put("content",content);
			return this;
		}
		/**
		 * @return the add_time 添加时间<BR/>
		 */
		public Long getAdd_time() {
			return add_time;
		}
		/**
		 * @param  add_time to add_time 添加时间 set
		 */
		public SysDictionary setAdd_time(Long add_time) {
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
		public SysDictionary getInstanceById() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("获取Bean时ID不能为空");
			}
			return dao.queryForBean("select * from " + getTableName() + " where id=:id", BEAN_VALUES, this);
		}
	
		
		
		@Override
		public SysDictionary queryForBean() {
			StringBuffer sBuffer = new StringBuffer("select * from sys_dictionary where ");
			if(isSetted_id){
				sBuffer.append("id=:id and ");
			}
				if (isSetted_title) {
					sBuffer.append("title=:title and ");
				}
				if (isSetted_tag_code) {
					sBuffer.append("tag_code=:tag_code and ");
				}
				if (isSetted_description) {
					sBuffer.append("description=:description and ");
				}
				if (isSetted_res_code) {
					sBuffer.append("res_code=:res_code and ");
				}
				if (isSetted_content) {
					sBuffer.append("content=:content and ");
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
				if (isSetted_title) {
					sBuffer.append("title=:title and ");
				}
				if (isSetted_tag_code) {
					sBuffer.append("tag_code=:tag_code and ");
				}
				if (isSetted_description) {
					sBuffer.append("description=:description and ");
				}
				if (isSetted_res_code) {
					sBuffer.append("res_code=:res_code and ");
				}
				if (isSetted_content) {
					sBuffer.append("content=:content and ");
				}
				if (isSetted_add_time) {
					sBuffer.append("add_time=:add_time and ");
				}
			
			String sql = sBuffer.toString();
			return StringUtils.removeEnd(sql, " and ");
		
		}
	
		@Override
		public String getTableName() {
			return "sys_dictionary";
		}
		
		
		public Map<String, Object> getBeanValues(){
			return this.BEAN_VALUES;
		}
	
		@Override
		public SysDictionary insert() {
			if (StringUtils.isBlank(id)) {
				this.setId(IdUtil.randomUUID());
			}
			dao.execute(getInsertSql(),BEAN_VALUES);
			return this;
		}
	
		@Override
		public SysDictionary update() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("更新Bean时ID不能为空");
			}
			dao.execute(getUpdateSql(),BEAN_VALUES);
			return this;
		}  
		
		public SysDictionary insertOrUpdate(){
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
			
			return dao.queryForMap("select * from sys_dictionary where id=:id",BEAN_VALUES);
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
		
		public SysDictionary newInstance(){
			return new SysDictionary();
		}
		
		private static class Mapper implements RowMapper<SysDictionary> {
			public SysDictionary mapRow(ResultSet rs, int rownum) throws SQLException {
				SysDictionary bean = new SysDictionary();
				Object id = rs.getObject("id");
				bean.setId(Convert.toStr(id));
				bean.BEAN_VALUES.put("id",id);
				Object obj = null;
					obj = rs.getObject("title");
					bean.setTitle(Convert.toStr(obj));
				bean.BEAN_VALUES.put("title",obj);
					obj = rs.getObject("tag_code");
					bean.setTag_code(Convert.toStr(obj));
				bean.BEAN_VALUES.put("tag_code",obj);
					obj = rs.getObject("description");
					bean.setDescription(Convert.toStr(obj));
				bean.BEAN_VALUES.put("description",obj);
					obj = rs.getObject("res_code");
					bean.setRes_code(Convert.toStr(obj));
				bean.BEAN_VALUES.put("res_code",obj);
					obj = rs.getObject("content");
					bean.setContent(Convert.toStr(obj));
				bean.BEAN_VALUES.put("content",obj);
					obj = rs.getObject("add_time");
					bean.setAdd_time(Convert.toLong(obj));
				bean.BEAN_VALUES.put("add_time",obj);
				return bean;
			}
    	}	
		
		public RowMapper<SysDictionary> newMapper(){
			return new SysDictionary.Mapper();
		}

}