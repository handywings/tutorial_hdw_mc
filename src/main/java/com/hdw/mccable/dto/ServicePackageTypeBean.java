package com.hdw.mccable.dto;

public class ServicePackageTypeBean extends DSTPUtilityBean{
	
	private Long id;
	private String packageTypeCode;
	private String packageTypeName;
	private String description;
	private int countPackage;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPackageTypeCode() {
		return packageTypeCode;
	}
	public void setPackageTypeCode(String packageTypeCode) {
		this.packageTypeCode = packageTypeCode;
	}
	public String getPackageTypeName() {
		return packageTypeName;
	}
	public void setPackageTypeName(String packageTypeName) {
		this.packageTypeName = packageTypeName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCountPackage() {
		return countPackage;
	}
	public void setCountPackage(int countPackage) {
		this.countPackage = countPackage;
	}
	
	@Override
	public String toString() {
		return "ServicePackageTypeBean [id=" + id + ", packageTypeCode=" + packageTypeCode + ", packageTypeName="
				+ packageTypeName + ", description=" + description + ", getCreateDate()=" + getCreateDate()
				+ ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()="
				+ getUpdateBy() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
