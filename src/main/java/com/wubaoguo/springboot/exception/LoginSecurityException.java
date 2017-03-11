package com.wubaoguo.springboot.exception;

import com.wubaoguo.springboot.response.StateMap;

public class LoginSecurityException extends BusinessException{
    private static final long serialVersionUID = -8108811801123138587L;

    public LoginSecurityException() {
        super();
        this.state = StateMap.S_403;
    }
    
    public LoginSecurityException(String message) {
        super(message);
        this.state = StateMap.S_403;
    }
    
    public LoginSecurityException(int state, String message) {
        super(state, message);
    }
}
