package com.hdw.mccable.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionGroupBean extends DSTPUtilityBean{
	
	private Long id;
	private String permissionGroupName;
	private String permissionGroupDesc;
	private boolean status;
	private List<FunctionBean> functions;
	private List<PersonnelBean> personnels;
	private String permissionType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPermissionGroupName() {
		return permissionGroupName;
	}
	public void setPermissionGroupName(String permissionGroupName) {
		this.permissionGroupName = permissionGroupName;
	}
	public String getPermissionGroupDesc() {
		return permissionGroupDesc;
	}
	public void setPermissionGroupDesc(String permissionGroupDesc) {
		this.permissionGroupDesc = permissionGroupDesc;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<FunctionBean> getFunctions() {
		return functions;
	}
	public void setFunctions(List<FunctionBean> functions) {
		this.functions = functions;
	}
	
	public List<PersonnelBean> getPersonnels() {
		return personnels;
	}
	public void setPersonnels(List<PersonnelBean> personnels) {
		this.personnels = personnels;
	}
	public String getPermissionType() {
		return permissionType;
	}
	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}
	
	@Override
	public String toString() {
		return "PermissionGroupBean [id=" + id + ", permissionGroupName=" + permissionGroupName
				+ ", permissionGroupDesc=" + permissionGroupDesc + ", status=" + status + ", functions=" + functions
				+ ", personnels=" + personnels + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()="
				+ getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()=" + getUpdateBy()
				+ ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()=" + getUpdateDateTh()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
