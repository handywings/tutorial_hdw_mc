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
@Table(name="equipment_product")
public class EquipmentProduct {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "productName", nullable=false)
	private String productName;
	
	@Column(name = "productCode", nullable=false, length=50)
	private String productCode;
	
	@Column(name = "supplier")
	private String supplier;
	
	@Column(name = "financial_type", nullable=true, length=1)  //A=Asset,C=Capital
	private String financial_type;
	
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
	
	@Column(name = "isMinimum")
	private boolean isMinimum;
	
	@Column(name = "minimumNumber")
	private Long minimumNumber;
	
	//relation
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="unitId", nullable=false)
	private Unit unit;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "equipmentProduct", cascade={CascadeType.ALL})
	List<EquipmentProductItem> equipmentProductItems;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "equipmentProduct", cascade={CascadeType.ALL})
	List<RequisitionItem> requisitionItems;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="productCategoryId", nullable=false)
	private EquipmentProductCategory equipmentProductCategory;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="stockId", nullable=false)
	private Stock stock;

	 // จำนวนสต๊อค (เหลือ)
	 @Column(name = "stockAmount")
	 private int stockAmount;
	 
	 // ที่ใช้ได้
	 @Column(name = "balance")
	 private int balance;
	 
	 // จอง
	 @Column(name = "reservations")
	 private int reservations;
	 
	 // ขาย
	 @Column(name = "sell")
	 private int sell;
	 
	 // สำรอง
	 @Column(name = "spare")
	 private int spare;
	 
	// ยืม
	@Column(name = "lend")
	private int lend;
	
	// รอซ่อม
	@Column(name = "repair")
	private int repair;
	
	// เสีย
	@Column(name = "out_of_order")
	private int out_of_order;
	
	// กรณีใช้ในข้อมูลเก่า
	@Column(name = "cost",columnDefinition = "float default 0.0", nullable = true)
	private float cost;
	
	@Column(name = "salePrice",columnDefinition = "float default 0.0", nullable = true)
	private float salePrice;
	
	@Column(name = "priceIncTax",columnDefinition = "float default 0.0", nullable = true)
	private float priceIncTax;
	// กรณีใช้ในข้อมูลเก่า
	
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

	public float getPriceIncTax() {
		return priceIncTax;
	}

	public void setPriceIncTax(float priceIncTax) {
		this.priceIncTax = priceIncTax;
	}

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

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
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

	public boolean isMinimum() {
		return isMinimum;
	}

	public void setMinimum(boolean isMinimum) {
		this.isMinimum = isMinimum;
	}

	public Long getMinimumNumber() {
		return minimumNumber;
	}

	public void setMinimumNumber(Long minimumNumber) {
		this.minimumNumber = minimumNumber;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public List<EquipmentProductItem> getEquipmentProductItems() {
		return equipmentProductItems;
	}

	public void setEquipmentProductItems(List<EquipmentProductItem> equipmentProductItems) {
		this.equipmentProductItems = equipmentProductItems;
	}

	public List<RequisitionItem> getRequisitionItems() {
		return requisitionItems;
	}

	public void setRequisitionItems(List<RequisitionItem> requisitionItems) {
		this.requisitionItems = requisitionItems;
	}

	public EquipmentProductCategory getEquipmentProductCategory() {
		return equipmentProductCategory;
	}

	public void setEquipmentProductCategory(EquipmentProductCategory equipmentProductCategory) {
		this.equipmentProductCategory = equipmentProductCategory;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public int getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(int stockAmount) {
		this.stockAmount = stockAmount;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getReservations() {
		return reservations;
	}

	public void setReservations(int reservations) {
		this.reservations = reservations;
	}

	public int getSell() {
		return sell;
	}

	public void setSell(int sell) {
		this.sell = sell;
	}

	public int getSpare() {
		return spare;
	}

	public void setSpare(int spare) {
		this.spare = spare;
	}

	public int getLend() {
		return lend;
	}

	public void setLend(int lend) {
		this.lend = lend;
	}

	public int getRepair() {
		return repair;
	}

	public void setRepair(int repair) {
		this.repair = repair;
	}

	public int getOut_of_order() {
		return out_of_order;
	}

	public void setOut_of_order(int out_of_order) {
		this.out_of_order = out_of_order;
	}

	public String getFinancial_type() {
		return financial_type;
	}

	public void setFinancial_type(String financial_type) {
		this.financial_type = financial_type;
	}
}
