package com.webapps.common.utils;

import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DataDecodeUtil<T> {

    public T decodeParams(T t){
        String jsonStr = JSONUtil.toJSONString(JSONObject.fromObject(t));
        try {
            jsonStr = URLDecoder.decode(jsonStr,"UTF-8");
            t = (T)JSONObject.toBean(JSONObject.fromObject(jsonStr),t.getClass());
            return t;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
