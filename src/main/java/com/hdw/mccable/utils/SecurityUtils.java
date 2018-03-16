package com.hdw.mccable.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import org.junit.Test;

public class SecurityUtils {

	private static SecurityService SECURE = new SecurityService(ConstantUtils.SECURITY_KEY);

	public static String encrypt(String text) {
		return SECURE.encrypt(text);
	}

	public static String decrypt(String text) {
		return SECURE.decrypt(text);
	}

	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	public static String SHA1(String text) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-1");
		byte[] sha1hash = new byte[40];
		md.update(text.getBytes("UTF-8"), 0, text.length());
		sha1hash = md.digest();
		return convertToHex(sha1hash);
	}

	public static String MD5(String to) throws Exception {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(to.getBytes());

		String result = "";
		byte[] of = m.digest();
		for (int i = 0; i < 16; i++) {
			result += (char) (of[i] & 0xff);
		}

		return result;
	}
	
//	@Test
//	public void test_encrypt(){
//		System.out.println(encrypt("Admin!Mahachai!2017"));
//	}
	
//	@Test
//	public void test_decrypt(){
//		System.out.println(decrypt("94a308ccc9007bc33207baffb7f5884d561a64ea31110fc0"));
//	}
}
