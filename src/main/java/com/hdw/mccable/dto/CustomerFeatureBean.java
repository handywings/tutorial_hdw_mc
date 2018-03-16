package com.hdw.mccable.dto;

public class CustomerFeatureBean extends DSTPUtilityBean{
	
	private Long id;
	private String customerFeatureName;
	private String customerFeatureCode;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustomerFeatureName() {
		return customerFeatureName;
	}
	public void setCustomerFeatureName(String customerFeatureName) {
		this.customerFeatureName = customerFeatureName;
	}
	public String getCustomerFeatureCode() {
		return customerFeatureCode;
	}
	public void setCustomerFeatureCode(String customerFeatureCode) {
		this.customerFeatureCode = customerFeatureCode;
	}
}
