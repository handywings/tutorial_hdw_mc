package com.hdw.mccable.utils;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class SecurityService {

	static final byte[] SALT = { 107, (byte) 103, (byte) 112, (byte) 97, (byte) 116, (byte) 109, (byte) 115, (byte) 97 };
	static final int ITERATIONS = 11;

	private Cipher _ecipher;
	private Cipher _dcipher;

	public SecurityService(final String passphrase) {
		try {
			final PBEParameterSpec params = new PBEParameterSpec(SALT, ITERATIONS);
			final KeySpec keySpec = new PBEKeySpec(passphrase.toCharArray());
			final SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
			_ecipher = Cipher.getInstance(key.getAlgorithm());
			_dcipher = Cipher.getInstance(key.getAlgorithm());
			_ecipher.init(Cipher.ENCRYPT_MODE, key, params);
			_dcipher.init(Cipher.DECRYPT_MODE, key, params);

		} catch (final Exception e) { }
	}

	public String encrypt(final String string) {
		try {
			// Encode the string into bytes using utf-8
			final byte[] bytes = string.getBytes("UTF-8");

			// Encrypt
			final byte[] enc = _ecipher.doFinal(bytes);

			// Encode bytes to base64 to get a string
			return bytesToHex(enc);
		} catch (final Exception e) {
			
		}
		return null;
	}

	public String decrypt(final String str) {
		try {
			// Decode base64 to get bytes
			final byte[] dec = hexToBytes(str);

			// Decrypt
			final byte[] utf8 = _dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (final Exception e) {
			
		}
		return null;
	}

	private static String bytesToHex(final byte[] bytes) {
		final StringBuilder buf = new StringBuilder(bytes.length * 2);
		for (final byte b : bytes) {
			final String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				buf.append("0");
			}
			buf.append(hex);
		}
		return buf.toString();
	}

	private static byte[] hexToBytes(final String hex) {
		final byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2),
					16);
		}
		return bytes;
	}
}
