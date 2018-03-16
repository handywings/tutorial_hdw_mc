package com.hdw.mccable.dto;

public class ReportCustomerByworksheetMoveBean {
	private int no;
	private String custCode; // รหัสสมาชิก 
	private String fullName; // ชื่อ-สกุล 
	private String mobile; // เบอร์ติดติอ
	private String serviceApplicationNo; // รหัสใบสมัคร
	private String createDate; // วันที่บันทึก 
	private String zoneOld; // โซนเดิม
	private String zoneNew; // โซนใหม
	private int countMove; // จำนวนครั้งที่ย้าย
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getServiceApplicationNo() {
		return serviceApplicationNo;
	}
	public void setServiceApplicationNo(String serviceApplicationNo) {
		this.serviceApplicationNo = serviceApplicationNo;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getZoneOld() {
		return zoneOld;
	}
	public void setZoneOld(String zoneOld) {
		this.zoneOld = zoneOld;
	}
	public String getZoneNew() {
		return zoneNew;
	}
	public void setZoneNew(String zoneNew) {
		this.zoneNew = zoneNew;
	}
	public int getCountMove() {
		return countMove;
	}
	public void setCountMove(int countMove) {
		this.countMove = countMove;
	}
	
}
