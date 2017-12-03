package com.webapps.common.utils;

import org.apache.commons.lang.StringUtils;

public class DataHandler {

    public static String initParams(String data){
        if(StringUtils.isNotBlank(data)){
            if(data.contains("encryptData")){
                return DataUtil.decryptData(data);
            }else{
                return data;
            }
        }else{
            return null;
        }
    }

}
