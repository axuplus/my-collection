package com.utils.util.encrypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class AesUtils {

	private Cipher decryptCipher;
	private Cipher encrptCipher;

	public AesUtils(byte[] key) {
		if (key.length == 16) {
			try {
				SecretKey aeskey = new SecretKeySpec(key, "AES"); // 加密
				encrptCipher = Cipher.getInstance("AES");
				encrptCipher.init(Cipher.ENCRYPT_MODE, aeskey);
				decryptCipher = Cipher.getInstance("AES");
				decryptCipher.init(Cipher.DECRYPT_MODE, aeskey);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("默认只支持16byte的密钥");
		}
	}

	public void setPublicKey(byte[] publicKeyBytes) {
		throw new RuntimeException("AES为对称加密，无公钥");
	}

	public void setPrivateKey(byte[] privateKeyBytes) {
		throw new RuntimeException("AES为对称加密，无私钥");
	}

	public byte[] encrypt(byte[] src) {
		try {
			return encrptCipher.doFinal(src);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] decrypt(byte[] src) {
		try {
			return decryptCipher.doFinal(src);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	public void setKey(byte[] key) {
	}

	public byte[] sign(byte[] src) {
		throw new RuntimeException("aes无签名功能");
	}

	public boolean check(byte[] src, byte[] sign) {
		throw new RuntimeException("aes无签名功能");
	}
}
