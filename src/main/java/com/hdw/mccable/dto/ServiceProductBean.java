package com.hdw.mccable.dto;

public class ServiceProductBean extends ProductBean {
	
	private Long id;
	private String productName;
	private String productCode; 
	private float price;
	private String criteria;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	 
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getCriteria() {
		return criteria;
	}
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	
	@Override
	public String toString() {
		return "ServiceProductBean [id=" + id + ", productName=" + productName + ", productCode=" + productCode
				+ ", price=" + price + ", criteria=" + criteria + ", getStock()=" + getStock() + ", getUnit()="
				+ getUnit() + ", getProductCategory()=" + getProductCategory() + ", getType()=" + getType()
				+ ", getViewType()=" + getViewType() + ", toString()=" + super.toString() + ", getCreateDate()="
				+ getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy()
				+ ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh()
				+ ", getUpdateDateTh()=" + getUpdateDateTh() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
	
}
