package com.hdw.mccable.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "custCode", length=150)
	private String custCode;
	
	@Column(name = "firstName", length=200)
	private String firstName;
	
	@Column(name = "lastName", length=200)
	private String lastName;
	
	@Column(name = "identityNumber", length=13, nullable=true)
	private String identityNumber;
	
//	@Column(name = "career")
//	private String career;
	
	@Column(name = "custType", length=3)
	private String custType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate", nullable=false)
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedDate")
	private Date updatedDate;
	
	@Column(name = "createdBy", nullable=false, length=150)
	private String createdBy;
	
	@Column(name = "updatedBy", length=150)
	private String updatedBy;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade={CascadeType.ALL})
	List<HistoryUseEquipment> historyUseEquipments;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade={CascadeType.ALL})
	List<ServiceApplication> serviceApplications;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade={CascadeType.ALL})
	List<Address> addresses;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "customer", cascade={CascadeType.ALL})
	Contact contact;
	
	@Column(name = "isDeleted")
	private boolean isDeleted;
	
	@Column(name = "isActive")
	private boolean isActive;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="customerFeatureTypeId", nullable=true)
	private CustomerFeature customerFeature;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="careerId", nullable=true)
	private Career career;
	
	@Column(name = "sex", length=6, nullable=true)
	private String sex; //Male or Female
	
	@Column(name = "prefix", length=120)
	private String prefix;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<HistoryUseEquipment> getHistoryUseEquipments() {
		return historyUseEquipments;
	}

	public void setHistoryUseEquipments(List<HistoryUseEquipment> historyUseEquipments) {
		this.historyUseEquipments = historyUseEquipments;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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

	public List<ServiceApplication> getServiceApplications() {
		return serviceApplications;
	}

	public void setServiceApplications(List<ServiceApplication> serviceApplications) {
		this.serviceApplications = serviceApplications;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
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
//
//	public void setCareer(String career) {
//		this.career = career;
//	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public CustomerFeature getCustomerFeature() {
		return customerFeature;
	}

	public void setCustomerFeature(CustomerFeature customerFeature) {
		this.customerFeature = customerFeature;
	}

	public Career getCareer() {
		return career;
	}

	public void setCareer(Career career) {
		this.career = career;
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
	
}
