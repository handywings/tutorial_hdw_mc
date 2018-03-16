package com.hdw.mccable.dto;

import java.util.List;

public class InvoiceHistoryPrintBean extends FinancialDocumentBean{
	
	private Long id;
	private int printTime; //ครั้งที่พิมพ์
	private PersonnelBean personnelBean; // ผู้พิมพ์
	private PersonnelBean assignPersonnelBean; //มอบหมายผู้รับผิดชอบ
	private InvoiceDocumentBean invoiceDocumentBean;
	private List<InvoiceDocumentBean> invoiceDocumentBeanList; // ไว้แสดงข้อมูลในตารางหน้า รายละเอียดการพิมพ์
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getPrintTime() {
		return printTime;
	}
	public void setPrintTime(int printTime) {
		this.printTime = printTime;
	}
	public PersonnelBean getPersonnelBean() {
		return personnelBean;
	}
	public void setPersonnelBean(PersonnelBean personnelBean) {
		this.personnelBean = personnelBean;
	}
	public InvoiceDocumentBean getInvoiceDocumentBean() {
		return invoiceDocumentBean;
	}
	public void setInvoiceDocumentBean(InvoiceDocumentBean invoiceDocumentBean) {
		this.invoiceDocumentBean = invoiceDocumentBean;
	}
	public List<InvoiceDocumentBean> getInvoiceDocumentBeanList() {
		return invoiceDocumentBeanList;
	}
	public void setInvoiceDocumentBeanList(List<InvoiceDocumentBean> invoiceDocumentBeanList) {
		this.invoiceDocumentBeanList = invoiceDocumentBeanList;
	}
	public PersonnelBean getAssignPersonnelBean() {
		return assignPersonnelBean;
	}
	public void setAssignPersonnelBean(PersonnelBean assignPersonnelBean) {
		this.assignPersonnelBean = assignPersonnelBean;
	}
	@Override
	public String toString() {
		return "InvoiceHistoryPrintBean [id=" + id + ", printTime=" + printTime + ", personnelBean=" + personnelBean
				+ ", assignPersonnelBean=" + assignPersonnelBean + ", invoiceDocumentBean=" + invoiceDocumentBean
				+ ", invoiceDocumentBeanList=" + invoiceDocumentBeanList + "]";
	}
	
}
