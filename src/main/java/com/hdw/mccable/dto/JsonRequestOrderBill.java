package com.hdw.mccable.dto;

public class JsonRequestOrderBill {
	private ServiceApplicationBean serviceApplicationBean;
	private String orderBillDate;
	
	public ServiceApplicationBean getServiceApplicationBean() {
		return serviceApplicationBean;
	}

	public void setServiceApplicationBean(ServiceApplicationBean serviceApplicationBean) {
		this.serviceApplicationBean = serviceApplicationBean;
	}

	public String getOrderBillDate() {
		return orderBillDate;
	}

	public void setOrderBillDate(String orderBillDate) {
		this.orderBillDate = orderBillDate;
	}
	
	
}
