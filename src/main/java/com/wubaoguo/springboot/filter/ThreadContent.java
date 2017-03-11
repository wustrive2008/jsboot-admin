package com.wubaoguo.springboot.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.wubaoguo.springboot.request.Parameter;
import com.wubaoguo.springboot.util.WebUtil;

/**
 * @Title: ThreadContent.java
 * @ClassName: com.vcxx.common.filter.ThreadContent
 * @Description: 存储线程变量的上下文
 *
 * Company: vc
 * @author Zhaoqt
 * @date Jul 15, 2015 4:17:55 PM
 */
public class ThreadContent implements Filter {
	
	static Logger logger = LoggerFactory.getLogger(ThreadContent.class);

	/**
	 * 每个线程对象
	 */
	public static class ThreadObject {
		public final HttpServletRequest request;
		final HttpServletResponse response;
		String ip;
		/** 其他附加数据 */
		final Map<String, Object> data = new HashMap<String, Object>();
		Parameter parameter;

		public ThreadObject(HttpServletRequest request, HttpServletResponse response) {
			this.request = request;
			this.response = response;
			parameter = null;
			if (request instanceof HttpServletRequest) {
				parameter = new Parameter((HttpServletRequest) request);
			}
		}
	}

	private static ThreadLocal<ThreadObject> THREAD_OBJECT = new ThreadLocal<ThreadObject>() {
		protected ThreadObject initialValue() {
			throw new RuntimeException(" 程序未初始化，请在web.xml中 配置");
		}
	};

	/**
	 * 从线程对象中获取数据
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getData(String key) {
		return (T) THREAD_OBJECT.get().data.get(key);
	}

	/**
	 * 向线程对象中设置数据， 为保证安全，如果该对象本身有值时会抛出错误
	 * 
	 * @param key
	 *            关键字
	 * @param obj
	 *            要设置的对象
	 */
	public static void addData(String key, Object obj) {
		Object oldValue = THREAD_OBJECT.get().data.put(key, obj);
		if (oldValue != null) {
			throw new IllegalArgumentException("数据已存在不能重复设置");
		}
	}

	/**
	 * 从线程中移除对象
	 * 
	 * @param key
	 *            要移除的对象名
	 */
	@SuppressWarnings("unchecked")
	public static <T> T removeData(String key) {
		return (T) THREAD_OBJECT.get().data.remove(key);
	}

	/** 得到servlet请求中的 request */
	public static HttpServletRequest request() {
		return THREAD_OBJECT.get().request;
	}

	/** 得到servlet请求中的 response */
	public static HttpServletResponse response() {
		return THREAD_OBJECT.get().response;
	}

	/** 得到servlet请求中的 客户端的ip 地址 */
	public static String clientIp() {
		ThreadObject threadObj = THREAD_OBJECT.get();
		String ip = threadObj.ip;
		if (ip == null) {
			ip = WebUtil.getClientIp(threadObj.request);
			threadObj.ip = ip;
		}
		return ip;
	}

	/** 得到参数对象 */
	public static Parameter parameter() {
		return THREAD_OBJECT.get().parameter;
	}

	public void init(FilterConfig arg0) throws ServletException {
		logger.info("#init threadContent");
	}

	public void destroy() {
		logger.info("#destroy threadContent");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		if (isStaticFile(request, response)) {
			filterChain.doFilter(request, response);
		} else {
			THREAD_OBJECT.set(new ThreadObject((HttpServletRequest) request, (HttpServletResponse) response));
			filterChain.doFilter(request, response);
			THREAD_OBJECT.set(null);
		}

	}

	/** 静态文件后缀名 */
	ImmutableSet<String> ignoreSuffix = new ImmutableSet.Builder<String>().add("jpg", "jpeg", "ico", "txt", "doc", "ppt", "xls", "pdf", "gif", "png",
			"bmp", "css", "js", "html", "htm", "swf", "flv", "mp3", "htc").build();

	/**
	 * 判断是否过滤该请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean isStaticFile(ServletRequest request, ServletResponse response) {
		if (request instanceof HttpServletRequest) {
			String uri = ((HttpServletRequest) request).getRequestURI();
			String suffix = StringUtils.substringAfterLast(uri, ".").toLowerCase();
			return ignoreSuffix.contains(suffix);
		}
		// request 无法解析时 暂时定为静态文件处理
		return true;
	}

	/**
	 * 设置新的共享对象
	 * 
	 * @param tHREAD_OBJECT
	 */
	public static void setThreadObject(ThreadObject threadObject) {
		THREAD_OBJECT.set(threadObject);
	}
}
