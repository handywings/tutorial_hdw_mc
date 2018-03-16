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
@Table(name="internet_product")
public class InternetProduct {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "productName", nullable=false, length=150)
	private String productName;
	
	@Column(name = "productCode", nullable=false, length=180)
	private String productCode;
	
//	@Column(name = "userName", length=60)
//	private String userName;
//	
//	@Column(name = "password", length=60)
//	private String password;
//	
//	@Column(name = "reference", nullable=false)
//	private String reference;
	
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
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="unitId", nullable=false)
//	private Unit unit;
	
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "internetProduct", cascade = CascadeType.ALL)
//	private TemplateServiceItem templateServiceItem;
	
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "internetProduct", cascade = CascadeType.ALL)
//	private ProductItem productItem;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "internetProduct", cascade={CascadeType.ALL})
	List<InternetProductItem> internetProductItems;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

	public List<InternetProductItem> getInternetProductItems() {
		return internetProductItems;
	}

	public void setInternetProductItems(List<InternetProductItem> internetProductItems) {
		this.internetProductItems = internetProductItems;
	}

	public EquipmentProductCategory getEquipmentProductCategory() {
		return equipmentProductCategory;
	}

	public void setEquipmentProductCategory(EquipmentProductCategory equipmentProductCategory) {
		this.equipmentProductCategory = equipmentProductCategory;
	}
 

}
