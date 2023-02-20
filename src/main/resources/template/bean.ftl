package ${package};

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import org.wustrive.java.common.util.ConvertUtil;
import org.wustrive.java.common.util.StringUtil;
import org.wustrive.java.dao.jdbc.bean.BaseBean;


/**
 * 数据库实体自动生成:
 * ${COMMENTS}
 */
@SuppressWarnings("unchecked")
public class ${ClassName} implements BaseBean{

	public final static Map<String, String> KEYS = new HashMap<>();
	private static final long serialVersionUID = 1L;
	private Map<String, Object> BEAN_VALUES = null;
	
	static {
		KEYS.put("${PK.COLUMN_NAME?lower_case}", "String");
		<#list columns as column>
		KEYS.put("${column.COLUMN_NAME?lower_case}", "${column.COLUMN_TYPE}");
		</#list>
	}
	public Map<String, String> getColumnMap(){
		return KEYS;
	}
	
	private String id;
	private Boolean isSetted_id = false;
	
	<#list columns as column>
	private ${column.COLUMN_TYPE} ${column.COLUMN_NAME?lower_case};
	private Boolean isSetted_${column.COLUMN_NAME?lower_case} = false;
	</#list>

	private void initBeanValues(){
		BEAN_VALUES = new HashMap<>();
		BEAN_VALUES.put("id",id);
		<#list columns as column>
			BEAN_VALUES.put("${column.COLUMN_NAME?lower_case}", null);
		</#list>
	}
	
	public ${ClassName}() {
		initBeanValues();
	}
	
