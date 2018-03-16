package com.hdw.mccable.dto;

import java.util.List;

public class EquipmentProductItemBean extends DSTPUtilityBean{
	
	private Long id;
	private String referenceNo;
	private String serialNo;
	private String supplierDate;
	private float cost;
	private float salePrice;
	private String guaranteeDate;
	private String orderDate;
	private String importSystemDate;
	private String status; // 0=เสีย, 1=ใช้งานได้ปกติ, 2=กำลังซ่อม/ซ่อม, 3=ยืม, 4=สำรอง
	private int statusReal;
	private int numberImport;
	private PersonnelBean personnelBean;
	private List<HistoryUseEquipmentBean> historyUseEquipmentBeans;
	private List<HistoryUpdateStatusBean> historyUpdateStatusBeans;
	private List<RequisitionItemBean> requisitionItemBeans;
	private EquipmentProductBean equipmentProductBean;
	private boolean expireGuarantee;
	private int balance;
	private int spare;
	private int reservations;
	private boolean isRepair; // เคยมีการ ซ่อม
	private float priceIncTax;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getSupplierDate() {
		return supplierDate;
	}
	public void setSupplierDate(String supplierDate) {
		this.supplierDate = supplierDate;
	}
 
	public String getGuaranteeDate() {
		return guaranteeDate;
	}
	public void setGuaranteeDate(String guaranteeDate) {
		this.guaranteeDate = guaranteeDate;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getImportSystemDate() {
		return importSystemDate;
	}
	public void setImportSystemDate(String importSystemDate) {
		this.importSystemDate = importSystemDate;
	}
	public PersonnelBean getPersonnelBean() {
		return personnelBean;
	}
	public void setPersonnelBean(PersonnelBean personnelBean) {
		this.personnelBean = personnelBean;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<HistoryUseEquipmentBean> getHistoryUseEquipmentBeans() {
		return historyUseEquipmentBeans;
	}
	public void setHistoryUseEquipmentBeans(List<HistoryUseEquipmentBean> historyUseEquipmentBeans) {
		this.historyUseEquipmentBeans = historyUseEquipmentBeans;
	}
	public List<HistoryUpdateStatusBean> getHistoryUpdateStatusBeans() {
		return historyUpdateStatusBeans;
	}
	public void setHistoryUpdateStatusBeans(List<HistoryUpdateStatusBean> historyUpdateStatusBeans) {
		this.historyUpdateStatusBeans = historyUpdateStatusBeans;
	}
	public int getNumberImport() {
		return numberImport;
	}
	public void setNumberImport(int numberImport) {
		this.numberImport = numberImport;
	}
	
	public int getStatusReal() {
		return statusReal;
	}
	public void setStatusReal(int statusReal) {
		this.statusReal = statusReal;
	}
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
	public EquipmentProductBean getEquipmentProductBean() {
		return equipmentProductBean;
	}
	public void setEquipmentProductBean(EquipmentProductBean equipmentProductBean) {
		this.equipmentProductBean = equipmentProductBean;
	}
	public boolean isExpireGuarantee() {
		return expireGuarantee;
	}
	public void setExpireGuarantee(boolean expireGuarantee) {
		this.expireGuarantee = expireGuarantee;
	}
	
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public List<RequisitionItemBean> getRequisitionItemBeans() {
		return requisitionItemBeans;
	}
	public void setRequisitionItemBeans(List<RequisitionItemBean> requisitionItemBeans) {
		this.requisitionItemBeans = requisitionItemBeans;
	}
	
	public int getSpare() {
		return spare;
	}
	public void setSpare(int spare) {
		this.spare = spare;
	}
	
	public boolean isRepair() {
		return isRepair;
	}
	public void setRepair(boolean isRepair) {
		this.isRepair = isRepair;
	}
	
	public int getReservations() {
		return reservations;
	}
	public void setReservations(int reservations) {
		this.reservations = reservations;
	}
	public float getPriceIncTax() {
		return priceIncTax;
	}
	public void setPriceIncTax(float priceIncTax) {
		this.priceIncTax = priceIncTax;
	}
	
	@Override
	public String toString() {
		return "EquipmentProductItemBean [id=" + id + ", referenceNo=" + referenceNo + ", serialNo=" + serialNo
				+ ", supplierDate=" + supplierDate + ", cost=" + cost + ", salePrice=" + salePrice + ", guaranteeDate="
				+ guaranteeDate + ", orderDate=" + orderDate + ", importSystemDate=" + importSystemDate + ", status="
				+ status + ", statusReal=" + statusReal + ", numberImport=" + numberImport + ", personnelBean="
				+ personnelBean + ", historyUseEquipmentBeans=" + historyUseEquipmentBeans
				+ ", historyUpdateStatusBeans=" + historyUpdateStatusBeans + ", equipmentProductBean="
				+ equipmentProductBean + ", expireGuarantee=" + expireGuarantee + ", getCreateDate()=" + getCreateDate()
				+ ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()="
				+ getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()="
				+ getUpdateDateTh() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
