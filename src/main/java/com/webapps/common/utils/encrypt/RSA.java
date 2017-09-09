package com.webapps.common.utils.encrypt;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
	 * @param ins
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws AlipayException
	 */
	private static PublicKey getPublicKeyFromX509(String algorithm,
			String bysKey) throws NoSuchAlgorithmException, Exception {
		byte[] decodedKey = Base64.decode(bysKey);
		X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);

		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		return keyFactory.generatePublic(x509);
	}


	/**
	* 得到私钥
	* @param key 密钥字符串（经过base64编码）
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
//			byte[] output = cipher.doFinal(plaintext);
//			String s = new String(Base64.encode(output));

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
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
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
	 * @param content：待签名的字符串
	 * @param privateKey：私钥
	 * @return：签名后的字符串
	 */
	public static String sign(String content, String privateKey) {
		String charset = "UTF-8";
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);

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
	 * @param content：待签名的字符串
	 * @param sign：私钥签名后的数据
	 * @param publicKey：公钥
	 * @return：true: 签名验证成功；false: 签名验证失败
	 */
	public static boolean doCheck(String content, String sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode(publicKey);
			PublicKey pubKey = keyFactory
					.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes("utf-8"));
			logger.info("doCheck content : "+content);
			logger.info("doCheck sign:   "+sign);
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
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
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
	
	
	/*public static void main(String[] args) {
		String str= "-954872223";
      String privatekey= "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMkwOyyarMoCEcyZKo6cY8kCcQIJHBv2nn64L5/v/cNAp5gpj9kIvdrimgt7H4bYg/RQCR+kVEaWqNcdu1mJxVZpUZHPbxs+nliaIe3e0Y50ff4RoLjsA9fj+zSRa0m8j4nZxv+DNiKCsax3heqLEszoa5rvd+WVjALAdYYUKXFLAgMBAAECgYBhCOMxgzSdhdwB20n8WI+ELqpEuhIVpqARLFLECCNTngZ3TGx12FKBlNOJgAvXpOwX1NZhrYd8OEQc5NksuVc0mBEZCi1xvRV1DNe23mCZp5Qq4kWPd+o5ymPx272RKjvB2A+WoUcseriV10qbVVs8wxqDhnwrrA6KLxqxkYzIwQJBAO4oKglqc0GQX5ZuObhea9tcihzePe5CG+c/qGZPx/p3I/I+XW/4K/dpk6zysQONwHlwdPL7lZI55ZbXk5nqMeECQQDYQwNu6wYPzVV2LsL6iIxtAkz0v3xlh/26xt4sQxyOIg0oUmestfehU5Oc7w/WEzNWkYyPQeS7eSH7/QoqPiCrAkEAm6FdjxekR+Ubwgc36vaxDwGTM7g7ylYjO+QKnQlnypJOyD/e+Yu4146DtZJHAOaCMBnAIwUrQgpZsVAhIYS8oQJAVLDBqnFNmWifHD4pyzUoURRCkOJgU96Sxc9VeF3708mP/4dt5FwKktoJB78zI3G3fCJZukxix+wjG+y3S12zKwJBANcLQZdGW8ogdvK/71VFuKl18EUHHrPQJRGdVfdm5RXsP0mk+d6tq7mv0f0aHD9Gmze6hL4w5QTG6eoc5cVhehg=";
      String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJMDssmqzKAhHMmSqOnGPJAnECCRwb9p5+uC+f7/3DQKeYKY/ZCL3a4poLex+G2IP0UAkfpFRGlqjXHbtZicVWaVGRz28bPp5YmiHt3tGOdH3+EaC47APX4/s0kWtJvI+J2cb/gzYigrGsd4XqixLM6Gua73fllYwCwHWGFClxSwIDAQAB";
		
		try {
			String encrypt = encrypt(str, pubkey);
			System.out.println(encrypt);
				System.out.println(decrypt("G3EWxEDzCENbQPbblEhw7j/s4x3zsO/mBpzeI/LMZA8w7MGi9v0oMopoyfe/uUpWMYlDF8KA3lOAfn7fB+siHofJ3SiGnIam4QuzkOYEEcgrN4VV636fvqfgm4ZU9sRZwFJmhgy+Wsk+rE7J7yx/YN1iOPXbhP4Hko+l4DUBf7E=", privatekey));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		
		 *  {"coId":92048,"endTime":"2017-07-24 22:57:48","mark":"发票作废手续费","peiId":37,"perId":69099,
		 *  "perName":"孙克航","rmb":"10","startTime":"2017-07-24 22:57:48"}
		 * 
		 
		JSONObject json = new JSONObject();
		try {
		json.put("coId", 92048);
		json.put("endTime","2017-07-24 22:57:48" );
		json.put("mark", "发票作废手续费");
		json.put("peiId",37);
		json.put("perId", 69099);
		json.put("perName","孙克航");
		json.put("rmb","10" );
		json.put("startTime", "2017-07-24 22:57:48");
		String accountJson = RSA.encrypt(json.toString(), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDeGR+6e5iZ+JbBZizreFFbBZQV4vylof2Fu0oLroJZoO+BN2al6IEmB84IeEovBEaKgkVbfNe6ohRpgXOop+zxMQaZK0pTZuKaz7H+gRofFUSzeXZd8CbSQr8KYN/SpejmHSmenHQqEGUHNWftbfoa8QaUEfr7TEgF/NSs6+DauwIDAQAB");
		System.out.println(decrypt(accountJson, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAN4ZH7p7mJn4lsFmLOt4UVsFlBXi/KWh/YW7Sguuglmg74E3ZqXogSYHzgh4Si8ERoqCRVt817qiFGmBc6in7PExBpkrSlNm4prPsf6BGh8VRLN5dl3wJtJCvwpg39Kl6OYdKZ6cdCoQZQc1Z+1t+hrxBpQR+vtMSAX81Kzr4Nq7AgMBAAECgYA01FsUu7OX5GbPyCMlO7B2a0RHVH/uUjMA7YT2dGMzOLHf1bLIAh8+UZrzrtFOj8DLz6L52R9jmIwscIt3ccJNR4DC/A+lZEkZ6n2m14fRAayYiP0mPE6xdQFzGsTD0PjbtSwHHn6DJ6KdilPMnlp0FJUH85+JxzyPTEYBGf/cyQJBAO8GhuoG0vB5XP3REkgtaqtwkWgKLGpA4Jj750iStdoA1Ju02bDU6TgUepv5WRHLRHksJWhBTktllmPAU2L1/L0CQQDt3tu8ww8nKurUoM5yUxYIfI1POSJf5jXqz4ik25yQDeSkB0eyAQB4DbnTdg1nJszkxweBtrJkvXpfmR03pXjXAkBLIrkcLaL3Np81phfiGmyykYMTukb164jubhjo5j1F1wb+Hx5jCLp7B2z3RK1r+4J9uJPVrtmmt7tLhfRwYjTdAkEAg1bcCVvCkCPYSdMIaoOJ1OHAvI+VdVINTzuL/UrtJXUmW5NReO2orqEVi7bRK7V2yJNEh9Cyq4EbgpFuWn1ZPQJADVZZBaTcglKyT+KCm0y34IxUe2+4Y8c3fNK05cl7y6T3dNMg4+0ljh+jQCuEqZgcJZ+g4fOm+3lNdxDykQ8YJA=="));
		System.out.println(accountJson);
		String result = HttpUtil.htttpPostJson("http://192.168.1.236:1999/services/billManageService/makeBill", accountJson);
		System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
	
	
	public static void main(String[] args){
		String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDeGR+6e5iZ+JbBZizreFFbBZQV4vylof2Fu0oLroJZoO+BN2al6IEmB84IeEovBEaKgkVbfNe6ohRpgXOop+zxMQaZK0pTZuKaz7H+gRofFUSzeXZd8CbSQr8KYN/SpejmHSmenHQqEGUHNWftbfoa8QaUEfr7TEgF/NSs6+DauwIDAQAB";
		String privateKeyStr = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAN4ZH7p7mJn4lsFmLOt4UVsFlBXi/KWh/YW7Sguuglmg74E3ZqXogSYHzgh4Si8ERoqCRVt817qiFGmBc6in7PExBpkrSlNm4prPsf6BGh8VRLN5dl3wJtJCvwpg39Kl6OYdKZ6cdCoQZQc1Z+1t+hrxBpQR+vtMSAX81Kzr4Nq7AgMBAAECgYA01FsUu7OX5GbPyCMlO7B2a0RHVH/uUjMA7YT2dGMzOLHf1bLIAh8+UZrzrtFOj8DLz6L52R9jmIwscIt3ccJNR4DC/A+lZEkZ6n2m14fRAayYiP0mPE6xdQFzGsTD0PjbtSwHHn6DJ6KdilPMnlp0FJUH85+JxzyPTEYBGf/cyQJBAO8GhuoG0vB5XP3REkgtaqtwkWgKLGpA4Jj750iStdoA1Ju02bDU6TgUepv5WRHLRHksJWhBTktllmPAU2L1/L0CQQDt3tu8ww8nKurUoM5yUxYIfI1POSJf5jXqz4ik25yQDeSkB0eyAQB4DbnTdg1nJszkxweBtrJkvXpfmR03pXjXAkBLIrkcLaL3Np81phfiGmyykYMTukb164jubhjo5j1F1wb+Hx5jCLp7B2z3RK1r+4J9uJPVrtmmt7tLhfRwYjTdAkEAg1bcCVvCkCPYSdMIaoOJ1OHAvI+VdVINTzuL/UrtJXUmW5NReO2orqEVi7bRK7V2yJNEh9Cyq4EbgpFuWn1ZPQJADVZZBaTcglKyT+KCm0y34IxUe2+4Y8c3fNK05cl7y6T3dNMg4+0ljh+jQCuEqZgcJZ+g4fOm+3lNdxDykQ8YJA==";
		String str = "测试123456";
		try {
			PublicKey publickKey = RSA.getPublicKeyFromX509("RSA", publicKeyStr);
			PrivateKey privateKey = RSA.getPrivateKey(privateKeyStr);
			//公钥加密
			String b1 = RSA.encrypt(str,publicKeyStr);
			System.out.println("enc="+b1);
//			//私钥解密
			String b2 = RSA.decrypt("pV21y5RfqUJ8S7qusfmV05n7FxxZhhWPRGYdy38RvInUOn10menamGDjO2/knMJwibYkEtwO1xZY6sWcvVlus/jXdap9UUWMbW+KQn3h0rw/EtYZcuZCYULFPtb44aK57smXy9dmehx7prdo5B1yfrTtrceVhniTRzETLrwjBnmbRvrTQEneC4uRhqXI/4lzZLFA6FN0xfMkPgHhhxOYQmpvo1wZJpYLcRcg3PNliaxAdLS2Txv6UAR2fDbTR9FR5+xum6DIUMQ4o5+1aAP+5BKmft0yZaGVRA4cb9f4AtvpZvJ1WHCaXYKf+vyksT3g0ZnznxR2kCnRq89J1bGwYiqPHrbPkJHuWE6HwWOsVvLFO+6dyQVF0rr263nqzuJgf59avmOdW4qOaTPGnqHrLDQkuzCcZjgnSfrjF4DoqwKSvkkBwfQfrgPjASkdrLJQ7T4PnGNTB5YD9m/A7nb5u1OZ7F8ctcrvq+0tC17nHBoOxEuqVHU93i0+vEbetnDYZ5p+tcGwPt9P5KDZGgNrKsiv8UGSlBlzSeyLJe1boew5rqtU3i8a+yO/DHMI6Rq8Yrhf8F0HOp6pFofQ34tzWedbMuaAc+sPRrObT3F6gPb/UVBpI3uaRZy5Dv7nQEqZtcNerZCr+Iv7rmNtk/S+fufPWMzeKN6JKOnWuYJRBSs=",privateKeyStr);
			System.out.println("dec="+b2);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
