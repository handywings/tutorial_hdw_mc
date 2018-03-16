package com.hdw.mccable.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DistrictBean extends DSTPUtilityBean{
	
	private Long id;
	private String DISTRICT_CODE;
	private String DISTRICT_NAME;
	private Long amphur_id;
	private AmphurBean amphurBean;
	private String ZIP_CODE;
	
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
	
	@JsonProperty("DISTRICT_NAME")
	public String getDISTRICT_NAME() {
		return DISTRICT_NAME;
	}
	public void setDISTRICT_NAME(String dISTRICT_NAME) {
		DISTRICT_NAME = dISTRICT_NAME;
	}
	public Long getAmphur_id() {
		return amphur_id;
	}
	public void setAmphur_id(Long amphur_id) {
		this.amphur_id = amphur_id;
	}
	public AmphurBean getAmphurBean() {
		return amphurBean;
	}
	public void setAmphurBean(AmphurBean amphurBean) {
		this.amphurBean = amphurBean;
	}
	public String getZIP_CODE() {
		return ZIP_CODE;
	}
	public void setZIP_CODE(String zIP_CODE) {
		ZIP_CODE = zIP_CODE;
	}
	
}
