package com.webapps.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.webapps.common.utils.encrypt.AES;
import com.webapps.common.utils.encrypt.RSA;

import net.sf.json.JSONObject;

public class EncryptAndDecryptUtil {
	
	private static Logger logger = Logger.getLogger(EncryptAndDecryptUtil.class);
	
	private static String rsaPublicKey = (String) PropertyUtil.getProperty("serverPubKey");
	
	private static String rasPrivateKey = (String) PropertyUtil.getProperty("serverPriKey");
	
	public static String dataEncrypt(String data){
		JSONObject result = new JSONObject();
		String aesKey = AES.getRandomKey(16);
		try {
			String encryptData = AES.encrypt(aesKey, data);
			String encryptAesKey = RSA.encrypt(aesKey, rsaPublicKey);
			result.put("data", encryptAesKey+"|"+encryptData);
		} catch (Exception e) {
			logger.error("数据加密异常");
			e.printStackTrace();
		}
		return JSONUtil.toJSONString(result);
	}
	
	public static String dataDecrypt(String data){
		if(StringUtils.isBlank(data)){
			logger.error("待解密数据为空");
			return null;
		}
		String[] array = data.split("|");
		try {
			String aesKey = RSA.decrypt(array[0], rasPrivateKey);
			String decryptData = AES.decrypt(array[1], aesKey);
			return decryptData;
		} catch (Exception e) {
			logger.error("解密异常");
			e.printStackTrace();
		}
		return null;
	}

}
