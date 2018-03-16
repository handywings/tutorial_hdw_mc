package com.hdw.mccable.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public abstract class ProductBean extends DSTPUtilityBean {
	
	@Autowired
    MessageSource messageSource;
	private Long id;
	private StockBean stock;
	private UnitBean unit;
	private EquipmentProductCategoryBean productCategory;
	private String criteria;
	private String viewType;
	private String type;
	// จำนวนสต๊อค
	 private int stockAmount;
	 // เหลือ
	 private int balance;
	 // จอง
	 private int reservations;
	 // ขาย
	 private int sell;
	 // สำรอง
	 private int spare;
	 
	public void unitTypeInternet(){
		UnitBean unitBean = new UnitBean();
		unitBean.setUnitName(messageSource.getMessage("unit.name.type.internet", null, LocaleContextHolder.getLocale()));
		setUnit(unitBean);
	}
	public void unitTypeServiceCharge(){
		UnitBean unitBean = new UnitBean();
		unitBean.setUnitName(messageSource.getMessage("unit.name.type.charge", null, LocaleContextHolder.getLocale()));
		setUnit(unitBean);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MessageSource getMessageSource() {
		return messageSource;
	}
	public StockBean getStock() {
		return stock;
	}

	public void setStock(StockBean stock) {
		this.stock = stock;
	}

	public UnitBean getUnit() {
		return unit;
	}

	public void setUnit(UnitBean unit) {
		this.unit = unit;
	}

	public EquipmentProductCategoryBean getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(EquipmentProductCategoryBean productCategory) {
		this.productCategory = productCategory;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public void setTypeEquipment() {
		setType("E");
	}

	public void setTypeInternet() {
		setType("I");
	}

	public void setTypeService() {
		setType("S");
	}

	@Override
	public String toString() {
		return "ProductBean [stock=" + stock + ", unit=" + unit + ", productCategory=" + productCategory + ", criteria="
				+ criteria + ", viewType=" + viewType + ", type=" + type + ", getCreateDate()=" + getCreateDate()
				+ ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()="
				+ getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()="
				+ getUpdateDateTh() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
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
	
}
