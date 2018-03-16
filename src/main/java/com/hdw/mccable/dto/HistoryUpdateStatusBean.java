package com.hdw.mccable.dto;

public class HistoryUpdateStatusBean extends DSTPUtilityBean{
	
	private Long id;
	private String dateRepair;
	private String informer;
	private String recordDate;
	private String status;
	private String remark;
	private PersonnelBean personnelBean;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDateRepair() {
		return dateRepair;
	}
	public void setDateRepair(String dateRepair) {
		this.dateRepair = dateRepair;
	}
	public String getInformer() {
		return informer;
	}
	public void setInformer(String informer) {
		this.informer = informer;
	}
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public PersonnelBean getPersonnelBean() {
		return personnelBean;
	}
	public void setPersonnelBean(PersonnelBean personnelBean) {
		this.personnelBean = personnelBean;
	}
	
	@Override
	public String toString() {
		return "HistoryUpdateStatusBean [id=" + id + ", dateRepair=" + dateRepair + ", informer=" + informer
				+ ", recordDate=" + recordDate + ", status=" + status + ", remark=" + remark + ", personnelBean="
				+ personnelBean + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()=" + getUpdateDate()
				+ ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()="
				+ getCreateDateTh() + ", getUpdateDateTh()=" + getUpdateDateTh() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
