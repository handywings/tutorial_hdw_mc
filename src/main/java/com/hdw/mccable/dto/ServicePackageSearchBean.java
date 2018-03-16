package com.hdw.mccable.dto;

public class ServicePackageSearchBean {
	 
	private String key;
	private String servicePackageTypeCode;
	private String status;
	private Long companyId;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getServicePackageTypeCode() {
		return servicePackageTypeCode;
	}
	public void setServicePackageTypeCode(String servicePackageTypeCode) {
		this.servicePackageTypeCode = servicePackageTypeCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	@Override
	public String toString() {
		return "ServicePackageSearchBean [key=" + key + ", servicePackageTypeCode=" + servicePackageTypeCode
				+ ", status=" + status + ", companyId=" + companyId + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
}
