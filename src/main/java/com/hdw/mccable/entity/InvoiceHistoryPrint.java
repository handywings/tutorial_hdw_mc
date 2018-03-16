package com.hdw.mccable.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="invoice_history_print")
public class InvoiceHistoryPrint {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "print_time")
	private int printTime;
	
	// common
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate", nullable = false)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedDate")
	private Date updatedDate;

	@Column(name = "createdBy", nullable = false, length = 150)
	private String createdBy;

	@Column(name = "updatedBy", length = 150)
	private String updatedBy;

	@Column(name = "isDeleted")
	private boolean isDeleted;
	
	//relation
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="invoiceId", nullable=false)
	private Invoice invoice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personnelId", nullable = true)
	private Personnel personnel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assignPersonnelId", nullable = true)
	private Personnel assignPersonnel; //มอบหมายผู้รับผิดชอบ

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPrintTime() {
		return printTime;
	}

	public void setPrintTime(int printTime) {
		this.printTime = printTime;
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

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public Personnel getAssignPersonnel() {
		return assignPersonnel;
	}

	public void setAssignPersonnel(Personnel assignPersonnel) {
		this.assignPersonnel = assignPersonnel;
	}
	
}
