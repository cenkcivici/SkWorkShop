package com.sk.service.encryption;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

	@Value("${shared.key}")
	private String sharedKey;
	
	private static final String ALGORITHM = "DESede";
	private static final String TRANSFORMATION = "DESede/CBC/PKCS5Padding";

	public String encrypt(String plainText){

		if(plainText == null){
			throw new IllegalArgumentException("Plain text should not be null");
		}
		
		try{
			Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
	
			byte[] input = plainText.getBytes("UTF-8");
			byte[] encrypted = cipher.doFinal(input);
			return new String(Hex.encodeHex(encrypted)).toUpperCase();
	
		}catch(Exception e){
			throw new RuntimeException(e);
		}

	}

	public String decrypt(String cipherText) {

		if(cipherText == null){
			throw new IllegalArgumentException("Cipher text should not be null");
		}
		
		try{
			Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
	
			byte[] decrypted = cipher.doFinal(Hex.decodeHex(cipherText.toCharArray()));
			return new String(decrypted, "UTF-8");
	
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	private Cipher getCipher(int cipherMode) {
		
		try{
			final MessageDigest md = MessageDigest.getInstance("md5");
			final byte[] digestOfPassword = md.digest(sharedKey.getBytes("utf-8"));
	
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			for (int j = 0, k = 16; j < 8;) {
	            keyBytes[k++] = keyBytes[j++];
			}
			
			DESedeKeySpec keySpec = new DESedeKeySpec(keyBytes);
			SecretKey key = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(keySpec);
			IvParameterSpec iv = new IvParameterSpec(new byte[8]);
			
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, key, iv);
			
			return cipher;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public void setSharedKey(String sharedKey) {
		this.sharedKey = sharedKey;
	}
}
