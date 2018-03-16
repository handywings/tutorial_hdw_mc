package com.hdw.mccable.dto;

public class ReceiptSearchBean {
	private String key;
	private String invoiceType;
	private String paymentType;
	private String daterange;
	private String itemPerPage;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getDaterange() {
		return daterange;
	}
	public void setDaterange(String daterange) {
		this.daterange = daterange;
	}
	public String getItemPerPage() {
		return itemPerPage;
	}
	public void setItemPerPage(String itemPerPage) {
		this.itemPerPage = itemPerPage;
	}
	@Override
	public String toString() {
		return "ReceiptSearchBean [key=" + key + ", invoiceType=" + invoiceType + ", paymentType=" + paymentType
				+ ", daterange=" + daterange + ", itemPerPage=" + itemPerPage + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
