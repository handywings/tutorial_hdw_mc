package com.hdw.mccable.dto;

import java.util.List;

public class ReportWorksheetMainBean {
	// -- ส่วนหัว -- \\
	private String barCode;
	private String workSheetName; // ชื่อใบงาน
	private String workSheetCode; // รหัสใบงาน
	private String workDate; // วันที่ปฏิบัติงาน
	private String recipient; // ผู้รับเรื่อง
	private String responsiblePerson; // ผู้รับผิดชอบ
	private String jobDetails; // รายละเอียดงาน
	// -- ส่วนหัว -- \\
	
	// -- ส่วนข้อมูลลูกค้า -- \\
	private String customerName; // ชื่อลูกค้า
	private String customerType; // ประเภทลูกค้า
	private String place; // สถานที่
	private String nearbyPlaces; // สถานที่ใกล้เคียง
	private String telephone; // หมายเลขโทรศัพท์
	
	private String serviceContract; // สัญญาใช้บริการ
	private String serviceDate;// วันที่ใช้บริการ
	private String status;// สถานะ
	private String numberPoints;// จำนวนจุด
	// -- ส่วนข้อมูลลูกค้า -- \\
	
	// -- รอบบิลค้างชำระ -- \\
	private String billCycleText;// รอบบิลค้างชำระ
	private List<ReportBilUnpaidBean> reportBilUnpaidBeanList;// รายการรอบบิลค้างชำระ
	// -- รอบบิลค้างชำระ -- \\
	
	// -- รายการวัสดุอุปกรณ์ที่ใช้ -- \\
	private String materialsUsedText; // รายการวัสดุอุปกรณ์ที่ใช้
	private List<ReportMaterialsUsedBean> reportMaterialsUsedBeanList;// รายการวัสดุอุปกรณ์ที่ใช้
	// -- รายการวัสดุอุปกรณ์ที่ใช้ -- \\
	
	// -- ทีมพนักงานช่างปฏิบัติงาน -- \\
	private String TechnicianGroupText; // ทีมพนักงานช่างปฏิบัติงาน
	private List<ReportTechnicianGroupBean> reportTechnicianGroupBeanList;// รายการทีมพนักงานช่างปฏิบัติงาน
	// -- ทีมพนักงานช่างปฏิบัติงาน -- \\
	
	private String line; // เส้นด้วยใต้ของ header

	private String personnelRecords; // เจ้าหน้าที่บันทึกข้อมูล
	
	private String technician; // เจ้าหน้าที่ปฏิบัติการ
	
	private String printDate ;// พิมพ์เมื่อ
	
	private String pathLogo;

	public String getWorkSheetName() {
		return workSheetName;
	}

	public void setWorkSheetName(String workSheetName) {
		if("C_S".equals(workSheetName)){
			workSheetName = "ใบงานติดตั้ง";
		}else if("C_AP".equals(workSheetName)){
			workSheetName = "ใบงานเสริมจุดบริการ";
		}else if("C_C".equals(workSheetName)){
			workSheetName = "ใบงานการจั้มสาย";
		}else if("C_TTV".equals(workSheetName)){
			workSheetName = "ใบงานการจูน TV";
		}else if("C_RC".equals(workSheetName)){
			workSheetName = "ใบงานการซ่อมสัญญาณ";
		}else if("C_ASTB".equals(workSheetName)){
			workSheetName = "ใบงานเพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี";
		}else if("C_MP".equals(workSheetName)){
			workSheetName = "ใบงานการย้ายจุด";
		}else if("C_RP".equals(workSheetName)){
			workSheetName = "ใบงานการลดจุด";
		}else if("C_CU".equals(workSheetName)){
			workSheetName = "ใบงานการตัดสาย";
		}else if("C_M".equals(workSheetName)){
			workSheetName = "ใบงานการย้ายสาย";
		}else if("C_B".equals(workSheetName)){
			workSheetName = "ใบงานยืมอุปกรณ์รับสัญญาณเคเบิลทีวี";
		}else if("I_AP".equals(workSheetName)){
			workSheetName = "วิเคราะห์ปัญหา";
		}
		this.workSheetName = workSheetName;
	}

	public String getJobDetails() {
		return jobDetails;
	}

	public void setJobDetails(String jobDetails) {
		this.jobDetails = jobDetails;
	}

	public String getWorkSheetCode() {
		return workSheetCode;
	}

