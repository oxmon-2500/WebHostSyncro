package at.noe.szb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

public class CryptoTest {
	//https://stackoverflow.com/questions/14486814/aes-encrypt-with-openssl-decrypt-using-java
	//openssl enc -nosalt -aes-128-ecb -in data.txt -out crypted-aes.data -K 50645367566B59703373367639792442
	final String TEST_FILE = "src/test/resources/crypted-aes.data";
	final String TEST_TXT = "HejTamPodLasem_123";
	private SecretKeySpec mkAesKey() {
		final byte[] aesKey = "PdSgVkYp3s6v9y$B".getBytes(StandardCharsets.UTF_8);
		return new SecretKeySpec(aesKey, "AES");
	}
	@Test
	public void test_encrypt() throws Exception{
		final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, mkAesKey());
		final byte[] encryptedMsg = cipher.doFinal(TEST_TXT.getBytes());
		Files.write(Paths.get(TEST_FILE), encryptedMsg, StandardOpenOption.CREATE);
		System.out.println("2___"+new String(Base64.getEncoder().encode(encryptedMsg))+"___");
		assertTrue(encryptedMsg.length>0);
	}
	@Test
	public void test_decrypt() throws Exception{
		final byte[] cryptedData = Files.readAllBytes(Paths.get(TEST_FILE));
		final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, mkAesKey());
		final byte[] decryptedMsg = cipher.doFinal(cryptedData);
		assertEquals(new String(decryptedMsg), TEST_TXT);
	}
	
	//@Test
	public void encrypt_php() throws Exception{
		//<?php echo openssl_encrypt("text to encrypt", "AES-128-ECB", "password______16"); ?>
		final byte[] aesKey = "password______16".getBytes(StandardCharsets.UTF_8);
		SecretKeySpec sc = new SecretKeySpec(aesKey, "AES");
		final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, sc);
		final byte[] encryptedMsg = cipher.doFinal("text to encrypt".getBytes());
		System.out.println("1___"+new String(Base64.getEncoder().encode(encryptedMsg))+"___");
		assertTrue(encryptedMsg.length>0);
	}
	@Test
	public void encrypt_php2() throws Exception{
		final byte[] aesKey = Crypto.AES_KEY.getBytes(StandardCharsets.UTF_8);
		SecretKeySpec sc = new SecretKeySpec(aesKey, "AES");
		final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, sc);
		final byte[] encryptedMsg = cipher.doFinal("text to encrypt".getBytes());
		System.out.println("3___"+new String(Base64.getEncoder().encode(encryptedMsg))+"___" );
		assertTrue(encryptedMsg.length>0);
	}
}
