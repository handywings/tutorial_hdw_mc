package com.hdw.mccable.dto;

import java.util.List;

public class ServicePackageBean extends DSTPUtilityBean{
	
	private Long id;
	private String packageCode;
	private String packageName;
	private CompanyBean company;
	private ServicePackageTypeBean serviceType;
	private TemplateServiceBean template;
	private float installationFee;
	private float deposit;
	private boolean monthlyService;  //true = per mounth
	private float monthlyServiceFee;
	private int perMounth;
	private int firstBillFree;
	private float firstBillFreeDisCount;
	private float oneServiceFee; //กรณีจ่ายครั่งเดียว
	private boolean active;
	private String createDateTh;
	private List<ServiceApplicationBean> serviceApplicationBeans;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public CompanyBean getCompany() {
		return company;
	}
	public void setCompany(CompanyBean company) {
		this.company = company;
	}
	public ServicePackageTypeBean getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServicePackageTypeBean serviceType) {
		this.serviceType = serviceType;
	}
	public TemplateServiceBean getTemplate() {
		return template;
	}
	public void setTemplate(TemplateServiceBean template) {
		this.template = template;
	}
	public float getInstallationFee() {
		return installationFee;
	}
	public void setInstallationFee(float installationFee) {
		this.installationFee = installationFee;
	}
	public float getDeposit() {
		return deposit;
	}
	public void setDeposit(float deposit) {
		this.deposit = deposit;
	}

	public float getMonthlyServiceFee() {
		return monthlyServiceFee;
	}
	public void setMonthlyServiceFee(float monthlyServiceFee) {
		this.monthlyServiceFee = monthlyServiceFee;
	}
	public int getPerMounth() {
		return perMounth;
	}
	public void setPerMounth(int perMounth) {
		this.perMounth = perMounth;
	}
	public int getFirstBillFree() {
		return firstBillFree;
	}
	public void setFirstBillFree(int firstBillFree) {
		this.firstBillFree = firstBillFree;
	}
	public float getFirstBillFreeDisCount() {
		return firstBillFreeDisCount;
	}
	public void setFirstBillFreeDisCount(float firstBillFreeDisCount) {
		this.firstBillFreeDisCount = firstBillFreeDisCount;
	}
	public float getOneServiceFee() {
		return oneServiceFee;
	}
	public void setOneServiceFee(float oneServiceFee) {
		this.oneServiceFee = oneServiceFee;
	}
	public boolean isMonthlyService() {
		return monthlyService;
	}
	public void setMonthlyService(boolean monthlyService) {
		this.monthlyService = monthlyService;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getCreateDateTh() {
		return createDateTh;
	}
	public void setCreateDateTh(String createDateTh) {
		this.createDateTh = createDateTh;
	}
	public List<ServiceApplicationBean> getServiceApplicationBeans() {
		return serviceApplicationBeans;
	}
	public void setServiceApplicationBeans(List<ServiceApplicationBean> serviceApplicationBeans) {
		this.serviceApplicationBeans = serviceApplicationBeans;
	}
	
	@Override
	public String toString() {
		return "ServicePackageBean [id=" + id + ", packageCode=" + packageCode + ", packageName=" + packageName
				+ ", company=" + company + ", serviceType=" + serviceType + ", template=" + template
				+ ", installationFee=" + installationFee + ", deposit=" + deposit + ", monthlyService=" + monthlyService
				+ ", monthlyServiceFee=" + monthlyServiceFee + ", perMounth=" + perMounth + ", firstBillFree="
				+ firstBillFree + ", firstBillFreeDisCount=" + firstBillFreeDisCount + ", oneServiceFee="
				+ oneServiceFee + ", active=" + active + ", createDateTh=" + createDateTh + ", getCreateDate()="
				+ getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy()
				+ ", getUpdateBy()=" + getUpdateBy() + ", getUpdateDateTh()=" + getUpdateDateTh() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
