package com.hdw.mccable.dto;

import java.util.List;

public class ZoneBean extends DSTPUtilityBean{
	
	private Long id;
	private String zoneName;
	private String zoneDetail;
	private String zoneType;
	private List<AddressBean> addressList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getZoneDetail() {
		return zoneDetail;
	}
	public void setZoneDetail(String zoneDetail) {
		this.zoneDetail = zoneDetail;
	}
	public String getZoneType() {
		return zoneType;
	}
	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}
	public List<AddressBean> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<AddressBean> addressList) {
		this.addressList = addressList;
	}
	@Override
	public String toString() {
		return "ZoneBean [id=" + id + ", zoneName=" + zoneName + ", zoneDetail=" + zoneDetail + ", zoneType=" + zoneType
				+ ", addressList=" + addressList + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()="
				+ getUpdateDate() + ", getCreateBy()=" + getCreateBy() + ", getUpdateBy()=" + getUpdateBy()
				+ ", getCreateDateTh()=" + getCreateDateTh() + ", getUpdateDateTh()=" + getUpdateDateTh()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
