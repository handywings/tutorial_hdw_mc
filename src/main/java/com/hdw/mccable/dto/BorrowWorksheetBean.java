package com.hdw.mccable.dto;

public class BorrowWorksheetBean extends WorksheetBean {
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "BorrowWorksheetBean [id=" + id + ", getServiceApplication()=" + getServiceApplication()
				+ ", getProductItemList()=" + getProductItemList() + ", getJobscheduleList()=" + getJobscheduleList()
				+ ", getStatus()=" + getStatus() + ", isDelete()=" + isDelete() + ", getRemark()=" + getRemark()
				+ ", getWorkSheetCode()=" + getWorkSheetCode() + ", getHistoryTechnicianGroupWorkBeans()="
				+ getHistoryTechnicianGroupWorkBeans() + ", getRemarkNotSuccess()=" + getRemarkNotSuccess()
				+ ", getProductItemWorksheetBeanList()=" + getProductItemWorksheetBeanList() + ", getWorkSheetType()="
				+ getWorkSheetType() + ", getWorkSheetTypeText()=" + getWorkSheetTypeText()
				+ ", getIdWorksheetParent()=" + getIdWorksheetParent() + ", getCreateDate()=" + getCreateDate()
				+ ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()="
				+ getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()="
				+ getUpdateDateTh() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	 
}
