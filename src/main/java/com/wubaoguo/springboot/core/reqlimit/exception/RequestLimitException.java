package com.wubaoguo.springboot.core.reqlimit.exception;

/**
 * Description:
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2017/11/4 16:17
 */
public class RequestLimitException extends Exception{
    private static final long serialVersionUID = 3082114125758156924L;

    public RequestLimitException() {
        super("HTTP请求速率超出设定的限制，引发限制机制");
    }

    public RequestLimitException(String message) {
        super(message);
    }
}
