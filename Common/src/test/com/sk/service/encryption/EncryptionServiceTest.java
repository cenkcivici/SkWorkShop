package com.sk.service.encryption;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;

public class EncryptionServiceTest {

	private final static String PLAIN_TEXT = "1234567812345678";
	private final static String CIPHER_TEXT = "A5E782DF6050F290B5D8CD7CF03B9C0B4418504E1FE6ED24";


	private EncryptionService encryptionService;
	
	@Before
	public void init(){
		encryptionService = new EncryptionService();
		encryptionService.setSharedKey("Some-Shared-Secret");
	}
	
	@Test
	public void shouldEncryptPlainText() throws Exception{
		String cipherText = encryptionService.encrypt(PLAIN_TEXT);
		assertThat(cipherText, equalTo(CIPHER_TEXT));
	}
	
	@Test
	public void shoudDecryptCipherText() throws Exception{
		String plainText = encryptionService.decrypt(CIPHER_TEXT);
		assertThat(plainText, equalTo(PLAIN_TEXT));
	}
}
