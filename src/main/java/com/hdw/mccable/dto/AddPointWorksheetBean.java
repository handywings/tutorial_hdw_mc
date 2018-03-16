package com.hdw.mccable.dto;

import java.util.List;

public class AddPointWorksheetBean extends WorksheetBean {
	
	private Long id;
	private int digitalPoint;
	private int analogPoint;
	private float addPointPrice; //ค่าเสริมจุด
	private float monthlyFree; //ค่าบริการรายเดือนเพิ่มเติม
	
	private List<ServiceProductBean> serviceProductBean; // ไว้ดึงราคาจาก master ออกมาใช้

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

	public float getAddPointPrice() {
		return addPointPrice;
	}

	public void setAddPointPrice(float addPointPrice) {
		this.addPointPrice = addPointPrice;
	}

	public float getMonthlyFree() {
		return monthlyFree;
	}

	public void setMonthlyFree(float monthlyFree) {
		this.monthlyFree = monthlyFree;
	}

	public List<ServiceProductBean> getServiceProductBean() {
		return serviceProductBean;
	}

	public void setServiceProductBean(List<ServiceProductBean> serviceProductBean) {
		this.serviceProductBean = serviceProductBean;
	}

	@Override
	public String toString() {
		return "AddPointWorksheetBean [id=" + id + ", digitalPoint=" + digitalPoint + ", analogPoint=" + analogPoint
				+ ", addPointPrice=" + addPointPrice + ", monthlyFree=" + monthlyFree + ", serviceProductBean="
				+ serviceProductBean + ", getServiceApplication()=" + getServiceApplication()
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
