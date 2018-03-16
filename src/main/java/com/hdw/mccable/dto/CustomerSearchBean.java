package com.hdw.mccable.dto;

public class CustomerSearchBean {
	
	private String key;
	private String custType;
	private Long customerFeatures;
	private Long zone;
	
	public Long getZone() {
		return zone;
	}
	public void setZone(Long zone) {
		this.zone = zone;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public Long getCustomerFeatures() {
		return customerFeatures;
	}
	public void setCustomerFeatures(Long customerFeatures) {
		this.customerFeatures = customerFeatures;
	}
}
