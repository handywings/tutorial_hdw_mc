package com.hdw.mccable.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceApplicationBean extends DSTPUtilityBean{
	
	private Long id;
	private String serviceApplicationNo; // รหัสใบสมัคร CONT-00000X
	private List<AddressBean> addressList; // ที่อยู่ 3, 4, 5
	private List<ProductItemBean> productitemList;
	private CustomerBean customer; // ที่อยู่ 1, 2 จะอยู่ใน customer  ? แล้ว  3,4,5 หล่ะอยู่ที่ไหน ฮ่าๆๆๆ
	private StatusBean status; // สถานะใบสมัคร/การใช้งาน A = ใช้งานปรกติ, I = ยกเลิกใช้งาน ,W = รอดำเนินการติดตั้ง,D = แบบร่าง
	private ServicePackageBean servicepackage;
	private List<DocumentFileBean> documents;
	private String easyInstallationDateTime; // วันเวลาที่สะดวกในการติดตั้ง
	private float installationFee; // ค่าติดตั้ง (ไม่รวม Vat)
	private float deposit; // ค่ามัดจำอุปกรณ์ (ไม่รวม Vat)
	private float monthlyServiceFee; // ค่าบริการรายเดือน
	private int perMonth; // ต่อเดือน
	private int firstBillFree; // รอบบิลแรกใช้ฟรี
	private float firstBillFreeDisCount; // รอบบิลแรกลดให้
	private float oneServiceFee; // กรณีจ่ายครั่งเดียว
	private float totalAmount;
	private String remark;
	private String startDate;
	private String endDate;
	private boolean houseRegistrationDocuments; // checkbox สำเนาทะเบียนบ้าน
	private boolean identityCardDocuments; // checkbox สำเนาบัตรประจำตัวประชาชน
	private boolean otherDocuments; // checkbox เอกสารอื่นๆ
	private boolean monthlyService;  //true = per mounth
	private ServiceApplicationTypeBean serviceApplicationTypeBean;
	List<WorksheetBean> worksheetBeanList = new ArrayList<WorksheetBean>();
	private String remarkOtherDocuments; // textarea เอกสารอื่นๆ
	private int overpay;
	private WorksheetBean worksheetSetup;
	private String currentCreateInvoice; // วันที่ออกใบแจ้งหนี้ล่าสุด
	private Long worksheetAddPointId;
	
	//bill
	private List<InvoiceDocumentBean> invoiceCurrentBill;
	private List<InvoiceDocumentBean> invoiceOverBill;
	
	private String refundDate; //วันที่เคืนเงินมีดจำ
	private String cancelServiceDate; //วันที่เยกเลิกบริการ
	private float refund; // จำนวนเงินมัดจำ
	private boolean flagRefund; //บอกว่าการยกเลิกใบสมัครนี้มีการคืนเงินมัดจำหรือไม่
	private String plateNumber;
	
	private String serviceDate; // วันที่เริ่มใช้บริการ
	
	//Location
	private String latitude;
	private String longitude;

	private ServiceApplicationBean referenceServiceApplicationBean; // ใบสมัครเดิม
	
	private String orderBillDate; // วันครบกำหนด ใช้ในหน้า เปลี่ยนบริการ
	
	private Long personnelId; //พนักงานเก็บเงินที่กำหนดไว้เป็นค่าพื้นฐาน
	
	private CompanyBean companyBean;
	
	public CompanyBean getCompanyBean() {
		return companyBean;
	}

	public void setCompanyBean(CompanyBean companyBean) {
		this.companyBean = companyBean;
	}

	public Long getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(Long personnelId) {
		this.personnelId = personnelId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceApplicationNo() {
		return serviceApplicationNo;
	}

	public void setServiceApplicationNo(String serviceApplicationNo) {
		this.serviceApplicationNo = serviceApplicationNo;
	}

	public List<AddressBean> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<AddressBean> addressList) {
		this.addressList = addressList;
	}

	public List<ProductItemBean> getProductitemList() {
		return productitemList;
	}

	public void setProductitemList(List<ProductItemBean> productitemList) {
		this.productitemList = productitemList;
	}

	public CustomerBean getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerBean customer) {
		this.customer = customer;
	}

	public StatusBean getStatus() {
		return status;
	}

	public void setStatus(StatusBean status) {
		this.status = status;
	}

	public ServicePackageBean getServicepackage() {
		return servicepackage;
	}

	public void setServicepackage(ServicePackageBean servicepackage) {
		this.servicepackage = servicepackage;
	}

	public List<DocumentFileBean> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentFileBean> documents) {
		this.documents = documents;
	}

	public String getEasyInstallationDateTime() {
		return easyInstallationDateTime;
	}

	public void setEasyInstallationDateTime(String easyInstallationDateTime) {
		this.easyInstallationDateTime = easyInstallationDateTime;
	}

	public float getInstallationFee() {
		return installationFee;
	}

	public void setInstallationFee(float installationFee) {
		this.installationFee = installationFee;
	}

	public float getDeposit() {
		return deposit;
	}

	public void setDeposit(float deposit) {
		this.deposit = deposit;
	}

	public float getMonthlyServiceFee() {
		return monthlyServiceFee;
	}

	public void setMonthlyServiceFee(float monthlyServiceFee) {
		this.monthlyServiceFee = monthlyServiceFee;
	}

	public int getPerMonth() {
		return perMonth;
	}

	public void setPerMonth(int perMonth) {
		this.perMonth = perMonth;
	}

	public int getFirstBillFree() {
		return firstBillFree;
	}

	public void setFirstBillFree(int firstBillFree) {
		this.firstBillFree = firstBillFree;
	}

	public float getFirstBillFreeDisCount() {
		return firstBillFreeDisCount;
	}

	public void setFirstBillFreeDisCount(float firstBillFreeDisCount) {
		this.firstBillFreeDisCount = firstBillFreeDisCount;
	}

	public float getOneServiceFee() {
		return oneServiceFee;
	}

	public void setOneServiceFee(float oneServiceFee) {
		this.oneServiceFee = oneServiceFee;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isHouseRegistrationDocuments() {
		return houseRegistrationDocuments;
	}

	public void setHouseRegistrationDocuments(boolean houseRegistrationDocuments) {
		this.houseRegistrationDocuments = houseRegistrationDocuments;
	}

	public boolean isIdentityCardDocuments() {
		return identityCardDocuments;
	}

	public void setIdentityCardDocuments(boolean identityCardDocuments) {
		this.identityCardDocuments = identityCardDocuments;
	}

	public boolean isOtherDocuments() {
		return otherDocuments;
	}

	public void setOtherDocuments(boolean otherDocuments) {
		this.otherDocuments = otherDocuments;
	}

	public boolean isMonthlyService() {
		return monthlyService;
	}

	public void setMonthlyService(boolean monthlyService) {
		this.monthlyService = monthlyService;
	}

	public ServiceApplicationTypeBean getServiceApplicationTypeBean() {
		return serviceApplicationTypeBean;
	}

	public void setServiceApplicationTypeBean(ServiceApplicationTypeBean serviceApplicationTypeBean) {
		this.serviceApplicationTypeBean = serviceApplicationTypeBean;
	}

	public List<WorksheetBean> getWorksheetBeanList() {
		return worksheetBeanList;
	}

	public void setWorksheetBeanList(List<WorksheetBean> worksheetBeanList) {
		this.worksheetBeanList = worksheetBeanList;
	}

	public String getRemarkOtherDocuments() {
		return remarkOtherDocuments;
	}

	public void setRemarkOtherDocuments(String remarkOtherDocuments) {
		this.remarkOtherDocuments = remarkOtherDocuments;
	}

	public int getOverpay() {
		return overpay;
	}

	public void setOverpay(int overpay) {
		this.overpay = overpay;
	}

	public WorksheetBean getWorksheetSetup() {
		return worksheetSetup;
	}

	public void setWorksheetSetup(WorksheetBean worksheetSetup) {
		this.worksheetSetup = worksheetSetup;
	}

	public String getCurrentCreateInvoice() {
		return currentCreateInvoice;
	}

	public void setCurrentCreateInvoice(String currentCreateInvoice) {
		this.currentCreateInvoice = currentCreateInvoice;
	}

	public Long getWorksheetAddPointId() {
		return worksheetAddPointId;
	}

	public void setWorksheetAddPointId(Long worksheetAddPointId) {
		this.worksheetAddPointId = worksheetAddPointId;
	}

	public List<InvoiceDocumentBean> getInvoiceCurrentBill() {
		return invoiceCurrentBill;
	}

	public void setInvoiceCurrentBill(List<InvoiceDocumentBean> invoiceCurrentBill) {
		this.invoiceCurrentBill = invoiceCurrentBill;
	}

	public List<InvoiceDocumentBean> getInvoiceOverBill() {
		return invoiceOverBill;
	}

	public void setInvoiceOverBill(List<InvoiceDocumentBean> invoiceOverBill) {
		this.invoiceOverBill = invoiceOverBill;
	}

	public String getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}

	public String getCancelServiceDate() {
		return cancelServiceDate;
	}

	public void setCancelServiceDate(String cancelServiceDate) {
		this.cancelServiceDate = cancelServiceDate;
	}

	public float getRefund() {
		return refund;
	}

	public void setRefund(float refund) {
		this.refund = refund;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	@Override
	public String toString() {
		return "ServiceApplicationBean [id=" + id + ", serviceApplicationNo=" + serviceApplicationNo + ", addressList="
				+ addressList + ", productitemList=" + productitemList + ", customer=" + customer + ", status=" + status
				+ ", servicepackage=" + servicepackage + ", documents=" + documents + ", easyInstallationDateTime="
				+ easyInstallationDateTime + ", installationFee=" + installationFee + ", deposit=" + deposit
				+ ", monthlyServiceFee=" + monthlyServiceFee + ", perMonth=" + perMonth + ", firstBillFree="
				+ firstBillFree + ", firstBillFreeDisCount=" + firstBillFreeDisCount + ", oneServiceFee="
				+ oneServiceFee + ", totalAmount=" + totalAmount + ", remark=" + remark + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", houseRegistrationDocuments=" + houseRegistrationDocuments
				+ ", identityCardDocuments=" + identityCardDocuments + ", otherDocuments=" + otherDocuments
				+ ", monthlyService=" + monthlyService + ", serviceApplicationTypeBean=" + serviceApplicationTypeBean
				+ ", worksheetBeanList=" + worksheetBeanList + ", remarkOtherDocuments=" + remarkOtherDocuments
				+ ", overpay=" + overpay + ", worksheetSetup=" + worksheetSetup + ", currentCreateInvoice="
				+ currentCreateInvoice + ", worksheetAddPointId=" + worksheetAddPointId + ", invoiceCurrentBill="
				+ invoiceCurrentBill + ", invoiceOverBill=" + invoiceOverBill + ", refundDate=" + refundDate
				+ ", cancelServiceDate=" + cancelServiceDate + ", refund=" + refund + ", plateNumber=" + plateNumber
				+ ", serviceDate=" + serviceDate + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()="
				+ getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()=" + getUpdateBy()
				+ ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()=" + getUpdateDateTh()
				+ ", getCreateByTh()=" + getCreateByTh() + ", getUpdateByTh()=" + getUpdateByTh() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public boolean isFlagRefund() {
		return flagRefund;
	}

	public void setFlagRefund(boolean flagRefund) {
		this.flagRefund = flagRefund;
	}

	public ServiceApplicationBean getReferenceServiceApplicationBean() {
		return referenceServiceApplicationBean;
	}

	public void setReferenceServiceApplicationBean(ServiceApplicationBean referenceServiceApplicationBean) {
		this.referenceServiceApplicationBean = referenceServiceApplicationBean;
	}

	public String getOrderBillDate() {
		return orderBillDate;
	}

	public void setOrderBillDate(String orderBillDate) {
		this.orderBillDate = orderBillDate;
	}
	
}
