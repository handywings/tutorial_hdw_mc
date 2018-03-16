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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="company")
public class Company {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(name = "companyName", unique = true, nullable=false, length=150)
	private String companyName;
	
	@Column(name = "taxId", unique = true, nullable=false, length=13)
	private String taxId;
	
	@Column(name = "vat", nullable=false)
	private float vat;
	
	@Column(name = "invCredit", nullable=false)
	private int invCredit;
	
	@Column(name = "parent", nullable=false)
	private Long parent;
	
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
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL)
	private Contact contact;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL)
	private Address address;

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade={CascadeType.ALL})
//	List<Position> positions;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade={CascadeType.ALL})
	List<Personnel> personnels;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade={CascadeType.ALL})
	List<Stock> stocks;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL)
	private List<ServicePackage> servicePackages;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade={CascadeType.ALL})
	List<ServiceApplication> serviceApplications;
	
	@Column(name = "isDeleted")
	private boolean isDeleted;
	
	public List<ServiceApplication> getServiceApplications() {
		return serviceApplications;
	}

	public void setServiceApplications(List<ServiceApplication> serviceApplications) {
		this.serviceApplications = serviceApplications;
	}

	@Column(name = "logo", columnDefinition = "TEXT")
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

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
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

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

//	public List<Position> getPositions() {
//		return positions;
//	}
//
//	public void setPositions(List<Position> positions) {
//		this.positions = positions;
//	}

	public List<Personnel> getPersonnels() {
		return personnels;
	}

	public void setPersonnels(List<Personnel> personnels) {
		this.personnels = personnels;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public List<ServicePackage> getServicePackages() {
		return servicePackages;
	}

	public void setServicePackages(List<ServicePackage> servicePackages) {
		this.servicePackages = servicePackages;
	}

	 
}
