package com.hdw.mccable.dto;

import java.util.List;

public class CustomerBean extends DSTPUtilityBean{
	
	private Long id;
	private String custCode; // รหัสลูกค้า CUST-00000X 
	private String firstName;
	private String lastName;
	private String identityNumber;
	//private String career;
	private List<AddressBean> addressList;
	private ContactBean contact;
	private CustomerTypeBean customerType;
	private boolean active;
	private List<ServicePackageBean> servicePackageList;
	private List<ServiceApplicationBean> serviceApplicationList;
	private String custTypeReal;
	private CustomerFeatureBean customerFeatureBean;
	private CareerBean careerBean;
	private String sex; //Male or Female
	private String prefix;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
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
	public String getIdentityNumber() {
		return identityNumber;
	}
	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}
//	public String getCareer() {
//		return career;
//	}
//	public void setCareer(String career) {
//		this.career = career;
//	}
	public List<AddressBean> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<AddressBean> addressList) {
		this.addressList = addressList;
	}
	public ContactBean getContact() {
		return contact;
	}
	public void setContact(ContactBean contact) {
		this.contact = contact;
	}
	public CustomerTypeBean getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerTypeBean customerType) {
		this.customerType = customerType;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public List<ServicePackageBean> getServicePackageList() {
		return servicePackageList;
	}
	public void setServicePackageList(List<ServicePackageBean> servicePackageList) {
		this.servicePackageList = servicePackageList;
	}
	public List<ServiceApplicationBean> getServiceApplicationList() {
		return serviceApplicationList;
	}
	public void setServiceApplicationList(List<ServiceApplicationBean> serviceApplicationList) {
		this.serviceApplicationList = serviceApplicationList;
	}
	public String getCustTypeReal() {
		return custTypeReal;
	}
	public void setCustTypeReal(String custTypeReal) {
		this.custTypeReal = custTypeReal;
	}
	public CustomerFeatureBean getCustomerFeatureBean() {
		return customerFeatureBean;
	}
	public void setCustomerFeatureBean(CustomerFeatureBean customerFeatureBean) {
		this.customerFeatureBean = customerFeatureBean;
	}
	public CareerBean getCareerBean() {
		return careerBean;
	}
	public void setCareerBean(CareerBean careerBean) {
		this.careerBean = careerBean;
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
	@Override
	public String toString() {
		return "CustomerBean [id=" + id + ", custCode=" + custCode + ", firstName=" + firstName + ", lastName="
				+ lastName + ", identityNumber=" + identityNumber + ", addressList=" + addressList + ", contact="
				+ contact + ", customerType=" + customerType + ", active=" + active + ", servicePackageList="
				+ servicePackageList + ", serviceApplicationList=" + serviceApplicationList + ", custTypeReal="
				+ custTypeReal + ", customerFeatureBean=" + customerFeatureBean + ", careerBean=" + careerBean
				+ ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()="
				+ getCreateBy() + ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh()
				+ ", getUpdateDateTh()=" + getUpdateDateTh() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
}
