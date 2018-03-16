package com.hdw.mccable.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="service_package")
public class ServicePackage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "packageCode")
	private String packageCode;
	
	@Column(name = "packageName")
	private String packageName;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="companyId", nullable=false)
	private Company company;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ServicePackageTypeId", nullable=false)
	private ServicePackageType servicePackageType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate", nullable=false)
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedDate")
	private Date updatedDate;
	
	@Column(name = "createdBy", nullable=false, length=150)
	private String createdBy;
	
	@Column(name = "updatedBy", length=150)
	private String updatedBy;
	
	@Column(name = "isDeleted")
	private boolean isDeleted;
	
	@Column(name = "installationFee")
	private float installationFee;
	
	@Column(name = "deposit")
	private float deposit;
	
	@Column(name = "isMonthlyService")
	private boolean isMonthlyService;  //true = per mounth
	
	@Column(name = "monthlyServiceFee", nullable=true)
	private float monthlyServiceFee;
	
	@Column(name = "perMounth", nullable=true)
	private int perMounth;
	
	@Column(name = "firstBillFree", nullable=true)
	private int firstBillFree;
	
	@Column(name = "firstBillFreeDisCount", nullable=true)
	private float firstBillFreeDisCount;
	
	@Column(name = "oneServiceFee", nullable=true)
	private float oneServiceFee;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn (name="templateServiceId", nullable=true)
	private TemplateService templateService;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "servicePackage", cascade = CascadeType.ALL)
	private List<ServiceApplication> serviceApplication;
	
	@Column(name = "isActive")
	private boolean isActive;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="unitId", nullable=false)
//	private Unit unit;

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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ServicePackageType getServicePackageType() {
		return servicePackageType;
	}

	public void setServicePackageType(ServicePackageType servicePackageType) {
		this.servicePackageType = servicePackageType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public boolean isMonthlyService() {
		return isMonthlyService;
	}

	public void setMonthlyService(boolean isMonthlyService) {
		this.isMonthlyService = isMonthlyService;
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

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public List<ServiceApplication> getServiceApplication() {
		return serviceApplication;
	}

	public void setServiceApplication(List<ServiceApplication> serviceApplication) {
		this.serviceApplication = serviceApplication;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

 
	 
}
