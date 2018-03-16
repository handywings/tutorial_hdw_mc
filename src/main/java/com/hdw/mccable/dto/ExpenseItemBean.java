package com.hdw.mccable.dto;

public class ExpenseItemBean {
	private String no; // No
	private String description; // รายละเอียด
	private String amount; // จำนวน
	private String price; // ราคา/หน่วย (บาท)
	private String totalPrice; // ราคา/หน่วย (บาท) รวม
	private String payment; // กำหนดชำระ
	
	private String overdue; // ยอดค้างชําระ
	private String prePrice; // ราคา/หน่วย (บาท) ครั้งก่อน
	
	public String getPrePrice() {
		return prePrice;
	}
	public void setPrePrice(String prePrice) {
		this.prePrice = prePrice;
	}
	public String getOverdue() {
		return overdue;
	}
	public void setOverdue(String overdue) {
		this.overdue = overdue;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

}