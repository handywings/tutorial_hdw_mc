package com.hdw.mccable.dto;

import java.util.List;

public class TechnicianGroupBean extends DSTPUtilityBean{
	
	private Long id;
	private String technicianGroupName;
	private PersonnelBean personnel;
	private List<PersonnelBean> personnelList;
	private int memberSize;
	
	public PersonnelBean getPersonnel() {
		return personnel;
	}

	public void setPersonnel(PersonnelBean personnel) {
		this.personnel = personnel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTechnicianGroupName() {
		return technicianGroupName;
	}

	public void setTechnicianGroupName(String technicianGroupName) {
		this.technicianGroupName = technicianGroupName;
	}

	public List<PersonnelBean> getPersonnelList() {
		return personnelList;
	}

	public void setPersonnelList(List<PersonnelBean> personnelList) {
		this.personnelList = personnelList;
	}

	public int getMemberSize() {
		return memberSize;
	}

	public void setMemberSize(int memberSize) {
		this.memberSize = memberSize;
	}

	@Override
	public String toString() {
		return "TechnicianGroupBean [id=" + id + ", technicianGroupName=" + technicianGroupName + ", personnel="
				+ personnel + ", personnelList=" + personnelList + ", getCreateDate()=" + getCreateDate()
				+ ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()="
				+ getUpdateBy() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}