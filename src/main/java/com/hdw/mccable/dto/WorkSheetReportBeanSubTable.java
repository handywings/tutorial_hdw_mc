package com.hdw.mccable.dto;

public class WorkSheetReportBeanSubTable {

	private String returnEquipment; 
	private String listEquipment;
	private String quantity;
	private String unitName;
	private String lendStatus;
	private String deposit;	
	private Float floatDeposit;
	private String serialNo;
	private String price;
	
	public String getReturnEquipment() {
		return returnEquipment;
	}
	public void setReturnEquipment(String returnEquipment) {
		this.returnEquipment = returnEquipment;
	}
	public String getListEquipment() {
		return listEquipment;
	}
	public void setListEquipment(String listEquipment) {
		this.listEquipment = listEquipment;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getLendStatus() {
		return lendStatus;
	}
	public void setLendStatus(String lendStatus) {
		this.lendStatus = lendStatus;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Float getFloatDeposit() {
		return floatDeposit;
	}
	public void setFloatDeposit(Float floatDeposit) {
		this.floatDeposit = floatDeposit;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
