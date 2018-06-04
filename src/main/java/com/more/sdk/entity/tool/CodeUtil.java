package com.more.sdk.entity.tool;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CodeUtil {
	
	public static String encryptCode(String key, String data){
		if(data == null){
			return "";
		}else{
			try{
				SecureRandom random = new SecureRandom();  
			    DESKeySpec keySpec = new DESKeySpec(key.getBytes());
			    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("des");  
			    SecretKey secretKey = keyFactory.generateSecret(keySpec);  
			    
			    Cipher cipher = Cipher.getInstance("des");  
			    cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);  
			    byte[] cipherData = cipher.doFinal(data.getBytes());  
			    return new BASE64Encoder().encode(cipherData);
			}catch (Exception e) {
				return "";
			}
			
		}
		
	}
	
	public static String decryptCode(String key, String data) throws Exception{
		if(data == null){
			return "";
		}else{
			try{
				SecureRandom random = new SecureRandom();  
			    DESKeySpec keySpec = new DESKeySpec(key.getBytes());
			    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("des");  
			    SecretKey secretKey = keyFactory.generateSecret(keySpec);  
			    
			    Cipher cipher = Cipher.getInstance("des");  
			    cipher.init(Cipher.DECRYPT_MODE, secretKey, random);  
			    byte[] cipherData = cipher.doFinal(new BASE64Decoder().decodeBuffer(data));  
			    return new String(cipherData);
			}catch (Exception e) {
				return "";
			}
		}
		
	}
}
