package com.hdw.mccable.dto;

import java.util.List;

public class PersonnelBean extends DSTPUtilityBean{
	
	private Long id;
	private String personnelCode;
	private String firstName;
	private String lastName;
	private String nickName;
	private String sex; //Male or Female
	private String prefix;
	private CompanyBean company;
	private PositionBean position;
	private PermissionGroupBean permissionGroup;
	private ContactBean contact;
	private TechnicianGroupBean technicianGroupBean;
	private AuthenticationBean authenticationBean;
	private List<InvoiceDocumentBean> invoiceDocmentBeanList; //สำหรับดูว่าเก็บเงินจากบิลไหนบ้างเพื่อคำนวณ commission
	private boolean cashier;
	private List<CountWorksheetBean> countWorksheetBeanList; //ลิสสำหรับใบงานของพนักงาน
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPersonnelCode() {
		return personnelCode;
	}
	public void setPersonnelCode(String personnelCode) {
		this.personnelCode = personnelCode;
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
	
	public CompanyBean getCompany() {
		return company;
	}
	public void setCompany(CompanyBean company) {
		this.company = company;
	}
	public PositionBean getPosition() {
		return position;
	}
	public void setPosition(PositionBean position) {
		this.position = position;
	}
	public PermissionGroupBean getPermissionGroup() {
		return permissionGroup;
	}
	public void setPermissionGroup(PermissionGroupBean permissionGroup) {
		this.permissionGroup = permissionGroup;
	}
	public ContactBean getContact() {
		return contact;
	}
	public void setContact(ContactBean contact) {
		this.contact = contact;
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
	public TechnicianGroupBean getTechnicianGroupBean() {
		return technicianGroupBean;
	}
	public void setTechnicianGroupBean(TechnicianGroupBean technicianGroupBean) {
		this.technicianGroupBean = technicianGroupBean;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public AuthenticationBean getAuthenticationBean() {
		return authenticationBean;
	}
	public void setAuthenticationBean(AuthenticationBean authenticationBean) {
		this.authenticationBean = authenticationBean;
	}
	public List<InvoiceDocumentBean> getInvoiceDocmentBeanList() {
		return invoiceDocmentBeanList;
	}
	public void setInvoiceDocmentBeanList(List<InvoiceDocumentBean> invoiceDocmentBeanList) {
		this.invoiceDocmentBeanList = invoiceDocmentBeanList;
	}
	public boolean isCashier() {
		return cashier;
	}
	public void setCashier(boolean cashier) {
		this.cashier = cashier;
	}
	public List<CountWorksheetBean> getCountWorksheetBeanList() {
		return countWorksheetBeanList;
	}
	public void setCountWorksheetBeanList(List<CountWorksheetBean> countWorksheetBeanList) {
		this.countWorksheetBeanList = countWorksheetBeanList;
	}
	
	@Override
	public String toString() {
		return "PersonnelBean [id=" + id + ", personnelCode=" + personnelCode + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", nickName=" + nickName + ", sex=" + sex + ", prefix=" + prefix
				+ ", company=" + company + ", position=" + position + ", permissionGroup=" + permissionGroup
				+ ", contact=" + contact + ", technicianGroupBean=" + technicianGroupBean + ", getCreateDate()="
				+ getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy()
				+ ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh()
				+ ", getUpdateDateTh()=" + getUpdateDateTh() + ", getCreateByTh()=" + getCreateByTh()
				+ ", getUpdateByTh()=" + getUpdateByTh() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
