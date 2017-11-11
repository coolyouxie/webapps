package com.webapps.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

/**
 * JSON工具类
 */
public class JSONUtil {

	/***
	 * 将List对象序列化为JSON文本
	 */
	public static <T> String toJSONString(List<T> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}

	/***
	 * 将对象序列化为JSON文本
	 */
	public static String toJSONString(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return jsonArray.toString();
	}
	
	/***
	 * 将对象序列化为JSON文本
	 */
	public static String toJSONObjectString(Object object) {
		if (object!=null) {
			JSONObject jsObject = JSONObject.fromObject(object);
			if (jsObject!=null) {
				return jsObject.toString().replace("\\", "");
			}
		}
		return "";
	}

	/***
	 * 将JSON对象数组序列化为JSON文本
	 */
	public static String toJSONString(JSONArray jsonArray) {
		return jsonArray.toString();
	}

	/***
	 * 将JSON对象序列化为JSON文本
	 */
	public static String toJSONString(JSONObject jsonObject) {
		return jsonObject.toString();
	}

	/***
	 * 将对象转换为List对象
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toArrayList(Object object) {
		List arrayList = new ArrayList();

		JSONArray jsonArray = JSONArray.fromObject(object);

		Iterator it = jsonArray.iterator();
		while (it.hasNext()) {
			JSONObject jsonObject = (JSONObject) it.next();

			Iterator keys = jsonObject.keys();
			while (keys.hasNext()) {
				Object key = keys.next();
				Object value = jsonObject.get(key);
				arrayList.add(value);
			}
		}
		return arrayList;
	}

	/***
	 * 将对象转换为Collection对象
	 */
	@SuppressWarnings("rawtypes")
	public static Collection toCollection(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return JSONArray.toCollection(jsonArray);
	}

	/***
	 * 将对象转换为JSON对象数组
	 */
	public static JSONArray toJSONArray(Object object) {
		return JSONArray.fromObject(object);
	}

	/***
	 * 将对象转换为JSON对象
	 */
	public static JSONObject toJSONObject(Object object) {
		return JSONObject.fromObject(object);
	}

	/***
	 * 将对象转换为List<Map<String,Object>>
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map<String, Object>> toList(Object object) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray jsonArray = JSONArray.fromObject(object);
		for (Object obj : jsonArray) {
			JSONObject jsonObject = (JSONObject) obj;
			Map<String, Object> map = new HashMap<String, Object>();
			Iterator it = jsonObject.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = jsonObject.get(key);
				map.put(key, value);
			}
			list.add(map);
		}
		return list;
	}

	public static boolean isContainsKey(String jsonSourceStr,String keyStr){
		boolean ret = false;
		if(StringUtils.isNotBlank(jsonSourceStr)){
			if(StringUtils.isBlank(keyStr)){
				return true;
			}
			
			JSONObject jsonObj = JSONUtil.toJSONObject(jsonSourceStr);
			if(jsonObj.containsKey(keyStr)){
				return true;
			}
//			List<String> keyList = new ArrayList<String>();
//			Iterator<String> it = jsonObj.keys(); 
//			while(it.hasNext()){
//				String key = it.next();
//				keyList.add(key);
//			}
//			ret = keyList.contains(keyStr);
		}
		
		
//		if(StringUtils.isNotBlank(jsonSourceStr)&&StringUtils.isNotBlank(keyStr)){
//			ret = jsonSourceStr.contains(keyStr);
//		}
//		if(StringUtils.isNotBlank(jsonSourceStr)&&StringUtils.isBlank(keyStr)){
//			ret = true;
//		}
		return ret;
	}
	
	/**
	 * 判断json字符串中是否含有key及其对应的值
	 * @param jsonSourceStr
	 * @param keyStr
	 * @return
	 */
	public static boolean isContainsKeyAndValue(String jsonSourceStr,String keyStr){
		boolean ret = false;
		if(StringUtils.isNotBlank(jsonSourceStr)){
			if(StringUtils.isNotBlank(keyStr)){
				JSONObject jsonObj = JSONUtil.toJSONObject(jsonSourceStr);
				if(jsonObj.containsKey(keyStr)){
					if(StringUtils.isNotBlank(jsonObj.getString(keyStr))){
						return true;
					}
				}
			}
		}
		return ret;
	}
	
	public static String getStringFromJson(JSONObject adata) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (Object key : adata.keySet()) {
			sb.append("\"" + key + "\":\"" + adata.get(key) + "\",");
		}
		String rtn = sb.toString().substring(0, sb.toString().length() - 1) + "}";
		return rtn;
	}
	
	
	public static void main(String[] args) {
		String src = "{'bankNo':8,'bankUserNo':'1','voucherNo':'530111198502218611','reqNo':'','signature':'DA53A4364DC305C049B8E14475B711ED'}";
		System.out.println(isContainsKey(src, ""));
		System.out.println(isContainsKey(src, "bankNo"));
		System.out.println(isContainsKey(src, "123"));
		System.out.println(isContainsKeyAndValue(src, "bankNo"));
		System.out.println(isContainsKeyAndValue(src, "reqNo"));
		}
}
