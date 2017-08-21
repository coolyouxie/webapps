package com.webapps.common.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class PropertyUtil {
	
	private static Properties prop = new Properties();
	
	private static String path = PropertyUtil.class.getResource("/config.properties").getPath();
	
	static{
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			try {
				prop.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Object getProperty(String key){
		return prop.get(key);
	}
	
	public static void main(String[] args){
		System.out.println((String)getProperty("WebApp_Path"));;
	}

}
