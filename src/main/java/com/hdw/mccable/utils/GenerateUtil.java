package com.hdw.mccable.utils;

import java.security.SecureRandom;

public class GenerateUtil {
	static final String AB = "0123456789abcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	
	public static String generatePassword(int lengthPassword){
		StringBuilder sb = new StringBuilder(lengthPassword);
		for (int i = 0; i < lengthPassword; i++){
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		}
		return sb.toString();
	}
	
//	public static void main(String[] args){
//		System.out.println(generatePassword(8));
//	}
}
