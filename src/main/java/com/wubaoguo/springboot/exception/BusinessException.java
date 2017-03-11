package com.wubaoguo.springboot.exception;

import com.wubaoguo.springboot.response.StateMap;

public class BusinessException extends Exception{
    private static final long serialVersionUID = -6825665437797680288L;

    protected int state;
    
    public BusinessException() {
        super();
        this.state = StateMap.S_ERROR;
    }

    public BusinessException(String message) {
        super(message);
        this.state = StateMap.S_ERROR;
    }

    public BusinessException(int state, String message) {
        super(message);
        this.state = state;
    }

    /**
     * Getter method for property <tt>state</tt>.
     * 
     * @return property value of state
     */
    public int getState() {
        return state;
    }

    /**
     * @see java.lang.Throwable#fillInStackTrace()
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
