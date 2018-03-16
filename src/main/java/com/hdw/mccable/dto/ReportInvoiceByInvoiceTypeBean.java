package com.hdw.mccable.dto;

public class ReportInvoiceByInvoiceTypeBean {
	private int no; // ลำดับ
	private String invoiceCode; // เลขที่บิล
	private String fullName; // ชื่อ-สกุล
	private String zoneOrCashier; // เขตชุมชน
	private String invoiceType; // ประเภทบิล
	private String createDate; // วันกาหนดชาระ 
	private String status; // สถานะชาระ
	private String vat; // ภาษี
	private String serviceCharge; // ค่าบริการ
	private String headerTable; // หัวตาราง
	private Boolean checkCashiers;
	private Boolean checkHeaderTable; // เช็ดการสร้างหัวตาราง
	
	private String noText; // Header ลำดับ
	private String invoiceCodeText; // Header เลขที่บิล
	private String fullNameText; // Header ชื่อ-สกุล
	private String zoneOrCashierText; // Header เขตชุมชน
	private String invoiceTypeText; // Header ประเภทบิล
	private String createDateText; // Header วันกาหนดชาระ 
	private String statusText; // Header สถานะชาระ
	private String vatText; // Header ภาษี
	private String serviceChargeText; // Header ค่าบริการ
	
	private String sumText; // รวมยอด
	private String sumVat; // รวมยอด ภาษี
	private String sumServiceCharge; // รวมยอด ค่าบริการ
	private Boolean checkSum;
	private Boolean checkSumFinal;
	
	private String total; // รวมค่าบริการทั้งสิ้น
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getZoneOrCashier() {
		return zoneOrCashier;
	}
	public void setZoneOrCashier(String zoneOrCashier) {
		this.zoneOrCashier = zoneOrCashier;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		//S=สำหรับติดตั้ง,R=สำหรับซ่อม,O=สำหรับตาม
		if("S".equals(invoiceType)){
			invoiceType = "ติดตั้งใหม่";
		}else if("R".equals(invoiceType)){
			invoiceType = "แจ้งซ่อม";
		}else if("O".equals(invoiceType)){
			invoiceType = "รอบบิล";
		}else {
			invoiceType = " - ";
		}
		this.invoiceType = invoiceType;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		//W=รอลูกค้าชำระ,S=ชำระแล้ว,O=เกินวันกำหนดชำระ
		if("S".equals(status)){
			status = "ชำระแล้ว";
		}else if("W".equals(status)){
			status = "รอชำระ";
		}else if("O".equals(status)){
			status = "ค้างชำระ";
		}else if("C".equals(status)){
			status = "ยกเลิก";
		}else {
			status = " - ";
		}
		this.status = status;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public String getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public String getHeaderTable() {
		return headerTable;
	}
	public void setHeaderTable(String headerTable) {
		this.headerTable = headerTable;
	}
	public Boolean getCheckHeaderTable() {
		return checkHeaderTable;
	}
	public void setCheckHeaderTable(Boolean checkHeaderTable) {
		this.checkHeaderTable = checkHeaderTable;
	}
	public String getNoText() {
		return noText;
	}
	public void setNoText(String noText) {
		this.noText = noText;
	}
	public String getInvoiceCodeText() {
		return invoiceCodeText;
	}
	public void setInvoiceCodeText(String invoiceCodeText) {
		this.invoiceCodeText = invoiceCodeText;
	}
	public String getFullNameText() {
		return fullNameText;
	}
	public void setFullNameText(String fullNameText) {
		this.fullNameText = fullNameText;
	}
	public String getZoneOrCashierText() {
		return zoneOrCashierText;
	}
	public void setZoneOrCashierText(String zoneOrCashierText) {
		this.zoneOrCashierText = zoneOrCashierText;
	}
	public String getInvoiceTypeText() {
		return invoiceTypeText;
	}
	public void setInvoiceTypeText(String invoiceTypeText) {
		this.invoiceTypeText = invoiceTypeText;
	}
	public String getCreateDateText() {
		return createDateText;
	}
	public void setCreateDateText(String createDateText) {
		this.createDateText = createDateText;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public String getVatText() {
		return vatText;
	}
	public void setVatText(String vatText) {
		this.vatText = vatText;
	}
	public String getServiceChargeText() {
		return serviceChargeText;
	}
	public void setServiceChargeText(String serviceChargeText) {
		this.serviceChargeText = serviceChargeText;
	}
	public String getSumText() {
		return sumText;
	}
	public void setSumText(String sumText) {
		this.sumText = sumText;
	}
	public String getSumVat() {
		return sumVat;
	}
	public void setSumVat(String sumVat) {
		this.sumVat = sumVat;
	}
	public String getSumServiceCharge() {
		return sumServiceCharge;
	}
	public void setSumServiceCharge(String sumServiceCharge) {
		this.sumServiceCharge = sumServiceCharge;
	}
	public Boolean getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(Boolean checkSum) {
		this.checkSum = checkSum;
	}
	public Boolean getCheckSumFinal() {
		return checkSumFinal;
	}
	public void setCheckSumFinal(Boolean checkSumFinal) {
		this.checkSumFinal = checkSumFinal;
	}
	public Boolean getCheckCashiers() {
		return checkCashiers;
	}
	public void setCheckCashiers(Boolean checkCashiers) {
		this.checkCashiers = checkCashiers;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}

}