	public void setWorkSheetCode(String workSheetCode) {
		this.workSheetCode = workSheetCode;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		if("I".equals(customerType)){
			customerType = "บุคคลธรรดา";
		}else if("C".equals(customerType)){
			customerType = "นิติบุคคล";
		}else{
			customerType = " - ";
		}
		this.customerType = customerType;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getNearbyPlaces() {
		return nearbyPlaces;
	}

	public void setNearbyPlaces(String nearbyPlaces) {
		this.nearbyPlaces = nearbyPlaces;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getServiceContract() {
		return serviceContract;
	}

	public void setServiceContract(String serviceContract) {
		this.serviceContract = serviceContract;
	}

	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if("D".equals(status)){
			status = "แบบร่าง";
		}else if("H".equals(status)){
			status = "รอมอบหมายงาน";
		}else if("A".equals(status)){
			status = "ใช้งานปกติ";
		}else if("I".equals(status)){
			status = "ยกเลิกการใช้บริการ";
		}else if("W".equals(status)){
			status = "ระหว่างการติดตั้ง";
		}else{
			status = " - ";
		}
		this.status = status;
	}

	public String getNumberPoints() {
		return numberPoints;
	}

	public void setNumberPoints(String numberPoints) {
		this.numberPoints = numberPoints;
	}

	public String getBillCycleText() {
		return billCycleText;
	}

	public void setBillCycleText(String billCycleText) {
		this.billCycleText = billCycleText;
	}

	public List<ReportBilUnpaidBean> getReportBilUnpaidBeanList() {
		return reportBilUnpaidBeanList;
	}

	public void setReportBilUnpaidBeanList(List<ReportBilUnpaidBean> reportBilUnpaidBeanList) {
		this.reportBilUnpaidBeanList = reportBilUnpaidBeanList;
	}

	public String getMaterialsUsedText() {
		return materialsUsedText;
	}

	public void setMaterialsUsedText(String materialsUsedText) {
		this.materialsUsedText = materialsUsedText;
	}

	public List<ReportMaterialsUsedBean> getReportMaterialsUsedBeanList() {
		return reportMaterialsUsedBeanList;
	}

	public void setReportMaterialsUsedBeanList(List<ReportMaterialsUsedBean> reportMaterialsUsedBeanList) {
		this.reportMaterialsUsedBeanList = reportMaterialsUsedBeanList;
	}

	public String getTechnicianGroupText() {
		return TechnicianGroupText;
	}

	public void setTechnicianGroupText(String technicianGroupText) {
		TechnicianGroupText = technicianGroupText;
	}

	public List<ReportTechnicianGroupBean> getReportTechnicianGroupBeanList() {
		return reportTechnicianGroupBeanList;
	}

	public void setReportTechnicianGroupBeanList(List<ReportTechnicianGroupBean> reportTechnicianGroupBeanList) {
		this.reportTechnicianGroupBeanList = reportTechnicianGroupBeanList;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		if("C_S".equals(line)){
			line = "ข้อมูลการติดตั้ง";
		}else if("C_AP".equals(line)){
			line = "ข้อมูลการเสริมจุดบริการ";
		}else if("C_C".equals(line)){
			line = "ข้อมูลการจั้มสาย";
		}else if("C_TTV".equals(line)){
			line = "ข้อมูลการจูน TV";
		}else if("C_RC".equals(line)){
			line = "ข้อมูลการซ่อมสัญญาณ";
		}else if("C_ASTB".equals(line)){
			line = "ข้อมูลการเพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี";
		}else if("C_MP".equals(line)){
			line = "ข้อมูลการย้ายจุด";
		}else if("C_RP".equals(line)){
			line = "ข้อมูลการลดจุด";
		}else if("C_CU".equals(line)){
			line = "ข้อมูลการตัดสาย";
		}else if("C_M".equals(line)){
			line = "ข้อมูลการย้ายสาย";
		}else if("C_B".equals(line)){
			line = "ข้อมูลการยืมอุปกรณ์รับสัญญาณเคเบิลทีวี";
		}else if("I_AP".equals(line)){
			line = "วิเคราะห์ปัญหา";
		}
		this.line = line;
	}

	public String getPersonnelRecords() {
		return personnelRecords;
	}

	public void setPersonnelRecords(String personnelRecords) {
		this.personnelRecords = personnelRecords;
	}

	public String getTechnician() {
		return technician;
	}

	public void setTechnician(String technician) {
		this.technician = technician;
	}

	public String getPrintDate() {
		return printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public String getPathLogo() {
		return pathLogo;
	}

	public void setPathLogo(String pathLogo) {
		this.pathLogo = pathLogo;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
}
