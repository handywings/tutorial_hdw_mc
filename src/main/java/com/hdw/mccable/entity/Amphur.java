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
@Table(name="amphur")
public class Amphur {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "AMPHUR_ID", unique=true, nullable=false)
	private Long id;
	
	@Column(name = "AMPHUR_CODE", length=4)
	private String AMPHUR_CODE;
	
	@Column(name = "AMPHUR_NAME", length=150)
	private String AMPHUR_NAME;
	
	@Column(name = "POSTCODE", length=5)
	private String POSTCODE;
	
	@Column(name = "PPGEO_ID", length=5, nullable=true)
	private Long PPGEO_ID;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PROVINCE_ID", nullable=false)
	private Province province; 
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "amphur", cascade={CascadeType.ALL})
	List<District> districts;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "amphur", cascade={CascadeType.ALL})
	List<Address> address; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAMPHUR_CODE() {
		return AMPHUR_CODE;
	}

	public void setAMPHUR_CODE(String aMPHUR_CODE) {
		AMPHUR_CODE = aMPHUR_CODE;
	}

	public String getAMPHUR_NAME() {
		return AMPHUR_NAME;
	}

	public void setAMPHUR_NAME(String aMPHUR_NAME) {
		AMPHUR_NAME = aMPHUR_NAME;
	}

	public String getPOSTCODE() {
		return POSTCODE;
	}

	public void setPOSTCODE(String pOSTCODE) {
		POSTCODE = pOSTCODE;
	}

	public Long getPPGEO_ID() {
		return PPGEO_ID;
	}

	public void setPPGEO_ID(Long pPGEO_ID) {
		PPGEO_ID = pPGEO_ID;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public List<District> getDistricts() {
		return districts;
	}

	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}
 
	 
}
