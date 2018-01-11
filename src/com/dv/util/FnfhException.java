package com.dv.util;

public class FnfhException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int code;
	private Object data;
	
	public FnfhException() {
		super();
		this.code = 0;
	}
	
	public FnfhException(String info) {
		super(info);
		this.code = 0;
		this.setData(null);
	}
	
	public FnfhException(int code, String info) {
		super(info);
		this.setCode(code);
		this.setData(null);
	}
	
	public FnfhException(int code, String info, Object data) {
		super(info);
		this.setCode(code);
		this.setData(data);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
