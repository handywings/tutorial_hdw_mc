package com.hdw.mccable.dto;

import java.util.List;

public class ReportWorksheetCutBean extends ReportWorksheetMainBean{
	
	private String reporter;
	private String mobile;
	private String cancelDate;
	private String endPackageDate;
	private String cutWorkType;
//	private List<ProductItemBean> productitemList;
	private List<WorkSheetReportBeanSubTable> workSheetReportBeanSubTables;

	public String getCutWorkType() {
		return cutWorkType;
	}

	public void setCutWorkType(String cutWorkType) {
		if ("1".equals(cutWorkType)) {
			cutWorkType = "ไม่มีเวลาดู";
		} else if ("2".equals(cutWorkType)) {
			cutWorkType = "ค้างชำระ";
		} else if ("3".equals(cutWorkType)) {
			cutWorkType = "ติดอินเตอร์เน็ต";
		} else if ("4".equals(cutWorkType)) {
			cutWorkType = "ติดจานที่อื่น";
		} else if ("5".equals(cutWorkType)) {
			cutWorkType = "ย้ายบ้าน (ไปในเขตที่สัญญาณไม่ครอบคลุม)";
		} else if ("6".equals(cutWorkType)) {
			cutWorkType = "อื่นๆ";
		}
		this.cutWorkType = cutWorkType;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getEndPackageDate() {
		return endPackageDate;
	}

	public void setEndPackageDate(String endPackageDate) {
		this.endPackageDate = endPackageDate;
	}

	public List<WorkSheetReportBeanSubTable> getWorkSheetReportBeanSubTables() {
		return workSheetReportBeanSubTables;
	}

	public void setWorkSheetReportBeanSubTables(List<WorkSheetReportBeanSubTable> workSheetReportBeanSubTables) {
		this.workSheetReportBeanSubTables = workSheetReportBeanSubTables;
	}	
}