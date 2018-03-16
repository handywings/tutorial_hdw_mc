package com.hdw.mccable.dto;

public class RepairMatchItemBean extends DSTPUtilityBean{
	
	private Long oldItemId;
	private Long newItemId;
	private String comment;
	private EquipmentProductItemBean equipmentProductItemOld;
	private EquipmentProductItemBean equipmentProductItemNew;
	private Long productItemWorksheetsId;
	
	public Long getOldItemId() {
		return oldItemId;
	}
	public void setOldItemId(Long oldItemId) {
		this.oldItemId = oldItemId;
	}
	public Long getNewItemId() {
		return newItemId;
	}
	public void setNewItemId(Long newItemId) {
		this.newItemId = newItemId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public EquipmentProductItemBean getEquipmentProductItemOld() {
		return equipmentProductItemOld;
	}
	public void setEquipmentProductItemOld(EquipmentProductItemBean equipmentProductItemOld) {
		this.equipmentProductItemOld = equipmentProductItemOld;
	}
	public EquipmentProductItemBean getEquipmentProductItemNew() {
		return equipmentProductItemNew;
	}
	public void setEquipmentProductItemNew(EquipmentProductItemBean equipmentProductItemNew) {
		this.equipmentProductItemNew = equipmentProductItemNew;
	}
	public Long getProductItemWorksheetsId() {
		return productItemWorksheetsId;
	}
	public void setProductItemWorksheetsId(Long productItemWorksheetsId) {
		this.productItemWorksheetsId = productItemWorksheetsId;
	}
	
}
