package com.hdw.mccable.dto;

public class SeachCountWorksheetRequest {
	private Long personnelId;
	private String dateRange;
	
	public Long getPersonnelId() {
		return personnelId;
	}
	public void setPersonnelId(Long personnelId) {
		this.personnelId = personnelId;
	}
	public String getDateRange() {
		return dateRange;
	}
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
	
	@Override
	public String toString() {
		return "SeachCountWorksheetRequest [personnelId=" + personnelId + ", dateRange=" + dateRange + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
