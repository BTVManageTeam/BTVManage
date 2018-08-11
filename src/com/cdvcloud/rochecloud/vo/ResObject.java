package com.cdvcloud.rochecloud.vo;

/**
 * 返回对象
 * 
 * @author TYW
 * 
 */
public class ResObject {

	private int status;
	private String message;
	private Object result;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "status:" + status + ",message:" + message + ",result:" + result;
	}
}