package com.hdw.mccable.dto;

import java.util.Date;
import java.util.List;

public class InvoiceDocumentBean extends FinancialDocumentBean {
	
	private Long id;
	private String invoiceCode;
	private String invoiceType; //S=สำหรับติดตั้ง,R=สำหรับซ่อม,O=สำหรับตาม
	private float amount;
	private String status; //W=รอลูกค้าชำระ,S=ชำระแล้ว,O=เกินวันกำหนดชำระ
	private Date paymentDate; //วันนัดชำระ
	private String paymentDateTh;
	private Date issueDocDate; //วันวันที่ออกเอกสาร
	private String issueDocDateTh;
	private WorksheetBean worksheet;
	private List<PaymentItemBean> paymentItemList;  // ?
	private ServiceApplicationBean serviceApplication;
	private String vat; //N=no vat,Y=vat
	private List<InvoiceHistoryPrintBean> invoiceHistoryPrintBeanList;
	private int printCount; //ครั้งที่พิมพ์
	private String printLastDate; //พิมพ์ล่าสุด
	
	//สำหรับการแสกนบิล
	private PersonnelBean personnelBean; //พนักงานผู้ออกไปเก็บเงิน
	private String statusScan; //E=แสกนออก, I=แสกนเข้า, N=ยังไม่มีการแสกน
	private Date scanOutDate; //วันที่แสกนบิลออก
	private String scanOutDateTh;
	private Date scanInDate; //วันที่แสกนบิลเข้า
	private String scanInDateTh;
	private boolean billing;
	//สำหรับค่าบริการรายเดือนเพิ่ม
	private AdditionMonthlyFee additionMonthlyFee;
	private String serviceRoundDate; //2 ธันวาคม 2559 - 2 มกราคม 2560
	
	//สำหรับรายเดือนที่เป็นแบบคิดเพิ่มจากติดสายกรณี ตัดสายเกิน 15 วัน
	private boolean cutting;
	private float specialDiscount;
	private int quantityBill; // จำนวนบิล
	
	private String billingStatus; //N=บิลค้างชำระ, Y=บิลชำระแล้ว, A=บิลค้างชำระพร้อมวางบิล
	
	ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean;
	
	private int quantityTotalPoint; // จำนวนจุดรวม
	
	private boolean isBadDebt; // หนี้สูญ
	
	private ReceiptBean receiptBean;
	
