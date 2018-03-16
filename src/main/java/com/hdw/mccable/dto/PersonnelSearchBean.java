package com.hdw.mccable.dto;

public class PersonnelSearchBean {
	
	private String key;
	private String position;
	private Long permissionGroup;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Long getPermissionGroup() {
		return permissionGroup;
	}
	public void setPermissionGroup(Long permissionGroup) {
		this.permissionGroup = permissionGroup;
	}
	
	@Override
	public String toString() {
		return "PersonnelSearchBean [key=" + key + ", position=" + position + ", permissionGroup=" + permissionGroup
				+ "]";
	}

}
