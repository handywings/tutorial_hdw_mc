package com.hdw.mccable.dto;

public class PersonnelAssignBean {
	private Long id;
	private PersonnelBean personnelBean;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PersonnelBean getPersonnelBean() {
		return personnelBean;
	}
	public void setPersonnelBean(PersonnelBean personnelBean) {
		this.personnelBean = personnelBean;
	}
	
	@Override
	public String toString() {
		return "PersonnelAssign [id=" + id + ", personnelBean=" + personnelBean + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
