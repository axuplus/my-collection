package com.utils.util.encrypt;


import com.utils.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RSAUtils implements EnDecrpt {

	private static final String SIGN_ALGORITHMS = "NONEwithRSA";

	private PublicKey publicKey;
	private PrivateKey privateKey;
	private Cipher decryptCipher;
	private Cipher encrptCipher;
	private Signature sign;
	private Signature check;

	/**
	 * 设置rsa加密所需要的公钥
	 */
	@Override
	public void setPublicKey(byte[] publicKeyBytes) {
		try {
			// 取得公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("rsa");
			publicKey = keyFactory.generatePublic(x509KeySpec);
			encrptCipher = Cipher.getInstance("rsa");
			encrptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
			check = Signature.getInstance(SIGN_ALGORITHMS);
			check.initVerify(publicKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 设置rsa解密所需要的密钥
	 */
	@Override
	public void setPrivateKey(byte[] privateKeyBytes) {
		try {
			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("rsa");
			privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
			decryptCipher = Cipher.getInstance("rsa");
			decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
			sign = Signature.getInstance(SIGN_ALGORITHMS);
			sign.initSign(privateKey);
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setKey(byte[] key) {
		throw new RuntimeException("rsa加密方法，不能设置对称密钥");
	}

	@Override
	public byte[] encrypt(byte[] src) {
		try {
			return encrptCipher.doFinal(src);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] decrypt(byte[] src) {
		try {
			return decryptCipher.doFinal(src);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] sign(byte[] src) {
		try {
			sign.update(src);
			return sign.sign();
		} catch (SignatureException e) {
			return new byte[0];
		}
	}

	@Override
	public boolean check(byte[] src, byte[] sign) {
		try {
			check.update(src);
			return check.verify(sign);
		} catch (SignatureException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void buildKey() throws IOException {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(1024);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			Key publicKey = keyPair.getPublic();
			Key privateKey = keyPair.getPrivate();
			System.out.println("公钥是：" + StringUtils.toHexString(publicKey.getEncoded()));
			System.out.println("私钥是：" + StringUtils.toHexString(privateKey.getEncoded()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		new RSAUtils().buildKey();
	}
}
