package com.hdw.mccable.dto;

public class ReportInvoiceExcelBwanMain {
	private String no;
	private String invoiceNo; // เลขที่ใบแจ้งหนี้
	private String cusName; // ชื่อลูกค้า
	private String cusCode; //
	private String zoneName; // เขตชุมชนส่งบิล
	private String zoneCode; // เขตชุมชนส่งบิล
	private int pointTotal; // จำนวนจุดรวม
	private float price; // ยอดเงิน (บาท)
	private String paymentDate; // วันที่นัดชำระ
	private String status; // สถานะ
	private String packageService; // 
	private String serviceApplicationType; // ประเภทใบสมัคร
	private String statusText;
	
	private String badDebt; // สถานะ/หนี้สูญ
	
	private String billing; // สถานะ/วางบิลแล้ว
	
	public String getBadDebt() {
		return badDebt;
	}
	public void setBadDebt(String badDebt) {
		this.badDebt = badDebt;
	}
	public String getBilling() {
		return billing;
	}
	public void setBilling(String billing) {
		this.billing = billing;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public String getPackageService() {
		return packageService;
	}
	public void setPackageService(String packageService) {
		this.packageService = packageService;
	}
	public String getServiceApplicationType() {
		return serviceApplicationType;
	}
	public void setServiceApplicationType(String serviceApplicationType) {
		this.serviceApplicationType = serviceApplicationType;
	}
	public String getCusCode() {
		return cusCode;
	}
	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public int getPointTotal() {
		return pointTotal;
	}
	public void setPointTotal(int pointTotal) {
		this.pointTotal = pointTotal;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}