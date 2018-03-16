package com.hdw.mccable.dto;

public class DropdownBean {
	private int key;
	private String text;
	
	public DropdownBean(int key, String text) {
		super();
		this.key = key;
		this.text = text;
	}
	
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	 
}
