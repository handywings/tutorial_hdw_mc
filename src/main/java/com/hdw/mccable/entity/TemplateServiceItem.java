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
@Table(name="template_service_item")
public class TemplateServiceItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "isFree")
	private boolean isFree;
	
	@Column(name = "amount")
	private float amount;
	
	@Column(name = "productType")
	private String productType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="equipmentProductId", nullable=true)
	private EquipmentProduct equipmentProduct;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="internetProductId", nullable=true)
	private InternetProduct internetProduct;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="serviceProductId", nullable=true)
	private ServiceProduct serviceProduct;
	
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
	@JoinColumn(name="templateServiceId", nullable=false)
	private TemplateService templateService;
	
	@Column(name = "isLend")
	private boolean isLend;

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

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public EquipmentProduct getEquipmentProduct() {
		return equipmentProduct;
	}

	public void setEquipmentProduct(EquipmentProduct equipmentProduct) {
		this.equipmentProduct = equipmentProduct;
	}

	public InternetProduct getInternetProduct() {
		return internetProduct;
	}

	public void setInternetProduct(InternetProduct internetProduct) {
		this.internetProduct = internetProduct;
	}

	public ServiceProduct getServiceProduct() {
		return serviceProduct;
	}

	public void setServiceProduct(ServiceProduct serviceProduct) {
		this.serviceProduct = serviceProduct;
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

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public boolean isLend() {
		return isLend;
	}

	public void setLend(boolean isLend) {
		this.isLend = isLend;
	}
	
}
