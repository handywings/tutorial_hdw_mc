package com.hdw.mccable.dto;

public class BillHistoryPrintBean extends FinancialDocumentBean{
	
	private Long id;
	private int printTime; //ครั้งที่พิมพ์
	private PersonnelBean personnelBean;
	
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
}
