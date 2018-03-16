package com.hdw.mccable.dto;

import java.util.List;

public class CompanyBean extends DSTPUtilityBean{
	
	private Long id;
	private String companyName;
	private String taxId; //Tax Identification Number
	private float vat;
	private int invCredit;
	private Long parent;
	private List<CompanyBean> subcompanyList;
	private AddressBean address;
	private ContactBean contact;
	private String logo;
	
	
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public float getVat() {
		return vat;
	}
	public void setVat(float vat) {
		this.vat = vat;
	}
	public int getInvCredit() {
		return invCredit;
	}
	public void setInvCredit(int invCredit) {
		this.invCredit = invCredit;
	}
	
	public List<CompanyBean> getSubcompanyList() {
		return subcompanyList;
	}
	public void setSubcompanyList(List<CompanyBean> subcompanyList) {
		this.subcompanyList = subcompanyList;
	}
	
	public AddressBean getAddress() {
		return address;
	}
	public void setAddress(AddressBean address) {
		this.address = address;
	}
	public ContactBean getContact() {
		return contact;
	}
	public void setContact(ContactBean contact) {
		this.contact = contact;
	}
	
	public Long getParent() {
		return parent;
	}
	public void setParent(Long parent) {
		this.parent = parent;
	}

}
