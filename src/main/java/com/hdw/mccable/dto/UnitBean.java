package com.hdw.mccable.dto;

public class UnitBean extends DSTPUtilityBean{
	
	private Long id;
	private String unitName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	@Override
	public String toString() {
		return "UnitBean [id=" + id + ", unitName=" + unitName + ", getCreateDate()=" + getCreateDate()
				+ ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()="
				+ getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()="
				+ getUpdateDateTh() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
