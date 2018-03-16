package com.hdw.mccable.dto;

public class MigrateInvoiceBean extends DSTPUtilityBean{
	private String invoiceCode; // รหัสใบแจ้งหนี้ -> inv_no
	private String invoiceCode2; // รหัสใบแจ้งหนี้2 -> inv_no2
	private String bdate2; // 
	private String bdate3; // 
	private String bdate4; // ครบกำหนด
	private String bdate5; // 
	private String customerCode; // *รหัสสมาชิก/รหัสลูกค้า -> customerid
	private String firstName; // ชื่อ -> firstName
	private String lastName; // นามสกุล -> lastName
	private String vat; // 
	private String num; // 
	private String sta; // 
	
	private String ch1; // 
	private String ch2; // 
	private String ch3; // 
	private String ch4; // 
	private String ch5; // 
	private String chk; // 
	private String pchk; // 
	
	private String txtnote; //

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceCode2() {
		return invoiceCode2;
	}

	public void setInvoiceCode2(String invoiceCode2) {
		this.invoiceCode2 = invoiceCode2;
	}

	public String getBdate2() {
		return bdate2;
	}

	public void setBdate2(String bdate2) {
		this.bdate2 = bdate2;
	}

	public String getBdate3() {
		return bdate3;
	}

	public void setBdate3(String bdate3) {
		this.bdate3 = bdate3;
	}

	public String getBdate4() {
		return bdate4;
	}

	public void setBdate4(String bdate4) {
		this.bdate4 = bdate4;
	}

	public String getBdate5() {
		return bdate5;
	}

	public void setBdate5(String bdate5) {
		this.bdate5 = bdate5;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getSta() {
		return sta;
	}

	public void setSta(String sta) {
		this.sta = sta;
	}

	public String getCh1() {
		return ch1;
	}

	public void setCh1(String ch1) {
		this.ch1 = ch1;
	}

	public String getCh2() {
		return ch2;
	}

	public void setCh2(String ch2) {
		this.ch2 = ch2;
	}

	public String getCh3() {
		return ch3;
	}

	public void setCh3(String ch3) {
		this.ch3 = ch3;
	}

	public String getCh4() {
		return ch4;
	}

	public void setCh4(String ch4) {
		this.ch4 = ch4;
	}

	public String getCh5() {
		return ch5;
	}

	public void setCh5(String ch5) {
		this.ch5 = ch5;
	}

	public String getChk() {
		return chk;
	}

	public void setChk(String chk) {
		this.chk = chk;
	}

	public String getPchk() {
		return pchk;
	}

	public void setPchk(String pchk) {
		this.pchk = pchk;
	}

	public String getTxtnote() {
		return txtnote;
	}

	public void setTxtnote(String txtnote) {
		this.txtnote = txtnote;
	}

}
