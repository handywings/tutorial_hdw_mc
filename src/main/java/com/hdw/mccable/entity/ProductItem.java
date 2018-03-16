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
@Table(name="product_item")
public class ProductItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
		
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "isFree")
	private boolean isFree;
	
	@Column(name = "isLend")
	private boolean isLend; // ยืม
	 
	@Column(name = "isDeposit")
	private boolean is_Deposit; // มัดจำ
	
	@Column(name = "amount")
	private float amount;
	
	@Column(name = "price")
	private float price;
	
	@Column(name = "deposit")
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
	@JoinColumn (name="equipmentProductId", nullable=true)
	private EquipmentProduct equipmentProduct;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="internetProductId", nullable=true)
	private InternetProduct internetProduct;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="serviceProductId", nullable=true)
	private ServiceProduct serviceProduct;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="serviceApplicationId", nullable=true)
	private ServiceApplication serviceApplication;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="worksheetId", nullable=true)
	private Worksheet workSheet;

	@Column(name = "productType")
	private String productType;
	
	@OneToMany(mappedBy = "productItem", cascade={CascadeType.ALL})
	List<ProductItemWorksheet> productItemWorksheets;
	
	@Column(name = "productTypeMatch", nullable=true, length = 1)  //O=โปรดักที่เกิดตอนสร้างใบงาน, N=โปรดักที่เกิดตอนเพิ่มอุปกรณ์เพิ่มตอน, A=เพิ่มเติมอุปกรณ์
	private String productTypeMatch;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "installDigital")  // วันที่ติด Digital
	private Date installDigital;

	public boolean isIs_Deposit() {
		return is_Deposit;
	}

	public void setIs_Deposit(boolean is_Deposit) {
		this.is_Deposit = is_Deposit;
	}

	public Date getInstallDigital() {
		return installDigital;
	}

	public void setInstallDigital(Date installDigital) {
		this.installDigital = installDigital;
	}

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

	public ServiceApplication getServiceApplication() {
		return serviceApplication;
	}

	public void setServiceApplication(ServiceApplication serviceApplication) {
		this.serviceApplication = serviceApplication;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Worksheet getWorkSheet() {
		return workSheet;
	}

	public void setWorkSheet(Worksheet workSheet) {
		this.workSheet = workSheet;
	}

	public List<ProductItemWorksheet> getProductItemWorksheets() {
		return productItemWorksheets;
	}

	public void setProductItemWorksheets(List<ProductItemWorksheet> productItemWorksheets) {
		this.productItemWorksheets = productItemWorksheets;
	}

	public float getDeposit() {
		return deposit;
	}

	public void setDeposit(float deposit) {
		this.deposit = deposit;
	}

	public String getProductTypeMatch() {
		return productTypeMatch;
	}

	public void setProductTypeMatch(String productTypeMatch) {
		this.productTypeMatch = productTypeMatch;
	} 
	
}
