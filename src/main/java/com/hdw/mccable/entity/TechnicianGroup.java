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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="technician_group")
public class TechnicianGroup {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "technicianGroupName", nullable=false, length=150)
	private String technicianGroupName;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="personnelId")
	private Personnel personnel;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "technicianGroupSub")
	List<Personnel> personnels;
	
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "technicianGroup", cascade = CascadeType.ALL)
	private List<RequisitionDocument> requisitionDocuments;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "technicianGroup", cascade = CascadeType.ALL)
	private List<HistoryTechnicianGroupWork> historyTechnicianGroupWorks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTechnicianGroupName() {
		return technicianGroupName;
	}

	public void setTechnicianGroupName(String technicianGroupName) {
		this.technicianGroupName = technicianGroupName;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public List<Personnel> getPersonnels() {
		return personnels;
	}

	public void setPersonnels(List<Personnel> personnels) {
		this.personnels = personnels;
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

	public List<RequisitionDocument> getRequisitionDocuments() {
		return requisitionDocuments;
	}

	public void setRequisitionDocuments(List<RequisitionDocument> requisitionDocuments) {
		this.requisitionDocuments = requisitionDocuments;
	}
 
}
