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
@Table(name="district")

public class District {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "DISTRICT_ID", unique=true, nullable=false)
	private Long id;
	
	@Column(name = "DISTRICT_CODE", length=6)
	private String DISTRICT_CODE;
	
	@Column(name = "DISTRICT_NAME", length=150)
	private String DISTRICT_NAME;
	
	@Column(name = "PROVINCE_ID", length=5, nullable=true)
	private Long PROVINCE_ID;
	
	@Column(name = "GEO_ID", length=5, nullable=true)
	private Long GEO_ID;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="AMPHUR_ID", nullable=false)
	private Amphur amphur;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "district", cascade={CascadeType.ALL})
	List<Address> address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDISTRICT_CODE() {
		return DISTRICT_CODE;
	}

	public void setDISTRICT_CODE(String dISTRICT_CODE) {
		DISTRICT_CODE = dISTRICT_CODE;
	}

	public String getDISTRICT_NAME() {
		return DISTRICT_NAME;
	}

	public void setDISTRICT_NAME(String dISTRICT_NAME) {
		DISTRICT_NAME = dISTRICT_NAME;
	}

	public Long getPROVINCE_ID() {
		return PROVINCE_ID;
	}

	public void setPROVINCE_ID(Long pROVINCE_ID) {
		PROVINCE_ID = pROVINCE_ID;
	}

	public Long getGEO_ID() {
		return GEO_ID;
	}

	public void setGEO_ID(Long gEO_ID) {
		GEO_ID = gEO_ID;
	}

	public Amphur getAmphur() {
		return amphur;
	}

	public void setAmphur(Amphur amphur) {
		this.amphur = amphur;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}
	
}
