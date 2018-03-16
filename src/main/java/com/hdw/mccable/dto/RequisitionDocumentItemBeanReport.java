package com.hdw.mccable.dto;

public class RequisitionDocumentItemBeanReport {
	
	private String no;
	private String productCode;
	private String serialNo;
	private String productName;
	private String quantity;
	private String unitName;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	@Override
	public String toString() {
		return "RequisitionDocumentItemBeanReport [no=" + no + ", productCode=" + productCode + ", serialNo=" + serialNo
				+ ", productName=" + productName + ", quantity=" + quantity + ", unitName=" + unitName + "]";
	}
	
}
