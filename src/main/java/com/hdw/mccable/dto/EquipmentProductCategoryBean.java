package com.hdw.mccable.dto;

public class EquipmentProductCategoryBean extends DSTPUtilityBean{
	
	private Long id;
	private String equipmentProductCategoryCode;
	private String equipmentProductCategoryName;
	private String description;
	private int countEquipmentProduct;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEquipmentProductCategoryCode() {
		return equipmentProductCategoryCode;
	}
	public void setEquipmentProductCategoryCode(String equipmentProductCategoryCode) {
		this.equipmentProductCategoryCode = equipmentProductCategoryCode;
	}
	public String getEquipmentProductCategoryName() {
		return equipmentProductCategoryName;
	}
	public void setEquipmentProductCategoryName(String equipmentProductCategoryName) {
		this.equipmentProductCategoryName = equipmentProductCategoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCountEquipmentProduct() {
		return countEquipmentProduct;
	}
	public void setCountEquipmentProduct(int countEquipmentProduct) {
		this.countEquipmentProduct = countEquipmentProduct;
	}
	@Override
	public String toString() {
		return "EquipmentProductCategoryBean [id=" + id + ", equipmentProductCategoryCode="
				+ equipmentProductCategoryCode + ", equipmentProductCategoryName=" + equipmentProductCategoryName
				+ ", description=" + description + ", countEquipmentProduct=" + countEquipmentProduct
				+ ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()="
				+ getCreateBy() + ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh()
				+ ", getUpdateDateTh()=" + getUpdateDateTh() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
