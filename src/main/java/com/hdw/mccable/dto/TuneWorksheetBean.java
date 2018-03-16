package com.hdw.mccable.dto;

public class TuneWorksheetBean extends WorksheetBean {
	private Long id;
	private int tuneType;  //1=ทีวีใหม่,2=ช่องเคลื่อน หรือ ช่องหาย,3=เพิ่มช่อง
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getTuneType() {
		return tuneType;
	}
	public void setTuneType(int tuneType) {
		this.tuneType = tuneType;
	}
	
	@Override
	public String toString() {
		return "TuneWorksheetBean [id=" + id + ", tuneType=" + tuneType + ", getServiceApplication()="
				+ getServiceApplication() + ", getProductItemList()=" + getProductItemList() + ", getJobscheduleList()="
				+ getJobscheduleList() + ", getStatus()=" + getStatus() + ", isDelete()=" + isDelete()
				+ ", getRemark()=" + getRemark() + ", getWorkSheetCode()=" + getWorkSheetCode() + ", getCreateDate()="
				+ getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy()
				+ ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh()
				+ ", getUpdateDateTh()=" + getUpdateDateTh() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	} 
}
