package com.webapps.common.utils.encrypt;

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
import sun.misc.BASE64Encoder;

/**
 * 加解密统一接口，支持流行的对称和非对称算法 目前可以使用3DES,AES，RSA进行加解密
 */
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
	
	/*public static void main(String args[]){
		String huPublicKeyStr = "30818902818100C31A373ED1CFF32F076CB3EE8A451DF78E342D3BB387F647D4069400CA7265D7781C1837235C58840D71ACD58B1C0E4157109900B95D9F1E627A16FD2BA6D4DC3DC0165332FF67CFD40B83EAFE486EB38389A2C2D450658749D767BF597CBFE6BD217BBD4AEAE166F1F4720679316CC14C7C208D905B072F4E16923D49BB582F0203010001";
		PublicKey pubKey = main.getPublicKey(huPublicKeyStr.substring(15,huPublicKeyStr.length()-10),"10001");
		String testStr="我就是一条测试的数据，不要打我";
		String jiami;
		String aeskey = "vEedcYzazBaFwqMKHsHh4A==";
		try {
			jiami = asymmEncry(aeskey, pubKey, EncryptAlgorithm.RSAWithPadding);
			System.out.println(jiami);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
//	public static void main1(String args[]){
//		String testStr="我就是一条测试的数据，不要打我";
//		BASE64Encoder encoder = new BASE64Encoder();
//		//公钥串
//		String publickey = "30819f300d06092a864886f70d010101050003818d0030818902818100cddbb059a6587be69b474983e0b15d0faafa0875667fc5b496bf9582c4c7bf8189e93f81b5412381285b605a2d27223274e069612d9779299b52177e229bbedad20fc5e532d41c476e083124f08e01aa6e9eab6abac899000d9dab5a23f3399da786828fcfe56af6604e6fe6215e6808836e361d4027d98173cafb57872c16050203010001";
//		try {
//			//获取公钥对象
//			PublicKey qkPublicKey = main.getPublicKey(encoder.encode(EncryptStringUtils.hex2byte(publickey.getBytes())));
//			//加密
//			String jiami = testAsymmEncry(testStr, qkPublicKey, EncryptAlgorithm.RSAWithPadding);
//			System.out.println("公钥加密："+jiami);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void main(String args[]){
		String publickey = "30819f300d06092a864886f70d010101050003818d0030818902818100cddbb059a6587be69b474983e0b15d0faafa0875667fc5b496bf9582c4c7bf8189e93f81b5412381285b605a2d27223274e069612d9779299b52177e229bbedad20fc5e532d41c476e083124f08e01aa6e9eab6abac899000d9dab5a23f3399da786828fcfe56af6604e6fe6215e6808836e361d4027d98173cafb57872c16050203010001";
		String privatekey = "30820278020100300d06092a864886f70d0101010500048202623082025e02010002818100cddbb059a6587be69b474983e0b15d0faafa0875667fc5b496bf9582c4c7bf8189e93f81b5412381285b605a2d27223274e069612d9779299b52177e229bbedad20fc5e532d41c476e083124f08e01aa6e9eab6abac899000d9dab5a23f3399da786828fcfe56af6604e6fe6215e6808836e361d4027d98173cafb57872c1605020301000102818100b49b5bdb2ba05203fe93caa1d34e88d12836799956b61f24d3657eacde2c498e430acad6e7c920773e8afcbbe1058671322d7906f35303d0471efcd1835795571f3b8e6e48cc84ae9b9b34100d1248373227d59547b150232f207bb92b3dc24f04a8c3a180caa61eb37cd8d82ae7c675589f810e7422ef2467feae933c854b01024100e92d014ce68079b835fca717dab1e0b5313ee4496e9c511cfb1c1aa5b32c3fa60166f56840e4bc22c2122c5a48530592057bc213a7442008639922679498b3e1024100e20226430c633f46c2ac72a9723566af0aecb689259ab0befc282ae88992955bedc9f561e1d9d634b75b8241dc326cebbc1b5173b56e84056c1cd6bab3a4e6a5024100c2bd518eb61baa8c8c9a1bbb326cc8accb1a2972a2ac6e99f3a67f22d0472e492876a292954ff3485c28ed5de17564fb9514ccfbb9008e7833265bb382491e41024100adbed5474a28aa266dd640b7bf130a839ab46b87779f3a2d3890709c1fd6ce77c39034d230dc546e665f34fe0897c3605e55ea02380263ffff231478a74e27f90240700d7a562e0e263198e94841f8d9ee5b0333472352448f6ab0f0469a56f020c60cd80ebb966363adf99c40e3c6015e8b20e663672ca3ea32762aa1835010cf7a";
		BASE64Encoder encoder = new BASE64Encoder();
		String testStr="vEedcYzazBaFwqMKHsHh4A==";
		System.out.println();
		try {
			PublicKey qkPublicKey = main.getPublicKey(encoder.encode(EncryptStringUtils.hex2byte(publickey.getBytes())));
//			System.out.println(encoder.encode(qkPublicKey.getEncoded()));
			PrivateKey qkPrivateKey = main.getPrivateKey(encoder.encode(EncryptStringUtils.hex2byte(privatekey.getBytes())));
//			System.out.println(encoder.encode(qkPrivateKey.getEncoded()));
			String jiami = asymmEncry(testStr, qkPublicKey, EncryptAlgorithm.RSAWithPadding);
			System.out.println("公钥加密："+jiami);
			String jiemiKey = asymmDecry(jiami, qkPrivateKey, EncryptAlgorithm.RSAWithPadding);
			System.out.println("私钥解密："+jiemiKey);
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
			jiami = asymmEncry(testStr, qkPrivateKey, EncryptAlgorithm.RSAWithPadding);
			System.out.println("私钥加密："+jiami);
			jiemiKey = asymmDecry(jiami, qkPublicKey, EncryptAlgorithm.RSAWithPadding);
			System.out.println("公钥解密："+jiemiKey);
			//私钥解密
			String aesKeyParam = "940FE1D80245C093AA31C19F28105187777BF9C0236FB18DB1587EDC9727247C29A01F28AE05C3A4EF2B55B71C05376C0DEFFA8B93237B3D3A89E4D12D2DD9BBFA202C044CCA6D633490CA87A0DF64E331BF7929C2575242951BD00794B43EC8A87DDD8760AD7EAD51ECB55818D8899D8AF8AE51E7B73464D3A2870EDFBE4058";
			String xmlParam = "zG1EhZdpCvn5UNCzrdGdX/ZvvtCRhvaRTTBZbMDzhLy2wWN4itiSke6MfENOHww69aZM92lA7nUB9CsOBvyLo2xNQziNvmQmZFwoTA58MHXlht3QGPq1zpYwXfKiMZFK67ySi5zoYRDLgtF4aEBM3h0PwLNzw1yPhHBCHs7e51vJdV/ebtuz75QGeGhuK7lw3K+tBCtOOjP1IzRBugBZhn6aOcu8nLt20y+sFzCL0CUj1Gl06fEQhGDebHx9TsEGKeVtrh+F11AlrHevpOLfPaA1rBG596+5oXFFUsDjsRAMGVmaicj7hKdcFOxY/YLJBbsql7Q+LAXycDeIYRWC+lDSY45AJHego5qgLM4pUq+C6Mnz/1wRiDiuvZaXlzXx6LChxg8fmZyRbgq1Yzg7ahruUzbdbHcXfaJyGQKHa+j3H0HyKVDdNrHoJaicossvfpo5y7ycu3bTL6wXMIvQJeQdBlN2KfEmIMDGTv2/abb6Bx/rEgQxgOM1u+KPzWiloMD1p+MjAQabz2FNvxEpA4As+gUjkG+/Amh1LCrmi3nlQ4lNQKy0zXLQGJ9fZGPupoVck4q8vCNzmU3rQLk2hompEuT7WHYQ0nRuw42UKtsjCrPA2UwuMkkoyb6JTVSK9zCDc8FBoQOMnUcU9iWuRIaqgCyJeLNOhn47ER3+LMQQCI25bbykt+Wn49peGcTyBBQ7fFLG68mP0OO2dp78l3EciiiAufhUl0mHxzS19vwEOnmgOv69euHtmkA261Waxu9tNyDlCQ85L9mMp9zQVFLScH0YghCckWLH8jwqsWoqPEz+bpIugnJ97M9G715pR6SpTxDwQPuUAa70hpO7HMyx8soeXNJiaicblhPsLdMRK3WTGRp3SllPfwwuZNjUnuoN2Afd9a8CbGhh5tG2Xad7btpBsn6Io2t60/c9xCDYJux4+JAQI5CDUA4K1UCkHgYmK0JTeg+yreFqI727xrrORLN22Tx6AoYAC6l67L2y0MEZBHG/YmOcerjU/2xMBi+av03m1JD6Cy3zfPg2kkccHRyqoNHyn1RdwbAL0D0gJbuSrjIA/nGXkRFKqgwQnirnQ+kXHFNgIE4LMyFRbX4Jb62y182afw+xOOx6RsXANbFIUmL1p3Hs4XjHAEKiGu5TNt1sdxd9onIZAodr6NYPJwX0sELujIU5tcfydaQEOnmgOv69euHtmkA261WawNDmqXMEojUrD8LlanQUUlD90jCQVAVhTe5grXFKt1u+HSwsEsW2mueB4k3VMMwjIbtibcPzof2rLsEbJpYNAIhK47bisepGKlRyxbtKps7WX3DEqNLZPQ5FTDnaKKXjku05FhuhYB2A3iGucnE07P8XXqeQGP3ODZyvJIaaRv3lrBzid//r5HH60WflFDDElE19wKqiQtX5puiayqT0MoHFCmHCsEj2aN7vuLNApA14oxT3H/7DKSNwfOXPDWhuVjIwWgm+2Q36yB0VZ9osONoO6mH0Jf5WuVcdHxK77YQ=";
			String aesKey = asymmDecry(aesKeyParam, qkPrivateKey, EncryptAlgorithm.RSAWithPadding);
			System.out.println("公钥解密1："+aesKey);
			System.out.println(AESEncrypt.decrypt(xmlParam, aesKey));
			String hello = "hello,world";
			String hello1 = AESEncrypt.encrypt(hello, "4eac3f22c8b0dff07dde989efa7e6e02");
			System.out.println("明文："+hello);
			System.out.println("AES加密："+hello1);
			System.out.println("AES解密："+AESEncrypt.decrypt(hello1, "4eac3f22c8b0dff07dde989efa7e6e02"));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	
//	@SuppressWarnings("unused")
//	public static void main(String[] args) throws Exception{
//		String str = "test123456";
//		BASE64Encoder encoder = new BASE64Encoder();
//		BASE64Decoder decoder = new BASE64Decoder();
//		String qkPublicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDN27BZplh75ptHSYPgsV0PqvoIdWZ/xbSWv5WCxMe/gYnpP4G1QSOBKFtgWi0nIjJ04GlhLZd5KZtSF34im77a0g/F5TLUHEduCDEk8I4Bqm6eq2q6yJkADZ2rWiPzOZ2nhoKPz+Vq9mBOb+YhXmgIg242HUAn2YFzyvtXhywWBQIDAQAB";
//		String qkPrivateKeyStr = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAM3bsFmmWHvmm0dJg+CxXQ+q+gh1Zn/FtJa/lYLEx7+Biek/gbVBI4EoW2BaLSciMnTgaWEtl3kpm1IXfiKbvtrSD8XlMtQcR24IMSTwjgGqbp6rarrImQANnataI/M5naeGgo/P5Wr2YE5v5iFeaAiDbjYdQCfZgXPK+1eHLBYFAgMBAAECgYEAtJtb2yugUgP+k8qh006I0Sg2eZlWth8k02V+rN4sSY5DCsrW58kgdz6K/LvhBYZxMi15BvNTA9BHHvzRg1eVVx87jm5IzISum5s0EA0SSDcyJ9WVR7FQIy8ge7krPcJPBKjDoYDKph6zfNjYKufGdVifgQ50Iu8kZ/6ukzyFSwECQQDpLQFM5oB5uDX8pxfaseC1MT7kSW6cURz7HBqlsyw/pgFm9WhA5LwiwhIsWkhTBZIFe8ITp0QgCGOZImeUmLPhAkEA4gImQwxjP0bCrHKpcjVmrwrstoklmrC+/Cgq6ImSlVvtyfVh4dnWNLdbgkHcMmzrvBtRc7VuhAVsHNa6s6TmpQJBAMK9UY62G6qMjJobuzJsyKzLGilyoqxumfOmfyLQRy5JKHaikpVP80hcKO1d4XVk+5UUzPu5AI54MyZbs4JJHkECQQCtvtVHSiiqJm3WQLe/EwqDmrRrh3efOi04kHCcH9bOd8OQNNIw3FRuZl80/giXw2BeVeoCOAJj//8jFHinTif5AkBwDXpWLg4mMZjpSEH42e5bAzNHI1JEj2qw8EaaVvAgxgzYDruWY2Ot+ZxA48YBXosg5mNnLKPqMnYqoYNQEM96";
//		PublicKey qkPublicKey = main.getPublicKey(qkPublicKeyStr);
//		String publickeyInString = "";
//		byte[] publicKey=qkPublicKey.getEncoded();
//		for (int i = 0; i < publicKey.length; i++) {
//			publickeyInString=publickeyInString+toHex(publicKey[i]);
//		}
//		System.out.println("公钥："+publickeyInString);
//		PrivateKey qkPrivateKey = main.getPrivateKey(qkPrivateKeyStr);
//		String privatekeyInString = "";
//		byte[] privateKey=qkPrivateKey.getEncoded();
//		for (int i = 0; i < privateKey.length; i++) {
//			privatekeyInString=privatekeyInString+toHex(privateKey[i]);
//		}
//		System.out.println("私钥："+privatekeyInString);
//		//1.公钥加密
//		String hrKeyStr = "30818902818100C31A373ED1CFF32F076CB3EE8A451DF78E342D3BB387F647D4069400CA7265D7781C1837235C58840D71ACD58B1C0E4157109900B95D9F1E627A16FD2BA6D4DC3DC0165332FF67CFD40B83EAFE486EB38389A2C2D450658749D767BF597CBFE6BD217BBD4AEAE166F1F4720679316CC14C7C208D905B072F4E16923D49BB582F0203010001";
//		String qkKeyStr = "30819f300d06092a864886f70d010101050003818d0030818902818100cddbb059a6587be69b474983e0b15d0faafa0875667fc5b496bf9582c4c7bf8189e93f81b5412381285b605a2d27223274e069612d9779299b52177e229bbedad20fc5e532d41c476e083124f08e01aa6e9eab6abac899000d9dab5a23f3399da786828fcfe56af6604e6fe6215e6808836e361d4027d98173cafb57872c16050203010001";
//		PublicKey pub1 = getPublicKey(hrKeyStr,"10001");
//		PublicKey qkpub = getPublicKey(qkKeyStr,"10001");
//		System.out.println(encoder.encode(qkpub.getEncoded()));
////		PublicKey pub = getPublicKey(encoder.encode(EncryptStringUtils.hex2byte(hrKeyStr.getBytes())));
////		PublicKey pubKey = getPublicKey(encoder.encode(EncryptStringUtils.hex2byte(publickeyInString.getBytes())));
//		PublicKey pubKey = getPublicKey(publickeyInString,"10001");
//		String encrytStr = testAsymmEncry(str, pubKey, EncryptAlgorithm.RSAWithPadding);
//		System.out.println("加密："+encrytStr);
//		//2.私钥解密
//		PrivateKey priKey = getPrivateKey(encoder.encode(EncryptStringUtils.hex2byte(privatekeyInString.getBytes())));
//		String decrytStr = testAsymmDecry(encrytStr, priKey, EncryptAlgorithm.RSAWithPadding);
//		System.out.println("解密："+decrytStr);
//	}

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
