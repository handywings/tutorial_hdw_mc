package com.hdw.mccable.dto;

public class CashierSearchBean {
	private String daterange;
	private Long calCommissionPercent;
	private Long calCommissionBill;
	
	public String getDaterange() {
		return daterange;
	}
	public void setDaterange(String daterange) {
		this.daterange = daterange;
	}
	public Long getCalCommissionPercent() {
		return calCommissionPercent;
	}
	public void setCalCommissionPercent(Long calCommissionPercent) {
		this.calCommissionPercent = calCommissionPercent;
	}
	public Long getCalCommissionBill() {
		return calCommissionBill;
	}
	public void setCalCommissionBill(Long calCommissionBill) {
		this.calCommissionBill = calCommissionBill;
	}
	
}
