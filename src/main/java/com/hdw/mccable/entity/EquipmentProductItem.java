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
@Table(name="equipment_product_item")
public class EquipmentProductItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "Reference", nullable=true, length=60)
	private String Reference;
	
	@Column(name = "serialNo", nullable=true, length=100)
	private String serialNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "supplierDate")
	private Date supplierDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "guaranteeDate")
	private Date guaranteeDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "orderDate")
	private Date orderDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "importSystemDate")
	private Date importSystemDate;
	
	@Column(name = "status", length=1)
	private int status;
	
	@Column(name = "numberImport")
	private int numberImport;
	
	@Column(name = "cost")
	private float cost;
	
	@Column(name = "salePrice")
	private float salePrice;
	
	@Column(name = "priceIncTax",columnDefinition = "float default 0.0", nullable = true)
	private float priceIncTax;
	
	//----- normail ---//
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
	
	//relation
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="equipmentProductId", nullable=false)
	private EquipmentProduct equipmentProduct;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="personnelId", nullable=true)
	private Personnel personnel;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "equipmentProductItem", cascade={CascadeType.ALL})
	List<HistoryUseEquipment> historyUseEquipments;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "equipmentProductItem", cascade={CascadeType.ALL})
	List<HistoryUpdateStatus> historyUpdateStatuses;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "equipmentProductItem", cascade={CascadeType.ALL})
	List<RequisitionItem> requisitionItems;

	 // ที่ใช้ได้
	 @Column(name = "balance")
	 private int balance;
	 
	 // สำรอง
	 @Column(name = "spare")
	 private int spare;
	 
	 // จอง
	 @Column(name = "reservations")
	 private int reservations;
	 
	 // เคยมีการ ซ่อม
	 @Column(name = "isRepair",columnDefinition = "boolean default false", nullable = false)
	 private boolean isRepair;
	 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReference() {
		return Reference;
	}

	public void setReference(String reference) {
		Reference = reference;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Date getSupplierDate() {
		return supplierDate;
	}

	public void setSupplierDate(Date supplierDate) {
		this.supplierDate = supplierDate;
	}

	public Date getGuaranteeDate() {
		return guaranteeDate;
	}

	public void setGuaranteeDate(Date guaranteeDate) {
		this.guaranteeDate = guaranteeDate;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getImportSystemDate() {
		return importSystemDate;
	}

	public void setImportSystemDate(Date importSystemDate) {
		this.importSystemDate = importSystemDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNumberImport() {
		return numberImport;
	}

	public void setNumberImport(int numberImport) {
		this.numberImport = numberImport;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public float getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
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

	public EquipmentProduct getEquipmentProduct() {
		return equipmentProduct;
	}

	public void setEquipmentProduct(EquipmentProduct equipmentProduct) {
		this.equipmentProduct = equipmentProduct;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public List<HistoryUseEquipment> getHistoryUseEquipments() {
		return historyUseEquipments;
	}

	public void setHistoryUseEquipments(List<HistoryUseEquipment> historyUseEquipments) {
		this.historyUseEquipments = historyUseEquipments;
	}

	public List<HistoryUpdateStatus> getHistoryUpdateStatuses() {
		return historyUpdateStatuses;
	}

	public void setHistoryUpdateStatuses(List<HistoryUpdateStatus> historyUpdateStatuses) {
		this.historyUpdateStatuses = historyUpdateStatuses;
	}

	public List<RequisitionItem> getRequisitionItems() {
		return requisitionItems;
	}

	public void setRequisitionItems(List<RequisitionItem> requisitionItems) {
		this.requisitionItems = requisitionItems;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getSpare() {
		return spare;
	}

	public void setSpare(int spare) {
		this.spare = spare;
	}

	public int getReservations() {
		return reservations;
	}

	public void setReservations(int reservations) {
		this.reservations = reservations;
	}

	public boolean isRepair() {
		return isRepair;
	}

	public void setRepair(boolean isRepair) {
		this.isRepair = isRepair;
	}

	public float getPriceIncTax() {
		return priceIncTax;
	}

	public void setPriceIncTax(float priceIncTax) {
		this.priceIncTax = priceIncTax;
	}
	
}
