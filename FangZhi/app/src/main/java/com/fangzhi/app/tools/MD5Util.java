package com.fangzhi.app.tools;
/*
 * Copyright (C) 2015 The lldq Project
 * All right reserved.
 * author: ltaiq@sina.com
 */
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 */
public class MD5Util {

	private static MD5Util md5Util = null;
	
	private MD5Util(){
		
	}
	
	public static MD5Util getInstance(){
		if(md5Util==null){
			md5Util = new MD5Util();
		}
		return md5Util;
	}
	
	/**
	 * 获取MD5加密字符串
	 * @param source
	 * @return
	 */
	public String getMD5(String source) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(source.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("NoSuchAlgorithmException caught!",e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1){
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			}else{
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}

		return md5StrBuff.toString();
	}
	
	/**
	 * 获取两次MD5加密的字符串
	 * @param source
	 * @return
	 */
	public String getDoubleMd5(String source){
		String result = getMD5(source);
		result = getMD5(result);
		return result;
	}

}
