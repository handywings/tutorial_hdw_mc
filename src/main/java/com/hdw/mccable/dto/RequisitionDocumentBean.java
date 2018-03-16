package com.hdw.mccable.dto;

import java.util.List;

public class RequisitionDocumentBean extends DSTPUtilityBean{
	
	private Long id;
	private TechnicianGroupBean technicianGroup; // ผู้ขอเบิกสินค้า
	private StatusBean status; // N=ปกติ, C=ยกเลิก
	private List<RequisitionItemBean> requisitionItemList;
	private String detail;
	private PersonnelBean personnelBean; // ผู้บันทึกใบเบิกสินค้าและอุปกรณ์
	private String withdraw;  //1 charater R=เบิกเพื่อไปสำรอง, O=เบิกเพื่องานสำนักงาน, U=เบิกเพื่อปรับปรุงลดสต็อก
	private String withdrawText;
	private String remarkStatus;
	private String requisitionDocumentCode;
	private String key;
	private String daterange;
	private String withdrawValue;
	
	public String getWithdrawValue() {
		return withdrawValue;
	}
	public void setWithdrawValue(String withdrawValue) {
		this.withdrawValue = withdrawValue;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TechnicianGroupBean getTechnicianGroup() {
		return technicianGroup;
	}
	public void setTechnicianGroup(TechnicianGroupBean technicianGroup) {
		this.technicianGroup = technicianGroup;
	}
	public StatusBean getStatus() {
		return status;
	}
	public void setStatus(StatusBean status) {
		this.status = status;
	}
	public List<RequisitionItemBean> getRequisitionItemList() {
		return requisitionItemList;
	}
	public void setRequisitionItemList(List<RequisitionItemBean> requisitionItemList) {
		this.requisitionItemList = requisitionItemList;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public PersonnelBean getPersonnelBean() {
		return personnelBean;
	}
	public void setPersonnelBean(PersonnelBean personnelBean) {
		this.personnelBean = personnelBean;
	}
	public String getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(String withdraw) {
		this.withdraw = withdraw;
	}
	public String getRemarkStatus() {
		return remarkStatus;
	}
	public void setRemarkStatus(String remarkStatus) {
		this.remarkStatus = remarkStatus;
	}
	public String getRequisitionDocumentCode() {
		return requisitionDocumentCode;
	}
	public void setRequisitionDocumentCode(String requisitionDocumentCode) {
		this.requisitionDocumentCode = requisitionDocumentCode;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDaterange() {
		return daterange;
	}
	public void setDaterange(String daterange) {
		this.daterange = daterange;
	}
	
	public String getWithdrawText() {
		return withdrawText;
	}
	public void setWithdrawText(String withdrawText) {
		this.withdrawText = withdrawText;
	}
	@Override
	public String toString() {
		return "RequisitionDocumentBean [id=" + id + ", technicianGroup=" + technicianGroup + ", status=" + status
				+ ", requisitionItemList=" + requisitionItemList + ", detail=" + detail + ", personnelBean="
				+ personnelBean + ", withdraw=" + withdraw + ", remarkStatus=" + remarkStatus
				+ ", requisitionDocumentCode=" + requisitionDocumentCode + ", key=" + key + ", daterange=" + daterange
				+ ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()="
				+ getCreateBy() + ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh()
				+ ", getUpdateDateTh()=" + getUpdateDateTh() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
