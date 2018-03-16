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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name="product_item_worksheet")
public class ProductItemWorksheet {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
		
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "isFree")
	private boolean isFree;
	
	@Column(name = "isLend")
	private boolean isLend;
	 
	@Column(name = "amount")
	private float amount;
	
	@Column(name = "price")
	private float price;
	
	@Column(name = "deposit", columnDefinition = "int default 0.0")
	private float deposit;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="equipmentProductItemId", nullable=true)
	private EquipmentProductItem equipmentProductItem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="internetProductItemId", nullable=true)
	private InternetProductItem internetProductItem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="productItemId", nullable=true)
	private ProductItem productItem;

	@Column(name = "productType")
	private String productType;
	
	@Column(name = "comment", nullable = true)
	@Type(type="text")
	private String comment;
	
	@Column(name = "parent", nullable=true) //สำหรับใบงานแบบซ่อมสายสัญญาณ ที่มีการซ่อมอุปกรณ์
	private Long parent;
	
	@Column(name = "productTypeMatch", nullable=true, length = 1)  //O=โปรดักที่เกิดตอนสร้างใบงาน, N=โปรดักที่เกิดตอนเพิ่มอุปกรณ์เพิ่มตอน, R=ใบงาน การซ่อมสัญญาณ, A=เพิ่มเติมอุปกรณ์
	private String productTypeMatch;
	
	@Column(name = "isReturnEquipment",columnDefinition = "boolean default false", nullable = false)
	private boolean isReturnEquipment; // คืนอุปกรณ์
	
	@Column(name = "lendStatus", length=1, columnDefinition = "int default 1")
	private int lendStatus; //สถานะการยืมของอุปกรณ์ ปกติ=1, ชำรุด=0, หาย(มองเป็นขายขาด)=5
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="requisitionItemId", nullable=true)
	private RequisitionItem requisitionItem;
	
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

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public boolean isLend() {
		return isLend;
	}

	public void setLend(boolean isLend) {
		this.isLend = isLend;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
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

	public EquipmentProductItem getEquipmentProductItem() {
		return equipmentProductItem;
	}

	public void setEquipmentProductItem(EquipmentProductItem equipmentProductItem) {
		this.equipmentProductItem = equipmentProductItem;
	}

	public InternetProductItem getInternetProductItem() {
		return internetProductItem;
	}

	public void setInternetProductItem(InternetProductItem internetProductItem) {
		this.internetProductItem = internetProductItem;
	}

	public ProductItem getProductItem() {
		return productItem;
	}

	public void setProductItem(ProductItem productItem) {
		this.productItem = productItem;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public float getDeposit() {
		return deposit;
	}

	public void setDeposit(float deposit) {
		this.deposit = deposit;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public String getProductTypeMatch() {
		return productTypeMatch;
	}

	public void setProductTypeMatch(String productTypeMatch) {
		this.productTypeMatch = productTypeMatch;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getLendStatus() {
		return lendStatus;
	}

	public void setLendStatus(int lendStatus) {
		this.lendStatus = lendStatus;
	}

	public boolean isReturnEquipment() {
		return isReturnEquipment;
	}

	public void setReturnEquipment(boolean isReturnEquipment) {
		this.isReturnEquipment = isReturnEquipment;
	}

	public RequisitionItem getRequisitionItem() {
		return requisitionItem;
	}

	public void setRequisitionItem(RequisitionItem requisitionItem) {
		this.requisitionItem = requisitionItem;
	}

}
