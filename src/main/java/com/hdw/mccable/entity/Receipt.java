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
@Table(name="receipt")
public class Receipt {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "receiptCode", nullable=false, length=100)
	private String receiptCode;
	
	@Column(name = "amount")
	private float amount;
	
	@Column(name = "status", nullable=false, length=1) //H=ชั่วคราว,P=ถาวร
	private String status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "paymentDate", nullable=true)
	private Date paymentDate; //วันที่ชำระเงิน
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "issueDocDate", nullable=true)
	private Date issueDocDate; //วันวันที่ออกเอกสาร
	
	@Column(name = "paymentType", nullable=true, length=1) //C=หน้า counter หรือ ณ บริษัท,T=โอนเงิน, P = พนักงานเก็บเงิน
	private String paymentType;
	
	//mapped relation
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="bankId", nullable=true)
	private Bank bank;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="invoiceId", nullable=true)
	private Invoice invoice;
	
	//common
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
	@JoinColumn(name="personnelId", nullable=true)
	private Personnel personnel;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "receipt", cascade={CascadeType.ALL})
	List<ReceiptHistoryPrint> receiptHistoryPrints;
	
	@Column(name = "reductAmount", columnDefinition = "float default 0.0")
	private float reductAmount; //ลดหนี้ให้
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReceiptCode() {
		return receiptCode;
	}

	public void setReceiptCode(String receiptCode) {
		this.receiptCode = receiptCode;
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

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
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

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public List<ReceiptHistoryPrint> getReceiptHistoryPrints() {
		return receiptHistoryPrints;
	}

	public void setReceiptHistoryPrints(List<ReceiptHistoryPrint> receiptHistoryPrints) {
		this.receiptHistoryPrints = receiptHistoryPrints;
	}

	public float getReductAmount() {
		return reductAmount;
	}

	public void setReductAmount(float reductAmount) {
		this.reductAmount = reductAmount;
	}

}
