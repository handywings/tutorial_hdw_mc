package com.hdw.mccable.dto;

public class ReportCustomerByConditionBean {
	private String custCode; // รหัสสมาชิก 
	private String fullName; // ชื่อ-สกุล 
	private String createDate; // วันที่บันทึก 
	private String customerType; // ประเภทลูกค้า
	private String career; // อาชีพ
	private String mobile; // เบอร์ติดติอ
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
