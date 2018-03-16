package com.hdw.mccable.dto;

public class InvoiceReportBean {
	private String invoiceCode;
	private String invoiceType; //S=สำหรับติดตั้ง,R=สำหรับซ่อม,O=สำหรับตาม
	private String custCode; // รหัสลูกค้า CUST-00000X 
	private String prefix;
	private String firstName;
	private String lastName;
	private String address;
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "InvoiceReportBean [invoiceCode=" + invoiceCode + ", invoiceType=" + invoiceType + ", custCode="
				+ custCode + ", prefix=" + prefix + ", firstName=" + firstName + ", lastName=" + lastName + ", address="
				+ address + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}
