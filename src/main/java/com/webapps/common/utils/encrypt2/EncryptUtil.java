package com.webapps.common.utils.encrypt2;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.ArrayUtils;

import sun.misc.BASE64Decoder;

public class EncryptUtil {

	
	// 转化字符串为十六进制编码
	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	// 转化十六进制编码为字符串
	public static String toStringHex2(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}
	
	/**
	    * 16进制字节数组转java字节数组
	    * @param b
	    * @return
	    */
	    public static byte[] hex2byte(byte[] b) {
	        if ((b.length % 2) != 0) {
	            throw new IllegalArgumentException("长度不是偶数");
	        }
	        byte[] b2 = new byte[b.length / 2];
	        for (int n = 0; n < b.length; n += 2) {
	            String item = new String(b, n, 2);
	            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节

	            b2[n / 2] = (byte) Integer.parseInt(item, 16);
	        }
	        b = null;
	        return b2;
	    }
	
	
	public static String toHex(byte b) {
		String result = Integer.toHexString(b & 0xFF);
		if (result.length() == 1) {
			result = '0' + result;
		}
		return result;
	}

	public static RSAPublicKey getPublicKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus, 16); // 此处为进制数
			BigInteger b2 = new BigInteger(exponent, 16);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] encrypt(byte[] bt_plaintext, String key) throws Exception {
		String exponent = "10001";
		PublicKey publicKey = getPublicKey(key, exponent);

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bt_encrypted = cipher.doFinal(bt_plaintext);
		return bt_encrypted;
	}

	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "ascii");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}


	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static String testSymmEncry(String plainText, byte[] key, EncryptAlgorithm alg) throws Exception {
		/* 测试对称加密方法的应用场景类 */
		byte[] encResult = encryt(EncryptStringUtils.getEncByteFromStr(plainText), key, alg);
		String encStr = EncryptStringUtils.byte2hex(encResult);
		return encStr;
	}

	public static String asymmEncry(String plainText, Key key, EncryptAlgorithm alg) throws Exception {
		/* 测试非对称加密方法的应用场景类 */
		// byte[] encResult =
		// encryt(EncryptStringUtils.getEncByteFromStr(plainText),key,alg);
		byte[] encResult = encryt(plainText.getBytes(), key, alg);
		String encStr = EncryptStringUtils.byte2hex(encResult);
		return encStr;
	}

	public static String testSymmDecry(String ciperText, byte[] key, EncryptAlgorithm alg) throws Exception {
		/* 测试解密方法的应用场景类 */
		byte[] decResult = decryt(EncryptStringUtils.getDecByteFromStr(ciperText), key, alg);
		String decStr = new String(decResult);
		return decStr;
	}

	public static String asymmDecry(String ciperText, Key key, EncryptAlgorithm alg) throws Exception {
		/* 测试非对称解密方法的应用场景类 */
		byte[] params = EncryptStringUtils.getDecByteFromStr(ciperText);
		byte[] decResult = decryt(params, key, alg);
		String decStr = new String(decResult);
		return decStr;
	}

	/**
	 * 对称加密方法
	 * 
	 * @param plainText
	 *            明文的16进制字节数组
	 * @param encrytKey
	 *            16进制的密钥数组
	 * @param alg
	 *            加密算法的枚举
	 * @return 加密结果，返回加密后的字节数组
	 * @throws Exception
	 */
	public static byte[] encryt(byte[] plainText, byte[] encrytKey, EncryptAlgorithm alg) throws Exception {
		Key k = toKey(encrytKey, alg);
		return encryt(plainText, k, alg);
	}

	/**
	 * 非对称加密方法
	 * 
	 * @param plainText
	 *            明文的16进制字节数组
	 * @param key
	 *            通过KeyPair获得的公钥
	 * @param alg
	 *            加密算法的枚举
	 * @return 加密结果，返回加密后的字节数组
	 * @throws Exception
	 */
	public static byte[] encryt(byte[] plainText, Key key, EncryptAlgorithm alg) throws Exception {
		Cipher cipher = Cipher.getInstance(alg.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, key);
		StringBuilder sb = new StringBuilder();
		byte[] dataReturn = null;
		for(int i=0;i<plainText.length;i+=117){
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(plainText, i,  
                    i + 100));  
            sb.append(new String(doFinal));  
            dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
		}
