package com.hdw.mccable.dto;

public class HistoryUseEquipmentBean extends DSTPUtilityBean{
	
	private Long id;
	private CustomerBean customerBean;
	private String activeDate;
	private String returnDate;
	private String status;
	private ServiceApplicationBean serviceApplicationBean;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CustomerBean getCustomerBean() {
		return customerBean;
	}
	public void setCustomerBean(CustomerBean customerBean) {
		this.customerBean = customerBean;
	}
	public String getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	
	@Override
	public String toString() {
		return "HistoryUseEquipment [id=" + id + ", customerBean=" + customerBean + ", activeDate=" + activeDate
				+ ", returnDate=" + returnDate + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()="
				+ getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()=" + getUpdateBy()
				+ ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()=" + getUpdateDateTh()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	public ServiceApplicationBean getServiceApplicationBean() {
		return serviceApplicationBean;
	}
	public void setServiceApplicationBean(ServiceApplicationBean serviceApplicationBean) {
		this.serviceApplicationBean = serviceApplicationBean;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		// สถานะอุปกรณ์ L = ยืม, F = ฟรี, ขายขาด = O, R = คืน, D = ชำรุด, B = หาย, C = CA
		if("L".equals(status)){
			status = "ยืม";
		}else if("F".equals(status)){
			status = "ฟรี";
		}else if("O".equals(status)){
			status = "ขายขาด";
		}else if("R".equals(status)){
			status = "คืน";
		}else if("D".equals(status)){
			status = "ชำรุด";
		}else if("B".equals(status)){
			status = "หาย";
		}else if("C".equals(status)){
			status = "CA";
		}else{
			status = " - ";
		}
		this.status = status;
	}
	
	
}
