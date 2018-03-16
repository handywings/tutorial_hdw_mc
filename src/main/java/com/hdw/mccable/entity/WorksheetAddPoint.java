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
@Table(name="worksheet_add_point")
public class WorksheetAddPoint {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "digitalPoint")
	private int digitalPoint;
	
	@Column(name = "analogPoint")
	private int analogPoint;
	
	@Column(name = "addPointPrice")
	private float addPointPrice; //ค่าเสริมจุด
	
	@Column(name = "monthlyFree")
	private float monthlyFree; //ค่าบริการรายเดือนเพิ่มเติม
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="worksheetId", nullable=true)
	private Worksheet workSheet;
	
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
	
	@Column(name = "isActive",columnDefinition = "boolean default false", nullable = false)
	private boolean isActive; // ถ้าใบงานยังไม่สำเร็จ = false

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDigitalPoint() {
		return digitalPoint;
	}

	public void setDigitalPoint(int digitalPoint) {
		this.digitalPoint = digitalPoint;
	}

	public int getAnalogPoint() {
		return analogPoint;
	}

	public void setAnalogPoint(int analogPoint) {
		this.analogPoint = analogPoint;
	}

	public float getAddPointPrice() {
		return addPointPrice;
	}

	public void setAddPointPrice(float addPointPrice) {
		this.addPointPrice = addPointPrice;
	}

	public float getMonthlyFree() {
		return monthlyFree;
	}

	public void setMonthlyFree(float monthlyFree) {
		this.monthlyFree = monthlyFree;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
