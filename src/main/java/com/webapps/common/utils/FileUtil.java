package com.webapps.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class FileUtil {

	// 验证字符串是否为正确路径名的正则表达式
	private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";
	// 通过 sPath.matches(matches) 方法的返回值判断是否正确

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public boolean DeleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String sPath) {
		boolean flag;

		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}

		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;

		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}

		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建目录
	 * 
	 * @param destDirName
	 *            目标目录名
	 * @return 目录创建成功返回true，否则返回false
	 */
	public boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			System.out.println("创建目录" + destDirName + "失败，目标目录已存在！");
			return false;
		}
		if (!destDirName.endsWith(File.separator))
			destDirName = destDirName + File.separator;
		// 创建单个目录
		if (dir.mkdirs()) {
			System.out.println("创建目录" + destDirName + "成功！");
			return true;
		} else {
			System.out.println("创建目录" + destDirName + "成功！");
			return false;
		}
	}

	// 删除filePath目录下，过去daysPast天的文件
	public void deleteFilesByDate(String filePath, int daysPast) {
		File dirFile = new File(filePath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -daysPast);

		File[] files = dirFile.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				// 删除文件
				if (files[i].isFile()) {
					long lastModify = files[i].lastModified();
					if (lastModify <= cal.getTimeInMillis()) {
						try {
							deleteFile(files[i].getAbsolutePath());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}

	public static String reFileName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = format.format(new Date());
		int num = (int) (Math.random() * (9999 - 1000 + 1) + 1000);
		return date + num;
	}

	public static ResponseEntity<byte[]> common(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			HttpHeaders httpHeaders = new HttpHeaders();
			try {
				// 为了解决中文名称乱码问题
				String fileName = new String(file.getName().getBytes("UTF-8"), "iso-8859-1");
				httpHeaders.setContentDispositionFormData("attachment", fileName);
				httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void downloadFile(HttpServletResponse response,String filePath ) throws IOException{
		
		File f = new File(filePath);
		response.setContentType("APPLICATION/OCTET-STREAM"); 
		response.setHeader("Content-Disposition","attachment; filename="+new String( f.getName().getBytes("gb2312"), "ISO8859-1" ));
		
		ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(f));
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
	}

}
