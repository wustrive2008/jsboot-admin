package com.wubaoguo.springboot.core.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: HTTP API 错误码定义
 * @author wubaoguo
 * @mail: wustrive2008@gmail.com
 * @date: 2017年3月14日 上午10:13:02
 * @version: v0.0.1
 */
public class StateMap {

	private static final Map<Integer, String> map;
	
	/** 调用处理成功  **/
	
	// 成功返回标识
	public static final int S_SUCCESS = 1;
	
	
	/** 客户端输入错误  **/
	
	//参数为空
	public static final int S_CLIENT_PARAM_NULL = 10001;
	
	//参数错误
    public static final int S_CLIENT_PARAM_ERROR = 10002;
    
    //操作警告
    public static final int S_CLIENT_WARNING = 10003;
    
    /** 客户端权限错误  **/
    
    //登录认证失败
    public static final int S_AUTH_ERROR = 20001;
    
    //重复登录
    public static final int S_AUTH_REPET_LOGIN = 20002;
    
    //Token过期
    public static final int S_AUTH_TIMEOUT = 20003;
    
    
    /** 服务器输出错误  **/
    
    //服务端异常
    public static final int S_SERVER_EXCEPTION = 30001;
    
    //请求过快，稍后重试
    public static final int S_SERVER_CONCURRENT = 30002;
    
	
	static {
		map = new HashMap<Integer, String>() {
			private static final long serialVersionUID = 2465184251826686088L;
		{
			put(S_SUCCESS, "操作成功"); 
			put(S_CLIENT_PARAM_NULL, "参数不能为空"); 
			put(S_CLIENT_PARAM_ERROR, "参数错误"); 
			put(S_AUTH_ERROR, "登录失败，请确认账号密码后重试"); 
			put(S_AUTH_REPET_LOGIN, "重复登录");
			put(S_AUTH_TIMEOUT, "授权过期，请重新登录"); 
			put(S_SERVER_EXCEPTION, "服务端异常，稍后重试或联系管理员"); 
			put(S_SERVER_CONCURRENT, "操作过快，稍后重试"); 
		}};
	}
	
	public static String get(Integer key) {
		return map.get(key);
	}
}
