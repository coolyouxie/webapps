package com.webapps.common.entity.encrypt;

import java.security.MessageDigest;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密算法
 * 
 */
public class AESEncrypt {

	/**
	 * 密钥算法
	 */
	public static final String aes_key_algorithm = "AES";

	/**
	 * 加解密算法/工作模式/填充方式
	 */
	public static final String cipher_algorithm = "AES/ECB/PKCS5Padding";

	/**
	 * AES加密: 使用原始字符串密钥，经MD5转成16字节密钥后，对字符串进行AES/ECB加密，PKCS5Padding。
	 * 
	 * @param src
	 *            ：
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String src, String key) throws Exception {
		String str = byte2Base64(encrypt(src.getBytes("UTF-8"), getKeyBytes(key)));
		return str;

	}

	/**
	 * AES加密: 使用原始字符串密钥，经MD5转成16字节密钥后，对字符串进行AES/ECB加密，PKCS5Padding。
	 * 
	 * @param src
	 *            ：
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String src, byte key) throws Exception {
		String str = byte2Base64(encrypt(src.getBytes(), getKeyBytes(key)));
		return str;
	}

	/**
	 * 加密：使用原始密钥，经MD5转成16字节密钥后，对待加密字节进行AES/ECB加密，PKCS5Padding。
	 * 
	 * @param src
	 *            ：待加密字节
	 * @param key
	 *            ：原始密钥字节
	 * @return：加密后的字节
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		if (key == null) {
			System.out.print("Key为空null");
			return null;
		}

		// 对原始密钥做MD5算hash值，得到16字节密钥
		MessageDigest alg = MessageDigest.getInstance("MD5");
		alg.update(key);
		byte[] key16 = alg.digest();

		// byte[] raw = key.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(key16, aes_key_algorithm);
		Cipher cipher = Cipher.getInstance(cipher_algorithm);// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(src);

		return encrypted;
	}

	/**
	 * 加密：使用原始密钥，经MD5转成16字节密钥后，对待加密字节进行AES/ECB加密，PKCS5Padding。
	 * 
	 * @param src
	 *            ：待加密字节
	 * @param key
	 *            ：原始密钥字节
	 * @return：加密后的字节
	 * @throws Exception
	 */
	private static byte[] encrypt2(byte[] src, byte[] key16) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key16, aes_key_algorithm);
		Cipher cipher = Cipher.getInstance(cipher_algorithm);// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(src);

		return encrypted;
	}

	/**
	 * AES解密：使用原始字符串密钥，经MD5转成16字节密钥后，对加密后的base64字符串解密
	 * 
	 * @param base64src
	 *            ：base64编码的待解密字符串
	 * @param key
	 *            ：原始密钥
	 * @return：解密后的字符串
	 * @throws Exception
	 */
	public static String decrypt(String base64src, String key) throws Exception {
		byte[] en = Base64.decode(base64src);
		byte[] b = decrypt(en, getKeyBytes(key));
		return new String(b, "UTF-8");
	}

	/**
	 * AES解密：使用原始字符串密钥，经MD5转成16字节密钥后，对加密后的base64字符串解密
	 * 
	 * @param base64src
	 *            ：base64编码的待解密字符串
	 * @param key
	 *            ：原始密钥
	 * @return 解密后的字节
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {

		try {
			// 判断Key是否正确
			if (key == null) {
				System.out.print("Key为空null");
				return null;
			}

			// 对原始密钥做MD5算hash值，得到16字节密钥
			MessageDigest alg = MessageDigest.getInstance("MD5");
			alg.update(key);
			byte[] key16 = alg.digest();

			SecretKeySpec skeySpec = new SecretKeySpec(key16, aes_key_algorithm);
			Cipher cipher = Cipher.getInstance(cipher_algorithm);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			try {
				byte[] original = cipher.doFinal(src);
				return original;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}

		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * 将字节转换成base64编码
	 * 
	 * @param b
	 *            ：待转换字节
	 * @return
	 */
	public static String byte2Base64(byte[] b) {
		return Base64.encode(b);
	}

	/**
	 * 将字节转换成base64编码
	 * 
	 * @param b
	 *            ：待转换字节
	 * @return
	 */
	public static String byte2Base64(byte b) {
		byte[] bytes = new byte[1];
		bytes[0] = b;
		return Base64.encode(bytes);
	}

	/**
	 * 计算16位长的密码byte值,对原始密钥做MD5算hash值
	 * 
	 * @param strKey
	 *            ：原始密钥
	 * @return：16字节密钥
	 * @throws Exception
	 */
	public static byte[] getKeyBytes(String strKey) throws Exception {
		if (null == strKey || strKey.length() < 1)
			throw new Exception("key is null or empty!");

		java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");
		alg.update(strKey.getBytes());

		byte[] bkey = alg.digest();
		// System.out.println("md5key.length=" + bkey.length);
		// System.out.println("md5key=" + byte2hex(bkey));

		return bkey;
	}

	/**
	 * 计算16位长的密码byte值,对原始密钥做MD5算hash值
	 * 
	 * @param byteKey
	 *            ：原始密钥
	 * @return：16字节密钥
	 * @throws Exception
	 */
	public static byte[] getKeyBytes(byte byteKey) throws Exception {
		java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");
		alg.update(byteKey);

		byte[] bkey = alg.digest();
		// System.out.println("md5key.length=" + bkey.length);
		// System.out.println("md5key=" + byte2hex(bkey));

		return bkey;
	}

	public static void main(String[] args) throws Exception {

		/*
		 * 
		 * 此处使用AES-128-ECB加密模式，key需要为16位。
		 */
		int cKey = 0x86;

		// 密钥
		String key = "123456789012345600";
		int d = (int) (Math.random() * 1000);
		key = String.valueOf((new Date()).getTime()) + d;
		// 需要加密的字串
		String cSrc = "青客官网www.qk365.com";
		System.out.println(cSrc);
		KeyGenerator kg = null;
		kg = KeyGenerator.getInstance("AES");
		kg.init(128);
		SecretKey sk = kg.generateKey();
		String randomKey = Base64.encode(sk.getEncoded());

		// // 加密（密钥采用字节）
		// String enString = AESEncrypt.encrypt(cSrc, (byte) (cKey & 0xFF));
		// System.out.println("加密1后的字串是：" + enString);

		// 密钥采用字符串
		String enString2 = AESEncrypt.encrypt(cSrc, "aaStjYkLGgy2bwA8mJOBhw==");
		System.out.println("加密2后的字串是------------：" + enString2);

		// 解密（密钥采用字符串）
		String DeString = AESEncrypt.decrypt(enString2, "aaStjYkLGgy2bwA8mJOBhw==");
		System.out.println("解密2后的字串是：" + DeString);

		// // 加密
		// byte[] en = AESEncrypt.encrypt2(cSrc.getBytes(), getKeyBytes((byte)
		// (cKey & 0xFF)));
		// System.out.println("加密后的字节是：" + en);
		//
		// byte[] bytes = new byte[] { -36, -85, -91, -48, -23, -22, -30, 120,
		// 115, -91, 28, -75, 1, 105, -92, 106 };
		//
		// byte[] bytes2 = new byte[] { 49, 50, 51, 52, 53, 54, 55, 56, 57, 48
		// };
		//
		// byte[] bytes3 = encrypt2(bytes2, bytes);
		//
		// String a = byte2Base64((byte) (cKey & 0xFF));
		// byte b = a.getBytes()[0];

	}
}
