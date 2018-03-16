package com.hdw.mccable.dto;

public class ReportBilUnpaidBean {
	private String invoiceNo; // เลขที่ใบแจ้งหนี้
	private String invoiceDescription; // รายละเอียดใบแจ้งหนี้
	private String payment; // ยอดชำระ
	private String status; // สถานะ
	private String sumPayment; // ยอดชำระรวม
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInvoiceDescription() {
		return invoiceDescription;
	}
	public void setInvoiceDescription(String invoiceDescription) {
		this.invoiceDescription = invoiceDescription;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSumPayment() {
		return sumPayment;
	}
	public void setSumPayment(String sumPayment) {
		this.sumPayment = sumPayment;
	}
	
}
