package com.hdw.mccable.dto;

public class ReportStockSummaryBean {
	private int no; // ลำดับ
	private String productCode; // รหัสสินค้า
	private String productName; // อุปกรณ์
	private String supplier; // บริษัทผู้จาหน่าย
	private String productCategory; // หมวดหมู่
	private String stock; // คลังสินค้า
	private String amount; // จำนวน
	private String pricePerUnitTotal; // มูลค่ารวม

	private String noText; // Header ลำดับ
	private String productCodeText; // Header สินค้า
	private String productNameText; // Header อุปกรณ์
	private String supplierText; // Header บริษัทผู้จาหน่าย
	private String productCategoryText; // Header หมวดหมู่ 
	private String stockText; // Header คลังสินค้า
	private String amountText; // Header จำนวน
	private String pricePerUnitTotalText; // Header มูลค่ารวม
	
	private String groupData; // ข้อมูลด้านบนตาราง
	
	private String sumText; // รวม
	private String sumAmount; // มูลค่าทั้งสิ้น
	private String sumPrice; // Text มูลค่าทั้งสิ้น
	
	private Boolean checkGroupData; // เช็คข้อมูลด้านบนตาราง
	private Boolean checkHeaderTable; // เช็ดการสร้างหัวตาราง
	private Boolean checkSum; // เช็ครวมจำนวน
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPricePerUnitTotal() {
		return pricePerUnitTotal;
	}
	public void setPricePerUnitTotal(String pricePerUnitTotal) {
		this.pricePerUnitTotal = pricePerUnitTotal;
	}
	public String getNoText() {
		return noText;
	}
	public void setNoText(String noText) {
		this.noText = noText;
	}
	public String getProductCodeText() {
		return productCodeText;
	}
	public void setProductCodeText(String productCodeText) {
		this.productCodeText = productCodeText;
	}
	public String getProductNameText() {
		return productNameText;
	}
	public void setProductNameText(String productNameText) {
		this.productNameText = productNameText;
	}
	public String getSupplierText() {
		return supplierText;
	}
	public void setSupplierText(String supplierText) {
		this.supplierText = supplierText;
	}
	public String getProductCategoryText() {
		return productCategoryText;
	}
	public void setProductCategoryText(String productCategoryText) {
		this.productCategoryText = productCategoryText;
	}
	public String getStockText() {
		return stockText;
	}
	public void setStockText(String stockText) {
		this.stockText = stockText;
	}
	public String getAmountText() {
		return amountText;
	}
	public void setAmountText(String amountText) {
		this.amountText = amountText;
	}
	public String getPricePerUnitTotalText() {
		return pricePerUnitTotalText;
	}
	public void setPricePerUnitTotalText(String pricePerUnitTotalText) {
		this.pricePerUnitTotalText = pricePerUnitTotalText;
	}
	public String getGroupData() {
		return groupData;
	}
	public void setGroupData(String groupData) {
		this.groupData = groupData;
	}
	public String getSumText() {
		return sumText;
	}
	public void setSumText(String sumText) {
		this.sumText = sumText;
	}
	public String getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}
	public String getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(String sumPrice) {
		this.sumPrice = sumPrice;
	}
	public Boolean getCheckGroupData() {
		return checkGroupData;
	}
	public void setCheckGroupData(Boolean checkGroupData) {
		this.checkGroupData = checkGroupData;
	}
	public Boolean getCheckHeaderTable() {
		return checkHeaderTable;
	}
	public void setCheckHeaderTable(Boolean checkHeaderTable) {
		this.checkHeaderTable = checkHeaderTable;
	}
	public Boolean getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(Boolean checkSum) {
		this.checkSum = checkSum;
	}
	
}
