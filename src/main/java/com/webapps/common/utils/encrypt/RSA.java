package com.webapps.common.utils.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {

	private static Logger logger = LoggerFactory.getLogger(RSA.class.getName());
	private static final String ALGORITHM = "RSA";
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * @param algorithm
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static PublicKey getPublicKeyFromX509(String algorithm, String bysKey)
			throws Exception {
		byte[] decodedKey = Base64.decode(bysKey);
		X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);

		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		return keyFactory.generatePublic(x509);
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 用公钥加密
	 * 
	 * @param content：待加密的字符串
	 * @param publickey:公钥
	 * @return：加密后的字符串(转成Base64格式返回)
	 */
	public static String encrypt(String content, String publickey) {
		try {
			PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, publickey);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubkey);
			byte output[] = content.getBytes("UTF-8");
			int inputLen = output.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(output, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(output, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			String s = new String(Base64.encode(encryptedData));
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用私钥解密
	 * 
	 * @param content：加密的字符串
	 * @param privateKey：私钥
	 * @return：解密后的字符串
	 * @throws Exception
	 */
	public static String decrypt(String content, String privateKey) throws Exception {
		PrivateKey prikey = getPrivateKey(privateKey);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, prikey);
		InputStream ins = new ByteArrayInputStream(Base64.decode(content));
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		// rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
		byte[] buf = new byte[128];
		int bufl;
		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;
			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; i++) {
					block[i] = buf[i];
				}
			}
			writer.write(cipher.doFinal(block));
		}
		return new String(writer.toByteArray(), "utf-8");
	}

	/**
	 * 使用私钥签名字符串
	 * 
	 * @param content：待签名的字符串
	 * @param privateKey：私钥
	 * @return：签名后的字符串
	 */
	public static String sign(String content, String privateKey) {
		String charset = "UTF-8";
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(charset));

			byte[] signed = signature.sign();

			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用公钥验证签名
	 * 
	 * @param content：待签名的字符串
	 * @param sign：私钥签名后的数据
	 * @param publicKey：公钥
	 * @return：true: 签名验证成功；false: 签名验证失败
	 */
	public static boolean doCheck(String content, String sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes("utf-8"));
			logger.info("doCheck content : " + content);
			logger.info("doCheck sign:   " + sign);
			boolean bverify = signature.verify(Base64.decode(sign));
			logger.info("doCheck bverify = " + bverify);
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getMD5(String content) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(content.getBytes());
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static void main(String[] args) {
		 try {
		 KeyPair kp = EncryptUtil.getDefaultKeyPair(EncryptAlgorithm.RSA);
		 PublicKey key1 = kp.getPublic();
		 PrivateKey key2 = kp.getPrivate();
		 String keyStr1 = Base64.encode(key1.getEncoded());
		 String keyStr2 = Base64.encode(key2.getEncoded());
		 System.out.println("publicKey:"+keyStr1);
		 System.out.println("privateKey:"+keyStr2);
		 } catch (Exception e1) {
		 e1.printStackTrace();
		 }

		 String serverPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHjtVGMmt2CyOy/bbE7F5gW6ympHnA9DECuFDTDQW9wNiU/oVPx+5+dSD6vuj/v0lwCyH0U1TEwXBygc+8k88qT+qWTwIlmygm5rRtsBzdUjKLxxr8Tn7axla55a7Nh9y0J4bdMGgz737yKtpDUxmlPfiM47agrkUi4CuRO745tQIDAQAB";
		 String serverPriKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIeO1UYya3YLI7L9tsTsXmBbrKakecD0MQK4UNMNBb3A2JT+hU/H7n51IPq+6P+/SXALIfRTVMTBcHKBz7yTzypP6pZPAiWbKCbmtG2wHN1SMovHGvxOftrGVrnlrs2H3LQnht0waDPvfvIq2kNTGaU9+IzjtqCuRSLgK5E7vjm1AgMBAAECgYBa9F5rpEbwRFcmsQ+iH8rPMpOsmG1NJ0t/PLaWdYVlpXBswD4oosiGNwby14e0md+newDEU+lrvzM40ZrWOALmsP7qWEb9ERybEXnMUPgeJl/72KYgzLn0ByKEQhdUesYt8cAgdoYD7SiuImqs0wtd+iXDn19UYrTi/r+WGcX0HQJBAMw6gcyepJpKrMpqJ25UFTYO+27moC19Y+LdTgSONe6fS9w8p0YZRgdsIV9Y3gCW05WKxu+OANpmIQeVOZGhwMsCQQCp6+uBbpYnOgHEv8soRtPp34SUt0tZ+pWjePuEGRufrMX7526BN5BihlXAaQlCO3YsAIARkIzEWUcFsliQlR9/AkAUZ7QYUbF4iQWCo+CUsWn9ILoWdoyCfwi/3gSxh9Pzp47YzmaYJmZMz4z2DdcAkBFL27XMsY98QsACFfLOji7JAkBPn6ON1To7S21EuvMB/p6SuxCvd2yxz0CLh8ekUPemzRlBP2OC3XylDnnkXdPe22o2mE1q7ado4sTrIHVr2tUVAkBlVwD125aIxTvzFgf5t6fiOUpAT6Zotu9gGHEN5AanwhOG3WoEatZewZYwFKbk4VVQKsgNboSJOyetQn+Flh7n";
		String testStr = "fxKNEcJfn0cNNRnwbSsHTMGfsIkWOMecKgylKAbLSma8et5jPSnDaaSseoI711pO5a9M+YmFRZsJr+MQnTEAvNIR6HflagxbW3uE1CZNIuzoSfQs+szfkXUlQAAlrYSR8oGu+H2YNkhLl8k7Dkvop7XtFcrjouziJVvbD1dv0W8=";
		 try {
			System.out.println(RSA.decrypt(testStr,serverPriKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
