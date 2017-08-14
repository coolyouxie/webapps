package com.webapps.common.entity.encrypt;

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

import org.apache.commons.codec.binary.Hex;

import sun.misc.BASE64Decoder;

/**
 * 加解密统一接口，支持流行的对称和非对称算法 目前可以使用3DES,AES，RSA进行加解密
 */
public class main {

		public static void main(String[] args) throws Exception {
			String modeHex = "C31A373ED1CFF32F076CB3EE8A451DF7----8E342D3BB387F647D4069400CA7265D7781C18----37235C5884---0D71ACD58B----1C0E41571099---00B95D9F1E6----27A16FD2BA6---D4DC3DC0165332FF67CF----D40B83EAFE486EB38----389A2C2D450658749D767BF597CBFE6BD217BBD4AEAE166F1F4720679316CC14C7C208D905B072F4E16923D49BB582F";
			String exponentHex = "10001";
			String paramStr = "|0B7A89A0F94070D4495A8E5D854AFA257D----852F12E75B87E960FD1F8076532A550FAB4F978FA2F14DBF4C6647---CFFD817D1CA8--73F5A541B--9DCE27CFA923E566--BFDEBA25EA43EA2CE072---0537091B2F6081--469E92AE53D0--F361C84B3FBD4E7B3B7A0D41CFED3--1D0A71C2E5EA110C4--1D6EE652983E5A59A--979B576F92EDBDB77---22B--B7E21500BF602708EB729C9749CFE825615DF3AD----52785A464892FEB7377D7E0E90B700||571781BDA31E8FA8F4506B95F66251B59A3B4140CAC17063BB9694740E41FB318CF719F98C3A75462F4892CEDFF0705C547A2B5E72E5ADBFBC27AC3559ACC757157AE1A10DBA9DB7269D7D64B40C50189D77DBE5D97D186E2764900DDE8D2007C38978018BCD20541911663A4869926E6A72F4E121C69BAE2A0ED34F0FE7B022C215660DCF7BDF17ABBDA2582765B922EACD438E248654721CC5D66A0421CEAC54B14CFB49B66F76AC7F9886EEA52AB56425C6A3DD42AC79D9563005DC5A7A7CE318B88BC98661AF0756E0877B9896433F30B0DA0A9BE3EC457C4E884E40F5E2681EE9CAED68A911F743D8E07291A1EC891299972B1F586C83468B2247531E0B5253E1E9EBE26CC5832F9264275B887479A29DED0309325D90FBB75AC45F30E196A316B0B820AE38F5F05AE68AA62291D41C9156177DDD0B2365ECC5E04A24D0E37A5136BA36B3F1A27B8AE64FFDC97B54B14CFB49B66F76AC7F9886EEA52AB533596F1FA985590030C6360554BF969ED06DC43FC0B8392AE22BC030C91D7615E0ECCA65F48786BED6DE58952F6A3F1D0F5D98174D39D699A65FB39F8C080491A7F0213CA2AC15B0900026892AB9E4C407E96B93132830596C98371F257777FCD145768528D000DE93B17160A538B25564B733F27B2925C3FD4DA91D8F81DDB0130E6D124E04ED2152CB0FB9B82744FECEA7C14EFDD93FB4DBC6A313FA43C3320453F90FFA79DEBC0638C1104C3BB916E970EE70A1F7C2ED1E40ED33359772864B6CFB5F7ACCA0C8FB027BA388727CD69EEA4FA31E4A3A5FC3BD6759B398EC7A87E2060DC3962DB8438D9FD96CABAD897F984DA76D5BE4788B894D109B31E1DDABD99802815EF21D295268A067845A2BE7CE3403BC160D6E6C320BBD12B275E6548A07E962C4FEC31E31CFC2362B8D4D5D9162A36E3894A7B2FEC4E6A3DA671D0611E5565A2A3485C464E059CA8AB5C129B59B88DCBF9353C3FD69F27281E6CDD17AAF3669D7C25DBA97BF2A4427381A827CBD148DC1D4A238579674D7E08555157DCF87C354D0B153B8F6A45A82530DA3C649B7106C4E182C3CC4844962776F726ADEA5C783917CEFC37D4D8C22A10ED783E2849B100B4A55246DFF72F20BE2A5CE1F5EC0329CA29FE06809585A58B89CFFD3C8DAE9B67950F7CA72FBD9B017FD8A49839C9608C49924656F99011992010FE257207A644AC87EEF065F95D009D41C9156177DDD0B2365ECC5E04A24D052963FD13F34C43AF4B51F3FC5C768F39EEA4FA31E4A3A5FC3BD6759B398EC7A59EC2C0180D4B813D927C665F45A574DBAA4EB24EB4E5BFA5303F36135A09EA416B2EAFD4B2E65F5C3F504B111BC970F251A3A37FD62B409F43D491D4A8AA2EA151756E37920DD1B57A3F4839A6C54522948C11BEC9A0A5B7BFF30934B539299985F441DBF52F093CA44895688004DEF1529CA69027C4A05130F380A73DCA58BC0514450064DAD1D497771592F5942E84A6D5DA9746704AA45188F578E2EE52472A586F7F2DEE3CCD245585ED6BF95F536B0353B0DE4AE1D7DB5B6D0B051820F50C3ADE10977B8F25ED53300F2DDD18224C7B6290AD0882EF3C51BA9D0E14012|";
			String[] params = paramStr.split("\\|");
			String aesKeyParam = "6D029722CD8445---D3DA98CC5A0CE3C83D893A7E2B3892DFA----FEEC686F371915970---35AB####6AB809DFEA1CC565---AEE5F48A127109****0A6DE26D945A0E195DF4D67AE2057B17B84D21A3109BB19DC1C386BD54D4B2F720B8F570AAEBE500BB07FF38====C90D18B3---++++4477DC8D393959F28CCCC87CB435EE144D9EA---068564C6B127E4ED----78090916E";
			String xmlParam = params[3];
			byte[] asKey = getDefaultKey(EncryptAlgorithm.AES);
			String messageg = "";
			for (int i = 0; i < asKey.length; i++) {
				messageg = messageg + toHex(asKey[i]);
			}
			System.out.println(messageg.toUpperCase());
			// String
			// messageg=Integer.toHexString(getDefaultKey(EncryptAlgorithm.AES));
			// messageg = new String(messageg.getBytes(), "GBK");
			KeyFactory factory = KeyFactory.getInstance("RSA");
			System.out.println("----加密-----");
			BigInteger n = new BigInteger(modeHex, 16);
			BigInteger e = new BigInteger(exponentHex, 16);
			RSAPublicKeySpec spec = new RSAPublicKeySpec(n, e);

			RSAPublicKey pub = (RSAPublicKey) factory.generatePublic(spec);
			Cipher enc = Cipher.getInstance(EncryptAlgorithm.RSAWithPadding.getAlgorithm());
			enc.init(Cipher.ENCRYPT_MODE, pub);

			byte[] encryptedContentKey = enc.doFinal(messageg.getBytes("GBK"));
			String result = new String(Hex.encodeHex(encryptedContentKey));
			result = result.toUpperCase();
			System.out.println(result);
			
			//解密*********************************************
			String aeskey = EncryptUtil.asymmDecry(aesKeyParam, pub, EncryptAlgorithm.RSAWithPadding);
			System.out.println("解密后的AES密钥："+aeskey);
			xmlParam = AESEncrypt.decrypt(toStringHex(xmlParam), aeskey);
			System.out.println("解密后的xml数据："+xmlParam);
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

		public static byte[] encrypt(byte[] bt_plaintext, String key)
				throws Exception {
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
					baKeyword[i] = (byte) (0xff & Integer.parseInt(
							s.substring(i * 2, i * 2 + 2), 16));
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

		public static String testSymmEncry(String plainText, byte[] key,
				EncryptAlgorithm alg) throws Exception {
			/* 测试对称加密方法的应用场景类 */
			byte[] encResult = encryt(
					EncryptStringUtils.getEncByteFromStr(plainText), key, alg);
			String encStr = EncryptStringUtils.byte2hex(encResult);
			return encStr;
		}

		public static String testAsymmEncry(String plainText, Key key,
				EncryptAlgorithm alg) throws Exception {
			/* 测试非对称加密方法的应用场景类 */
			// byte[] encResult =
			// encryt(EncryptStringUtils.getEncByteFromStr(plainText),key,alg);
			byte[] encResult = encryt(plainText.getBytes(), key, alg);
			String encStr = EncryptStringUtils.byte2hex(encResult);
			return encStr;
		}

		public static String testSymmDecry(String ciperText, byte[] key,
				EncryptAlgorithm alg) throws Exception {
			/* 测试解密方法的应用场景类 */
			byte[] decResult = decryt(
					EncryptStringUtils.getDecByteFromStr(ciperText), key, alg);
			String decStr = new String(decResult);
			return decStr;
		}

		public static String testAsymmDecry(String ciperText, Key key,
				EncryptAlgorithm alg) throws Exception {
			/* 测试非对称解密方法的应用场景类 */
			byte[] decResult = decryt(
					EncryptStringUtils.getDecByteFromStr(ciperText), key, alg);
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
		 * */
		public static byte[] encryt(byte[] plainText, byte[] encrytKey,
				EncryptAlgorithm alg) throws Exception {
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
		 * */
		public static byte[] encryt(byte[] plainText, Key key, EncryptAlgorithm alg)
				throws Exception {
			Cipher cipher = Cipher.getInstance(alg.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(plainText);
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
		 * */
		public static byte[] decryt(byte[] ciperText, byte[] decrytKey,
				EncryptAlgorithm alg) throws Exception {
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
		 * */
		public static byte[] decryt(byte[] ciperText, Key key, EncryptAlgorithm alg)
				throws Exception {
			Cipher cipher = Cipher.getInstance(alg.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(ciperText);
		}

		/**
		 * 获取对称加密算法算法的密钥
		 * 
		 * @param alg
		 *            加密算法枚举
		 * @return 16进制的密钥数组
		 * @throws
		 * */
		public static byte[] getDefaultKey(EncryptAlgorithm alg) throws Exception {
			KeyGenerator keygen = KeyGenerator.getInstance(alg.getAlgorithm());
			SecretKey deskey = keygen.generateKey();
			return deskey.getEncoded();
		}

		/**
		 * 获取非对称加密算法的密钥
		 * 
		 * @param alg
		 *            加密算法枚举
		 * @return 16进制的密钥数组
		 * @throws
		 * */
		public static KeyPair getDefaultKeyPair(EncryptAlgorithm alg)
				throws Exception {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(alg
					.getAlgorithm());
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
					keyFactory = SecretKeyFactory
							.getInstance(EncryptAlgorithm.ThreeDES.getAlgorithm());
				}// 将DESKeySpec对象转换成SecretKey对象
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
				part2 = testAsymmEncry(part2, kp.getPublic(),
						EncryptAlgorithm.RSAWithPadding);
				part1 = testAsymmEncry(asKeyString, kp.getPrivate(),
						EncryptAlgorithm.RSAWithPadding);
				result = part1 + "|" + part2 + "|" + part3;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return result;
		}

}
