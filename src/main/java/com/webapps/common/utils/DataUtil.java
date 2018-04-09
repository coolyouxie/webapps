package com.webapps.common.utils;

import java.util.Random;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.webapps.common.utils.encrypt.AES;
import com.webapps.common.utils.encrypt.RSA;

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

    public static void main(String[] args){
       String date = "{\"encryptData\":\"SnAHiHOt8rzqtUwox0KYFYkrvxQfX37Vr/tDxQGEPg8BJVcGaY/N4LSznjXu7aEwwzcO7mD5vuACzegiQtck0FVJo3iR94PsghW2pF0BwaB9PpvCstUU1bE612Zz7yZVr4pgjnbzcV0Bu751lfviGUKW9fqc0srtwBgU+cWHhBI=|AXEUVRTZCCkQBY2ED1W9sTloPHO7jtURFyfrV1DgTBt+4Ab/YOcgX0HT1E6WfIrDxTk/J/F86Bi7QyxCwm2dg9Put8g1OUbngcfgqrb86o4vQgeDg9R0VcZLh+MKpr2bCfUsyMPMOZtwjy2UebFzKItJTQk/lcGG6I6t+u2/wz6Gl0wcXLn0l76RMLN9sM7sbEOYEKQaszV6T40MQdtsx1LBvZv6fb+ue2pXjZ26MiISfxOSQ/6I5wTgXFma2TT9yZH3KheuiY9+3pYi1j+Ijb1B9SuL/j+UMIgyMRuqc430Wg0vFzzyLcwvvf1se3Kkx8NKAFoMzLPPsfuXo7wp0guEwki3SvKzEQyYmPRNmPG+0z1emVjOLpZPL0/CA3O1fdI11nF5EDwBrkfhUCNwhMa4CVf0/qNrWxg8K4evb2S4XdeIz+jROX1zQ6vFWsAaPTeEYo2FwWLtnvvSawWmJ4GVNQK5IiE/rIqMI9PSVjE+9O+/au320XCKn0j1eCVZJEyaVrxCPZHr/GBHR5Oc8syqyOckfn/UV/x+UNyM2RK35r6ERvDVEsMV8kZpjCD5wo2HBwEqoQx9q5Me/HRQM59YmajbDYwWmBhzlqTZbZufQOjp3TgJnnJW1+XR9u7K3dDUSgS7zuIQDL2auyGF9DVYNc/UwIN1FYA06iFdy3vPJL5iypadFj7uoi9X4k/s19L+eznjy7pCHA1rXGyi7g9gNOsdafQNRhi7V9iVE0oEbNl3zZv53uK7AZ9kduc+LhfeWYGI7Q5up1znUKK/Oy8RJU5umvKpWyHLelDGMXE6Y7APXrp/rRwtvWYV7+wdN7xqVPTa5aqowvyJCsE52T+7aYuQBQSgJyV1uZvGzZUx7vtl0pnTRA/1BTHqZ51oAzt0gxzbtj8r7okz5WlP93nXV94/2qObYdUKCYAozyKeRF/V+rKqZW5jq3rAfo7Q\"}";
       System.out.println(decryptData(date));
    }
}
