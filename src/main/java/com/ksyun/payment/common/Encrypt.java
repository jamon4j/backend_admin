/**
 * @author: yangji
 * @data:   Nov 12, 2013
 */
package com.ksyun.payment.common;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.ksyun.tools.Base64;


public class Encrypt {

	public static final String KEY_ALGORITHM = "DES";
	public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";	
	
	public static byte[] getSecret(){
		KeyGenerator keyGenerator = null;
		SecretKey secretKey = null;
		try {
			keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
			keyGenerator.init(56);  
		    secretKey = keyGenerator.generateKey();  
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}	    
	    return secretKey.getEncoded();
	}
	
	private static Key toKey(byte[] key) {
	    DESKeySpec des = null;
	    SecretKeyFactory keyFactory = null;
	    SecretKey secretKey = null;
		try {
			des = new DESKeySpec(key);
			keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);  
		    secretKey = keyFactory.generateSecret(des);  
		} catch (Exception e) {
			e.printStackTrace();
		} 
	    
	    return secretKey;  
	}
	
	private static byte[] encryptBytes(byte[] data ,byte[] key) {  
	    Key k = toKey(key);  
	    Cipher cipher;
	    byte[] bytes = null;
		try {
			cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, k);  
			bytes = cipher.doFinal(data); 
		} catch (Exception e) {
			e.printStackTrace();
		}	    
	     
		return bytes;
	} 
	
	private static byte[] decryptBytes(byte[] data ,byte[] key){
		Key k = toKey(key);  
	    Cipher cipher;
	    byte[] bytes = null;
		try {
			cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, k);  
			bytes = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	    
	    return bytes;
	}
	
	public static String encrypt(String content, byte[] key){
		byte[] bytes = null;
		byte[] encrypted = null;
		try {
			bytes = content.getBytes("UTF-8");			
		} catch (Exception e) {}
		
		encrypted = encryptBytes(bytes, key);
		return Base64.encodeBytes(encrypted);
	}
	
	public static String encrypt(String content, String key){
		byte[] bytes = null;
		byte[] encrypted = null;
		try {
			bytes = content.getBytes("UTF-8");			
		} catch (Exception e) {}
		
		encrypted = encryptBytes(bytes, key.getBytes());
		return Base64.encodeBytes(encrypted);
	}
	
	public static String decrypt(String content, byte[] key){
		byte[] bytes = null;		
		byte[] encrypted = null;
		byte[] decrypted = null;
		String result = null;
		
		try {
			bytes = content.getBytes("UTF-8");
			encrypted = Base64.decode(bytes);		
			decrypted = decryptBytes(encrypted, key);
			result = new String(decrypted,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	public static String decrypt(String content, String key){
		byte[] bytes = null;		
		byte[] encrypted = null;
		byte[] decrypted = null;
		String result = null;
		
		try {
			bytes = content.getBytes("UTF-8");
			encrypted = Base64.decode(bytes);		
			decrypted = decryptBytes(encrypted, key.getBytes());
			result = new String(decrypted,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
}