package com.hdw.mccable.dto;

import java.util.List;

public class RequisitionDocumentBeanReport {
	
	private String requisitionDocumentCode;
	private String createDateTh;
	private String withdraw;
	private String detail;
	private String personnelCode;
	private String personnelName;
	private String personnelByName;
	private String personnelByCode;
	private List<RequisitionDocumentItemBeanReport> requisitionItemList;
	public String getRequisitionDocumentCode() {
		return requisitionDocumentCode;
	}
	public void setRequisitionDocumentCode(String requisitionDocumentCode) {
		this.requisitionDocumentCode = requisitionDocumentCode;
	}
	public String getCreateDateTh() {
		return createDateTh;
	}
	public void setCreateDateTh(String createDateTh) {
		this.createDateTh = createDateTh;
	}
	public String getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(String withdraw) {
		this.withdraw = withdraw;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getPersonnelCode() {
		return personnelCode;
	}
	public void setPersonnelCode(String personnelCode) {
		this.personnelCode = personnelCode;
	}
	public String getPersonnelName() {
		return personnelName;
	}
	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}
	public String getPersonnelByName() {
		return personnelByName;
	}
	public void setPersonnelByName(String personnelByName) {
		this.personnelByName = personnelByName;
	}
	public String getPersonnelByCode() {
		return personnelByCode;
	}
	public void setPersonnelByCode(String personnelByCode) {
		this.personnelByCode = personnelByCode;
	}
	public List<RequisitionDocumentItemBeanReport> getRequisitionItemList() {
		return requisitionItemList;
	}
	public void setRequisitionItemList(List<RequisitionDocumentItemBeanReport> requisitionItemList) {
		this.requisitionItemList = requisitionItemList;
	}
	@Override
	public String toString() {
		return "RequisitionDocumentBeanReport [requisitionDocumentCode=" + requisitionDocumentCode + ", createDateTh="
				+ createDateTh + ", withdraw=" + withdraw + ", detail=" + detail + ", personnelCode=" + personnelCode
				+ ", personnelName=" + personnelName + ", personnelByName=" + personnelByName + ", personnelByCode="
				+ personnelByCode + ", requisitionItemList=" + requisitionItemList + "]";
	}
	
}
