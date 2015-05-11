package com.zycx.frame.file;

import java.io.File;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-24 上午11:08:18 
 * 类说明 
 */
public class FileWrapper 
{
	private String fileName;
	private File  file ;
	private String fileExt;
	
	public String getFileExt() {
		return fileExt;
	}


	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}


	public FileWrapper(File file,String fileName)
	{
		this.file = file;
		this.fileName = fileName;
		this.fileExt =  fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}
	
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
