package com.hdw.mccable.dto;

public class SearchBillScanBean {
	private String key;
	private Long zoneId;
	private Long personnelCashierId;
	private String dateExportRange;
	private String exportDate;
	private String year;
	private String month;
	private boolean isMobile;
	
	public boolean isMobile() {
		return isMobile;
	}
	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Long getZoneId() {
		return zoneId;
	}
	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}
	public Long getPersonnelCashierId() {
		return personnelCashierId;
	}
	public void setPersonnelCashierId(Long personnelCashierId) {
		this.personnelCashierId = personnelCashierId;
	}
	public String getDateExportRange() {
		return dateExportRange;
	}
	public void setDateExportRange(String dateExportRange) {
		this.dateExportRange = dateExportRange;
	}
	public String getExportDate() {
		return exportDate;
	}
	public void setExportDate(String exportDate) {
		this.exportDate = exportDate;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@Override
	public String toString() {
		return "SearchBillScanBean [key=" + key + ", zoneId=" + zoneId + ", personnelCashierId=" + personnelCashierId
				+ ", dateExportRange=" + dateExportRange + ", exportDate=" + exportDate + ", year=" + year + ", month="
				+ month + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}
