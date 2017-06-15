package com.ramytech.android.util;

import java.security.NoSuchAlgorithmException;

public class MD5 {
	private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'};
	public static String genMD5(byte[] source) {
		java.security.MessageDigest md;
		try {
			md = java.security.MessageDigest.getInstance( "MD5" );
		} catch (NoSuchAlgorithmException e) { 
			return null;
		}
	    md.update( source );
	    return byteToString(md.digest());        
	}
	
	public static String genMD5(String source) {
		return genMD5(source.getBytes());
	}
	
	public static String byteToString(byte[] tmp) {
		char str[] = new char[32];   // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
		int k = 0;                                // 表示转换结果中对应的字符位置
		for (int i = 0; i < 16; i++) {          // 从第一个字节开始，对 MD5 的每一个字节
		            // 转换成 16 进制字符的转换
		byte byte0 = tmp[i];                 // 取第 i 个字节
		str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // 取字节中高 4 位的数字转换, 
		                        // >>> 为逻辑右移，将符号位一起右移
		str[k++] = hexDigits[byte0 & 0xf];            // 取字节中低 4 位的数字转换		     
		}
		return new String(str);
	}
	
	public static void main(String args[]) {
		System.out.println("md5=" + MD5.genMD5("ranger"));
	}
}
