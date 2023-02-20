package com.wubaoguo.springboot.core.request;


public class BaseState extends StateMap {

	private boolean success = true; // 成功标识
	
	private int state;
	
	private String message;
	
	public BaseState(boolean success, int state, String message) {
		super();
		this.success = success;
		this.state = state;
		this.message = message;
	}

	public BaseState(int state, String message) {
		this(false, state, message);
	}
	
	public BaseState(int state) {
		this(false, state, get(state));
	}
	
	public BaseState() {
		this.state = S_SUCCESS;
		this.message = "操作成功";
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
