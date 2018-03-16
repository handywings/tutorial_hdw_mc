package com.hdw.mccable.utils;

import java.text.DecimalFormat;

public class TextUtil {
	public synchronized static String changeEmptyText(String text) {
		return text == null || text == "" ? "-" : text;
	}
	
	public synchronized static boolean isNotEmpty(Object object) {
		try {
			if (object != null) {
				if (object.toString().trim().length() > 0 && (!object.toString().trim().equalsIgnoreCase("null"))) {
					return true;
				}
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
		return false;
	}
	
	public synchronized static String getFormatNumber(double number) {
		try {
			DecimalFormat decimalformat = new DecimalFormat("#,##0.00");
			return decimalformat.format(number);
		} catch(Exception ex) {
			return "0.00";
		}		
	}
	
	public synchronized static String getFormatNumberInt(int number) {
		try {
			DecimalFormat decimalformat = new DecimalFormat("#,##0");
			return decimalformat.format(number);
		} catch(Exception ex) {
			return "0.00";
		}
	}
	
	public synchronized static String getFormatNumberDouble(double number, int decimal) {
		try {
			String text = "";
			for (int i = 0; i < decimal; i++) {
				text += "0";
			}
			DecimalFormat decimalformat = new DecimalFormat("#,###,##0." + text);
			return decimalformat.format(number);
		} catch(Exception ex) {
			return "0.00";
		}				
	}
}
