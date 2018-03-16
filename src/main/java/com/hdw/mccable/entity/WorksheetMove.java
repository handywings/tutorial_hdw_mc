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
@Table(name="worksheet_move")
public class WorksheetMove {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="worksheetId", nullable=true)
	private Worksheet workSheet;
	
	@Column(name = "digitalPoint")
	private int digitalPoint;
	
	@Column(name = "analogPoint")
	private int analogPoint;
	
	@Column(name = "moveCablePrice")
	private float moveCablePrice; //ค่าย้ายสาย

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "worksheetMove", cascade={CascadeType.ALL})
	private List<Address> Addresss;
	
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

	public List<Address> getAddresss() {
		return Addresss;
	}

	public void setAddresss(List<Address> addresss) {
		Addresss = addresss;
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

	public float getMoveCablePrice() {
		return moveCablePrice;
	}

	public void setMoveCablePrice(float moveCablePrice) {
		this.moveCablePrice = moveCablePrice;
	}

}
