package com.hdw.mccable.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class SmartCardReaderUtil {

	final SimpleDateFormat formatDataTh_yyyy_mm_dd = new SimpleDateFormat("yyyyMMdd", new Locale("TH", "th"));
	
	private String Citizenid;
	private Date Birthday;
	private String Sex;
	private String Th_Prefix;
	private String Th_Firstname;
	private String Th_Lastname;
	private String En_Prefix;
	private String En_Firstname;
	private String En_Lastname;
	private String Card_Issuer; // สถานที่ออกบัตร
	private Date Issue; // วันออกบัตร
	private Date Expire; // วันหมดอายุ
	private String Address;
	private String addrHouseNo; // บ้านเลขที่
	private String addrVillageNo; // หมู่ที่
	private String addrLane; // ซอย
	private String addrRoad; // ถนน
	private String addrTambol;
	private String addrAmphur;
	private String addrProvince;
	
	private String PhotoPart;

	public void setEN_Fullname(String eN_Fullname) {
		if(StringUtils.isNotEmpty(eN_Fullname)){
			String[] fullname = eN_Fullname.split("##");
			if(null != fullname && fullname.length > 1){
				String[] fullname1 = fullname[0].split("#");
				if(null != fullname1 && fullname1.length > 0){
					this.En_Prefix = fullname1[0];
					this.En_Firstname = fullname1[1];
				}
				this.En_Lastname = fullname[1].trim();
			}
		}
	}

	public void setTH_Fullname(String tH_Fullname) {
		if(StringUtils.isNotEmpty(tH_Fullname)){
			String[] fullname = tH_Fullname.split("##");
			if(null != fullname && fullname.length > 1){
				String[] fullname1 = fullname[0].split("#");
				if(null != fullname1 && fullname1.length > 0){
					this.Th_Prefix = fullname1[0];
					this.Th_Firstname = fullname1[1];
				}
				this.Th_Lastname = fullname[1].trim();
			}
		}
	}

	public String getCitizenid() {
		return Citizenid.trim();
	}

	public void setCitizenid(String citizenid) {
		Citizenid = citizenid;
	}

	public Date getBirthday() {
		return Birthday;
	}

	public void setBirthday(String birthday) {
		if(StringUtils.isNotEmpty(birthday)){
			try {
				Birthday = formatDataTh_yyyy_mm_dd.parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public String getSex() {
		return Sex.trim();
	}

	public void setSex(String sex) {
		if(StringUtils.isNotEmpty(sex)){
			if("1".equals(sex)){
				Sex = "male";
			}else{
				Sex = "female";
			}
		}
	}

	public String getTh_Prefix() {
		return Th_Prefix.trim();
	}

	public void setTh_Prefix(String th_Prefix) {
		Th_Prefix = th_Prefix;
	}

	public String getTh_Firstname() {
		return Th_Firstname.trim();
	}

	public void setTh_Firstname(String th_Firstname) {
		Th_Firstname = th_Firstname;
	}

	public String getTh_Lastname() {
		return Th_Lastname.trim();
	}

	public void setTh_Lastname(String th_Lastname) {
		Th_Lastname = th_Lastname;
	}

	public String getEn_Prefix() {
		return En_Prefix.trim();
	}

	public void setEn_Prefix(String en_Prefix) {
		En_Prefix = en_Prefix;
	}

	public String getEn_Firstname() {
		return En_Firstname.trim();
	}

	public void setEn_Firstname(String en_Firstname) {
		En_Firstname = en_Firstname;
	}

	public String getEn_Lastname() {
		return En_Lastname.trim();
	}

	public void setEn_Lastname(String en_Lastname) {
		En_Lastname = en_Lastname;
	}

	public String getCard_Issuer() {
		return Card_Issuer.trim();
	}

	public void setCard_Issuer(String card_Issuer) {
		Card_Issuer = card_Issuer.trim();
	}

	public Date getIssue() {
		return Issue;
	}

	public void setIssue(String issue) {
		if(StringUtils.isNotEmpty(issue)){
			try {
				Issue = formatDataTh_yyyy_mm_dd.parse(issue);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public Date getExpire() {
		return Expire;
	}

	public void setExpire(String expire) {
		if(StringUtils.isNotEmpty(expire)){
			try {
				Expire = formatDataTh_yyyy_mm_dd.parse(expire);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public String getAddress() {
		return Address.trim();
	}

	public void setAddress(String address) {
		if(StringUtils.isNotEmpty(address)){
			String[] addressArray = address.split("####");
			if(null != addressArray && addressArray.length > 1){
				String[] address1 = addressArray[0].split("#");
				String[] address2 = addressArray[1].split("#");
				if(null != address1 && address1.length > 1){
					this.addrHouseNo = address1[0];
					this.addrVillageNo = address1[1];
				}
				if(null != address2 && address2.length > 2){
					this.addrTambol = address2[0];
					this.addrAmphur = address2[1];
					this.addrProvince = address2[2].trim();
				}
			}
		}
	}

	public String getAddrHouseNo() {
		return addrHouseNo.trim();
	}

	public void setAddrHouseNo(String addrHouseNo) {
		this.addrHouseNo = addrHouseNo;
	}

	public String getAddrVillageNo() {
		return addrVillageNo.trim();
	}

	public void setAddrVillageNo(String addrVillageNo) {
		this.addrVillageNo = addrVillageNo;
	}

	public String getAddrLane() {
		return addrLane.trim();
	}

	public void setAddrLane(String addrLane) {
		this.addrLane = addrLane;
	}

	public String getAddrRoad() {
		return addrRoad.trim();
	}

	public void setAddrRoad(String addrRoad) {
		this.addrRoad = addrRoad;
	}

	public String getAddrTambol() {
		return addrTambol.trim();
	}

	public void setAddrTambol(String addrTambol) {
		this.addrTambol = addrTambol;
	}

	public String getAddrAmphur() {
		return addrAmphur.trim();
	}

	public void setAddrAmphur(String addrAmphur) {
		this.addrAmphur = addrAmphur;
	}

	public String getAddrProvince() {
		return addrProvince.trim();
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

	@Override
	public String toString() {
		return "SmartCardReaderBean [Citizenid=" + Citizenid + ", Birthday=" + Birthday + ", Sex=" + Sex
				+ ", Th_Prefix=" + Th_Prefix + ", Th_Firstname=" + Th_Firstname + ", Th_Lastname=" + Th_Lastname
				+ ", En_Prefix=" + En_Prefix + ", En_Firstname=" + En_Firstname + ", En_Lastname=" + En_Lastname
				+ ", Card_Issuer=" + Card_Issuer + ", Issue=" + Issue + ", Expire=" + Expire + ", Address=" + Address
				+ ", addrHouseNo=" + addrHouseNo + ", addrVillageNo=" + addrVillageNo + ", addrLane=" + addrLane
				+ ", addrRoad=" + addrRoad + ", addrTambol=" + addrTambol + ", addrAmphur=" + addrAmphur
				+ ", addrProvince=" + addrProvince + ", PhotoPart=" + PhotoPart + "]";
	}
	
}
