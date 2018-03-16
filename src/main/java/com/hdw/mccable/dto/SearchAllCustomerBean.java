package com.hdw.mccable.dto;

public class SearchAllCustomerBean {
	private String key;
	private String customerType;
	private Long customerFeatures;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public Long getCustomerFeatures() {
		return customerFeatures;
	}
	public void setCustomerFeatures(Long customerFeatures) {
		this.customerFeatures = customerFeatures;
	}
	@Override
	public String toString() {
		return "SearchAllCustomerBean [key=" + key + ", customerType=" + customerType + ", customerFeature=" + customerFeatures + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}	
}
