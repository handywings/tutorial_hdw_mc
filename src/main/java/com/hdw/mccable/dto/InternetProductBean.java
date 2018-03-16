package com.hdw.mccable.dto;

import java.util.List;

public class InternetProductBean extends ProductBean {
	
	private Long id;
	private String productName;
	private String productCode; 
	private StockBean stock;
	private String criteria;
	private List<InternetProductBeanItem> internetProductBeanItems;
	private String status;
	
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
	public StockBean getStock() {
		return stock;
	}
	public void setStock(StockBean stock) {
		this.stock = stock;
	}
	public String getCriteria() {
		return criteria;
	}
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	public List<InternetProductBeanItem> getInternetProductBeanItems() {
		return internetProductBeanItems;
	}
	public void setInternetProductBeanItems(List<InternetProductBeanItem> internetProductBeanItems) {
		this.internetProductBeanItems = internetProductBeanItems;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "InternetProductBean [id=" + id + ", productName=" + productName + ", productCode=" + productCode
				+ ", stock=" + stock + ", criteria=" + criteria + ", internetProductBeanItems="
				+ internetProductBeanItems + ", status=" + status + ", getUnit()=" + getUnit()
				+ ", getProductCategory()=" + getProductCategory() + ", getViewType()=" + getViewType()
				+ ", toString()=" + super.toString() + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()="
				+ getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()=" + getUpdateBy()
				+ ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()=" + getUpdateDateTh()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	 
}