//		return cipher.doFinal(plainText);
		return dataReturn;
	}

	/**
	 * 对称加密解密方法
	 * 
	 * @param ciperText
	 *            密文的16进制字节数组
	 * @param decrytKey
	 *            16进制的密钥数组
	 * @param alg
	 *            加密算法的枚举
	 * @return 解密结果，返回解密后的字节数组
	 * @throws Exception
	 */
	public static byte[] decryt(byte[] ciperText, byte[] decrytKey, EncryptAlgorithm alg) throws Exception {
		Key k = toKey(decrytKey, alg);
		return decryt(ciperText, k, alg);
	}

	/**
	 * 非对称加密解密方法
	 * 
	 * @param ciperText
	 *            密文的16进制字节数组
	 * @param key
	 *            通过keypair得到的非对称加密私钥
	 * @param alg
	 *            加密算法的枚举
	 * @return 解密结果，返回解密后的字节数组
	 * @throws Exception
	 */
	public static byte[] decryt(byte[] ciperText, Key key, EncryptAlgorithm alg) throws Exception {
		Cipher cipher = Cipher.getInstance(alg.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(ciperText);
	}

	/**
	 * 获取对称加密算法算法的密钥 @param alg 加密算法枚举 @return 16进制的密钥数组 @throws
	 */
	public static byte[] getDefaultKey(EncryptAlgorithm alg) throws Exception {
		KeyGenerator keygen = KeyGenerator.getInstance(alg.getAlgorithm());
		SecretKey deskey = keygen.generateKey();
		return deskey.getEncoded();
	}

	/**
	 * 获取非对称加密算法的密钥 @param alg 加密算法枚举 @return 16进制的密钥数组 @throws
	 */
	public static KeyPair getDefaultKeyPair(EncryptAlgorithm alg) throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(alg.getAlgorithm());
		// 密钥位数
		keyPairGen.initialize(1024);
		// 密钥对
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}

	/**
	 * 通过key的字节数组和特定的算法得到用于加解密的密钥对象
	 * 
	 * @param key
	 *            密钥数组
	 * @param alg
	 *            加解密算法的枚举
	 * @return KEY
	 * @throws Exception
	 */
	private static Key toKey(byte[] key, EncryptAlgorithm alg) throws Exception {
		SecretKeySpec spec = new SecretKeySpec(key, alg.getAlgorithm());
		if (alg.getAlgorithm().indexOf("DES") > -1) {
			KeySpec desKey = null;
			SecretKeyFactory keyFactory = null;
			if ("DES".equals(alg.getAlgorithm())) {
				desKey = new DESKeySpec(key);
				keyFactory = SecretKeyFactory.getInstance(alg.getAlgorithm());
			} else {
				desKey = new DESedeKeySpec(key);
				keyFactory = SecretKeyFactory.getInstance(EncryptAlgorithm.ThreeDES.getAlgorithm());
			} // 将DESKeySpec对象转换成SecretKey对象
			SecretKey securekey = keyFactory.generateSecret(desKey);
			return securekey;
		}

		return spec;
	}

	public static String encodeBySHA(String str) throws Exception {
		MessageDigest sha1;
		sha1 = MessageDigest.getInstance("SHA1");
		sha1.update(str.getBytes()); // 先更新摘要
		byte[] digest = sha1.digest(); // 再通过执行诸如填充之类的最终操作完成哈希计算。在调用此方法之后，摘要被重置。

		/*
		 * 使用指定的 byte 数组对摘要进行最后更新，然后完成摘要计算。 也就是说，此方法首先调用 update(input)， 向 update
		 * 方法传递 input 数组，然后调用 digest()。
		 */
		String hex = toHex(digest);
		System.out.println("SHA1摘要:" + hex);
		return hex;
	}

	/**
	 * sha1 摘要转16进制
	 * 
	 * @param digest
	 * @return
	 */
	private static String toHex(byte[] digest) {
		StringBuilder sb = new StringBuilder();
		int len = digest.length;
		String out = null;
		for (int i = 0; i < len; i++) {
			// out = Integer.toHexString(0xFF & digest[i] + 0xABCDEF); //加任意
			// salt
			out = Integer.toHexString(0xFF & digest[i]);// 原始方法
			if (out.length() == 1) {
				sb.append("0");// 如果为1位 前面补个0
			}
			sb.append(out);
		}
		return sb.toString();
	}

	public static String transmissionEncrytion(String xml) {
		String result = "";
		// 接收方RSA公钥加密后的本次会话的AES密钥
		String part1 = "";
		// 发送方RSA私钥签的本次业务数据的签名
		String part2 = "";
		// AES加密的本次业务数据
		String part3 = "";
		try {
			byte[] asKey = getDefaultKey(EncryptAlgorithm.AES);
			String asKeyString = new String(asKey);
			part3 = testSymmEncry(xml, asKey, EncryptAlgorithm.AES);
			part2 = encodeBySHA(xml);
			KeyPair kp = getDefaultKeyPair(EncryptAlgorithm.RSA);
			part2 = asymmEncry(part2, kp.getPublic(), EncryptAlgorithm.RSAWithPadding);
			part1 = asymmEncry(asKeyString, kp.getPrivate(), EncryptAlgorithm.RSAWithPadding);
			result = part1 + "|" + part2 + "|" + part3;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
