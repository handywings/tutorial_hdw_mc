package com.hdw.mccable.dto;

import javax.persistence.Column;

public class RequisitionItemBean extends DSTPUtilityBean{
	
	private Long id;
	private EquipmentProductBean equipment;
	private int quantity;
	private EquipmentProductItemBean equipmentItem;
	private PersonnelBean personnelBean; // ผู้ขอเบิกสินค้า  (หัวหน้ากลุ่ม)
	private RequisitionDocumentBean requisitionDocumentBean;
	// จำนวนที่คืนอุปกรณ์
	private int returnEquipmentProductItem;
	// จำนวนอุปกรณ์ที่ถูกใช้งานไป
	private int sellEquipmentProductItem;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EquipmentProductBean getEquipment() {
		return equipment;
	}
	public void setEquipment(EquipmentProductBean equipment) {
		this.equipment = equipment;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public EquipmentProductItemBean getEquipmentItem() {
		return equipmentItem;
	}
	public void setEquipmentItem(EquipmentProductItemBean equipmentItem) {
		this.equipmentItem = equipmentItem;
	}
	public PersonnelBean getPersonnelBean() {
		return personnelBean;
	}
	public void setPersonnelBean(PersonnelBean personnelBean) {
		this.personnelBean = personnelBean;
	}
	public RequisitionDocumentBean getRequisitionDocumentBean() {
		return requisitionDocumentBean;
	}
	public void setRequisitionDocumentBean(RequisitionDocumentBean requisitionDocumentBean) {
		this.requisitionDocumentBean = requisitionDocumentBean;
	}
	public int getReturnEquipmentProductItem() {
		return returnEquipmentProductItem;
	}
	public void setReturnEquipmentProductItem(int returnEquipmentProductItem) {
		this.returnEquipmentProductItem = returnEquipmentProductItem;
	}
	public int getSellEquipmentProductItem() {
		return sellEquipmentProductItem;
	}
	public void setSellEquipmentProductItem(int sellEquipmentProductItem) {
		this.sellEquipmentProductItem = sellEquipmentProductItem;
	}
	@Override
	public String toString() {
		return "RequisitionItemBean [id=" + id + ", equipment=" + equipment + ", quantity=" + quantity
				+ ", equipmentItem=" + equipmentItem + ", personnelBean=" + personnelBean + ", requisitionDocumentBean="
				+ requisitionDocumentBean + ", returnEquipmentProductItem=" + returnEquipmentProductItem
				+ ", sellEquipmentProductItem=" + sellEquipmentProductItem + ", getCreateDate()=" + getCreateDate()
				+ ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()="
				+ getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()="
				+ getUpdateDateTh() + ", getCreateByTh()=" + getCreateByTh() + ", getUpdateByTh()=" + getUpdateByTh()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
}
