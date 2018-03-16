package com.hdw.mccable.dto;

public class AnalyzeProblemsWorksheetBean extends WorksheetBean {
	private Long id;
	private Long menuReportId;
	private String menuReportName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMenuReportId() {
		return menuReportId;
	}
	public void setMenuReportId(Long menuReportId) {
		this.menuReportId = menuReportId;
	}
	public String getMenuReportName() {
		return menuReportName;
	}
	public void setMenuReportName(String menuReportName) {
		this.menuReportName = menuReportName;
	}
}
