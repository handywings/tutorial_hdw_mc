package com.hdw.mccable.dto;

import java.util.List;

public class MoveWorksheetBean extends WorksheetBean {
	private Long id;
	private List<AddressBean> addressList; // ใช้ตอน set
	private AddressBean address; // ใช้ตอน get
	private int digitalPoint;
	private int analogPoint;
	private float moveCablePrice; //ค่าย้ายสาย
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<AddressBean> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<AddressBean> addressList) {
		this.addressList = addressList;
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
	public float getMoveCablePrice() {
		return moveCablePrice;
	}
	public void setMoveCablePrice(float moveCablePrice) {
		this.moveCablePrice = moveCablePrice;
	}
	public AddressBean getAddress() {
		return address;
	}
	public void setAddress(AddressBean address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "MoveWorksheetBean [id=" + id + ", addressList=" + addressList + ", digitalPoint=" + digitalPoint
				+ ", analogPoint=" + analogPoint + ", moveCablePrice=" + moveCablePrice + ", getServiceApplication()="
				+ getServiceApplication() + ", getProductItemList()=" + getProductItemList() + ", getJobscheduleList()="
				+ getJobscheduleList() + ", getStatus()=" + getStatus() + ", isDelete()=" + isDelete()
				+ ", getRemark()=" + getRemark() + ", getWorkSheetCode()=" + getWorkSheetCode()
				+ ", getHistoryTechnicianGroupWorkBeans()=" + getHistoryTechnicianGroupWorkBeans()
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
