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
@Table(name="requisition_document")
public class RequisitionDocument {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "status", nullable=false, length=1)
	private String status; // N=ปกติ, C=ยกเลิก
	
	@Column(name = "detail", columnDefinition = "TEXT")
	private String detail;
	
	@Column(name = "withdraw", nullable=false, length=1)
	private String withdraw;  //1 charater R=เบิกเพื่อไปสำรอง, O=เบิกเพื่องานสำนักงาน, U=เบิกเพื่อปรับปรุงลดสต็อก
	
	@Column(name = "requisitionDocumentCode", nullable=false, length=15)
	private String requisitionDocumentCode;
	
	@Column(name = "remarkStatus", columnDefinition = "TEXT")
	private String remarkStatus;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="technicianGroupId", nullable=true)
	private TechnicianGroup technicianGroup;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "requisitionDocument", cascade={CascadeType.ALL})
	List<RequisitionItem> requisitionItems;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="personnelId", nullable=false)
	private Personnel personnel;
	
	@Column(name = "isDeleted")
	private boolean isDeleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(String withdraw) {
		this.withdraw = withdraw;
	}

	public String getRequisitionDocumentCode() {
		return requisitionDocumentCode;
	}

	public void setRequisitionDocumentCode(String requisitionDocumentCode) {
		this.requisitionDocumentCode = requisitionDocumentCode;
	}

	public String getRemarkStatus() {
		return remarkStatus;
	}

	public void setRemarkStatus(String remarkStatus) {
		this.remarkStatus = remarkStatus;
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

	public TechnicianGroup getTechnicianGroup() {
		return technicianGroup;
	}

	public void setTechnicianGroup(TechnicianGroup technicianGroup) {
		this.technicianGroup = technicianGroup;
	}

	public List<RequisitionItem> getRequisitionItems() {
		return requisitionItems;
	}

	public void setRequisitionItems(List<RequisitionItem> requisitionItems) {
		this.requisitionItems = requisitionItems;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
