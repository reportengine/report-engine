package com.redhat.reportengine.server.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 26, 2013
 */
public class Password {
	protected static final int DEFAULT_NEXT_BYTES_SIZE = 16; //16 bytes == 128 bits
	public static final int hashIterations = 1024;
	public static final String hashAlgorithmName = "SHA-512"; //Available Options: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512 

	public String getRandomSalt(){
		return getRandomSalt(DEFAULT_NEXT_BYTES_SIZE);	
	}

	public String getRandomSalt(int nextByteSize){
		byte[] bytes = new byte[nextByteSize];
		SecureRandom rnd =  new SecureRandom();
		rnd.setSeed(bytes);
		rnd.nextBytes(bytes);
		return Base64.encodeBase64String(bytes);
	}
	
	public String getSaltedPassword(String plainPassword, String salt) throws NoSuchAlgorithmException{
		return getSaltedPassword(plainPassword, salt, hashIterations, hashAlgorithmName);
	}
	
	public String getSaltedPassword(String plainPassword, String salt, int hashIterations, String algorithm) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.reset();
		digest.update(salt.getBytes());
        byte byteData[] = digest.digest(plainPassword.getBytes());
        for (int i = 0; i < hashIterations; i++) {
            digest.reset();
            byteData = digest.digest(byteData);
        }
        return Base64.encodeBase64String(byteData);        
	}
}
