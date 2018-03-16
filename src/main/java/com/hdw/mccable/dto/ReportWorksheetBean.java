package com.hdw.mccable.dto;

public class ReportWorksheetBean {
	private int no; // ลำดับ
	private String workSheetCode; // รหัสใบงาน
	private String custName; // ชื่อ-สกุลลูกค้า
	private String tel; // โทรศัพท์
	private String workSheetType; // ประเภทใบงาน
	private String zone; // เขตชุมชน
	private String personnelName; // ผู้รับมอบหมาย
	private String workDate; // วันปฏิบัติงาน
	private String status; // สถานะ

	private String noText; // Header ลำดับ
	private String workSheetCodeText; // Header รหัสใบงาน
	private String custNameText; // Header ชื่อ-สกุลลูกค้า
	private String telText; // Header โทรศัพท์
	private String workSheetTypeText; // Header ประเภทใบงาน 
	private String zoneText; // Header เขตชุมชน
	private String personnelNameText; // Header ผู้รับมอบหมาย
	private String workDateText; // Header วันปฏิบัติงาน
	private String statusText; // สถานะ
	
	private String groupData; // ข้อมูลด้านบนตาราง
	
	private Boolean checkGroupData; // เช็คข้อมูลด้านบนตาราง
	private Boolean checkHeaderTable; // เช็ดการสร้างหัวตาราง
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getWorkSheetCode() {
		return workSheetCode;
	}
	public void setWorkSheetCode(String workSheetCode) {
		this.workSheetCode = workSheetCode;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getWorkSheetType() {
		return workSheetType;
	}
	public void setWorkSheetType(String workSheetType) {
		if("C_S".equals(workSheetType)){
			workSheetType = "ติดตั้งใหม่";
		}else if("C_AP".equals(workSheetType)){
			workSheetType = "เสริมจุดบริการ";
		}else if("C_C".equals(workSheetType)){
			workSheetType = "การจั้มสาย";
		}else if("C_TTV".equals(workSheetType)){
			workSheetType = "การจูน TV";
		}else if("C_RC".equals(workSheetType)){
			workSheetType = "การซ่อมสัญญาณ";
		}else if("C_ASTB".equals(workSheetType)){
			workSheetType = "เพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี";
		}else if("C_MP".equals(workSheetType)){
			workSheetType = "การย้ายจุด";
		}else if("C_RP".equals(workSheetType)){
			workSheetType = "การลดจุด";
		}else if("C_CU".equals(workSheetType)){
			workSheetType = "การตัดสาย";
		}else if("C_M".equals(workSheetType)){
			workSheetType = "การย้ายสาย";
		}else if("C_B".equals(workSheetType)){
			workSheetType = "ยืมอุปกรณ์รับสัญญาณเคเบิลทีวี";
		}
		
		this.workSheetType = workSheetType;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getPersonnelName() {
		return personnelName;
	}
	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		
		if("W".equals(status)){
			status = "รอมอบหมายงาน";
		}else if("R".equals(status)){
			status = "อยู่ระหว่างดำเนินงาน";
		}else if("H".equals(status)){
			status = "งานคงค้าง";
		}else if("S".equals(status)){
			status = "เสร็จสมบูรณ์";
		}else if("D".equals(status)){
			status = "งานยกเลิก";
		}
		
		this.status = status;
	}
	public String getNoText() {
		return noText;
	}
	public void setNoText(String noText) {
		this.noText = noText;
	}
	public String getWorkSheetCodeText() {
		return workSheetCodeText;
	}
	public void setWorkSheetCodeText(String workSheetCodeText) {
		this.workSheetCodeText = workSheetCodeText;
	}
	public String getCustNameText() {
		return custNameText;
	}
	public void setCustNameText(String custNameText) {
		this.custNameText = custNameText;
	}
	public String getTelText() {
		return telText;
	}
	public void setTelText(String telText) {
		this.telText = telText;
	}
	public String getWorkSheetTypeText() {
		return workSheetTypeText;
	}
	public void setWorkSheetTypeText(String workSheetTypeText) {
		this.workSheetTypeText = workSheetTypeText;
	}
	public String getZoneText() {
		return zoneText;
	}
	public void setZoneText(String zoneText) {
		this.zoneText = zoneText;
	}
	public String getPersonnelNameText() {
		return personnelNameText;
	}
	public void setPersonnelNameText(String personnelNameText) {
		this.personnelNameText = personnelNameText;
	}
	public String getWorkDateText() {
		return workDateText;
	}
	public void setWorkDateText(String workDateText) {
		this.workDateText = workDateText;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public String getGroupData() {
		return groupData;
	}
	public void setGroupData(String groupData) {
		this.groupData = groupData;
	}
	public Boolean getCheckGroupData() {
		return checkGroupData;
	}
	public void setCheckGroupData(Boolean checkGroupData) {
		this.checkGroupData = checkGroupData;
	}
	public Boolean getCheckHeaderTable() {
		return checkHeaderTable;
	}
	public void setCheckHeaderTable(Boolean checkHeaderTable) {
		this.checkHeaderTable = checkHeaderTable;
	}
	
	
}
