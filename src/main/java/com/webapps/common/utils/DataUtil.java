package com.webapps.common.utils;

import com.webapps.common.utils.encrypt.AES;
import com.webapps.common.utils.encrypt.RSA;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.Random;

public class DataUtil {

    private static Logger logger = Logger.getLogger(DataUtil.class);

    private static String serverPriKey = (String)PropertiesUtil.getProperty("serverPriKey");

    private static String serverPubKey = (String)PropertiesUtil.getProperty("serverPubKey");

    private static String appPubKey = (String)PropertiesUtil.getProperty("appPubKey");

    private static String appPriKey = (String)PropertiesUtil.getProperty("appPriKey");

    private static String randomStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public static String getRandomKey(int length){
        Random random = new Random();
        StringBuilder key = new StringBuilder("");
        for(int i=0;i<length;i++){
            int num = random.nextInt(randomStr.length());
            key.append(randomStr.charAt(num));
        }
        return key.toString();
    }

    public static String encryptData(String data){
        String key = getRandomKey(16);
        try {
            String encryptData = AES.encrypt(data,key);
            String encryptKey = RSA.encrypt(key,appPubKey);
            JSONObject obj = new JSONObject();
            obj.put("encryptData",encryptKey+"|"+encryptData);
            return JSONUtil.toJSONString(obj);
        } catch (Exception e) {
            logger.error("数据加密异常");
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptData(String data){
        JSONObject obj = JSONUtil.toJSONObject(data);
        String encryptData = obj.getString("encryptData");
        String[] dataArray = encryptData.split("\\|");
        String encryptAesKey = dataArray[0];
        String encryptParams = dataArray[1];
        try {
            String aesKey = RSA.decrypt(encryptAesKey,appPriKey);
            String params = AES.decrypt(encryptParams,aesKey);
            return params;
        } catch (Exception e) {
            logger.error("数据解密异常");
            e.printStackTrace();
        }
        return null;
    }

    public static String testServerEncrypt(String data){
        String key = getRandomKey(16);
        try {
            String encryptData = AES.encrypt(data,key);
            String encryptKey = RSA.encrypt(key,serverPubKey);
            JSONObject obj = new JSONObject();
            obj.put("encryptData",encryptKey+"|"+encryptData);
            return JSONUtil.toJSONString(obj);
        } catch (Exception e) {
            logger.error("数据加密异常");
            e.printStackTrace();
        }
        return null;
    }

    public static String testAppDecrypt(String data){
        JSONObject obj = JSONUtil.toJSONObject(data);
        String encryptData = obj.getString("encryptData");
        String[] dataArray = encryptData.split("\\|");
        String encryptAesKey = dataArray[0];
        String encryptParams = dataArray[1];
        try {
            String aesKey = RSA.decrypt(encryptAesKey,appPriKey);
            String params = AES.decrypt(encryptParams,aesKey);
            return params;
        } catch (Exception e) {
            logger.error("数据解密异常");
            e.printStackTrace();
        }
        return null;
    }
}
