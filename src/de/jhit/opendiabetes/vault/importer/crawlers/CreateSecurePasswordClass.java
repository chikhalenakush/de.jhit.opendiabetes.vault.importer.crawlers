package de.jhit.opendiabetes.vault.importer.crawlers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CreateSecurePasswordClass {

	public String[] CreateHash(String username, String password, Logger logger) throws GeneralSecurityException, IOException {
		// TODO Auto-generated method stub
		logger.info("Inside Class CreateSecurePasswordClass, Method CreateHash");
		String userkey = username;
		/*
		 * if (password == null) { throw new
		 * IllegalArgumentException("Run with -Dpassword=<password>"); }
		 */

		// The salt (probably) can be stored along with the encrypted data
		byte[] salt = new String("12345678").getBytes();

		// Decreasing this speeds down startup time and can be useful during
		// testing, but it also makes it easier for brute force attackers
		int iterationCount = 40000;
		// Other values give me java.security.InvalidKeyException: Illegal key
		// size or default parameters
		int keyLength = 128;
		SecretKeySpec key = createSecretKey(userkey.toCharArray(), salt, iterationCount, keyLength,logger);

		String originalPassword = password;
		//System.out.println("Original password: " + originalPassword);
		String encryptedPassword = encrypt(originalPassword, key);
		//System.out.println("Encrypted password: " + encryptedPassword);
		String decryptedPassword = decrypt(encryptedPassword, key,logger);
		//System.out.println("Decrypted password: " + decryptedPassword);
		return new String[] { encryptedPassword, decryptedPassword };

	}

	public static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength, Logger logger)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		logger.info("Inside Class CreateSecurePasswordClass, Method createSecretKey");
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
		SecretKey keyTmp = keyFactory.generateSecret(keySpec);
		return new SecretKeySpec(keyTmp.getEncoded(), "AES");
	}

	static String encrypt(String property, SecretKeySpec key)
			throws GeneralSecurityException, UnsupportedEncodingException {
		Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		pbeCipher.init(Cipher.ENCRYPT_MODE, key);
		AlgorithmParameters parameters = pbeCipher.getParameters();
		IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
		byte[] cryptoText = pbeCipher.doFinal(property.getBytes("UTF-8"));
		byte[] iv = ivParameterSpec.getIV();
		return base64Encode(iv) + ":" + base64Encode(cryptoText);
	}

	private static String base64Encode(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	public static String decrypt(String string, SecretKeySpec key, Logger logger) throws GeneralSecurityException, IOException {
		logger.info("Inside Class CreateSecurePasswordClass, Method decrypt");
		String iv = string.split(":")[0];
		String property = string.split(":")[1];
		Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
		return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
	}

	private static byte[] base64Decode(String property) throws IOException {
		return Base64.getDecoder().decode(property);
	}
}
