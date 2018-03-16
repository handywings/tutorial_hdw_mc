package com.hdw.mccable.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockBean extends DSTPUtilityBean{
	private Long id;
	private String stockCode;
	private String stockDetail;
	private String stockName;
	private CompanyBean company;
	private AddressBean address;
	List<EquipmentProductBean> equipmentProducts;
	List<InternetProductBean> internetProducts;
	List<ServiceProductBean> serviceProducts;
	private int allProducts;
	private int lowInStock;
	private int outOfStock;
	private int insuranceExpire;
	
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
	public CompanyBean getCompany() {
		return company;
	}
	public void setCompany(CompanyBean company) {
		this.company = company;
	}
	public AddressBean getAddress() {
		return address;
	}
	public void setAddress(AddressBean address) {
		this.address = address;
	}
	public List<EquipmentProductBean> getEquipmentProducts() {
		return equipmentProducts;
	}
	public void setEquipmentProducts(List<EquipmentProductBean> equipmentProducts) {
		this.equipmentProducts = equipmentProducts;
	}
	public List<InternetProductBean> getInternetProducts() {
		return internetProducts;
	}
	public void setInternetProducts(List<InternetProductBean> internetProducts) {
		this.internetProducts = internetProducts;
	}
	public List<ServiceProductBean> getServiceProducts() {
		return serviceProducts;
	}
	public void setServiceProducts(List<ServiceProductBean> serviceProducts) {
		this.serviceProducts = serviceProducts;
	}
	public int getAllProducts() {
		return allProducts;
	}
	public void setAllProducts(int allProducts) {
		this.allProducts = allProducts;
	}
	public int getLowInStock() {
		return lowInStock;
	}
	public void setLowInStock(int lowInStock) {
		this.lowInStock = lowInStock;
	}
	public int getOutOfStock() {
		return outOfStock;
	}
	public void setOutOfStock(int outOfStock) {
		this.outOfStock = outOfStock;
	}
	public int getInsuranceExpire() {
		return insuranceExpire;
	}
	public void setInsuranceExpire(int insuranceExpire) {
		this.insuranceExpire = insuranceExpire;
	}

}
