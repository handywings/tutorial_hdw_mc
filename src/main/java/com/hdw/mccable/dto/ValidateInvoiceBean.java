package com.hdw.mccable.dto;

import java.util.List;

public class ValidateInvoiceBean {
	
	private List<InvoiceDocumentBean> invoiceDocumentBeanList;
	private String exportDate;
	private String importDate;
	private Long idPersonnelCashier;
	private boolean payed; 
	
	public List<InvoiceDocumentBean> getInvoiceDocumentBeanList() {
		return invoiceDocumentBeanList;
	}
	public void setInvoiceDocumentBeanList(List<InvoiceDocumentBean> invoiceDocumentBeanList) {
		this.invoiceDocumentBeanList = invoiceDocumentBeanList;
	}
	public String getExportDate() {
		return exportDate;
	}
	public void setExportDate(String exportDate) {
		this.exportDate = exportDate;
	}
	public String getImportDate() {
		return importDate;
	}
	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}
	public Long getIdPersonnelCashier() {
		return idPersonnelCashier;
	}
	public void setIdPersonnelCashier(Long idPersonnelCashier) {
		this.idPersonnelCashier = idPersonnelCashier;
	}
	public boolean isPayed() {
		return payed;
	}
	public void setPayed(boolean payed) {
		this.payed = payed;
	}
	
}