	public ReceiptBean getReceiptBean() {
		return receiptBean;
	}
	public void setReceiptBean(ReceiptBean receiptBean) {
		this.receiptBean = receiptBean;
	}
	public boolean isBadDebt() {
		return isBadDebt;
	}
	public void setBadDebt(boolean isBadDebt) {
		this.isBadDebt = isBadDebt;
	}
	public int getQuantityTotalPoint() {
		return quantityTotalPoint;
	}
	public void setQuantityTotalPoint(int quantityTotalPoint) {
		this.quantityTotalPoint = quantityTotalPoint;
	}
	public ReportInvoiceOrReceiptBean getReportInvoiceOrReceiptBean() {
		return reportInvoiceOrReceiptBean;
	}
	public void setReportInvoiceOrReceiptBean(ReportInvoiceOrReceiptBean reportInvoiceOrReceiptBean) {
		this.reportInvoiceOrReceiptBean = reportInvoiceOrReceiptBean;
	}
	public String getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Date getIssueDocDate() {
		return issueDocDate;
	}
	public void setIssueDocDate(Date issueDocDate) {
		this.issueDocDate = issueDocDate;
	}
	public WorksheetBean getWorksheet() {
		return worksheet;
	}
	public void setWorksheet(WorksheetBean worksheet) {
		this.worksheet = worksheet;
	}
	public List<PaymentItemBean> getPaymentItemList() {
		return paymentItemList;
	}
	public void setPaymentItemList(List<PaymentItemBean> paymentItemList) {
		this.paymentItemList = paymentItemList;
	}
	public ServiceApplicationBean getServiceApplication() {
		return serviceApplication;
	}
	public void setServiceApplication(ServiceApplicationBean serviceApplication) {
		this.serviceApplication = serviceApplication;
	}
	public String getPaymentDateTh() {
		return paymentDateTh;
	}
	public void setPaymentDateTh(String paymentDateTh) {
		this.paymentDateTh = paymentDateTh;
	}
	public String getIssueDocDateTh() {
		return issueDocDateTh;
	}
	public void setIssueDocDateTh(String issueDocDateTh) {
		this.issueDocDateTh = issueDocDateTh;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public List<InvoiceHistoryPrintBean> getInvoiceHistoryPrintBeanList() {
		return invoiceHistoryPrintBeanList;
	}
	public void setInvoiceHistoryPrintBeanList(List<InvoiceHistoryPrintBean> invoiceHistoryPrintBeanList) {
		this.invoiceHistoryPrintBeanList = invoiceHistoryPrintBeanList;
	}
	
	public PersonnelBean getPersonnelBean() {
		return personnelBean;
	}
	public void setPersonnelBean(PersonnelBean personnelBean) {
		this.personnelBean = personnelBean;
	}
	public String getStatusScan() {
		return statusScan;
	}
	public void setStatusScan(String statusScan) {
		this.statusScan = statusScan;
	}
	public Date getScanOutDate() {
		return scanOutDate;
	}
	public void setScanOutDate(Date scanOutDate) {
		this.scanOutDate = scanOutDate;
	}
	public String getScanOutDateTh() {
		return scanOutDateTh;
	}
	public void setScanOutDateTh(String scanOutDateTh) {
		this.scanOutDateTh = scanOutDateTh;
	}
	public Date getScanInDate() {
		return scanInDate;
	}
	public void setScanInDate(Date scanInDate) {
		this.scanInDate = scanInDate;
	}
	public String getScanInDateTh() {
		return scanInDateTh;
	}
	public void setScanInDateTh(String scanInDateTh) {
		this.scanInDateTh = scanInDateTh;
	}
	public AdditionMonthlyFee getAdditionMonthlyFee() {
		return additionMonthlyFee;
	}
	public void setAdditionMonthlyFee(AdditionMonthlyFee additionMonthlyFee) {
		this.additionMonthlyFee = additionMonthlyFee;
	}
	public boolean isBilling() {
		return billing;
	}
	public void setBilling(boolean billing) {
		this.billing = billing;
	}
	public String getServiceRoundDate() {
		return serviceRoundDate;
	}
	public void setServiceRoundDate(String serviceRoundDate) {
		this.serviceRoundDate = serviceRoundDate;
	}
	
	public void createServiceRoundDate(String startDate,String endDate){
		if(!startDate.isEmpty() &&  !endDate.isEmpty()){
			setServiceRoundDate(endDate+" - "+startDate);
		}else{
			setServiceRoundDate("");
		}
	}
	
	public boolean isCutting() {
		return cutting;
	}
	public void setCutting(boolean cutting) {
		this.cutting = cutting;
	}
	public float getSpecialDiscount() {
		return specialDiscount;
	}
	public void setSpecialDiscount(float specialDiscount) {
		this.specialDiscount = specialDiscount;
	}
	public int getPrintCount() {
		return printCount;
	}
	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}
	public String getPrintLastDate() {
		return printLastDate;
	}
	public void setPrintLastDate(String printLastDate) {
		this.printLastDate = printLastDate;
	}
	public int getQuantityBill() {
		return quantityBill;
	}
	public void setQuantityBill(int quantityBill) {
		this.quantityBill = quantityBill;
	}
	@Override
	public String toString() {
		return "InvoiceDocumentBean [id=" + id + ", invoiceCode=" + invoiceCode + ", invoiceType=" + invoiceType
				+ ", amount=" + amount + ", status=" + status + ", paymentDate=" + paymentDate + ", paymentDateTh="
				+ paymentDateTh + ", issueDocDate=" + issueDocDate + ", issueDocDateTh=" + issueDocDateTh
				+ ", worksheet=" + worksheet + ", paymentItemList=" + paymentItemList + ", serviceApplication="
				+ serviceApplication + ", vat=" + vat + ", invoiceHistoryPrintBeanList=" + invoiceHistoryPrintBeanList
				+ ", personnelBean=" + personnelBean + ", statusScan=" + statusScan + ", scanOutDate=" + scanOutDate
				+ ", scanOutDateTh=" + scanOutDateTh + ", scanInDate=" + scanInDate + ", scanInDateTh=" + scanInDateTh
				+ ", billing=" + billing + ", additionMonthlyFee=" + additionMonthlyFee + ", serviceRoundDate="
				+ serviceRoundDate + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()=" + getUpdateDate()
				+ ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()="
				+ getCreateDateTh() + ", getUpdateDateTh()=" + getUpdateDateTh() + ", getCreateByTh()="
				+ getCreateByTh() + ", getUpdateByTh()=" + getUpdateByTh() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
