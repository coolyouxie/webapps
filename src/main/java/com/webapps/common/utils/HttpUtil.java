package com.webapps.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class HttpUtil {
	
	/**
	 * 发起GET请求，param中放对应参数的名称和值
	 * 例如：url:http://api.map.baidu.com/geocoder/v2/
	 * 	   param:{"ak":"yourak","output":"json","callback":"showLocation","address":"上海市徐汇区龙华路1887号"}
	 * 拼装后：http://api.map.baidu.com/geocoder/v2/?ak=yourak&output=json&callback=showLocation&address=上海市徐汇区龙华路1887号
	 * @param url
	 * @param param
	 * @return
	 */
	public static JSONObject doGet(String url,JSONObject param){
		OkHttpClient client = new OkHttpClient();
		client.setConnectTimeout(30, TimeUnit.SECONDS);
		Request request = null;
		StringBuilder paramStr = new StringBuilder();
		if(param!=null){
			if(url.lastIndexOf("/")!=url.length()-1){
				url += "/?";
			}else{
				url = url+"?";
			}
			int i=0;
			for(Entry<String, Object> es:param.entrySet()){
				if(i==param.size()-1){
					paramStr.append(es.getKey()+"="+param.getString(es.getKey()));
				}else{
					paramStr.append(es.getKey()+"="+param.getString(es.getKey())+"&");
				}
			}
			url += paramStr;
		}
		try {
			request = new Request.Builder().url(url).get().build();
			Response response = client.newCall(request).execute();
			if(response.isSuccessful()){
				JSONObject result = new JSONObject();
				//response.body().string()只能调用一次，只有第一次调用时会返回数据。
				result.put("response", response.body().string());
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){
		String hh="{'result':{'confidence':80,'level':'道路','location':{'lat':40.05703033345938,'lng':116.3084202915042},'precise':1},'status':0}";
		String url = "http://api.map.baidu.com/geocoder/v2/";
		JSONObject param = new JSONObject();
		param.put("address", "北京市海淀区上地十街10号");
		param.put("output", "json");
		String ak = (String) PropertiesUtil.getProperty("map_api_ak");
		param.put("ak", ak);
		param.put("callback", "showLocation");
		JSONObject response = doGet(url,param);
		String result = response.getString("response");
		result = result.substring(result.lastIndexOf("(")+1,result.length()-1);
		JSONObject obj = JSONObject.parseObject(result);
		System.out.println(response.toString());
	}

}
