package com.zc.addmony.bean;

/**
 *基本解析类
 * 
 */
public class BaseBean {
	private String  function;

	private int status;

	private String content;

	private String message;
	
	
	
	public String getFunction() {
		return function;
	}



	public void setFunction(String function) {
		this.function = function;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	@Override
	public String toString() {
		String str = status + "\n" + message + "\n" + content;
		return str;
	}
}
