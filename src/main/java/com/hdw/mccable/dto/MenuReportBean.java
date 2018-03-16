package com.hdw.mccable.dto;

public class MenuReportBean extends DSTPUtilityBean{
	
	private Long id;
	
	private String menuReportCode;
	
	private String menuReportName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuReportCode() {
		return menuReportCode;
	}

	public void setMenuReportCode(String menuReportCode) {
		this.menuReportCode = menuReportCode;
	}

	public String getMenuReportName() {
		return menuReportName;
	}

	public void setMenuReportName(String menuReportName) {
		this.menuReportName = menuReportName;
	}
	
}
