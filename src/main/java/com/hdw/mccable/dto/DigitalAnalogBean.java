package com.hdw.mccable.dto;


import com.hdw.mccable.entity.ServiceApplication;

public class DigitalAnalogBean extends DSTPUtilityBean{
	private Long serviceApplicationId;
	private ServiceApplication serviceApplication; // เลขที่ใบสมัคร
	private String serviceApplicationNo; // เลขที่ใบสมัคร
	private String typePoints; // ประเภทจุด
	private String numberPoints; // จำนวนจุด
	private String installDate; // วันที่ติดตั้ง
	private String deposit; // ค่ามัดจำ
	private String status; // สถานะ : ยืม, มัดจำ, ขายขาด
	
	public DigitalAnalogBean(ServiceApplication serviceApplication, String serviceApplicationNo, String typePoints, String numberPoints, String installDate,
			String deposit, String status) {
		super();
		this.serviceApplication = serviceApplication;
		this.serviceApplicationNo = serviceApplicationNo;
		this.typePoints = typePoints;
		this.numberPoints = numberPoints;
		this.installDate = installDate;
		this.deposit = deposit;
		this.status = status;
	}
	
	public DigitalAnalogBean() {
		// TODO Auto-generated constructor stub
	}


	public Long getServiceApplicationId() {
		return serviceApplicationId;
	}

	public void setServiceApplicationId(Long serviceApplicationId) {
		this.serviceApplicationId = serviceApplicationId;
	}

	public ServiceApplication getServiceApplication() {
		return serviceApplication;
	}

	public void setServiceApplication(ServiceApplication serviceApplication) {
		this.serviceApplication = serviceApplication;
	}

	public String getServiceApplicationNo() {
		return serviceApplicationNo;
	}
	public void setServiceApplicationNo(String serviceApplicationNo) {
		this.serviceApplicationNo = serviceApplicationNo;
	}
	public String getTypePoints() {
		return typePoints;
	}
	public void setTypePoints(String typePoints) {
		this.typePoints = typePoints;
	}
	public String getNumberPoints() {
		return numberPoints;
	}
	public void setNumberPoints(String numberPoints) {
		this.numberPoints = numberPoints;
	}
	public String getInstallDate() {
		return installDate;
	}
	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "DigitalAnalogBean [serviceApplicationNo=" + serviceApplicationNo + ", typePoints=" + typePoints
				+ ", numberPoints=" + numberPoints + ", installDate=" + installDate + ", deposit=" + deposit
				+ ", status=" + status + "]";
	}
	
}
