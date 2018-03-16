package com.hdw.mccable.dto;

import java.util.List;

public class RepairConnectionWorksheetBean extends WorksheetBean {
	private Long id;
	private String problemDescription;
	private List<RepairMatchItemBean> repairMatchItemBeanList;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}

	public List<RepairMatchItemBean> getRepairMatchItemBeanList() {
		return repairMatchItemBeanList;
	}

	public void setRepairMatchItemBeanList(List<RepairMatchItemBean> repairMatchItemBeanList) {
		this.repairMatchItemBeanList = repairMatchItemBeanList;
	}
	 
}
