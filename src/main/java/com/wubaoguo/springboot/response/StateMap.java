package com.wubaoguo.springboot.response;

import java.util.HashMap;
import java.util.Map;

public class StateMap {

	private static final Map<Integer, String> map;
	
	// 成功返回标识
	public static final int S_SUCCESS = 1;
	
	// 操作失败
	public static final int S_FAIL = -1;
	
	// 无效请求action 动作
	public static final int S_INVALID_ACTION = 101;
	
	// 配置错误
	public static final int S_CONFIG_ERROR = 102;
	
	// 数据不存在
	public static final int S_NOT_EXIST = 301;
	
	// 数据参数为空
	public static final int S_IS_NULL = 302;
	
	// 数据参数格式错误
	public static final int S_IS_FORMAT = 303;
	
	// 数据重复
	public static final int S_IS_REPEAT = 304;
	
	// 未知错误
	public static final int S_ERROR = 500;
	
	// 数据持久化失败
	public static final int S_STORAGE_FAIL = 501;
	
	public static final int S_404 = 404;
	
	public static final int S_403 = 403;
	
	// 重复登录
	public static final int S_403_1 = 40301;
	// 重复登录。强制下线状态
	public static final int S_403_2 = 40302;
	
	// accessToken 过期，需重新获取
	public static final int S_403_3 = 40303;
	// 服务器忙稍后请求
	public static final int S_408_1 = 40801;
	
	public static final int S_401 = 401;
	
	// 状态 200 客户端 输出 提示
	public static final int S_200 = 200;
	
	static {
		map = new HashMap<Integer, String>() {
			private static final long serialVersionUID = 2465184251826686088L;
		{
			put(S_SUCCESS, "\u64cd\u4f5c\u6210\u529f"); // 操作成功
			put(S_FAIL, "\u64cd\u4f5c\u5931\u8d25"); // 操作失败
			put(S_INVALID_ACTION, "\u65e0\u6548\u8bf7\u6c42"); //  无效请求
			put(S_CONFIG_ERROR, "\u914d\u7f6e\u9519\u8bef"); // 配置错误
			put(S_NOT_EXIST, "\u6570\u636e\u4e0d\u5b58\u5728"); // 数据不存在
			put(S_IS_NULL, "\u65e0\u6548\u53c2\u6570"); // 无效参数
			put(S_IS_FORMAT, "\u683c\u5f0f\u9519\u8bef"); // 格式错误
			put(S_403_1, "\u91cd\u590d\u767b\u5f55"); // 重复登录
			put(S_408_1, "\u670d\u52a1\u5668\u5fd9\u0020\u8bf7\u7a0d\u540e\u518d\u8bd5"); // 重复登录
			put(S_ERROR, "unknow error.");
			put(S_STORAGE_FAIL, "data storage error.");
			put(S_403_3, "expire accessToken.");
		}};
	}
	
	public static String get(Integer key) {
		return map.get(key);
	}
}
