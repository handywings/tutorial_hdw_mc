package com.hdw.mccable.dto;

public class InvoiceSearchBean {
	private String tab;
	private String key;
	private String invoiceType;
	private String daterange;
	private String daterange1;
	private String status;
	private String itemPerPage;
	private Long zone;
	private String searchbill;
	private Long personnelId;
	private String searchbadDebt;
	private Long servicePackageTypeId;
	
	public String getDaterange1() {
		return daterange1;
	}
	public void setDaterange1(String daterange1) {
		this.daterange1 = daterange1;
	}
	public Long getServicePackageTypeId() {
		return servicePackageTypeId;
	}
	public void setServicePackageTypeId(Long servicePackageTypeId) {
		this.servicePackageTypeId = servicePackageTypeId;
	}
	public String getSearchbadDebt() {
		return searchbadDebt;
	}
	public void setSearchbadDebt(String searchbadDebt) {
		this.searchbadDebt = searchbadDebt;
	}
	public Long getPersonnelId() {
		return personnelId;
	}
	public void setPersonnelId(Long personnelId) {
		this.personnelId = personnelId;
	}
	public String getTab() {
		return tab;
	}
	public void setTab(String tab) {
		this.tab = tab;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getDaterange() {
		return daterange;
	}
	public void setDaterange(String daterange) {
		this.daterange = daterange;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getItemPerPage() {
		return itemPerPage;
	}
	public void setItemPerPage(String itemPerPage) {
		this.itemPerPage = itemPerPage;
	}
	public Long getZone() {
		return zone;
	}
	public void setZone(Long zone) {
		this.zone = zone;
	}
	public String getSearchbill() {
		return searchbill;
	}
	public void setSearchbill(String searchbill) {
		this.searchbill = searchbill;
	}
	@Override
	public String toString() {
		return "InvoiceSearchBean [tab=" + tab + ", key=" + key + ", invoiceType=" + invoiceType + ", daterange="
				+ daterange + ", status=" + status + ", itemPerPage=" + itemPerPage + ", zone=" + zone + ", searchbill="
				+ searchbill + ", personnelId=" + personnelId + "]";
	}

}
