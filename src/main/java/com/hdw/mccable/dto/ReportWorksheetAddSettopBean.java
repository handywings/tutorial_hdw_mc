package com.hdw.mccable.dto;

import java.util.List;

public class ReportWorksheetAddSettopBean {

	private List<WorkSheetReportBeanSubTable> workSheetReportBeanSubTables;
	private String customer;
	private Float sumDeposit;
	private String mobile;
	private String identityNumber;
	
	private String no;
	private String village;
	private String alley;
	private String road;
	private String amphurName;
	private String districtName;
	private String provinceName;
	private String postcode;
	private String createDate;
	private String cusCode;
	private String workCode;
	
	private String companyName;
	private String companyMobile;
	private String companyFax;
	private String companyAddress;

	public List<WorkSheetReportBeanSubTable> getWorkSheetReportBeanSubTables() {
		return workSheetReportBeanSubTables;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyMobile() {
		return companyMobile;
	}

	public void setCompanyMobile(String companyMobile) {
		this.companyMobile = companyMobile;
	}

	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public void setWorkSheetReportBeanSubTables(List<WorkSheetReportBeanSubTable> workSheetReportBeanSubTables) {
		this.workSheetReportBeanSubTables = workSheetReportBeanSubTables;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Float getSumDeposit() {
		return sumDeposit;
	}

	public void setSumDeposit(Float sumDeposit) {
		this.sumDeposit = sumDeposit;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getAlley() {
		return alley;
	}

	public void setAlley(String alley) {
		this.alley = alley;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getAmphurName() {
		return amphurName;
	}

	public void setAmphurName(String amphurName) {
		this.amphurName = amphurName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}

	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}
}