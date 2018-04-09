package com.webapps.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.alibaba.fastjson.JSONObject;

public class DataDecodeUtil<T> {

    @SuppressWarnings("unchecked")
	public T decodeParams(T t){
        String jsonStr = JSONObject.toJSONString(t);
        try {
            jsonStr = URLDecoder.decode(jsonStr,"UTF-8");
            t = (T) JSONObject.parseObject(jsonStr, t.getClass());
            return t;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
