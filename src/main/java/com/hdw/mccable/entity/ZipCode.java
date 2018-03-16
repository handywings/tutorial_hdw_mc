package com.hdw.mccable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="zipcode")

public class ZipCode {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "LOCATION_ZIPCODE_ID", unique=true, nullable=false)
	private Long LOCATION_ZIPCODE_ID;
	
	@Column(name = "DISTRICT_CODE", length=6)
	private String DISTRICT_CODE;

	@Column(name = "ZIP_CODE", length=5)
	private String ZIP_CODE;

	public Long getLOCATION_ZIPCODE_ID() {
		return LOCATION_ZIPCODE_ID;
	}

	public void setLOCATION_ZIPCODE_ID(Long lOCATION_ZIPCODE_ID) {
		LOCATION_ZIPCODE_ID = lOCATION_ZIPCODE_ID;
	}

	public String getDISTRICT_CODE() {
		return DISTRICT_CODE;
	}

	public void setDISTRICT_CODE(String dISTRICT_CODE) {
		DISTRICT_CODE = dISTRICT_CODE;
	}

	public String getZIP_CODE() {
		return ZIP_CODE;
	}

	public void setZIP_CODE(String zIP_CODE) {
		ZIP_CODE = zIP_CODE;
	}
	
}
