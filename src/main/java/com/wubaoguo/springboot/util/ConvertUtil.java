package com.wubaoguo.springboot.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class ConvertUtil {

	public static Boolean obj2Boolean(Object obj){
		return obj == null ? false : Boolean.getBoolean(obj.toString());
	}
	
	public static Double obj2Double(Object obj){
		if (obj == null) {
			return null;
		}
		return StringUtils.isBlank(obj2Str(obj)) ? null : Double.valueOf(obj.toString());
	}
	
	/**
	 * list 转换 map
	 * @Title list2Map
	 * @param dataList
	 * @param key
	 * @return
	 * @author zhaoqt
	 * @date Jul 27, 2015 5:24:32 AM
	 * @return Map<Object,Object>
	 */
	public static Map<Object, Object> list2Map(
			List<Map<Object, Object>> dataList, Object key){
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		if (dataList != null && dataList.size() > 0) {
			for (Map<Object, Object> map : dataList) {
				if (map.get(key) != null) {
					resultMap.put(map.get(key), map);
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * gbk 转换 utf-8
	 * @Title gbk2UTF8
	 * @param str
	 * @return
	 * @author zhaoqt
	 * @date Jul 27, 2015 5:24:19 AM
	 * @return String
	 */
	public static String gbk2UTF8(String str){
		if (StringUtils.isBlank(str)) {
			return str;
		}
		try {
			str = URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String html2Text(String html){
		return html.replaceAll("<[^>]+>", "").replaceAll("&nbsp;","");
	}
	
	public static String obj2Str(Object obj) {
		return obj == null ? null : obj.toString();
	}
	public static String obj2StrBlank(Object obj) {
		return obj == null ? "" : obj.toString();
	}
	public static Integer obj2Integer(Object obj){
		return obj == null || obj.toString().trim().equals("")  ? null : Integer.parseInt(obj.toString()); 
	}
	
	public static int obj2Int(Object obj){
		return obj == null || obj.toString().trim().equals("")  ? null : Double.valueOf(obj.toString()).intValue(); 
	}
	
	public static Long obj2Long(Object obj){
		return obj == null || obj.toString().trim().equals("") ? null : Long.parseLong(obj.toString()); 
	}
	
	public static Long obj2Long(Object obj,boolean filter){
		if (!filter) {
			return obj == null || obj.toString().trim().equals("") ? null : Long.parseLong(obj.toString()); 
		} else {
			return obj == null || obj.toString().trim().equals("") ? null : Long.parseLong(obj.toString().replaceAll("[^0-9]", "")); 
		}
	}

	public static Long obj2LongNotNull(Object obj) {
		return obj == null || obj.toString().trim().equals("") ? 0 : Long.parseLong(obj.toString()); 
	}
	
	public static Long number2LongBy100(Double d) {
		BigDecimal bigDecimal = new BigDecimal(d != null ? d : 0.00).setScale(2, RoundingMode.HALF_EVEN);
		return obj2Long(new BigDecimal(bigDecimal.doubleValue() * 100).setScale(0, BigDecimal.ROUND_HALF_UP));
	}
	
	public static Double long2NumberBy100(Long l) {
		return divide(l != null ? l : 0, 100, 2).doubleValue();
	}
	
	public static BigDecimal long2NumberBy100ToBig(Long l) {
		return divide(l != null ? l : 0, 100, 2);
	}
	
	public static Long number2LongBy100(BigDecimal bigDecimal) {
		if(bigDecimal == null) {
			bigDecimal = new BigDecimal(0.00);
		}
		bigDecimal.setScale(2, RoundingMode.HALF_EVEN);
		return obj2Long(new BigDecimal(bigDecimal.doubleValue() * 100).setScale(0, BigDecimal.ROUND_HALF_UP));
	}	
	/**
	 * 返回list容器 第0个坐标对象 list 为null 返回null
	 * 
	 * @param collection
	 * @return
	 */
	public static Object firstObjectByListObjcet(List<?> collection) {
		if(PropertyUtil.objectNotEmpty(collection)) {
			return collection.get(0);
		}
		return null;
	}
	
	public static <T> T firstObjectByList(List<T> collection) {
		if(PropertyUtil.objectNotEmpty(collection)) {
			return collection.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("all")
	public static  Map<String, Object> beanToMap(Object model) {
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        if (model == null)
	            return null;
	        List<Field> fieldList = new ArrayList<Field>();
	        Class class1 = (Class) model.getClass();
	        Class upClasses = class1.getSuperclass();
	        //添加父类属性
	        fieldList.addAll(Arrays.asList(upClasses.getDeclaredFields()));
	        //添加类属性
	        fieldList.addAll(Arrays.asList(class1.getDeclaredFields()));

	        for (Field field : fieldList) {
	            String fieldName = field.getName();
	            Object fieldValue=null;
	            try {
	                Method method = class1.getMethod(
	                        "get" + Character.toUpperCase(fieldName.charAt(0))
	                                + fieldName.substring(1), model.getClass());
	                fieldValue = method.invoke(class1, null);
	            } catch (Exception e) {
	                try {
	                    if (!Modifier.isPublic(field.getModifiers())) {
	                        field.setAccessible(true);
	                    }

	                    fieldValue = field.get(model);
	                } catch (Exception exception) {

	                }
	            }
	            if (fieldValue != null) {
	                if (fieldValue instanceof String) {
	                    String fieldValueString = String.valueOf(fieldValue);
	                    if (StringUtils.isNotBlank(fieldValueString)) {
	                        resultMap.put(fieldName, fieldValueString.trim()); // 处理查询参数
	                    }
	                } else if(fieldValue instanceof Long ||fieldValue instanceof Integer){
	                    resultMap.put(fieldName, fieldValue);
	                    //getConditionMap(fieldValue);// 处理查询参数TODO
	                }
	            }
	        }
	        return resultMap;
	    }
	    
	@SuppressWarnings("all")
	public static Object getFieldValueObj(Object model, Field field) {
		 Class<?> localClass = model.getClass();
		 Object fieldValue = null;
		 String fieldName = field.getName();
	        try {
	            Method method = localClass.getMethod(
	                    "get" + Character.toUpperCase(fieldName.charAt(0))
	                            + fieldName.substring(1), localClass);
	            fieldValue = method.invoke(localClass, null);
	        } catch (Exception e) {
	            try {
	                if (!Modifier.isPublic(field.getModifiers())) {
	                    field.setAccessible(true);
	                }
	                fieldValue = field.get(model);
	            } catch (Exception exception) {
	
	            }
	        }
	        return fieldValue;
		}
	
	/**
	 *  反射回去新事例赋值。并返回
	 *  
	 * @param type
	 * @param value
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public static <T> T setFieldValue(Class<T> type, Object value, String field) throws Exception {
		T o = type.newInstance();
		setFieldValueObj(o, field, value);
		return o;
	}
	
	public static void setFieldValueObj(Object o, String field, Object value) throws Exception {
		Class<? extends Object> clazz = o.getClass();
		Method setMethod = null;
		if ((value instanceof HashSet)) {
			setMethod = clazz.getMethod(catchMethodName("set", field), new Class[] { (value instanceof HashSet) ? Set.class : value.getClass() });
		} else {
			setMethod = clazz.getMethod(catchMethodName("set", field), new Class[] { (value instanceof Timestamp) ? Date.class : value.getClass() });
		}
		setMethod.invoke(o, new Object[] { value });
	}
	
	private static String catchMethodName(String getterOrSetter, String fieldName) {
		char[] charArray = fieldName.toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		return getterOrSetter + new String(charArray);
	}
	
	    /**
	     * 委托bean转换 map  委托bean 不包含字段名称 暂不支持基础boolean 类型
	     * 
	     * @Title delegateBean2Map
	     * @param delegateBean
	     * @return
	     * @author Zhaoqt
	     * @date 2015.05.13 12:41:37
	     * @return Map<String,Object>
	     */
	    @SuppressWarnings("all")
	    public static Map<String, Object> delegateBean2Map(Object delegateBean) {
	    	 Map<String, Object> resultMap = new HashMap<String, Object>();
	         if (delegateBean == null) {
	        	 return new HashMap<String, Object>();
	         }
	    	 BeanInfo beanInfo = null;
			 try {
				 beanInfo = Introspector.getBeanInfo(delegateBean.getClass());
			 } catch (IntrospectionException e) {
				// TODO 异常暂不处理
			 }
			 PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			 for(PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					Method method = propertyDescriptor.getReadMethod();
					// 根据方法名字 获取所以get 方法 并排除 getClass 方法
					String methodName = method.getName();
					if(methodName.indexOf("get") != -1 && !"getClass".equals(methodName)) {
						try {
							// 去掉方法名称 get 保留属性字段名称
							methodName = methodName.substring(3, methodName.length());
							String fieldName = Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
							resultMap.put(fieldName, method.invoke(delegateBean, null));
						} catch (Exception e) {
							// TODO 异常暂不处理
						}
					}
				}
	         return resultMap;
	    }
	    
	public static BigDecimal divide(double d1, double d2, int len) {
	    BigDecimal b1 = new BigDecimal(d1);
	    BigDecimal b2 = new BigDecimal(d2);
	    return b1.divide(b2,len,BigDecimal.ROUND_HALF_UP);
	}
	
}
