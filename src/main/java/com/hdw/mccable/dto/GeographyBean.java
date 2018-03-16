package com.hdw.mccable.dto;

import java.util.List;

import com.hdw.mccable.entity.Province;

public class GeographyBean extends DSTPUtilityBean{
	
	private Long id;
	private String GEO_NAME;
	private List<Province> provinces;
	
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
