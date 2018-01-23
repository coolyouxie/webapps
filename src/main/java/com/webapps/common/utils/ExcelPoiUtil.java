package com.webapps.common.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * EXCEL 工具类
 * 
 * @author Alice
 *
 */
public class ExcelPoiUtil<T> {

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param data
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	public void exportExcel(String title, String[] headers, String[] fields, List data, OutputStream out) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为20个字节
		sheet.setDefaultColumnWidth((short) 20);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColorPredefined.SKY_BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColorPredefined.VIOLET.getIndex());
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColorPredefined.LIGHT_YELLOW.getIndex());
		style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);
		style2.setAlignment(HorizontalAlignment.CENTER);
		style2.setVerticalAlignment(VerticalAlignment.CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBold(false);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		int index = 0;
		HSSFFont font3 = workbook.createFont();
		font3.setColor(HSSFColorPredefined.BLUE.getIndex());
		for (Object t : data) {
			if (t != null) {
				index++;
				row = sheet.createRow(index);
				// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				for (short i = 0; i < fields.length; i++) {
					if (i >= headers.length) {
						break;
					}
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(style2);
					String fieldName = fields[i];
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					try {
						Class tCls = t.getClass();
						Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
						Object value = getMethod.invoke(t);
						// 判断值的类型后进行强制类型转换
						String textValue = null;
						if (value != null) {
							if (value instanceof Date) {
								Date date = (Date) value;
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								textValue = sdf.format(date);
							} else if (value instanceof Double) {
								Double double1 = (Double) value;
								DecimalFormat df = new DecimalFormat("###0.00");
								textValue = df.format(double1);
							} else if (value instanceof byte[]) {
								// 有图片时，设置行高为60px;
								row.setHeightInPoints(60);
								// 设置图片所在列宽度为80px,注意这里单位的一个换算
								sheet.setColumnWidth(i, (short) (35.7 * 80));
								// sheet.autoSizeColumn(i);
								byte[] bsValue = (byte[]) value;
								HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index,
										(short) 6, index);
								anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
								patriarch.createPicture(anchor,
										workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
							} else {
								// 其它数据类型都当作字符串简单处理
								textValue = value.toString();
							}
						}
						// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
						if (textValue != null) {
							Pattern p = Pattern.compile("^//d+(//.//d+)?$");
							Matcher matcher = p.matcher(textValue);
							if (matcher.matches()) {
								// 是数字当作double处理
								cell.setCellValue(Double.parseDouble(textValue));
							} else {
								HSSFRichTextString richString = new HSSFRichTextString(textValue);
								richString.applyFont(font3);
								cell.setCellValue(richString);
							}
						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
