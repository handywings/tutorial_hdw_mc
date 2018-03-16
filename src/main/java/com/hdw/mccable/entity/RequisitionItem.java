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
@Table(name="requisition_item")
public class RequisitionItem {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "quantity", nullable=false)
	private int quantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="personnelId", nullable=true)
	private Personnel personnel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="requisitionDocumentId", nullable=false)
	private RequisitionDocument requisitionDocument;
	
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
	@JoinColumn (name="equipmentProductId", nullable=false)
	private EquipmentProduct equipmentProduct;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="equipmentProductItemId", nullable=false)
	private EquipmentProductItem equipmentProductItem;
	
	// จำนวนที่คืนอุปกรณ์
	@Column(name = "returnEquipmentProductItem", nullable=false)
	private int returnEquipmentProductItem;
	
	// จำนวนอุปกรณ์ที่ถูกใช้งานไป
	@Column(name = "sellEquipmentProductItem", nullable=false)
	private int sellEquipmentProductItem;
	
	@Column(name = "isDeleted")
	private boolean isDeleted;
	
	@OneToMany(mappedBy = "requisitionItem", cascade={CascadeType.ALL})
	List<ProductItemWorksheet> productItemWorksheets;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public RequisitionDocument getRequisitionDocument() {
		return requisitionDocument;
	}

	public void setRequisitionDocument(RequisitionDocument requisitionDocument) {
		this.requisitionDocument = requisitionDocument;
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

	public EquipmentProduct getEquipmentProduct() {
		return equipmentProduct;
	}

	public void setEquipmentProduct(EquipmentProduct equipmentProduct) {
		this.equipmentProduct = equipmentProduct;
	}

	public EquipmentProductItem getEquipmentProductItem() {
		return equipmentProductItem;
	}

	public void setEquipmentProductItem(EquipmentProductItem equipmentProductItem) {
		this.equipmentProductItem = equipmentProductItem;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getReturnEquipmentProductItem() {
		return returnEquipmentProductItem;
	}

	public void setReturnEquipmentProductItem(int returnEquipmentProductItem) {
		this.returnEquipmentProductItem = returnEquipmentProductItem;
	}

	public int getSellEquipmentProductItem() {
		return sellEquipmentProductItem;
	}

	public void setSellEquipmentProductItem(int sellEquipmentProductItem) {
		this.sellEquipmentProductItem = sellEquipmentProductItem;
	}

	public List<ProductItemWorksheet> getProductItemWorksheets() {
		return productItemWorksheets;
	}

	public void setProductItemWorksheets(List<ProductItemWorksheet> productItemWorksheets) {
		this.productItemWorksheets = productItemWorksheets;
	}	
	
}
