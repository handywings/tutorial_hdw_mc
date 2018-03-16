package com.hdw.mccable.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductItemBean extends DSTPUtilityBean{
	
	private Long id;
	private Long productId;
	private ProductBean product;
	private int quantity;
	private boolean free;
	private boolean lend;
	private boolean returnDevice; // คืนอุปกรณ์
	private float amount;
	private float price;
	private String type;
	private float feeLend;
	private float deposit;
	private WorksheetBean worksheetBean;
	private List<ProductItemWorksheetBean> productItemWorksheetBeanList = new ArrayList<ProductItemWorksheetBean>();
	private ServiceProductBean serviceProductBean;
	private boolean isSerialNo;
	private String productCategoryName;
	private String productTypeMatch; //O=โปรดักที่เกิดตอนสร้างใบงาน, N=โปรดักที่เกิดตอนเพิ่มอุปกรณ์เพิ่มตอน
	
	private String installDigital;
	
	public String getInstallDigital() {
		return installDigital;
	}
	public void setInstallDigital(String installDigital) {
		this.installDigital = installDigital;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProductBean getProduct() {
		return product;
	}
	public void setProduct(ProductBean product) {
		this.product = product;
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
	public float getDeposit() {
		return deposit;
	}
	public void setDeposit(float deposit) {
		this.deposit = deposit;
	}
	public WorksheetBean getWorksheetBean() {
		return worksheetBean;
	}
	public void setWorksheetBean(WorksheetBean worksheetBean) {
		this.worksheetBean = worksheetBean;
	}
	public List<ProductItemWorksheetBean> getProductItemWorksheetBeanList() {
		return productItemWorksheetBeanList;
	}
	public void setProductItemWorksheetBeanList(List<ProductItemWorksheetBean> productItemWorksheetBeanList) {
		this.productItemWorksheetBeanList = productItemWorksheetBeanList;
	}
	public ServiceProductBean getServiceProductBean() {
		return serviceProductBean;
	}
	public void setServiceProductBean(ServiceProductBean serviceProductBean) {
		this.serviceProductBean = serviceProductBean;
	}
	public boolean isSerialNo() {
		return isSerialNo;
	}
	public void setSerialNo(boolean isSerialNo) {
		this.isSerialNo = isSerialNo;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	public String getProductTypeMatch() {
		return productTypeMatch;
	}
	public void setProductTypeMatch(String productTypeMatch) {
		this.productTypeMatch = productTypeMatch;
	}
	
	public boolean isReturnDevice() {
		return returnDevice;
	}
	public void setReturnDevice(boolean returnDevice) {
		this.returnDevice = returnDevice;
	}
	@Override
	public String toString() {
		return "ProductItemBean [id=" + id + ", product=" + product + ", quantity=" + quantity + ", free=" + free
				+ ", lend=" + lend + ", amount=" + amount + ", price=" + price + ", type=" + type + ", feeLend="
				+ feeLend + ", deposit=" + deposit + ", worksheetBean=" + worksheetBean
				+ ", productItemWorksheetBeanList=" + productItemWorksheetBeanList + ", serviceProductBean="
				+ serviceProductBean + ", isSerialNo=" + isSerialNo + ", productCategoryName=" + productCategoryName
				+ ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()="
				+ getCreateBy() + ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh()
				+ ", getUpdateDateTh()=" + getUpdateDateTh() + ", getCreateByTh()=" + getCreateByTh()
				+ ", getUpdateByTh()=" + getUpdateByTh() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
