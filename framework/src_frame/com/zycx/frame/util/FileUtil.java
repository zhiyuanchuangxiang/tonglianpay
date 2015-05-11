package com.zycx.frame.util;

import java.io.File;
import java.io.InputStream;

import com.zycx.frame.struts.GlobalParameters;

/**
 * 文件处理操作类
 */
public class FileUtil {
	
	public static boolean isExists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
	
	public static boolean delete(String filePath) {
		File file = new File(filePath);
		return file.delete();
	}
	
	
	public static InputStream getClassResourceStream(String file) throws Exception {
		InputStream in = GlobalParameters.class.getClassLoader().getResourceAsStream(file);
		if (in == null) {
			throw new RuntimeException("file " + file + " not exist!");
		}
		return in;
	}
}
