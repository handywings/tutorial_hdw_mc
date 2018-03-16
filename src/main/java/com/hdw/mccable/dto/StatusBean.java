package com.hdw.mccable.dto;

public class StatusBean extends DSTPUtilityBean{
	
	private String stringValue; 
	private int numberValue;
	
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public int getNumberValue() {
		return numberValue;
	}
	public void setNumberValue(int numberValue) {
		this.numberValue = numberValue;
	}
	
	@Override
	public String toString() {
		return "StatusBean [stringValue=" + stringValue + ", numberValue=" + numberValue + ", getCreateDate()="
				+ getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()=" + getCreateBy()
				+ ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh()
				+ ", getUpdateDateTh()=" + getUpdateDateTh() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
