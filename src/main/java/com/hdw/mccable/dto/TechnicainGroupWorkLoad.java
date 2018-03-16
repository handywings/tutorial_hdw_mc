package com.hdw.mccable.dto;

public class TechnicainGroupWorkLoad extends DSTPUtilityBean{
	
	private TechnicianGroupBean technicianGroupBean;
	private int worksheetSize;
	
	public TechnicianGroupBean getTechnicianGroupBean() {
		return technicianGroupBean;
	}
	public void setTechnicianGroupBean(TechnicianGroupBean technicianGroupBean) {
		this.technicianGroupBean = technicianGroupBean;
	}
	public int getWorksheetSize() {
		return worksheetSize;
	}
	public void setWorksheetSize(int worksheetSize) {
		this.worksheetSize = worksheetSize;
	}
}