	public ${ClassName}(String id) {
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
	public ${ClassName} setId(String id) {
		this.id = id;
		this.isSetted_id = true;
		BEAN_VALUES.put("id",id);
		return this;
	}
	
	@Override
	public String getUpdateSql() {
		StringBuilder sBuffer = new StringBuilder("update " + getTableName() + " set ");
		<#list columns as column>
			if (isSetted_${column.COLUMN_NAME?lower_case}) {
				sBuffer.append("${column.COLUMN_NAME?lower_case}=:${column.COLUMN_NAME?lower_case},");
			}
		</#list>
		String sql = sBuffer.toString();
		return StringUtils.removeEnd(sql, ",") + " where ${PK.COLUMN_NAME?lower_case}=:id";
	}
	
	
	@Override
	public String getInsertSql() {
		StringBuilder sBuffer = new StringBuilder("insert into " + getTableName() + " set ");
		StringBuilder fileds = new StringBuilder("${PK.COLUMN_NAME?lower_case}=:id,");
		if(BEAN_VALUES!=null){
			<#list columns as column>
			  	 if(BEAN_VALUES.get("${column.COLUMN_NAME?lower_case}")!=null){
					fileds.append("${column.COLUMN_NAME?lower_case}=:${column.COLUMN_NAME?lower_case},");
				  }
			</#list>
		}
		sBuffer.append(StringUtils.removeEnd(fileds.toString(), ","));
		return sBuffer.toString();
	}
	

	<#list columns as column>
		/**
		 * @return the ${column.COLUMN_NAME} ${column.COLUMN_COMMENT}<BR/>
		 */
		public ${column.COLUMN_TYPE} get${column.COLUMN_NAME?lower_case?cap_first}() {
			return ${column.COLUMN_NAME?lower_case};
		}
		/**
		 * @param  ${column.COLUMN_NAME} to ${column.COLUMN_NAME} ${column.COLUMN_COMMENT} set
		 */
		public ${ClassName} set${column.COLUMN_NAME?lower_case?cap_first}(${column.COLUMN_TYPE} ${column.COLUMN_NAME?lower_case}) {
			this.${column.COLUMN_NAME?lower_case} = ${column.COLUMN_NAME?lower_case};
			this.isSetted_${column.COLUMN_NAME?lower_case} = true;
			BEAN_VALUES.put("${column.COLUMN_NAME?lower_case}",${column.COLUMN_NAME?lower_case});
			return this;
		}
	</#list>

		/**
		 * 使用ID删除Bean<BR/>
		 */
		public void deleteById() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("删除bean时ID不能为空");
			}
			dao.execute("delete from " + getTableName() + " where ${PK.COLUMN_NAME?lower_case} = :id", BEAN_VALUES);
		}
	
		@Override
		public ${ClassName} getInstanceById() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("获取Bean时ID不能为空");
			}
			return dao.queryForBean("select * from " + getTableName() + " where ${PK.COLUMN_NAME?lower_case}=:id", BEAN_VALUES, this);
		}
	
	   <#list MYFK as pk>
		<#if pk.TABLE_NAME?lower_case == TABLENAME?lower_case>
		public ${pk.R_TABLE_NAME?upper_case} get${pk.R_TABLE_NAME?upper_case}() {         
			if(null == get${pk.COLUMN_NAME?lower_case?cap_first}()){
				throw new RuntimeException("${pk.COLUMN_NAME} is null");
			}
			return new ${pk.R_TABLE_NAME?upper_case}(get${pk.COLUMN_NAME?lower_case?cap_first}()).getInstanceById();
			//return dao.queryForBean("select * from ${pk.R_TABLE_NAME} where ${pk.R_COLUMN_NAME}=:${pk.COLUMN_NAME?lower_case}", new ${pk.R_TABLE_NAME?upper_case}(this.get${pk.COLUMN_NAME?lower_case?cap_first}()));
		}
		</#if>
		</#list>
		
		<#list MYFK as pk>
		<#if pk.R_TABLE_NAME?lower_case == TABLENAME?lower_case>
		public List<${pk.TABLE_NAME?upper_case}> get${pk.TABLE_NAME?upper_case}List() {         
			if(null == get${pk.R_COLUMN_NAME?lower_case?cap_first}()){
				throw new RuntimeException("${pk.R_COLUMN_NAME} is null");
			}
			return dao.queryForBeanList("select * from ${pk.TABLE_NAME} where ${pk.COLUMN_NAME}=:${pk.R_COLUMN_NAME?lower_case}", this.BEAN_VALUES, new ${pk.TABLE_NAME?upper_case}());
		}
		</#if>
		</#list>
		
		@Override
		public ${ClassName} queryForBean() {
			StringBuilder sBuffer = new StringBuilder("select * from " + getTableName() + " where ");
			if(isSetted_id){
				sBuffer.append("${PK.COLUMN_NAME?lower_case}=:id and ");
			}
			<#list columns as column>
				if (isSetted_${column.COLUMN_NAME?lower_case}) {
					sBuffer.append("${column.COLUMN_NAME?lower_case}=:${column.COLUMN_NAME?lower_case} and ");
				}
			</#list>
			String sql = sBuffer.toString();
			sql = StringUtils.removeEnd(sql, " and ");
			return dao.queryForBean(sql,this);
		}
		
		@Override
		public String getParameter(){
			StringBuilder sBuffer = new StringBuilder("");
			if(isSetted_id){
				sBuffer.append("${PK.COLUMN_NAME?lower_case}=:id and ");
			}
			<#list columns as column>
				if (isSetted_${column.COLUMN_NAME?lower_case}) {
					sBuffer.append("${column.COLUMN_NAME?lower_case}=:${column.COLUMN_NAME?lower_case} and ");
				}
			</#list>
			
			String sql = sBuffer.toString();
			return StringUtils.removeEnd(sql, " and ");
		
		}
	
		@Override
		public String getTableName() {
			return "${TABLENAME?lower_case}";
		}
		
		
		public Map<String, Object> getBeanValues(){
			return this.BEAN_VALUES;
		}
	
		@Override
		public ${ClassName} insert() {
			if (StringUtils.isBlank(id)) {
				this.setId(StringUtil.getUUID());
			}
			dao.execute(getInsertSql(),BEAN_VALUES);
			return this;
		}
	
		@Override
		public ${ClassName} update() {
			if (StringUtils.isBlank(id)) {
				throw new RuntimeException("更新Bean时ID不能为空");
			}
			dao.execute(getUpdateSql(),BEAN_VALUES);
			return this;
		}  
		
		public ${ClassName} insertOrUpdate(){
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
			
			return dao.queryForMap("select * from " + getTableName() + " where ${PK.COLUMN_NAME?lower_case}=:id",BEAN_VALUES);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("[");
			for (String key : KEYS.keySet()) {
				sb.append(key).append("=").append(BEAN_VALUES.get(key)).append(",");
			}
			sb.append("]");
			return sb.toString();
		}
		
		public ${ClassName} newInstance(){
			return new ${ClassName}();
		}
		
		private static class Mapper implements RowMapper<${ClassName}> {
			public ${ClassName} mapRow(ResultSet rs, int rownum) throws SQLException {
				${ClassName} bean = new ${ClassName}();
				Object id = rs.getObject("${PK.COLUMN_NAME?lower_case}");
				bean.setId(ConvertUtil.obj2Str(id));
				bean.BEAN_VALUES.put("${PK.COLUMN_NAME?lower_case}",id);
				Object obj;
				<#list columns as column>
				<#if column.COLUMN_TYPE == "String">
					obj = rs.getObject("${column.COLUMN_NAME?lower_case}");
					bean.set${column.COLUMN_NAME?lower_case?cap_first}(ConvertUtil.obj2Str(obj));
				</#if>
				<#if column.COLUMN_TYPE == "Integer">
					<#if column.COLUMN_TYPE_TINYINT == "Integer">
						obj = rs.getInt("${column.COLUMN_NAME?lower_case}");
					<#else>
						obj = rs.getObject("${column.COLUMN_NAME?lower_case}");
					</#if>
					bean.set${column.COLUMN_NAME?lower_case?cap_first}(ConvertUtil.obj2Integer(obj));
				</#if>
				<#if column.COLUMN_TYPE == "Long">
					obj = rs.getObject("${column.COLUMN_NAME?lower_case}");
					bean.set${column.COLUMN_NAME?lower_case?cap_first}(ConvertUtil.obj2Long(obj));
				</#if>
				<#if column.COLUMN_TYPE == "Double">
					obj = rs.getObject("${column.COLUMN_NAME?lower_case}");
					bean.set${column.COLUMN_NAME?lower_case?cap_first}(ConvertUtil.obj2Double(obj));
				</#if>
				bean.BEAN_VALUES.put("${column.COLUMN_NAME?lower_case}",obj);
				</#list>
				return bean;
			}
    	}	
		
		public RowMapper<${ClassName}> newMapper(){
			return new ${ClassName}.Mapper();
		}

}