package com.hdw.mccable.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="bank")
public class Bank {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "bankNameTh", nullable=false)
	private String bankNameTh;
	
	@Column(name = "bankNameEn", nullable=true)
	private String bankNameEn;
	
	@Column(name = "bankShortName", nullable=false)
	private String bankShortName;
	
	@Column(name = "bankCode", unique = true, nullable=false)
	private String bankCode;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bank", cascade={CascadeType.ALL})
	List<Receipt> receipts;

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

	public List<Receipt> getReceipts() {
		return receipts;
	}

	public void setReceipts(List<Receipt> receipts) {
		this.receipts = receipts;
	}
	
}
