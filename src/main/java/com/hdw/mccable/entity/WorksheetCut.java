package com.hdw.mccable.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="worksheet_cut")
public class WorksheetCut {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="worksheetId", nullable=true)
	private Worksheet workSheet;
	
	@Column(name = "reporter")
	private String reporter;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "cutWorkType")
	private int cutWorkType;
	
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
	
	@Column(name = "isReturnEquipment",columnDefinition = "boolean default false", nullable = false)
	private boolean isReturnEquipment; // คืนอุปกรณ์
	
	@Column(name = "submitCA",columnDefinition = "boolean default false", nullable = false)
	private boolean submitCA; // ส่งเรื่องเพื่อแจ้ง CA
	
	@Column(name = "isConfirmCA",columnDefinition = "boolean default false", nullable = false)
	private boolean isConfirmCA; // ส่งเรื่องเพื่อแจ้ง CA

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cancelDate") //วันที่แจ้งยกเลิก
	private Date cancelDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endPackageDate") //วันที่สิ้นสุดการใช้บริการ Package
	private Date endPackageDate;
	
	@Column(name = "specialDiscount", columnDefinition = "int default 0.0")
	private float specialDiscount; //ส่วนลดพิเศษ
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getCutWorkType() {
		return cutWorkType;
	}

	public void setCutWorkType(int cutWorkType) {
		this.cutWorkType = cutWorkType;
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

	public Worksheet getWorkSheet() {
		return workSheet;
	}

	public void setWorkSheet(Worksheet workSheet) {
		this.workSheet = workSheet;
	}

	public boolean isReturnEquipment() {
		return isReturnEquipment;
	}

	public void setReturnEquipment(boolean isReturnEquipment) {
		this.isReturnEquipment = isReturnEquipment;
	}

	public boolean isSubmitCA() {
		return submitCA;
	}

	public void setSubmitCA(boolean submitCA) {
		this.submitCA = submitCA;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public Date getEndPackageDate() {
		return endPackageDate;
	}

	public void setEndPackageDate(Date endPackageDate) {
		this.endPackageDate = endPackageDate;
	}

	public float getSpecialDiscount() {
		return specialDiscount;
	}

	public void setSpecialDiscount(float specialDiscount) {
		this.specialDiscount = specialDiscount;
	}

	public boolean isConfirmCA() {
		return isConfirmCA;
	}

	public void setConfirmCA(boolean isConfirmCA) {
		this.isConfirmCA = isConfirmCA;
	}
	
	

}
