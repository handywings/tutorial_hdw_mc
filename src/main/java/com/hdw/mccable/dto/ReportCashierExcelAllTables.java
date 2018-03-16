package com.hdw.mccable.dto;

public class ReportCashierExcelAllTables {
	private String cusName; // ชื่อลูกค้า
	private String cusCode; // รหัสลูกค้า
	private String no; // บ้านเลขที่
	private String invoiceNo; // เลขที่ใบแจ้งหนี้
	private String zoneName; // เขตชื่อชุมชนส่งบิล
	private String zoneCode; // เขตรหัสชุมชนส่งบิล
	private String nearbyPlaces; // สถานที่ใกล้เคียง
	private String serviceRoundDate; // รอบบิล

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getNearbyPlaces() {
		return nearbyPlaces;
	}

	public void setNearbyPlaces(String nearbyPlaces) {
		this.nearbyPlaces = nearbyPlaces;
	}

	public String getServiceRoundDate() {
		return serviceRoundDate;
	}

	public void setServiceRoundDate(String serviceRoundDate) {
		this.serviceRoundDate = serviceRoundDate;
	}

	
}