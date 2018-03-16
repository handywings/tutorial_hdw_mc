package com.hdw.mccable.dto;

public class SearchAllProductBean {
	private String key;
	private String productTypeCode;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getProductTypeCode() {
		return productTypeCode;
	}
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	
	@Override
	public String toString() {
		return "SearchAllProductBean [key=" + key + ", productTypeCode=" + productTypeCode + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	 
}
