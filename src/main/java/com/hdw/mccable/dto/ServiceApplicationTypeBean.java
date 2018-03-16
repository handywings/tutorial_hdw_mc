package com.hdw.mccable.dto;

public class ServiceApplicationTypeBean extends DSTPUtilityBean{
	
	private Long id;
	private String serviceApplicationTypeName;
	private String serviceApplicationTypeCode;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getServiceApplicationTypeName() {
		return serviceApplicationTypeName;
	}
	public void setServiceApplicationTypeName(String serviceApplicationTypeName) {
		this.serviceApplicationTypeName = serviceApplicationTypeName;
	}
	public String getServiceApplicationTypeCode() {
		return serviceApplicationTypeCode;
	}
	public void setServiceApplicationTypeCode(String serviceApplicationTypeCode) {
		this.serviceApplicationTypeCode = serviceApplicationTypeCode;
	}
}
