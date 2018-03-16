package com.hdw.mccable.dto;

public class InternetProductBeanItem extends ProductBean{
	private Long id;
	private String userName;
	private String password;
	private String reference;
	private InternetProductBean internetProductBean;
	private String status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public InternetProductBean getInternetProductBean() {
		return internetProductBean;
	}
	public void setInternetProductBean(InternetProductBean internetProductBean) {
		this.internetProductBean = internetProductBean;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "InternetProductBeanItem [id=" + id + ", userName=" + userName + ", password=" + password
				+ ", reference=" + reference + ", internetProductBean=" + internetProductBean + ", getStock()="
				+ getStock() + ", getUnit()=" + getUnit() + ", getProductCategory()=" + getProductCategory()
				+ ", getCriteria()=" + getCriteria() + ", getType()=" + getType() + ", getViewType()=" + getViewType()
				+ ", toString()=" + super.toString() + ", getStockAmount()=" + getStockAmount() + ", getBalance()="
				+ getBalance() + ", getReservations()=" + getReservations() + ", getSell()=" + getSell()
				+ ", getSpare()=" + getSpare() + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()="
				+ getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()=" + getUpdateBy()
				+ ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()=" + getUpdateDateTh()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
}
