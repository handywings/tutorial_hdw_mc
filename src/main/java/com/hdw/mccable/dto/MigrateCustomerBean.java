package com.hdw.mccable.dto;

import java.util.Date;

public class MigrateCustomerBean extends DSTPUtilityBean{
	private String customerCode; // *รหัสสมาชิก/รหัสลูกค้า
	private String sex; // *เพศ
	private String prefix; // คำนำหน้า
	private String firstName; // *ชื่อ
	private String lastName; // *นามสกุล
	private String customerType; // *ประเภทลูกค้า
	private String customerFeatures; // ลักษณะของลูกค้า
	private String identityNumber; // *เลขที่บัตรประชาชน
	private String career; // อาชีพ

	private String mobile; // เบอร์ติดต่อ
	private String fax; // fax
	private String email; // email
	
	private String no; // *บ้านเลขที่
	private String section; // หมู่ที่
	private String building; // อาคาร
	private String room; // เลขที่ห้อง
	private String floor; // ชั้นที่
	private String village; // หมู่บ้าน
	private String alley; // ตรอกซอย
	private String road; // ถนน
	private String province; // *จังหวัด
	private String amphur; // *อำเภอ / เขต
	private String district; // *ตำบล / แขวง
	private String postcode; // *รหัสไปรษณีย์
	private String nearbyPlaces; // สถานที่ใกล้เคียง
	private String zone; // *เขตชุมชน
	
	private String servicePackage; // PACKAGE บริการ
	
	private Date serviceApplicationDate; // วันที่สมัคร
	private String serviceApplicationType; // *ประเภทใบสมัคร
	private String serviceApplicationTypeCode;
	private Date dateOrderBill; // *ครบกำหนด
	private String billingFee; // *ค่าบริการรอบบิล
	private String deposit; // เงินประกัน/ค่ามัดจำ
	private String digitalPoint; // จำนวนจุด Digital
	private String installDigital; // วันที่ติด Digital
	private String statusDigital; // สถานะ Digital
	private String digitalPrice; // ราคา Digital
	private String analogPoint; // จำนวนจุด Analog
	private String installAnalog; // วันที่ติด Analog
	private String analogPrice;	// ราคา Analog
	
	private String totalPoint; //  จำนวนจุดทั้งหมด
	
	private String remark; // หมายเหตุ / ข้อมูลเพิ่มเติม

	private String plat; // เพลท000015
	
	private Date dateBill; // วันรอบบิล
	private String costBill; 

	private Date dateCut; // วันตัดสาย
	private String cutStatus; // 1=ปกติ, 3=ตัดสาย
	
	private Date datePayment;
	
	public Date getDatePayment() {
		return datePayment;
	}

	public void setDatePayment(Date datePayment) {
		this.datePayment = datePayment;
	}

	public String getServiceApplicationTypeCode() {
		return serviceApplicationTypeCode;
	}

	public void setServiceApplicationTypeCode(String serviceApplicationTypeCode) {
		this.serviceApplicationTypeCode = serviceApplicationTypeCode;
	}

	public String getCutStatus() {
		return cutStatus;
	}

	public void setCutStatus(String cutStatus) {
		this.cutStatus = cutStatus;
	}

	public Date getDateCut() {
		return dateCut;
	}

	public void setDateCut(Date dateCut) {
		this.dateCut = dateCut;
	}

	public Date getDateBill() {
		return dateBill;
	}

	public void setDateBill(Date dateBill) {
		this.dateBill = dateBill;
	}

	public String getCostBill() {
		return costBill;
	}

	public void setCostBill(String costBill) {
		this.costBill = costBill;
	}

	public String getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(String totalPoint) {
		this.totalPoint = totalPoint;
	}

	public String getPlat() {
		return plat;
	}

	public void setPlat(String plat) {
		this.plat = plat;
	}

	public String getStatusDigital() {
		return statusDigital;
	}

	public void setStatusDigital(String statusDigital) {
		this.statusDigital = statusDigital;
	}

	public String getInstallDigital() {
		return installDigital;
	}

	public void setInstallDigital(String installDigital) {
		this.installDigital = installDigital;
	}

	public String getInstallAnalog() {
		return installAnalog;
	}

	public void setInstallAnalog(String installAnalog) {
		this.installAnalog = installAnalog;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerFeatures() {
		return customerFeatures;
	}

	public void setCustomerFeatures(String customerFeatures) {
		this.customerFeatures = customerFeatures;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getAlley() {
		return alley;
	}

	public void setAlley(String alley) {
		this.alley = alley;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAmphur() {
		return amphur;
	}

	public void setAmphur(String amphur) {
		this.amphur = amphur;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getNearbyPlaces() {
		return nearbyPlaces;
	}

	public void setNearbyPlaces(String nearbyPlaces) {
		this.nearbyPlaces = nearbyPlaces;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public Date getServiceApplicationDate() {
		return serviceApplicationDate;
	}

	public void setServiceApplicationDate(Date serviceApplicationDate) {
		this.serviceApplicationDate = serviceApplicationDate;
	}

	public String getServiceApplicationType() {
		return serviceApplicationType;
	}

	public void setServiceApplicationType(String serviceApplicationType) {
		this.serviceApplicationType = serviceApplicationType;
	}

	public Date getDateOrderBill() {
		return dateOrderBill;
	}

	public void setDateOrderBill(Date dateOrderBill) {
		this.dateOrderBill = dateOrderBill;
	}

	public String getBillingFee() {
		return billingFee;
	}

	public void setBillingFee(String billingFee) {
		this.billingFee = billingFee;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public String getDigitalPoint() {
		return digitalPoint;
	}

	public void setDigitalPoint(String digitalPoint) {
		this.digitalPoint = digitalPoint;
	}

	public String getDigitalPrice() {
		return digitalPrice;
	}

	public void setDigitalPrice(String digitalPrice) {
		this.digitalPrice = digitalPrice;
	}

	public String getAnalogPoint() {
		return analogPoint;
	}

	public void setAnalogPoint(String analogPoint) {
		this.analogPoint = analogPoint;
	}

	public String getAnalogPrice() {
		return analogPrice;
	}

	public void setAnalogPrice(String analogPrice) {
		this.analogPrice = analogPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
