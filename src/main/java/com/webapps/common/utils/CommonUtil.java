package com.webapps.common.utils;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {
	
	public static String TEMPLATE_PATH = null;

	/**
	 * 将输入值做四舍五入处理
	 * @param input
	 * @return
	 */
	public static Double roundHalfUp(Double input){
		 BigDecimal bg = new BigDecimal(input);
	     return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**获取真实ip
	 * @author lzh
	 * @create 2014-3-14 下午02:32:05 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
	       String ip = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
	       return ip; 
	}
	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);
	}

	/**
	 * 随机获取字符串
	 * 
	 * @param length
	 *            随机字符串长度
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomString(int length) {
		if (length <= 0) {
			return "";
		}
		char[] randomChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i',
				'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
		}
		return stringBuffer.toString();
	}

	/**
	 * 根据指定长度 分隔字符串
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @param length
	 *            分隔长度
	 * 
	 * @return 字符串集合
	 */
	public static List<String> splitString(String str, int length) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length(); i += length) {
			int endIndex = i + length;
			if (endIndex <= str.length()) {
				list.add(str.substring(i, i + length));
			} else {
				list.add(str.substring(i, str.length() - 1));
			}
		}
		return list;
	}

	/**
	 * 将字符串List转化为字符串，以分隔符间隔.
	 * 
	 * @param list
	 *            需要处理的List.
	 *            
	 * @param separator
	 *            分隔符.
	 * 
	 * @return 转化后的字符串
	 */
	public static String toString(List<String> list, String separator) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : list) {
			stringBuffer.append(separator + str);
		}
		stringBuffer.deleteCharAt(0);
		return stringBuffer.toString();
	}

	//转义参数中的特殊字符
	public static String filtParam(String value) {
		if (value == null) {
			return null;
		}
		StringBuffer result = new StringBuffer(value.length());
		for (int i = 0; i < value.length(); ++i) {
			switch (value.charAt(i)) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '"':
				result.append("&quot;");
				break;
			case '\'':
				result.append("&#39;");
				break;
			//			case '%':
			//				result.append("&#37;");
			//				break;
			case ';':
				result.append("&#59;");
				break;
			case '(':
				result.append("&#40;");
				break;
			case ')':
				result.append("&#41;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '+':
				result.append("&#43;");
				break;
			default:
				result.append(value.charAt(i));
				break;
			}
		}
		return result.toString();
	}

	//校验参数中是否含有特殊字符
	public static boolean validateParam(String value) {
		if (value == null) {
			return false;
		}
		for (int i = 0; i < value.length(); ++i) {
			char c = value.charAt(i);
			if (c == '<' || c == '>' || c == '"' || c == '\'' || c == '%' || c == ';' || c == '(' || c == ')'
					|| c == '&' || c == '+') {
				return true;
			}
		}
		return false;
	}

	public static long getStartTimestamp(Date date) {
		Calendar nowDate = new GregorianCalendar();
		nowDate.setTime(date);
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		return nowDate.getTimeInMillis();
	}

	public static String getPath() {
		String path = getClassFilePath(CommonUtil.class);
		path = path.substring(0, path.indexOf("com"));
		return path;
	}
	
	public static String getTemplatePath(){
//		String path = PropertiesUtil.getPath();
//		String path = getClassFilePath(CommonUtil.class);
//		path = path.substring(0, path.indexOf("target"+File.separator+"classes"+File.separator+"com"));
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"config" + File.separator;
		return path;
	}

	public static String getClassFilePath(Class<?> clazz) {
		try {
			return java.net.URLDecoder.decode(getClassFile(clazz).getAbsolutePath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	private static File getClassFile(Class<?> clazz) {
		URL path = clazz.getResource(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1) + ".class");
		if (path == null) {
			String name = clazz.getName().replaceAll("[.]", "/");
			path = clazz.getResource("/" + name + ".class");
		}
		return new File(path.getFile());
	}

	public static String getFormatDate(Date date, String formatStr) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
		String dateString = simpleDateFormat.format(date);
		return dateString;
	}
	
	/**
	 * 判断邮箱地址是否符合规范
	 */
	
	public static Boolean emailFormat(String email){
		boolean flag = false;
		try{
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		   	Pattern regex = Pattern.compile(check);
		   	Matcher matcher = regex.matcher(email);
		   	flag = matcher.matches();
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}
	//判断电信手机号码
	public static boolean ChinaNetRegex(String mobiles){
		//String regex = PropertiesUtil.getProperty("ChinaNetRegex");
		//String regex = "";
		Pattern p = Pattern.compile("^((13[3])|(15[3])|(18[0,9]))\\d{8}$");
		//Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	//判断联通手机号码
	public static boolean UnicomRegex(String mobiles){
		//String regex = PropertiesUtil.getProperty("UniComRegex");
		
		Pattern p = Pattern.compile("^((13[3])|(15[3])|(18[0,9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	//判断移动手机号码
	public static boolean MobilesRegex(String mobiles){
		String regex = (String)PropertiesUtil.getProperty("MobilesRegex");
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	/**
	 * 1 联通 2移动 3电信
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean getMobileTypeLT(String mobile) {
		// 联通
		String[] unicoms = new String[] { "130", "131",
				"132", "152", "155", "156", "185", "186" };

		for (int i = 0; i < unicoms.length; i++) {
			if (mobile.startsWith(unicoms[i]))
				return true;
		}
		return false;
	}
	public static boolean getMobileTypeYD(String mobile) {
			
		// 移动
		String[] mobiles = new String[] { "134", "135",
				"136", "137", "138", "139", "150", "151", "157", "158", "159",
				"187", "188", "183" };

		for (int i = 0; i < mobiles.length; i++) {
			if (mobile.startsWith(mobiles[i]))
				return true;
		}
		return false;
	}
	public static boolean getMobileTypeDX(String mobile) {

		// 电信
		String[] telecoms = new String[] { "133", "153",
				"180", "189" };

		for (int i = 0; i < telecoms.length; i++) {
			if (mobile.startsWith(telecoms[i]))
				return true;
		}
		return false;
	}

	/**
	 * 转换带都好的字符串成LIST
	 * @param str
	 * @return
	 */
	public static List<String> stringToList(String str){
		List<String> list = new ArrayList<String>();
		String[] array = str.split(",");
		for (int i = 0; i < array.length; i++) {
			if(array[i]!=null && !array[i].equals("")){
				list.add(array[i]);
			}
		}
		return list;
	}

	/**
	 * 根据length的最后一位数值进行str的截取后24位字符，后面不足位从第一个字符进行补上
	 * @param str
	 * @param length
	 * @return
	 */
	public static String interceptCharacterBit24(String str, Long length){
		if(length!=null){
			String lengthStr = String.valueOf(length);
			if(lengthStr.length()>0){
				Integer bit = Integer.parseInt(lengthStr.substring(lengthStr.length()-1, lengthStr.length()));
				Integer strLength = str.length();
				if(strLength-bit>24){
					return str.substring(bit, 24+bit);
				}else{
					return str.substring(bit, str.length())+str.substring(0, Math.abs(str.length()-24-bit));
				}
			}
			
		}else{
			return str.substring(0, 23);
		}
		
		return str;
	}

	
	public static void main(String[] args) {
		System.out.println(UnicomRegex("18688408863"));;
		
		String str = "d153d497aeb21b6f29bfa31678882b6f";
		/*System.out.println(str.substring(0, 23));
		System.out.println(str.substring(str.length()-23, str.length()));*/
		
		System.out.println(interceptCharacterBit24(str, Long.valueOf("1458232379595")));
		/*System.out.println(interceptCharacterBit24(str, Long.valueOf("1238")));
		System.out.println(interceptCharacterBit24(str, Long.valueOf("1237")));
		System.out.println(interceptCharacterBit24(str, Long.valueOf("1236")));
		System.out.println(interceptCharacterBit24(str, Long.valueOf("1235")));*/
		
	}
}