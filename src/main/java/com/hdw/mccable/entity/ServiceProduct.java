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
@Table(name="service_product")
public class ServiceProduct {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "serviceChargeName", nullable=false, length=150)
	private String serviceChargeName;
	
	@Column(name = "price")
	private float price;
	
	// ----- normail ---//
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
	
	//relation
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="stockId", nullable=false)
	private Stock stock;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="productCategoryId", nullable=false)
	private EquipmentProductCategory equipmentProductCategory;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceProduct", cascade={CascadeType.ALL})
	private List<WorksheetConnect> worksheetConnects;
	
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "serviceProduct", cascade = CascadeType.ALL)
//	private TemplateServiceItem templateServiceItem;
	
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "serviceProduct", cascade = CascadeType.ALL)
//	private ProductItem productItem;
	
	@Column(name = "productCode", nullable=true, length=180)
	private String productCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="unitId", nullable=false)
	private Unit unit;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceChargeName() {
		return serviceChargeName;
	}

	public void setServiceChargeName(String serviceChargeName) {
		this.serviceChargeName = serviceChargeName;
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

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public EquipmentProductCategory getProductCategory() {
		return equipmentProductCategory;
	}

	public void setProductCategory(EquipmentProductCategory equipmentProductCategory) {
		this.equipmentProductCategory = equipmentProductCategory;
	}

	public EquipmentProductCategory getEquipmentProductCategory() {
		return equipmentProductCategory;
	}

	public void setEquipmentProductCategory(EquipmentProductCategory equipmentProductCategory) {
		this.equipmentProductCategory = equipmentProductCategory;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	 
}
