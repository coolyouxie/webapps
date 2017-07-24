package com.webapps.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 图片验证码生成类
 * 
 * @author 解帅
 */
@Component("validatorCode")
public class ValidatorCodeUtil {

	/*
	 * Spring 3支持@value注解的方式获取properties文件中的配置值，大简化了读取配置文件的代码。
	 * 在applicationContext.xml文件中配置properties文件,在bean中使用@value注解获取配置文件的值
	 * 即使给变量赋了初值也会以配置文件的值为准。
	 */
	public static int width = 120;

	public static int height = 38;

	public static int codeLength = 4;

	public static String randomString = "ABCDEFGHIJKLMNPQRSTUVWXYZ1234567890abcdefghijkmnpqrstuvwxyz";

	public static String sessionKey = "SESSIONCODE";

	public static String fontName = "Times New Roman";

	public static int fontStyle = 0;

	public static int fontSize = 24;

	public static BufferedImage getImage(HttpServletRequest request) {
		// 在内存中创建图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图片上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景颜色
		g.setColor(getRandColor(100, 250));
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, xl, y + yl);
		}
		// 取随机产生的认证码
		String sRand = randomRand(codeLength);
		int strWidth = width / 2 - g.getFontMetrics().stringWidth(sRand) / codeLength - fontSize;
		int strHeight = height / 2 + g.getFontMetrics().getHeight() / 4;
		for (int i = 0; i < codeLength; i++) {
			String rand = sRand.substring(i, i + 1);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6 + strWidth, strHeight);
		}
		// System.out.println(sRand);
		request.getSession().setAttribute(sessionKey, sRand);
		request.setAttribute("randomCode", sRand);
		g.dispose();
		return image;
	}

	public static String randomResult(int length) {
		String[] i = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
		List<String> l = new ArrayList<String>();
		l.addAll(Arrays.asList(i));
		Random ran = new Random();
		String s = "";
		while (l.size() > 10 - length)
			s += l.remove(ran.nextInt(l.size()));
		s = s.replaceAll("^(0)(\\d)", "$2$1");
		return s;
	}

	// 给定范围获取随机颜色
	public static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);

	}

	private static String randomRand(int n) {
		String rand = "";
		int len = randomString.length() - 1;
		double r;
		for (int i = 0; i < n; i++) {
			r = ((Math.random())) * len;
			rand = rand + randomString.charAt((int) r);
		}
		return rand;
	}

}
