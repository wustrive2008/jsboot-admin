package com.wubaoguo.springboot.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: Parameter.java
 * @ClassName: com.vc.base.core.json.Parameter
 * @Description: 页面请求公告参数
 *
 * Company: vc
 * @author Zhaoqt
 * @date Jul 15, 2015 4:20:14 PM
 */
public class Parameter {

	/** 日志对象 */
	static Logger logger = LoggerFactory.getLogger(Parameter.class);

	/** 页面的request请求 */
	private final HttpServletRequest request;

	/** 存储预设值，如果设置了值，在取值时代替request中的值 */
	private Map<String, String> setMap = new HashMap<String, String>();

	/**
	 * 构造函数
	 * 
	 * @param request
	 *            页面请求
	 */
	public Parameter(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 设置值 bate版功能
	 * 
	 * @param key
	 *            要设置的key
	 * @param value
	 *            要设置的值
	 */
	public void put(String key, String value) {
		setMap.put(key, value);
	}

	/**
	 * 得到 HttpServletRequest
	 * 
	 * @return request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * 判断值是否存在
	 * 
	 * @param key
	 *            要判断的值
	 * @return 是否存在
	 */
	public boolean containsKey(String key) {
		String value = getString(key);
		return value != null && value.length() > 0;
	}

	/**
	 * 取出指定参数
	 * 
	 * @param key
	 *            参数名
	 * @return 指定名称的参数值
	 */
	public String getString(String key) {
		String result = null;
		if (setMap.containsKey(key)) {
			return setMap.get(key);
		}

		String[] array = request.getParameterValues(key);
		if (array != null) {
			if (array.length > 1) {
				throw new IllegalArgumentException(key + "的属性为数组，建议不单独取值");
			}
			result = array[0];
		}
		return result;
	}

	/**
	 * 取出指定参数
	 * 
	 * @param key
	 *            参数名
	 * @param defaultValue
	 *            如果该参数为空则返回 defaultValue
	 * @return 参数值
	 */
	public String getString(String key, String defaultValue) {
		String result = getString(key);
		if (result == null || result.isEmpty()) {
			result = defaultValue;
		}
		return result;
	}

	/**
	 * 取出指定参数
	 * 
	 * @param key
	 *            参数名
	 * @return 整型值
	 */
	public int getInt(String key) {
		return Integer.parseInt(getString(key));
	}

	/**
	 * 取出指定参数
	 * 
	 * @param key
	 *            参数名
	 * @return 长整型的值
	 */
	public long getLong(String key) {
		return Long.parseLong(getString(key));
	}

	/**
	 * 取出指定参数
	 * 
	 * @param key
	 *            参数名
	 * @param defaultValue
	 *            如果该参数为空则返回 defaultValue
	 * @return 整型的值
	 */
	public int getInt(String key, int defaultValue) {
		int result;
		try {
			result = getInt(key);
		} catch (NumberFormatException e) {
			result = defaultValue;
		}

		return result;
	}

	/**
	 * 取出指定参数
	 * 
	 * @param key
	 *            参数名
	 * @param defaultValue
	 *            如果该参数为空则返回 defaultValue
	 * @return 长整型的值
	 */
	public long getLong(String key, long defaultValue) {
		long result;
		try {
			result = getLong(key);
		} catch (NumberFormatException e) {
			result = defaultValue;
		}
		return result;
	}

	/**
	 * 取出指定参数
	 * 
	 * @param key
	 *            参数名
	 * @return 参数值
	 */
	public double getDouble(String key) {
		return Double.parseDouble(getString(key));
	}

	/**
	 * 取出指定参数
	 * 
	 * @param key
	 *            参数名
	 * @param defaultValue
	 *            如果该参数为空则返回 defaultValue
	 * @return 参数值
	 */
	public double getDouble(String key, double defaultValue) {
		double result;
		try {
			result = getDouble(key);
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	/**
	 * 取出指定的布尔型参数
	 * 
	 * @param key
	 *            参数名
	 * @return 参数值
	 */
	public boolean getBoolean(String key) {
		final String str = getString(key);
		logger.debug("para {} = {}", key, str);
		return Boolean.parseBoolean(str);
	}

	/**
	 * 取出指定参数
	 * 
	 * @param key
	 *            参数名
	 * @param defaultValue
	 *            如果该参数为空则返回 defaultValue
	 * @return 参数值
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		final String str = getString(key);
		if (str == null) {
			return defaultValue;
		}
		if (str.equalsIgnoreCase("true")) {
			return true;
		}
		if (str.equalsIgnoreCase("false")) {
			return false;
		}
		return defaultValue;
	}

	/**
	 * 得到数组参数，如果不存在，则返回0长度的string[]
	 * 
	 * @param key
	 *            参数名
	 * @return 参数值
	 */
	public String[] getStringValues(String key) {
		String[] str = request.getParameterValues(key);
		if (str == null) {
			str = new String[0];
		}
		return str;
	}

	/**
	 * 得到整型的数组参数，如果不存在，则返回0长度的int[]
	 * 
	 * @param key
	 *            参数名
	 * @return 参数值
	 */
	public int[] getIntValue(String key) {
		String[] str = getStringValues(key);
		int[] result = new int[str.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = Integer.parseInt(str[i]);
		}
		return result;
	}

	/**
	 * 比较para中的指定参数和 输入参数是否相等
	 * 
	 * @param key
	 *            指定的key
	 * @param compareValue
	 *            要比较的值
	 * @return 是否相等
	 */
	public boolean equals(String key, String compareValue) {
		String src = getString(key);
		if (src == null) {
			return compareValue == null;
		}
		return src.equals(compareValue);
	}

	/**
	 * 得到以map形式表现的请求参数
	 * 
	 * @return
	 */
	public Map<String, String> getParameterMap() {
		Map<String, String[]> map = request.getParameterMap();

		Map<String, String> result = new HashMap<String, String>();
		for (Entry<String, String[]> entry : map.entrySet()) {
			String key = entry.getKey();
			String[] value = entry.getValue();
			if (value.length > 1) {
				logger.debug("#parameter_warn {} 参数值多于一个 {}", key, value);
			}
			result.put(key, value[0]);
		}
		return result;
	}

}
