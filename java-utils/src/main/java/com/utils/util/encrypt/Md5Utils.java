
package com.utils.util.encrypt;


import com.utils.util.StringUtils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5Utils {

	private static Charset charset = Charset.forName("UTF-8");

	public static byte[] md5(byte[] array) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] result = md.digest(array);
			return result;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] data = str.getBytes(charset);
			byte[] result = md.digest(data);
			return result;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String md5Str(String str) {
		return StringUtils.toHexString(md5(str));
	}

	public static void main(String[] args) {
		System.out.println(Md5Utils.md5Str("admin"));
	}
}
