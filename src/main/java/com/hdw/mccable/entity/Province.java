package com.hdw.mccable.entity;

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
import javax.persistence.Table;

@Entity
@Table(name="province")
public class Province {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PROVINCE_ID", unique=true, nullable=false)
	private Long id;
	
	@Column(name = "PROVINCE_CODE", length=2)
	private String PROVINCE_CODE;
	
	@Column(name = "PROVINCE_NAME", length=150)
	private String PROVINCE_NAME;
	
	@Column(name = "PROVINCE_NAME_ENG", length=150)
	private String PROVINCE_NAME_ENG;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="GEO_ID", nullable=false)
	private Geography geography; 
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "province", cascade={CascadeType.ALL})
	List<Amphur> amphurs;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "province", cascade={CascadeType.ALL})
	List<Address> address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPROVINCE_CODE() {
		return PROVINCE_CODE;
	}

	public void setPROVINCE_CODE(String pROVINCE_CODE) {
		PROVINCE_CODE = pROVINCE_CODE;
	}

	public String getPROVINCE_NAME() {
		return PROVINCE_NAME;
	}

	public void setPROVINCE_NAME(String pROVINCE_NAME) {
		PROVINCE_NAME = pROVINCE_NAME;
	}

	public String getPROVINCE_NAME_ENG() {
		return PROVINCE_NAME_ENG;
	}

	public void setPROVINCE_NAME_ENG(String pROVINCE_NAME_ENG) {
		PROVINCE_NAME_ENG = pROVINCE_NAME_ENG;
	}

	public Geography getGeography() {
		return geography;
	}

	public void setGeography(Geography geography) {
		this.geography = geography;
	}

	public List<Amphur> getAmphurs() {
		return amphurs;
	}

	public void setAmphurs(List<Amphur> amphurs) {
		this.amphurs = amphurs;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}
	 
}
