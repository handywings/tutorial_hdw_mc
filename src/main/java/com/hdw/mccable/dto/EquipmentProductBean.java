package com.hdw.mccable.dto;

import java.util.List;

public class EquipmentProductBean extends ProductBean {
	
	private Long id;
	private String productName;
	private String productCode;
	private List<EquipmentProductItemBean> equipmentProductItemBeans;
	private String supplier;
	private int rest; // เหลือ
	private int usable; // ใช้ได้
	private int reserve; // สำรอง
	private int lend; // ยืม
	private int outOfOrder; // เสีย/ ชำรุด
	private int repair; // ซ่อม
	private boolean isminimum;
	private Long minimumNumber;
//	private float cost;
//	private float salePrice;
	private String equipmentTypeName;
	private String financialType; //A=Asset,C=Capital
	
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
	public List<EquipmentProductItemBean> getEquipmentProductItemBeans() {
		return equipmentProductItemBeans;
	}
	public void setEquipmentProductItemBeans(List<EquipmentProductItemBean> equipmentProductItemBeans) {
		this.equipmentProductItemBeans = equipmentProductItemBeans;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public int getRest() {
		return rest;
	}
	public void setRest(int rest) {
		this.rest = rest;
	}
	public int getUsable() {
		return usable;
	}
	public void setUsable(int usable) {
		this.usable = usable;
	}
	public int getReserve() {
		return reserve;
	}
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}
	public int getOutOfOrder() {
		return outOfOrder;
	}
	public void setOutOfOrder(int outOfOrder) {
		this.outOfOrder = outOfOrder;
	}
	public int getLend() {
		return lend;
	}
	public void setLend(int lend) {
		this.lend = lend;
	}
	public int getRepair() {
		return repair;
	}
	public void setRepair(int repair) {
		this.repair = repair;
	}
	public boolean isIsminimum() {
		return isminimum;
	}
	public void setIsminimum(boolean isminimum) {
		this.isminimum = isminimum;
	}
	public Long getMinimumNumber() {
		return minimumNumber;
	}
	public void setMinimumNumber(Long minimumNumber) {
		this.minimumNumber = minimumNumber;
	}
	public String getEquipmentTypeName() {
		return equipmentTypeName;
	}
	public void setEquipmentTypeName(String equipmentTypeName) {
		this.equipmentTypeName = equipmentTypeName;
	}
	public String getFinancialType() {
		return financialType;
	}
	public void setFinancialType(String financialType) {
		this.financialType = financialType;
	}
	
	@Override
	public String toString() {
		return "EquipmentProductBean [id=" + id + ", productName=" + productName + ", productCode=" + productCode
				+ ", equipmentProductItemBeans=" + equipmentProductItemBeans + ", supplier=" + supplier + ", rest="
				+ rest + ", usable=" + usable + ", reserve=" + reserve + ", lend=" + lend + ", outOfOrder=" + outOfOrder
				+ ", repair=" + repair + ", isminimum=" + isminimum + ", minimumNumber=" + minimumNumber
				+ ", getStock()=" + getStock() + ", getUnit()=" + getUnit() + ", getProductCategory()="
				+ getProductCategory() + ", getCriteria()=" + getCriteria() + ", getViewType()=" + getViewType()
				+ ", toString()=" + super.toString() + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()="
				+ getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()=" + getUpdateBy()
				+ ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()=" + getUpdateDateTh()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	} 
}
