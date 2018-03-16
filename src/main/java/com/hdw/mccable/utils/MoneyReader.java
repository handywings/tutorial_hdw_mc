package com.hdw.mccable.utils;

import java.util.LinkedList;

public class MoneyReader {
	
	private String moneyValue;
	private String fractionalPart;
	private String wholeNumberPart;
	private LinkedList<MoneyNode> moneyList;
	
	public MoneyReader(float money) {
		this.moneyValue = String.format("%.2f", money);
	}
	
	public MoneyReader(int money) {
		float tempValue = money;
		this.moneyValue = String.format("%.2f", tempValue);
	}
	
	public MoneyReader(String money) {
		money = money.replaceAll(",", "");
		float tempValue;
		try {
			tempValue = Float.parseFloat(money);
		} catch (NumberFormatException ex) {
			tempValue = 0.0f;
		}
		this.moneyValue = String.format("%.2f",tempValue);
	}
	
	public String Read() {
		String result;
		wholeNumberPart = this.moneyValue.substring(0, this.moneyValue.indexOf("."));
		fractionalPart = this.moneyValue.substring(moneyValue.indexOf(".") + 1, this.moneyValue.length());
		
		String wholeNumberText, fractionalText;
		wholeNumberText = readWholeNumberPart();
		fractionalText  = readFractionalPart();
		
		if ("".equals(fractionalText)) {
			result = wholeNumberText + "ถ้วน";
		} else {
			result = wholeNumberText + fractionalText;
		}
		return result;
	}
	
	public String readFractionalPart() {
		String result = "";
		char firstDigit = fractionalPart.charAt(0);
		char lastDigit = fractionalPart.charAt(1);
		
		switch (firstDigit) {
			case '1': result = "สิบ"; break;
			case '2': result = "ยี่สิบ"; break;
			case '3': result = "สามสิบ"; break;
			case '4': result = "สี่สิบ"; break;
			case '5': result = "ห้าสิบ"; break;
			case '6': result = "หกสิบ"; break;
			case '7': result = "เจ็ดสิบ"; break;
			case '8': result = "แปดสิบ"; break;
			case '9': result = "เก้าสิบ"; break;
			case '0': result = ""; break;
		}
		
		switch (lastDigit) {
			case '1': result += firstDigit == '0' ? "หนึ่งสตางค์" : "เอ็ดสตางค์"; break;
			case '2': result += "สองสตางค์"; break;
			case '3': result += "สามสตางค์"; break;
			case '4': result += "สี่สตางค์"; break;
			case '5': result += "ห้าสตางค์"; break;
			case '6': result += "หกสตางค์"; break;
			case '7': result += "เจ็ดสตางค์"; break;
			case '8': result += "แปดสตางค์"; break;
			case '9': result += "เก้าสตางค์"; break;
			case '0': result += firstDigit == '0' ? "" : "สตางค์"; break;
			default: result += ""; break;
		}
		return result;
	}

	public String readWholeNumberPart() {
		String result = "";
		moneyList = new LinkedList<MoneyNode>();
		
		for(int count = wholeNumberPart.length(); count > 0; count--) {
			char digitValue = wholeNumberPart.charAt(count-1);
			int digitPosition = wholeNumberPart.length() - (count-1);
			
			MoneyNode moneyNode = new MoneyNode(digitValue, digitPosition);
			if( count == wholeNumberPart.length() ) {
				moneyList.add(moneyNode);
			} else {
				moneyList.getLast().setNextNode(moneyNode);
				moneyList.add(moneyNode);
			}
		}
		
		for (int count = 0; count < moneyList.size(); count++) {
			moneyList.get(count).ReadValueAsText();
		}
		
		result = moneyList.get(0).Read();
		if (!"".equals(result)) {
			result += "บาท";
		}
			
		return result;
	}
	

}

