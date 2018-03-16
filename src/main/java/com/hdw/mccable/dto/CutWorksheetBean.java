package com.hdw.mccable.dto;

import java.util.Date;

public class CutWorksheetBean extends WorksheetBean {
	
	private Long id;
	private String reporter;
	private String mobile;
	private int cutWorkType;
	private boolean returnEquipment; // คืนอุปกรณ์
	private boolean submitCA; // คืนอุปกรณ์
	private String cancelDate; //วันที่แจ้งยกเลิก
	private String endPackageDate; //วันที่สิ้นสุดการใช้บริการ Package
	private float specialDiscount; //ส่วนลดพิเศษ
	private boolean isConfirmCA;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public int getCutWorkType() {
		return cutWorkType;
	}
	public void setCutWorkType(int cutWorkType) {
		this.cutWorkType = cutWorkType;
	}
	public boolean isReturnEquipment() {
		return returnEquipment;
	}
	public void setReturnEquipment(boolean returnEquipment) {
		this.returnEquipment = returnEquipment;
	}
	public boolean isSubmitCA() {
		return submitCA;
	}
	public void setSubmitCA(boolean submitCA) {
		this.submitCA = submitCA;
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
	public float getSpecialDiscount() {
		return specialDiscount;
	}
	public void setSpecialDiscount(float specialDiscount) {
		this.specialDiscount = specialDiscount;
	}
	@Override
	public String toString() {
		return "CutWorksheetBean [id=" + id + ", reporter=" + reporter + ", mobile=" + mobile + ", cutWorkType="
				+ cutWorkType + ", returnEquipment=" + returnEquipment + ", submitCA=" + submitCA + ", cancelDate="
				+ cancelDate + ", endPackageDate=" + endPackageDate + ", specialDiscount=" + specialDiscount
				+ ", getServiceApplication()=" + getServiceApplication() + ", getProductItemList()="
				+ getProductItemList() + ", getJobscheduleList()=" + getJobscheduleList() + ", getStatus()="
				+ getStatus() + ", isDelete()=" + isDelete() + ", getRemark()=" + getRemark() + ", getWorkSheetCode()="
				+ getWorkSheetCode() + ", getHistoryTechnicianGroupWorkBeans()=" + getHistoryTechnicianGroupWorkBeans()
				+ ", getRemarkNotSuccess()=" + getRemarkNotSuccess() + ", getProductItemWorksheetBeanList()="
				+ getProductItemWorksheetBeanList() + ", getWorkSheetType()=" + getWorkSheetType()
				+ ", getWorkSheetTypeText()=" + getWorkSheetTypeText() + ", getIdWorksheetParent()="
				+ getIdWorksheetParent() + ", getAddPointWorksheetBean()=" + getAddPointWorksheetBean()
				+ ", getAddSetTopBoxWorksheetBean()=" + getAddSetTopBoxWorksheetBean() + ", getBorrowWorksheetBean()="
				+ getBorrowWorksheetBean() + ", getConnectWorksheetBean()=" + getConnectWorksheetBean()
				+ ", getCutWorksheetBean()=" + getCutWorksheetBean() + ", getMovePointWorksheetBean()="
				+ getMovePointWorksheetBean() + ", getMoveWorksheetBean()=" + getMoveWorksheetBean()
				+ ", getReducePointWorksheetBean()=" + getReducePointWorksheetBean()
				+ ", getRepairConnectionWorksheetBean()=" + getRepairConnectionWorksheetBean()
				+ ", getSetupWorksheetBean()=" + getSetupWorksheetBean() + ", getTuneWorksheetBean()="
				+ getTuneWorksheetBean() + ", getHistoryTechnicianGroupWorkBeanSize()="
				+ getHistoryTechnicianGroupWorkBeanSize() + ", getSubWorksheetBeanList()=" + getSubWorksheetBeanList()
				+ ", getCurrentDateAssignText()=" + getCurrentDateAssignText() + ", getAssignDateTh()="
				+ getAssignDateTh() + ", getDateOrderBillTh()=" + getDateOrderBillTh() + ", getCreateDate()="
				+ getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy()
				+ ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh()
				+ ", getUpdateDateTh()=" + getUpdateDateTh() + ", getCreateByTh()=" + getCreateByTh()
				+ ", getUpdateByTh()=" + getUpdateByTh() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	public boolean isConfirmCA() {
		return isConfirmCA;
	}
	public void setConfirmCA(boolean isConfirmCA) {
		this.isConfirmCA = isConfirmCA;
	}
	
}
