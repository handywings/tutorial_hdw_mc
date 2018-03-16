package com.hdw.mccable.dto;

public class JsonResponse {
	
	private String message;
	private String code;
	private boolean error;
	private Object result;
	private Object resultStore01;
	private Object resultStore02;
	private Object resultStore03;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public Object getResultStore01() {
		return resultStore01;
	}
	public void setResultStore01(Object resultStore01) {
		this.resultStore01 = resultStore01;
	}
	public Object getResultStore02() {
		return resultStore02;
	}
	public void setResultStore02(Object resultStore02) {
		this.resultStore02 = resultStore02;
	}
	public Object getResultStore03() {
		return resultStore03;
	}
	public void setResultStore03(Object resultStore03) {
		this.resultStore03 = resultStore03;
	}
	
}
