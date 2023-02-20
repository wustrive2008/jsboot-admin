package com.wubaoguo.springboot.core.exception;


import com.wubaoguo.springboot.core.request.StateMap;

public class LoginSecurityException extends BusinessException{
    private static final long serialVersionUID = -8108811801123138587L;

    public LoginSecurityException() {
        super();
        this.state = StateMap.S_AUTH_ERROR;
    }
    
    public LoginSecurityException(String message) {
        super(message);
        this.state = StateMap.S_AUTH_ERROR;
    }
    
    public LoginSecurityException(int state, String message) {
        super(state, message);
    }
}
