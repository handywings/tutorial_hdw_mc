package com.hdw.mccable.dto;

import java.util.Date;
import java.util.List;

public class ReceiptBean extends FinancialDocumentBean {
	
	private Long id;
	private String receiptCode;
	private float amount;
	private String status; //H=ชั่วคราว,P=ถาวร
	private Date paymentDate; //วันที่ชำระเงิน
	private String paymentDateTh;
	private Date issueDocDate; //วันวันที่ออกเอกสาร
	private String issueDocDateTh;
	private String paymentType; //C=หน้า counter หรือ ณ บริษัท,T=โอนเงิน
	private BankBean bankBean;
	private InvoiceDocumentBean invoiceDocumentBean;
	private List<BillHistoryPrintBean> billHistoryPrintBeanList;
	private int lastPrint; //ครั้งที่พิมพ์ล่าสุด
	private float reductAmount; //ลดหนี้ให้
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReceiptCode() {
		return receiptCode;
	}
	public void setReceiptCode(String receiptCode) {
		this.receiptCode = receiptCode;
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
	public String getPaymentDateTh() {
		return paymentDateTh;
	}
	public void setPaymentDateTh(String paymentDateTh) {
		this.paymentDateTh = paymentDateTh;
	}
	public Date getIssueDocDate() {
		return issueDocDate;
	}
	public void setIssueDocDate(Date issueDocDate) {
		this.issueDocDate = issueDocDate;
	}
	public String getIssueDocDateTh() {
		return issueDocDateTh;
	}
	public void setIssueDocDateTh(String issueDocDateTh) {
		this.issueDocDateTh = issueDocDateTh;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public BankBean getBankBean() {
		return bankBean;
	}
	public void setBankBean(BankBean bankBean) {
		this.bankBean = bankBean;
	}
	public InvoiceDocumentBean getInvoiceDocumentBean() {
		return invoiceDocumentBean;
	}
	public void setInvoiceDocumentBean(InvoiceDocumentBean invoiceDocumentBean) {
		this.invoiceDocumentBean = invoiceDocumentBean;
	}
	public List<BillHistoryPrintBean> getBillHistoryPrintBeanList() {
		return billHistoryPrintBeanList;
	}
	public void setBillHistoryPrintBeanList(List<BillHistoryPrintBean> billHistoryPrintBeanList) {
		this.billHistoryPrintBeanList = billHistoryPrintBeanList;
	}
	
	public int getLastPrint() {
		return lastPrint;
	}
	public void setLastPrint(int lastPrint) {
		this.lastPrint = lastPrint;
	}
	
	public float getReductAmount() {
		return reductAmount;
	}
	public void setReductAmount(float reductAmount) {
		this.reductAmount = reductAmount;
	}
	@Override
	public String toString() {
		return "ReceiptBean [id=" + id + ", receiptCode=" + receiptCode + ", amount=" + amount + ", status=" + status
				+ ", paymentDate=" + paymentDate + ", paymentDateTh=" + paymentDateTh + ", issueDocDate=" + issueDocDate
				+ ", issueDocDateTh=" + issueDocDateTh + ", paymentType=" + paymentType + ", bankBean=" + bankBean
				+ ", invoiceDocumentBean=" + invoiceDocumentBean + ", billHistoryPrintBeanList="
				+ billHistoryPrintBeanList + ", lastPrint=" + lastPrint + ", reductAmount=" + reductAmount
				+ ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()="
				+ getCreateBy() + ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh()
				+ ", getUpdateDateTh()=" + getUpdateDateTh() + ", getCreateByTh()=" + getCreateByTh()
				+ ", getUpdateByTh()=" + getUpdateByTh() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
