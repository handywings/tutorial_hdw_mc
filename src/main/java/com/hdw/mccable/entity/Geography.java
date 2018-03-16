package com.hdw.mccable.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="geography")
public class Geography {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "GEO_ID", unique=true, nullable=false)
	private Long id;
	
	@Column(name = "GEO_NAME")
	private String GEO_NAME;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "geography", cascade={CascadeType.ALL})
	List<Province> provinces;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGEO_NAME() {
		return GEO_NAME;
	}

	public void setGEO_NAME(String gEO_NAME) {
		GEO_NAME = gEO_NAME;
	}

	public List<Province> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<Province> provinces) {
		this.provinces = provinces;
	}
	
}
