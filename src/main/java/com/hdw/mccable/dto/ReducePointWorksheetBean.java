package com.hdw.mccable.dto;
public class ReducePointWorksheetBean extends WorksheetBean {
	
	private Long id;
	private int digitalPoint;
	private int analogPoint;
	private float monthlyFree; //ค่าบริการรายเดือนลดลง
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getDigitalPoint() {
		return digitalPoint;
	}
	public void setDigitalPoint(int digitalPoint) {
		this.digitalPoint = digitalPoint;
	}
	public int getAnalogPoint() {
		return analogPoint;
	}
	public void setAnalogPoint(int analogPoint) {
		this.analogPoint = analogPoint;
	}
	
	public float getMonthlyFree() {
		return monthlyFree;
	}
	public void setMonthlyFree(float monthlyFree) {
		this.monthlyFree = monthlyFree;
	}
	@Override
	public String toString() {
		return "ReducePointWorksheetBean [id=" + id + ", digitalPoint=" + digitalPoint + ", analogPoint=" + analogPoint
				+ ", getServiceApplication()=" + getServiceApplication() + ", getProductItemList()="
				+ getProductItemList() + ", getJobscheduleList()=" + getJobscheduleList() + ", getStatus()="
				+ getStatus() + ", isDelete()=" + isDelete() + ", getRemark()=" + getRemark() + ", getWorkSheetCode()="
				+ getWorkSheetCode() + ", getHistoryTechnicianGroupWorkBeans()=" + getHistoryTechnicianGroupWorkBeans()
				+ ", getRemarkNotSuccess()=" + getRemarkNotSuccess() + ", getProductItemWorksheetBeanList()="
				+ getProductItemWorksheetBeanList() + ", getWorkSheetType()=" + getWorkSheetType()
				+ ", getWorkSheetTypeText()=" + getWorkSheetTypeText() + ", getIdWorksheetParent()="
				+ getIdWorksheetParent() + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()="
				+ getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()=" + getUpdateBy()
				+ ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()=" + getUpdateDateTh()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
}
