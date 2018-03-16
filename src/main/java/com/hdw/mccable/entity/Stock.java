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
@Table(name="stock")
public class Stock {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "stockCode", nullable=false, length=100)
	private String stockCode;
	
	@Column(name = "stockDetail", columnDefinition = "TEXT")
	private String stockDetail;
	
	@Column(name = "stockName", nullable=false, length=100)
	private String stockName;
	
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
	@JoinColumn(name="companyId", nullable=false)
	private Company company;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "stock", cascade = CascadeType.ALL)
	private Address address;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stock", cascade={CascadeType.ALL})
	List<EquipmentProduct> equipmentProducts;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stock", cascade={CascadeType.ALL})
	List<InternetProduct> internetProducts;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stock", cascade={CascadeType.ALL})
	List<ServiceProduct> serviceProducts;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockDetail() {
		return stockDetail;
	}

	public void setStockDetail(String stockDetail) {
		this.stockDetail = stockDetail;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<EquipmentProduct> getEquipmentProducts() {
		return equipmentProducts;
	}

	public void setEquipmentProducts(List<EquipmentProduct> equipmentProducts) {
		this.equipmentProducts = equipmentProducts;
	}

	public List<InternetProduct> getInternetProducts() {
		return internetProducts;
	}

	public void setInternetProducts(List<InternetProduct> internetProducts) {
		this.internetProducts = internetProducts;
	}

	public List<ServiceProduct> getServiceProducts() {
		return serviceProducts;
	}

	public void setServiceProducts(List<ServiceProduct> serviceProducts) {
		this.serviceProducts = serviceProducts;
	}

}
