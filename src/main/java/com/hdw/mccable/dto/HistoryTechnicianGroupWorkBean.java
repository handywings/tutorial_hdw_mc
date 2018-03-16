package com.hdw.mccable.dto;

import java.util.Date;
import java.util.List;

public class HistoryTechnicianGroupWorkBean extends DSTPUtilityBean{
	
	private Long id;
	private TechnicianGroupBean technicainGroup;
	private List<PersonnelAssignBean> personnelAssigns;
	private String remarkNotSuccess;
	private String statusHistory;
	private Date assingCurrentDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TechnicianGroupBean getTechnicainGroup() {
		return technicainGroup;
	}
	public void setTechnicainGroup(TechnicianGroupBean technicainGroup) {
		this.technicainGroup = technicainGroup;
	}
	public List<PersonnelAssignBean> getPersonnelAssigns() {
		return personnelAssigns;
	}
	public void setPersonnelAssigns(List<PersonnelAssignBean> personnelAssigns) {
		this.personnelAssigns = personnelAssigns;
	}
	public String getRemarkNotSuccess() {
		return remarkNotSuccess;
	}
	public void setRemarkNotSuccess(String remarkNotSuccess) {
		this.remarkNotSuccess = remarkNotSuccess;
	}
	public String getStatusHistory() {
		return statusHistory;
	}
	public void setStatusHistory(String statusHistory) {
		this.statusHistory = statusHistory;
	}
	public Date getAssingCurrentDate() {
		return assingCurrentDate;
	}
	public void setAssingCurrentDate(Date assingCurrentDate) {
		this.assingCurrentDate = assingCurrentDate;
	} 
	
}
