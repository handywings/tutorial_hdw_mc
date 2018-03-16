package com.hdw.mccable.dto;

public class ReportRequisitionproductBean {
	private int no; // ลำดับ
	private String requisitionDocumentCode; // ใบเบิก
	private String withdrawalDate; // วันที่เบิก
	private String productName; // สินค้า
	private String productCategory; // หมวดหมู่
	private String stock; // คลังสินค้า
	private String technicianGroup; // ผู้เบิก
	private String amount; // จำนวน
	private String pricePerUnit; // ราคาต่อหน่วย
	private String pricePerUnitTotal; // มูลค่ารวม

	private String noText; // Header ลำดับ
	private String requisitionDocumentCodeText; // Header ใบเบิก
	private String withdrawalDateText; // Header วันที่เบิก
	private String productNameText; // Header สินค้า
	private String productCategoryText; // Header หมวดหมู่ 
	private String stockText; // Header คลังสินค้า
	private String technicianGroupText; // Header ผู้เบิก
	private String amountText; // Header จำนวน
	private String pricePerUnitText; // Header ราคาต่อหน่วย
	private String pricePerUnitTotalText; // Header มูลค่ารวม
	
	private String groupData; // ข้อมูลด้านบนตาราง
	
	private String sumAmount; // รวมจำนวน
	private String sumAmountText; // Text รวมจำนวน
	private String sumPrice; // มูลค่าทั้งสิ้น
	private String sumPriceText; // Text มูลค่าทั้งสิ้น
	
	private Boolean checkGroupData; // เช็คข้อมูลด้านบนตาราง
	private Boolean checkHeaderTable; // เช็ดการสร้างหัวตาราง
	private Boolean checkSum; // เช็ครวมจำนวน
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getRequisitionDocumentCode() {
		return requisitionDocumentCode;
	}
	public void setRequisitionDocumentCode(String requisitionDocumentCode) {
		this.requisitionDocumentCode = requisitionDocumentCode;
	}
	public String getWithdrawalDate() {
		return withdrawalDate;
	}
	public void setWithdrawalDate(String withdrawalDate) {
		this.withdrawalDate = withdrawalDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public String getTechnicianGroup() {
		return technicianGroup;
	}
	public void setTechnicianGroup(String technicianGroup) {
		this.technicianGroup = technicianGroup;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(String pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
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
	public String getRequisitionDocumentCodeText() {
		return requisitionDocumentCodeText;
	}
	public void setRequisitionDocumentCodeText(String requisitionDocumentCodeText) {
		this.requisitionDocumentCodeText = requisitionDocumentCodeText;
	}
	public String getWithdrawalDateText() {
		return withdrawalDateText;
	}
	public void setWithdrawalDateText(String withdrawalDateText) {
		this.withdrawalDateText = withdrawalDateText;
	}
	public String getProductNameText() {
		return productNameText;
	}
	public void setProductNameText(String productNameText) {
		this.productNameText = productNameText;
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
	public String getTechnicianGroupText() {
		return technicianGroupText;
	}
	public void setTechnicianGroupText(String technicianGroupText) {
		this.technicianGroupText = technicianGroupText;
	}
	public String getAmountText() {
		return amountText;
	}
	public void setAmountText(String amountText) {
		this.amountText = amountText;
	}
	public String getPricePerUnitText() {
		return pricePerUnitText;
	}
	public void setPricePerUnitText(String pricePerUnitText) {
		this.pricePerUnitText = pricePerUnitText;
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
	public String getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}
	public String getSumAmountText() {
		return sumAmountText;
	}
	public void setSumAmountText(String sumAmountText) {
		this.sumAmountText = sumAmountText;
	}
	public String getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(String sumPrice) {
		this.sumPrice = sumPrice;
	}
	public String getSumPriceText() {
		return sumPriceText;
	}
	public void setSumPriceText(String sumPriceText) {
		this.sumPriceText = sumPriceText;
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
