package com.hdw.mccable.dto;

public class ReportCustomerByworksheetCancelBean {
	private int no;
	private String custCode; // รหัสสมาชิก 
	private String fullName; // ชื่อ-สกุล 
	private String mobile; // เบอร์ติดติอ
	private String serviceApplicationNo; // รหัสใบสมัคร
	private String servicePackageType; // ประเภทบริการ
	private String createDate; // วันที่บันทึก 
	private String remark; // เหตุผลตัดสาย
	private String zone; // เขตชุมชน
	
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
	public String getServicePackageType() {
		return servicePackageType;
	}
	public void setServicePackageType(String servicePackageType) {
		this.servicePackageType = servicePackageType;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		if("1".equals(remark)){
			remark = "ไม่มีเวลาดู";
		}else if("2".equals(remark)){
			remark = "ค้างชำระ";
		}else if("3".equals(remark)){
			remark = "ติดอินเตอร์เน็ต";
		}else if("4".equals(remark)){
			remark = "ติดจานที่อื่น";
		}else if("5".equals(remark)){
			remark = "ย้ายบ้าน (ไปในเขตที่สัญญาณไม่ครอบคลุม)";
		}else if("6".equals(remark)){
			remark = "อื่นๆ";
		}else{
			remark = " - ";
		}
		this.remark = remark;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	
}
