package com.hdw.mccable.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AmphurBean extends DSTPUtilityBean{
	
	private Long id;
	private String AMPHUR_CODE;
	private String AMPHUR_NAME;
	private String POSTCODE;
	private List<DistrictBean> districtBeans;
	
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
	@JsonProperty("AMPHUR_NAME")
	public String getAMPHUR_NAME() {
		return AMPHUR_NAME;
	}
	public void setAMPHUR_NAME(String aMPHUR_NAME) {
		AMPHUR_NAME = aMPHUR_NAME;
	}
	@JsonProperty("POSTCODE")
	public String getPOSTCODE() {
		return POSTCODE;
	}
	public void setPOSTCODE(String pOSTCODE) {
		POSTCODE = pOSTCODE;
	}
	public List<DistrictBean> getDistrictBeans() {
		return districtBeans;
	}
	public void setDistrictBeans(List<DistrictBean> districtBeans) {
		this.districtBeans = districtBeans;
	}
	@Override
	public String toString() {
		return "AmphurBean [id=" + id + ", AMPHUR_CODE=" + AMPHUR_CODE + ", AMPHUR_NAME=" + AMPHUR_NAME + ", POSTCODE="
				+ POSTCODE + ", districtBeans=" + districtBeans + "]";
	}
	
}
