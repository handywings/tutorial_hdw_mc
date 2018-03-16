package com.hdw.mccable.dto;

public class JsonRequestAssignWorksheet {
	private Long worksheetId;
	private Long technicianGroupId;
	private String assignDate;
	
	public Long getWorksheetId() {
		return worksheetId;
	}
	public void setWorksheetId(Long worksheetId) {
		this.worksheetId = worksheetId;
	}
	public Long getTechnicianGroupId() {
		return technicianGroupId;
	}
	public void setTechnicianGroupId(Long technicianGroupId) {
		this.technicianGroupId = technicianGroupId;
	}
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
}
