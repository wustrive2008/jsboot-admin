package com.wubaoguo.springboot.util;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class PropertyUtil {

	public static boolean objectNotEmpty(Object obj) {
		boolean result = true;
		if (obj == null) {
			return false;
		}
		if (((obj instanceof String)) && ("".equals(((String) obj).trim()))) {
			result = false;
		}
		if (((obj instanceof Integer)) && (0 == ((Integer) obj).intValue())) {
			result = false;
		}
		if (((obj instanceof Long)) && (0 == ((Long) obj).intValue())) {
			result = false;
		}
		if (((obj instanceof String[])) && (0 == ((String[]) obj).length)) {
			result = false;
		}
		if (((obj instanceof Integer[])) && (0 == ((Integer[]) obj).length)) {
			result = false;
		}
		if (((obj instanceof Map)) && (((Map) obj).isEmpty())) {
			result = false;
		}
		if (((obj instanceof List)) && ((((List) obj).size() == 0) || (((List) obj).isEmpty()))) {
			result = false;
		}
		if (((obj instanceof Set)) && ((((Set) obj).isEmpty()) || (((Set) obj).size() == 0))) {
			result = false;
		}
		return result;
	}

	public static String sqlInjectionFilter(String queryProperty) throws Exception {
		if ((queryProperty != null) && (queryProperty.indexOf("'") != -1)) {
			queryProperty = queryProperty.replaceAll("'", "_");
		}
		return queryProperty;
	}

	public static void propertyDynamicUpdate(Object dbObj, Object obj) {
		String[] fieldNameArray = getAllFieldName(obj);
		for (int i = 0; i < fieldNameArray.length; i++) {
			String fieldName = fieldNameArray[i];
			try {
				Object value = getFieldValueObj(obj, fieldName);
				if (value != null) {
					setFieldValueObj(dbObj, fieldName, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@SuppressWarnings("all")
	public static Object getFieldValueObj(Object o, String field) throws Exception {
		Class clazz = o.getClass();
		Method getMethod = clazz.getMethod(catchMethodName("get", field), null);
		Object returnObj = getMethod.invoke(o, null);
		return returnObj;
	}

	private static void setFieldValueObj(Object o, String field, Object value) throws Exception {
		Class<? extends Object> clazz = o.getClass();
		Method setMethod = null;
		if ((value instanceof HashSet)) {
			setMethod = clazz.getMethod(catchMethodName("set", field), new Class[] { (value instanceof HashSet) ? Set.class : value.getClass() });
		} else {
			setMethod = clazz.getMethod(catchMethodName("set", field), new Class[] { (value instanceof Timestamp) ? Date.class : value.getClass() });
		}
		setMethod.invoke(o, new Object[] { value });
	}

	private static String[] getAllFieldName(Object obj) {
		ArrayList<String> methodList = new ArrayList();
		Method[] methods = obj.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if ((method.getName().startsWith("get")) && (method.getReturnType() != null) && (!"getClass".equals(method.getName()))) {
				methodList.add(catchFieldByMethodName(method.getName()));
			}
		}
		return (String[]) methodList.toArray(new String[methodList.size()]);
	}

	private static String catchFieldByMethodName(String methodName) {
		char[] charArray = methodName.substring(3).toCharArray();
		charArray[0] = Character.toLowerCase(charArray[0]);
		return new String(charArray);
	}

	private static String catchMethodName(String getterOrSetter, String fieldName) {
		char[] charArray = fieldName.toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		return getterOrSetter + new String(charArray);
	}
}
