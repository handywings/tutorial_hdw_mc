package com.hdw.mccable.dto;

public class OrderBillSearchBean {
	private String key;
	private String orderBillDate;
	private Long zone;
	private Long servicePackageTypeId;
	
	public Long getServicePackageTypeId() {
		return servicePackageTypeId;
	}
	public void setServicePackageTypeId(Long servicePackageTypeId) {
		this.servicePackageTypeId = servicePackageTypeId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getOrderBillDate() {
		return orderBillDate;
	}
	public void setOrderBillDate(String orderBillDate) {
		this.orderBillDate = orderBillDate;
	}
	public Long getZone() {
		return zone;
	}
	public void setZone(Long zone) {
		this.zone = zone;
	}
	
	
}
