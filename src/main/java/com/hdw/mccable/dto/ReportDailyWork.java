package com.hdw.mccable.dto;

public class ReportDailyWork {
	private String no; // ลำดับที่ 
	private String workSheetType; // ประเภทใบงาน
	private String custName; // ข้อมูลลูกค้า
	private String custTel; // หมายเลขติดต่อ
	private String address; // ที่อยู่ปฏิบัติงาน
	private String craetedBy; // ผู้รับเรื่อง
	private String craetedDate; // เวลารับเรื่อง
	private String invoiceCode; // เลขที่บิล	
	private String amount; // จำนวนเงิน
	private String desc; // กรณีติดตั้งบริการเคเบิล
	private String numberPoints;
	
	
	public String getNumberPoints() {
		return numberPoints;
	}
	public void setNumberPoints(String numberPoints) {
		this.numberPoints = numberPoints;
	}
	public void setWorkSheetType(String workSheetType) {
		this.workSheetType = workSheetType;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustTel() {
		return custTel;
	}
	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCraetedBy() {
		return craetedBy;
	}
	public void setCraetedBy(String craetedBy) {
		this.craetedBy = craetedBy;
	}
	public String getCraetedDate() {
		return craetedDate;
	}
	public void setCraetedDate(String craetedDate) {
		this.craetedDate = craetedDate;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getWorkSheetType() {
		return workSheetType;
	}
	public void setWorkSheetType(String workSheetType, String point) {
		if("C_S".equals(workSheetType)){
			workSheetType = "ติดตั้งใหม่";
		}else if("C_AP".equals(workSheetType)){
			workSheetType = "เสริมจุดบริการ";
		}else if("C_C".equals(workSheetType)){
			workSheetType = "การจั้มสาย";
		}else if("C_TTV".equals(workSheetType)){
			workSheetType = "การจูน TV";
		}else if("C_RC".equals(workSheetType)){
			workSheetType = "การซ่อมสัญญาณ";
		}else if("C_ASTB".equals(workSheetType)){
			workSheetType = "เพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี";
		}else if("C_MP".equals(workSheetType)){
			workSheetType = "การย้ายจุด";
		}else if("C_RP".equals(workSheetType)){
			workSheetType = "การลดจุด";
		}else if("C_CU".equals(workSheetType)){
			workSheetType = "การตัดสาย";
		}else if("C_M".equals(workSheetType)){
			workSheetType = "การย้ายสาย";
		}else if("C_B".equals(workSheetType)){
			workSheetType = "ยืมอุปกรณ์รับสัญญาณเคเบิลทีวี";
		}
		
		this.workSheetType = "ใบงาน"+workSheetType+" "+point;
	}

}
