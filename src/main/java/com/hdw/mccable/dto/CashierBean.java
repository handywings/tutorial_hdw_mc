package com.hdw.mccable.dto;

public class CashierBean {

	private Long id;
	private String fullName;
	private Float sumAmount;
	private Long totalBill;
	private Float commission;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Float getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(Float sumAmount) {
		this.sumAmount = sumAmount;
	}
	public Long getTotalBill() {
		return totalBill;
	}
	public void setTotalBill(Long totalBill) {
		this.totalBill = totalBill;
	}
	public Float getCommission() {
		return commission;
	}
	public void setCommission(Float commission) {
		this.commission = commission;
	}	
	
}
