package com.hdw.mccable.dto;

public class PositionBean extends DSTPUtilityBean{
	
	private Long id;
	private String positionName;
	private String desctiption;
	private CompanyBean company;

	public CompanyBean getCompany() {
		return company;
	}

	public void setCompany(CompanyBean company) {
		this.company = company;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getDesctiption() {
		return desctiption;
	}

	public void setDesctiption(String desctiption) {
		this.desctiption = desctiption;
	}

	@Override
	public String toString() {
		return "PositionBean [id=" + id + ", positionName=" + positionName + ", desctiption=" + desctiption
				+ ", company=" + company + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()="
				+ getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()=" + getUpdateBy()
				+ ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()=" + getUpdateDateTh()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
