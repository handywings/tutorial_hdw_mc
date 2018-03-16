package com.hdw.mccable.dto;

public class AssignWorksheetSearchBean {
	
	private String key;
	private String jobType;
	private String jobStatus;
	private Long zone;
	private String orderByType; //
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public Long getZone() {
		return zone;
	}
	public void setZone(Long zone) {
		this.zone = zone;
	}
	public String getOrderByType() {
		return orderByType;
	}
	public void setOrderByType(String orderByType) {
		this.orderByType = orderByType;
	}
	
	@Override
	public String toString() {
		return "AssignWorksheetSearchBean [key=" + key + ", jobType=" + jobType + ", jobStatus=" + jobStatus + ", zone="
				+ zone + ", orderByType=" + orderByType + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
