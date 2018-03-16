package com.hdw.mccable.dto;

public class BankBean extends DSTPUtilityBean{
	
	private Long id;
	private String bankNameTh;
	private String bankNameEn;
	private String bankShortName;
	private String bankCode;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBankNameTh() {
		return bankNameTh;
	}
	public void setBankNameTh(String bankNameTh) {
		this.bankNameTh = bankNameTh;
	}
	public String getBankNameEn() {
		return bankNameEn;
	}
	public void setBankNameEn(String bankNameEn) {
		this.bankNameEn = bankNameEn;
	}
	public String getBankShortName() {
		return bankShortName;
	}
	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
}
