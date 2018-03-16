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
@Table(name="invoice")
public class Invoice {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "invoiceCode", unique=true,nullable=false, length=100)
	private String invoiceCode;
	
	@Column(name = "invoiceType", nullable=false, length=1) //S=สำหรับติดตั้ง,R=สำหรับซ่อม,O=สำหรับตาม
	private String invoiceType;
	
	@Column(name = "amount")
	private float amount;
	
	@Column(name = "status", nullable=false, length=1) //W=รอลูกค้าชำระ,S=ชำระแล้ว,O=เกินวันกำหนดชำระ
	private String status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "paymentDate", nullable=true)
	private Date paymentDate; //วันนัดชำระ
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "issueDocDate", nullable=true)
	private Date issueDocDate; //วันวันที่ออกเอกสาร
	
	//mapped relation
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "invoice", cascade={CascadeType.ALL})
	Receipt receipt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="serviceApplicationId", nullable=false)
	private ServiceApplication serviceApplication;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="worksheetId", nullable=true)
	private Worksheet workSheet;
	
	//common
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate", nullable=true)
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedDate")
	private Date updatedDate;
	
	@Column(name = "createdBy", nullable=false, length=150)
	private String createdBy; // วันที่นัดชำระ
	
	@Column(name = "updatedBy", length=150)
	private String updatedBy;
	
	@Column(name = "isDeleted")
	private boolean isDeleted;
	
	@Column(name = "vat",nullable=true)
	private String vat; // N=no vat,Y=vat,A=Add vat
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice", cascade={CascadeType.ALL})
	List<InvoiceHistoryPrint> invoiceHistoryPrints;
	
	//สำหรับการแสกนบิล
	@Column(name = "statusScan", nullable=false, length=1) //E=แสกนออก, I=แสกนเข้า, N=ยังไม่มีการแสกน
	private String statusScan;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "scanOutDate", nullable=true)
	private Date scanOutDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "scanInDate", nullable=true)
	private Date scanInDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personnelId", nullable = true)
	private Personnel personnel; // ผู้รับผิดชอบ ล่าสุด และ ใช้ในกรณีวางบิล
	
	@Column(name = "billing",columnDefinition = "boolean default false")
	private boolean billing;
	
	@Column(name = "isCutting",columnDefinition = "boolean default false")
	private boolean isCutting;
	
	@Column(name = "isBadDebt",columnDefinition = "boolean default false")
	private boolean isBadDebt; // หนี้สูญ
	
	@Column(name = "isMobile",columnDefinition = "boolean default false")
	private boolean isMobile; // กรณีมอบหมายพนักงานเก็บเงินที่มีเครื่องโมบายปริ้นเตอร์
	
	public boolean isMobile() {
		return isMobile;
	}

	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}

	public boolean isBadDebt() {
		return isBadDebt;
	}

	public void setBadDebt(boolean isBadDebt) {
		this.isBadDebt = isBadDebt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getIssueDocDate() {
		return issueDocDate;
	}

	public void setIssueDocDate(Date issueDocDate) {
		this.issueDocDate = issueDocDate;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public ServiceApplication getServiceApplication() {
		return serviceApplication;
	}

	public void setServiceApplication(ServiceApplication serviceApplication) {
		this.serviceApplication = serviceApplication;
	}

	public Worksheet getWorkSheet() {
		return workSheet;
	}

	public void setWorkSheet(Worksheet workSheet) {
		this.workSheet = workSheet;
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

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public List<InvoiceHistoryPrint> getInvoiceHistoryPrints() {
		return invoiceHistoryPrints;
	}

	public void setInvoiceHistoryPrints(List<InvoiceHistoryPrint> invoiceHistoryPrints) {
		this.invoiceHistoryPrints = invoiceHistoryPrints;
	}

	public String getStatusScan() {
		return statusScan;
	}

	public void setStatusScan(String statusScan) {
		this.statusScan = statusScan;
	}

	public Date getScanOutDate() {
		return scanOutDate;
	}

	public void setScanOutDate(Date scanOutDate) {
		this.scanOutDate = scanOutDate;
	}

	public Date getScanInDate() {
		return scanInDate;
	}

	public void setScanInDate(Date scanInDate) {
		this.scanInDate = scanInDate;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public boolean isBilling() {
		return billing;
	}

	public void setBilling(boolean billing) {
		this.billing = billing;
	}

	public boolean isCutting() {
		return isCutting;
	}

	public void setCutting(boolean isCutting) {
		this.isCutting = isCutting;
	}
}
