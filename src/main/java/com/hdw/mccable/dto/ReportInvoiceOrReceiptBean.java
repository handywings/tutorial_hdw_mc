package com.hdw.mccable.dto;

import java.util.List;

public class ReportInvoiceOrReceiptBean {
	private String barCode;
	private String barCodeText;
	private String manuscript; // ต้นฉบับ
	private String title; // หัวข้อ
	private String no; // No ขวาบน
	private String invoiceCode; // เลขที่
	private String createdDate; // ลงวันที่
	private String serviceApplicationNo; // เอกสารอ้างอิง
	private String customerCode; // รหัสลูกค้า
	private String customerName; // ชื่อลูกค้า
	private String customerAddress; // ที่อยู่ลูกค้า
	private String customerTelephone; // โทรศัพท์ลูกค้า
	private String customerNearbyPlaces; // สถานที่ใกล้เคียงลูกค้า
	private String list1; // รายการ
	private String list2_1; // รายการ
	private String list2_2; // รายการ
	private String totalMoney; // รวมเป็นเงิน
	
	private String payWithin; // ชำระภายในวันที่
	
	private List<ExpenseItemBean> expenseItemList; // รายการค่าใช้จ่าย
	
	private String serviceCode; // รหัสบริการ
	private String privateVat; // vat
	private String privateNotVat; // ราคาก่อน vat
	private String companyVat; // vat ของบริษัท
	private String discount;
	private double discountValue;
	
	private double vat;
	private double vatCompany;
	
	public double getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public double getVatCompany() {
		return vatCompany;
	}
	public void setVatCompany(double vatCompany) {
		this.vatCompany = vatCompany;
	}
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
	public String getCompanyVat() {
		return companyVat;
	}
	public void setCompanyVat(String companyVat) {
		this.companyVat = companyVat;
	}
	public String getPrivateNotVat() {
		return privateNotVat;
	}
	public void setPrivateNotVat(String privateNotVat) {
		this.privateNotVat = privateNotVat;
	}
	public String getPrivateVat() {
		return privateVat;
	}
	public void setPrivateVat(String privateVat) {
		this.privateVat = privateVat;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getBarCodeText() {
		return barCodeText;
	}
	public void setBarCodeText(String barCodeText) {
		this.barCodeText = barCodeText;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getPayWithin() {
		return payWithin;
	}
	public void setPayWithin(String payWithin) {
		this.payWithin = payWithin;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getManuscript() {
		return manuscript;
	}
	public void setManuscript(String manuscript) {
		this.manuscript = manuscript;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getServiceApplicationNo() {
		return serviceApplicationNo;
	}
	public void setServiceApplicationNo(String serviceApplicationNo) {
		this.serviceApplicationNo = serviceApplicationNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getCustomerTelephone() {
		return customerTelephone;
	}
	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}
	public String getCustomerNearbyPlaces() {
		return customerNearbyPlaces;
	}
	public void setCustomerNearbyPlaces(String customerNearbyPlaces) {
		this.customerNearbyPlaces = customerNearbyPlaces;
	}
	public String getList1() {
		return list1;
	}
	public void setList1(String list1) {
		this.list1 = list1;
	}
	public String getList2_1() {
		return list2_1;
	}
	public void setList2_1(String list2_1) {
		this.list2_1 = list2_1;
	}
	public String getList2_2() {
		return list2_2;
	}
	public void setList2_2(String list2_2) {
		this.list2_2 = list2_2;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public List<ExpenseItemBean> getExpenseItemList() {
		return expenseItemList;
	}
	public void setExpenseItemList(List<ExpenseItemBean> expenseItemList) {
		this.expenseItemList = expenseItemList;
	}

}