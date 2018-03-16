package com.hdw.mccable.dto;

public class JsonRequestUpdateStatus {
	private Long equipmentProductItemId;
	private int status;
	private String remark;
	private String dateRepair;
	
	public Long getEquipmentProductItemId() {
		return equipmentProductItemId;
	}
	public void setEquipmentProductItemId(Long equipmentProductItemId) {
		this.equipmentProductItemId = equipmentProductItemId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDateRepair() {
		return dateRepair;
	}
	public void setDateRepair(String dateRepair) {
		this.dateRepair = dateRepair;
	}
	
}
