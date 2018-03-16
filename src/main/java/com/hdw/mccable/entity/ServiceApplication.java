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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="service_application")
public class ServiceApplication {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "serviceApplicationNo")
	private String serviceApplicationNo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceApplication", cascade={CascadeType.ALL})
	List<Address> addresses;
		
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceApplication", cascade={CascadeType.ALL})
	List<ProductItem> productItems;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceApplication", cascade={CascadeType.ALL})
	List<HistoryUseEquipment> historyUseEquipmentList;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="customerId", nullable=false)
	private Customer customer;
	
	@Column(name = "easyInstallationDateTime")
	private String easyInstallationDateTime; // วันเวลาที่สะดวกในการติดตั้ง
	
	@Column(name = "installationFee")
	private float installationFee;
	
	@Column(name = "deposit")
	private float deposit;
	
	@Column(name = "monthlyServiceFee")
	private float monthlyServiceFee;
	
	@Column(name = "perMonth")
	private int perMonth;
	
	@Column(name = "firstBillFree")
	private int firstBillFree;
	
	@Column(name = "firstBillFreeDisCount")
	private float firstBillFreeDisCount;
	
	@Column(name = "oneServiceFee")
	private float oneServiceFee; //กรณีจ่ายครั่งเดียว
	
	@Column(name = "totalAmount")
	private float totalAmount;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "status", length=1)
	private String status;
	
	@Column(name = "isAccecptDocument")
	private boolean isAccecptDocument;
	
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
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="servicePackageId", nullable=false)
	private ServicePackage servicePackage;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceApplication", cascade={CascadeType.ALL})
	List<DocumentFile> documentFiles;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "startDate")  //วันที่เริ่มต้นใช้งาน
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "cancelServiceDate")  //วันที่เยกเลิกบริการ
	private Date cancelServiceDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "refundDate")  //วันที่เคืนเงินมีดจำ
	private Date refundDate;
	
	@Column(name = "isMonthlyService")
	private boolean isMonthlyService;  //true = per mounth
	
	@Column(name = "isHouseRegistrationDocuments")
	private boolean isHouseRegistrationDocuments; // checkbox สำเนาทะเบียนบ้าน
	
	@Column(name = "isIdentityCardDocuments")
	private boolean isIdentityCardDocuments; // checkbox สำเนาบัตรประจำตัวประชาชน
	
	@Column(name = "isOtherDocuments")
	private boolean isOtherDocuments; // checkbox เอกสารอื่นๆ
	
	@Column(name = "remarkOtherDocuments")
	private String remarkOtherDocuments; // textarea เอกสารอื่นๆ
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceApplication", cascade={CascadeType.ALL})
	List<Worksheet> worksheets;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="serviceApplicationTypeId", nullable=true)
	private ServiceApplicationType serviceApplicationType;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceApplication", cascade={CascadeType.ALL})
	List<Invoice> invoices;

	@Column(name = "plateNumber")
	private String plateNumber;
	
	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "longitude")
	private String longitude;
	
	@Column(name = "referenceServiceApplicationId")
	private Long referenceServiceApplicationId; // ใบสมัครเดิม
	
	@Column(name = "refund", columnDefinition = "float default 0.0")
	private float refund;
	
	@Column(name = "flagRefund", columnDefinition = "bit default 0")
	private boolean flagRefund; // true มีการคืนเงินมัดจำ
	
	@Column(name = "plat")
	private String plat; // เพลท000015
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="companyId", nullable=true)
	private Company company;
	
	@Column(name = "paymentAfter", columnDefinition = "bit default 0")
	private boolean paymentAfter; // true เก็บค่าบริการหลัง
	
	public boolean getPaymentAfter() {
		return paymentAfter;
	}

	public void setPaymentAfter(boolean paymentAfter) {
		this.paymentAfter = paymentAfter;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getPlat() {
		return plat;
	}

	public void setPlat(String plat) {
		this.plat = plat;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceApplicationNo() {
		return serviceApplicationNo;
	}

	public void setServiceApplicationNo(String serviceApplicationNo) {
		this.serviceApplicationNo = serviceApplicationNo;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<ProductItem> getProductItems() {
		return productItems;
	}

	public void setProductItems(List<ProductItem> productItems) {
		this.productItems = productItems;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getEasyInstallationDateTime() {
		return easyInstallationDateTime;
	}

	public void setEasyInstallationDateTime(String easyInstallationDateTime) {
		this.easyInstallationDateTime = easyInstallationDateTime;
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

	public int getPerMonth() {
		return perMonth;
	}

	public void setPerMonth(int perMonth) {
		this.perMonth = perMonth;
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

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isAccecptDocument() {
		return isAccecptDocument;
	}

	public void setAccecptDocument(boolean isAccecptDocument) {
		this.isAccecptDocument = isAccecptDocument;
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

	public ServicePackage getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(ServicePackage servicePackage) {
		this.servicePackage = servicePackage;
	}

	public List<DocumentFile> getDocumentFiles() {
		return documentFiles;
	}

	public void setDocumentFiles(List<DocumentFile> documentFiles) {
		this.documentFiles = documentFiles;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public boolean isMonthlyService() {
		return isMonthlyService;
	}

	public void setMonthlyService(boolean isMonthlyService) {
		this.isMonthlyService = isMonthlyService;
	}

	public boolean isHouseRegistrationDocuments() {
		return isHouseRegistrationDocuments;
	}

	public void setHouseRegistrationDocuments(boolean isHouseRegistrationDocuments) {
		this.isHouseRegistrationDocuments = isHouseRegistrationDocuments;
	}

	public boolean isIdentityCardDocuments() {
		return isIdentityCardDocuments;
	}

	public void setIdentityCardDocuments(boolean isIdentityCardDocuments) {
		this.isIdentityCardDocuments = isIdentityCardDocuments;
	}

	public boolean isOtherDocuments() {
		return isOtherDocuments;
	}

	public void setOtherDocuments(boolean isOtherDocuments) {
		this.isOtherDocuments = isOtherDocuments;
	}

	public ServiceApplicationType getServiceApplicationType() {
		return serviceApplicationType;
	}

	public void setServiceApplicationType(ServiceApplicationType serviceApplicationType) {
		this.serviceApplicationType = serviceApplicationType;
	}

	public List<Worksheet> getWorksheets() {
		return worksheets;
	}

	public void setWorksheets(List<Worksheet> worksheets) {
		this.worksheets = worksheets;
	}

	public String getRemarkOtherDocuments() {
		return remarkOtherDocuments;
	}

	public void setRemarkOtherDocuments(String remarkOtherDocuments) {
		this.remarkOtherDocuments = remarkOtherDocuments;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public Date getCancelServiceDate() {
		return cancelServiceDate;
	}

	public void setCancelServiceDate(Date cancelServiceDate) {
		this.cancelServiceDate = cancelServiceDate;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public List<HistoryUseEquipment> getHistoryUseEquipmentList() {
		return historyUseEquipmentList;
	}

	public void setHistoryUseEquipmentList(List<HistoryUseEquipment> historyUseEquipmentList) {
		this.historyUseEquipmentList = historyUseEquipmentList;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Long getReferenceServiceApplicationId() {
		return referenceServiceApplicationId;
	}

	public void setReferenceServiceApplicationId(Long referenceServiceApplicationId) {
		this.referenceServiceApplicationId = referenceServiceApplicationId;
	}

	public float getRefund() {
		return refund;
	}

	public void setRefund(float refund) {
		this.refund = refund;
	}

	public boolean isFlagRefund() {
		return flagRefund;
	}

	public void setFlagRefund(boolean flagRefund) {
		this.flagRefund = flagRefund;
	}
	
}
