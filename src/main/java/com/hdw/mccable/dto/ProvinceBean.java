package com.hdw.mccable.dto;

import java.util.List;

public class ProvinceBean extends DSTPUtilityBean{
	
	private Long id;
	private String PROVINCE_CODE;
	private String PROVINCE_NAME;
	private List<AmphurBean> amphurBeans;
	
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
	public List<AmphurBean> getAmphurBeans() {
		return amphurBeans;
	}
	public void setAmphurBeans(List<AmphurBean> amphurBeans) {
		this.amphurBeans = amphurBeans;
	}
	
	
}
