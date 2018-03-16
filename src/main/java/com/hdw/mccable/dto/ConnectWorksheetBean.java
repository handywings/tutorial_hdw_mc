package com.hdw.mccable.dto;

public class ConnectWorksheetBean extends WorksheetBean {
	private Long id;
	private ServiceProductBean serviceProductBean;
	private float price;  //ใช้สำหรับเก็บค่า amount product service #00007 หน้าจอที่เปลี่ยนแปลง
	
	//เหลือ list ของใบค้างหนี้
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ServiceProductBean getServiceProductBean() {
		return serviceProductBean;
	}
	public void setServiceProductBean(ServiceProductBean serviceProductBean) {
		this.serviceProductBean = serviceProductBean;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "ConnectWorksheetBean [id=" + id + ", serviceProductBean=" + serviceProductBean + ", price=" + price
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
