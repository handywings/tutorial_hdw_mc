package com.hdw.mccable.dto;

public class ReportWorksheetTuneBean extends ReportWorksheetMainBean{
	private String tuneType;

	public String getTuneType() {
		return tuneType;
	}

	public void setTuneType(String tuneType) {
		if ("1".equals(tuneType)) {
			tuneType = "โทรทัศน์เครื่องใหม่";
		} else if ("2".equals(tuneType)) {
			tuneType = "ช่องสัญญาณเคลื่อน หรือ ช่องสัญญาณหาย ";
		} else if ("3".equals(tuneType)) {
			tuneType = "เพิ่มช่องสัญญาณ";
		}
		this.tuneType = tuneType;
	}
	
}