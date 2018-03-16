package com.hdw.mccable.dto;

public class ProductItemWorksheetBean extends DSTPUtilityBean{
	
	private Long id;
	private InternetProductBeanItem internetProductBeanItem;
	private EquipmentProductItemBean equipmentProductItemBean;
	private int quantity;
	private boolean free;
	private boolean lend;
	private float amount;
	private float price;
	private float deposit;
	private String type;
	private float feeLend;
	private ProductItemBean productItemBean;
	private Long parent; //สำหรับใบงานแบบซ่อมสายสัญญาณ ที่มีการซ่อมอุปกรณ์
	private String productTypeMatch; //O=โปรดักที่เกิดตอนสร้างใบงาน, N=โปรดักที่เกิดตอนเพิ่มอุปกรณ์เพิ่มเติม
	private int lendStatus; //สถานะการยืมของอุปกรณ์ ปกติ=1, ชำรุด=0, หาย(มองเป็นขายขาด)=5
	private boolean returnEquipment; // คืนอุปกรณ์
	private RequisitionItemBean requisitionItemBean; // ใบเบิก
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public InternetProductBeanItem getInternetProductBeanItem() {
		return internetProductBeanItem;
	}
	public void setInternetProductBeanItem(InternetProductBeanItem internetProductBeanItem) {
		this.internetProductBeanItem = internetProductBeanItem;
	}
	public EquipmentProductItemBean getEquipmentProductItemBean() {
		return equipmentProductItemBean;
	}
	public void setEquipmentProductItemBean(EquipmentProductItemBean equipmentProductItemBean) {
		this.equipmentProductItemBean = equipmentProductItemBean;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
	public boolean isLend() {
		return lend;
	}
	public void setLend(boolean lend) {
		this.lend = lend;
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
	public float getDeposit() {
		return deposit;
	}
	public void setDeposit(float deposit) {
		this.deposit = deposit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getFeeLend() {
		return feeLend;
	}
	public void setFeeLend(float feeLend) {
		this.feeLend = feeLend;
	}
	public ProductItemBean getProductItemBean() {
		return productItemBean;
	}
	public void setProductItemBean(ProductItemBean productItemBean) {
		this.productItemBean = productItemBean;
	}
	public Long getParent() {
		return parent;
	}
	public void setParent(Long parent) {
		this.parent = parent;
	}
	public String getProductTypeMatch() {
		return productTypeMatch;
	}
	public void setProductTypeMatch(String productTypeMatch) {
		this.productTypeMatch = productTypeMatch;
	}
	public int getLendStatus() {
		return lendStatus;
	}
	public void setLendStatus(int lendStatus) {
		this.lendStatus = lendStatus;
	}
	public boolean isReturnEquipment() {
		return returnEquipment;
	}
	public void setReturnEquipment(boolean returnEquipment) {
		this.returnEquipment = returnEquipment;
	}
	
	public RequisitionItemBean getRequisitionItemBean() {
		return requisitionItemBean;
	}
	public void setRequisitionItemBean(RequisitionItemBean requisitionItemBean) {
		this.requisitionItemBean = requisitionItemBean;
	}
	@Override
	public String toString() {
		return "ProductItemWorksheetBean [id=" + id + ", internetProductBeanItem=" + internetProductBeanItem
				+ ", equipmentProductItemBean=" + equipmentProductItemBean + ", quantity=" + quantity + ", free=" + free
				+ ", lend=" + lend + ", amount=" + amount + ", price=" + price + ", deposit=" + deposit + ", type="
				+ type + ", feeLend=" + feeLend + ", productItemBean=" + productItemBean + ", parent=" + parent
				+ ", productTypeMatch=" + productTypeMatch + ", lendStatus=" + lendStatus + ", getCreateDate()="
				+ getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy()
				+ ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh()
				+ ", getUpdateDateTh()=" + getUpdateDateTh() + ", getCreateByTh()=" + getCreateByTh()
				+ ", getUpdateByTh()=" + getUpdateByTh() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
