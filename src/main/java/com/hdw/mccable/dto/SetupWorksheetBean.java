package com.hdw.mccable.dto;

public class SetupWorksheetBean extends WorksheetBean {
	private Long id;
	private String dateStartBill;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDateStartBill() {
		return dateStartBill;
	}

	public void setDateStartBill(String dateStartBill) {
		this.dateStartBill = dateStartBill;
	}
	
}
