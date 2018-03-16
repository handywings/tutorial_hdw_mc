package com.hdw.mccable.dto;

public class ReportWorksheetMoveBean extends ReportWorksheetMainBean{

	private String nearbyPlacess;
	private String collectAddressDetail;
	private String zoneDetail;
	private int originalPointAll;
	private int movePointAll;
	
	public String getNearbyPlacess() {
		return nearbyPlacess;
	}
	public void setNearbyPlacess(String nearbyPlacess) {
		this.nearbyPlacess = nearbyPlacess;
	}
	public String getCollectAddressDetail() {
		return collectAddressDetail;
	}
	public void setCollectAddressDetail(String collectAddressDetail) {
		this.collectAddressDetail = collectAddressDetail;
	}
	public String getZoneDetail() {
		return zoneDetail;
	}
	public void setZoneDetail(String zoneDetail) {
		this.zoneDetail = zoneDetail;
	}
	public int getOriginalPointAll() {
		return originalPointAll;
	}
	public void setOriginalPointAll(int originalPointAll) {
		this.originalPointAll = originalPointAll;
	}
	public int getMovePointAll() {
		return movePointAll;
	}
	public void setMovePointAll(int movePointAll) {
		this.movePointAll = movePointAll;
	}
}