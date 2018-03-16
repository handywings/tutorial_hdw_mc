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
@Table(name="history_technician_groupwork")
public class HistoryTechnicianGroupWork {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="technicianGroupId", nullable=true)
	private TechnicianGroup technicianGroup;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="worksheetId", nullable=true)
	private Worksheet workSheet;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historyTechnicianGroupWork", cascade={CascadeType.ALL})
	private List<PersonnelAssign> personnelAssigns;
	
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
	
	@Column(name = "statusHistory", length=1)  //เก็บคัดลอกประวัติสถานะก่อนหน้าของใบงาน
	private String statusHistory;
	
	@Column(name = "remarkNotSuccess")  //รายละเอียดงานที่ไม่สำเร็จ
	private String remarkNotSuccess;
	
	@Temporal(TemporalType.TIMESTAMP) //วันที่ให้ช่างไปทำงาน
	@Column(name = "assignDate")
	private Date assignDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TechnicianGroup getTechnicianGroup() {
		return technicianGroup;
	}

	public void setTechnicianGroup(TechnicianGroup technicianGroup) {
		this.technicianGroup = technicianGroup;
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

	public List<PersonnelAssign> getPersonnelAssigns() {
		return personnelAssigns;
	}

	public void setPersonnelAssigns(List<PersonnelAssign> personnelAssigns) {
		this.personnelAssigns = personnelAssigns;
	}

	public String getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(String statusHistory) {
		this.statusHistory = statusHistory;
	}

	public String getRemarkNotSuccess() {
		return remarkNotSuccess;
	}

	public void setRemarkNotSuccess(String remarkNotSuccess) {
		this.remarkNotSuccess = remarkNotSuccess;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}
	
}
