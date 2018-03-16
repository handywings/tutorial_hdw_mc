package com.hdw.mccable.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class SmartCardReaderBean {

	final SimpleDateFormat formatDataTh_yyyy_mm_dd = new SimpleDateFormat("yyyyMMdd", new Locale("TH", "th"));
	final SimpleDateFormat formatDataTh_dd_mm_yyyy = new SimpleDateFormat("dd-MM-yyyy", new Locale("TH", "th"));
	
	private String Citizenid;
	private String Birthday;
	private String Sex;
	private String Th_Prefix;
	private String Th_Firstname;
	private String Th_Lastname;
	private String En_Prefix;
	private String En_Firstname;
	private String En_Lastname;
	private String Card_Issuer; // สถานที่ออกบัตร
	private String Issue; // วันออกบัตร
	private String Expire; // วันหมดอายุ
	private String Address;
	private String addrHouseNo; // บ้านเลขที่
	private String addrVillageNo; // หมู่ที่
	private String addrLane; // ซอย
	private String addrRoad; // ถนน
	private String addrTambol;
	private String addrAmphur;
	private String addrProvince;
	private String addrZipCode;
	
	private String PhotoPart;

	public String getCitizenid() {
		return Citizenid;
	}

	public void setCitizenid(String citizenid) {
		Citizenid = citizenid;
	}

	public String getBirthday() {
		return Birthday;
	}

	public void setBirthday(Date birthday) {
		if(null != birthday){
			Birthday = formatDataTh_dd_mm_yyyy.format(birthday);
		}
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getTh_Prefix() {
		return Th_Prefix;
	}

	public void setTh_Prefix(String th_Prefix) {
		Th_Prefix = th_Prefix;
	}

	public String getTh_Firstname() {
		return Th_Firstname;
	}

	public void setTh_Firstname(String th_Firstname) {
		Th_Firstname = th_Firstname;
	}

	public String getTh_Lastname() {
		return Th_Lastname;
	}

	public void setTh_Lastname(String th_Lastname) {
		Th_Lastname = th_Lastname;
	}

	public String getEn_Prefix() {
		return En_Prefix;
	}

	public void setEn_Prefix(String en_Prefix) {
		En_Prefix = en_Prefix;
	}

	public String getEn_Firstname() {
		return En_Firstname;
	}

	public void setEn_Firstname(String en_Firstname) {
		En_Firstname = en_Firstname;
	}

	public String getEn_Lastname() {
		return En_Lastname;
	}

	public void setEn_Lastname(String en_Lastname) {
		En_Lastname = en_Lastname;
	}

	public String getCard_Issuer() {
		return Card_Issuer;
	}

	public void setCard_Issuer(String card_Issuer) {
		Card_Issuer = card_Issuer;
	}

	public String getIssue() {
		return Issue;
	}

	public void setIssue(Date issue) {
		if(null != issue){
			Issue = formatDataTh_dd_mm_yyyy.format(issue);
		}
	}

	public String getExpire() {
		return Expire;
	}

	public void setExpire(Date expire) {
		if(null != expire){
			Expire = formatDataTh_dd_mm_yyyy.format(expire);
		}
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getAddrHouseNo() {
		return addrHouseNo;
	}

	public void setAddrHouseNo(String addrHouseNo) {
		this.addrHouseNo = addrHouseNo;
	}

	public String getAddrVillageNo() {
		return addrVillageNo;
	}

	public void setAddrVillageNo(String addrVillageNo) {
		this.addrVillageNo = addrVillageNo;
	}

	public String getAddrLane() {
		return addrLane;
	}

	public void setAddrLane(String addrLane) {
		this.addrLane = addrLane;
	}

	public String getAddrRoad() {
		return addrRoad;
	}

	public void setAddrRoad(String addrRoad) {
		this.addrRoad = addrRoad;
	}

	public String getAddrTambol() {
		return addrTambol;
	}

	public void setAddrTambol(String addrTambol) {
		this.addrTambol = addrTambol;
	}

	public String getAddrAmphur() {
		return addrAmphur;
	}

	public void setAddrAmphur(String addrAmphur) {
		this.addrAmphur = addrAmphur;
	}

	public String getAddrProvince() {
		return addrProvince;
	}

	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}

	public String getPhotoPart() {
		return PhotoPart;
	}

	public void setPhotoPart(String photoPart) {
		PhotoPart = photoPart;
	}

	public String getAddrZipCode() {
		return addrZipCode;
	}

	public void setAddrZipCode(String addrZipCode) {
		this.addrZipCode = addrZipCode;
	}

	@Override
	public String toString() {
		return "SmartCardReaderBean [Citizenid=" + Citizenid + ", Birthday=" + Birthday + ", Sex=" + Sex
				+ ", Th_Prefix=" + Th_Prefix + ", Th_Firstname=" + Th_Firstname + ", Th_Lastname=" + Th_Lastname
				+ ", En_Prefix=" + En_Prefix + ", En_Firstname=" + En_Firstname + ", En_Lastname=" + En_Lastname
				+ ", Card_Issuer=" + Card_Issuer + ", Issue=" + Issue + ", Expire=" + Expire + ", Address=" + Address
				+ ", addrHouseNo=" + addrHouseNo + ", addrVillageNo=" + addrVillageNo + ", addrLane=" + addrLane
				+ ", addrRoad=" + addrRoad + ", addrTambol=" + addrTambol + ", addrAmphur=" + addrAmphur
				+ ", addrProvince=" + addrProvince + ", addrZipCode=" + addrZipCode + ", PhotoPart="
				+ PhotoPart + "]";
	}

}
