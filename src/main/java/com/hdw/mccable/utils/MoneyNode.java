package com.hdw.mccable.utils;

public class MoneyNode {
	
	private char value;
	private int position;
	private MoneyNode nextNode;
	private String valueAsText;
	
	public MoneyNode(char value, int rightHandSidePosition) {
		this.value = value;
		this.position = rightHandSidePosition;
	}
	
	public String Read() {
		String result = "";
		if (nextNode != null) 
			result = nextNode.Read() + this.valueAsText;
		else
			result = this.valueAsText;
		return result;
	}
	
	public void setNextNode(MoneyNode nextNode) {
		this.nextNode = nextNode;
	}
	
	public String ReadValueAsText() {
		valueAsText = "";
		
		switch (this.value) {
		case '1': 
			if ((( this.position - 1) % 6 == 0 ) && nextNode != null ) {
				valueAsText = "เอ็ด";
				break;
			}
			if ((this.position -1) % 6 == 0 && nextNode == null) {
				valueAsText = "หนึ่ง";
				break;
			}
			if ((this.position -1) % 6  >= 2) {
				valueAsText = "หนึ่ง";
				break;
			}
			break;
		case '2': valueAsText = ((this.position -1) % 6 == 1) ? "ยี่" : "สอง"; break;
		case '3': valueAsText = "สาม"; break;
		case '4': valueAsText = "สี่"; break;
		case '5': valueAsText = "ห้า"; break;
		case '6': valueAsText = "หก"; break;
		case '7': valueAsText = "เจ็ด"; break;
		case '8': valueAsText = "แปด"; break;
		case '9': valueAsText = "เก้า"; break;
		case '0': valueAsText = ""; break;
		}
		
		switch ((this.position - 1) % 6) {
		case 0: valueAsText += (this.position == 1) ? "" : "ล้าน"; break;
		case 1: valueAsText += (this.value == '0') ? "" : "สิบ"; break;
		case 2: valueAsText += (this.value == '0') ? "" : "ร้อย"; break;
		case 3: valueAsText += (this.value == '0') ? "" : "พัน"; break;
		case 4: valueAsText += (this.value == '0') ? "" : "หมื่น"; break;
		case 5: valueAsText += (this.value == '0') ? "" : "แสน"; break;
		}
		return valueAsText;
	}
}
