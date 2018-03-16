package com.hdw.mccable.dto;

public class ApplicationSearchBean {
	
	private String key;
	private Long servicePackageType;
	private Long servicePackage;
	private String status;
	private String custType;
	private Long customerFeatures;
	private Long zone;
	private String dateRangeRefund;
	private String billPaymentDateSearch;
	private Long personnelId; //
	
	public Long getPersonnelId() {
		return personnelId;
	}
	public void setPersonnelId(Long personnelId) {
		this.personnelId = personnelId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Long getServicePackageType() {
		return servicePackageType;
	}
	public void setServicePackageType(Long servicePackageType) {
		this.servicePackageType = servicePackageType;
	}
	public Long getServicePackage() {
		return servicePackage;
	}
	public void setServicePackage(Long servicePackage) {
		this.servicePackage = servicePackage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public Long getCustomerFeatures() {
		return customerFeatures;
	}
	public void setCustomerFeatures(Long customerFeatures) {
		this.customerFeatures = customerFeatures;
	}
	public Long getZone() {
		return zone;
	}
	public void setZone(Long zone) {
		this.zone = zone;
	}
	public String getDateRangeRefund() {
		return dateRangeRefund;
	}
	public void setDateRangeRefund(String dateRangeRefund) {
		this.dateRangeRefund = dateRangeRefund;
	}
	public String getBillPaymentDateSearch() {
		return billPaymentDateSearch;
	}
	public void setBillPaymentDateSearch(String billPaymentDateSearch) {
		this.billPaymentDateSearch = billPaymentDateSearch;
	}
	@Override
	public String toString() {
		return "ApplicationSearchBean [key=" + key + ", servicePackageType=" + servicePackageType + ", servicePackage="
				+ servicePackage + ", status=" + status + ", custType=" + custType + ", customerFeatures="
				+ customerFeatures + ", zone=" + zone + ", dateRangeRefund=" + dateRangeRefund
				+ ", billPaymentDateSearch=" + billPaymentDateSearch + ", personnelId=" + personnelId + "]";
	}


}
