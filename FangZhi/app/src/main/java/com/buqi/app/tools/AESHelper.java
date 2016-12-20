package com.buqi.app.tools;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {
	
	public final static String key="adsfqe1er32df2f2";//加密用的Key可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定

	public  void test(){
		try {
			String cSrc = "#￥%abv123四大四大";//需要加密的字串
			//加密
			String enString = AESHelper.Encrypt(cSrc);
			System.out.println("加密后的字串是：" + enString);
			//解密
			String DeString = AESHelper.Decrypt(enString);
			System.out.println("解密后的字串是：" + DeString);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 1.加密
	 */
	public static String Encrypt(String sSrc) {
		try {
			String sKey=AESHelper.key;
//			if (sKey == null) {
//				System.out.print("Key为空null");
//				return null;
//			}
//			//判断Key是否为16位
//			if (sKey.length() != 16) {
//				System.out.print("Key长度不是16位");
//				return null;
//			}
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes());
			
			return byte2hex(encrypted).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * 2.解密
	 */
	public static String Decrypt(String sSrc){
		String sKey=AESHelper.key;
		try {
//			//判断Key是否正确
//			if (sKey == null) {
//				System.out.print("Key为空null");
//				return null;
//			}
//			//判断Key是否为16位
//			if (sKey.length() != 16) {
//				System.out.print("Key长度不是16位");
//				return null;
//			}
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = hex2byte(sSrc);//16進制字符串轉換成byte[]
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * 16進制字符串-->byte[]
	 */
	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
		return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
		return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
		b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}
	
	
	/**
	 * byte[]-->16進制字符串
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
		stmp = (Integer.toHexString(b[n] & 0XFF));
		if (stmp.length() == 1) {
		hs = hs + "0" + stmp;
		} else {
		hs = hs + stmp;
		}
		}
		return hs.toUpperCase();
	}
	
}

