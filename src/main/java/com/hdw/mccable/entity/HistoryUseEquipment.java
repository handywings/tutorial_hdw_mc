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
@Table(name="history_use_equipment")
public class HistoryUseEquipment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "activeDate")
	private Date activeDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "returnDate")
	private Date returnDate;
	
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
	
	@Column(name = "status", length=1)
	private String status; // สถานะอุปกรณ์ L = ยืม, F = ฟรี, ขายขาด = O, R = คืน, D = ชำรุด, B = หาย, C = CA
	
	//relation
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="customerId", nullable=false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="equipmentProductItemId", nullable=false)
	private EquipmentProductItem equipmentProductItem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="serviceApplicationId", nullable=true)
	private ServiceApplication serviceApplication;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getActiveDate() {
		return activeDate;
	}


	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}


	public Date getReturnDate() {
		return returnDate;
	}


	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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

	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public boolean isDeleted() {
		return isDeleted;
	}


	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}


	public EquipmentProductItem getEquipmentProductItem() {
		return equipmentProductItem;
	}


	public void setEquipmentProductItem(EquipmentProductItem equipmentProductItem) {
		this.equipmentProductItem = equipmentProductItem;
	}


	public ServiceApplication getServiceApplication() {
		return serviceApplication;
	}


	public void setServiceApplication(ServiceApplication serviceApplication) {
		this.serviceApplication = serviceApplication;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

}
