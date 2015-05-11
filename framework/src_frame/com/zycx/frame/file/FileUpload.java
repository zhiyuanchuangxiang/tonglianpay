package com.zycx.frame.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUpload {
	

	private int maxSizeMega = 30;
	private int maxSize = maxSizeMega * 1024 * 1024;
	private int thresholdSize = 1024;
	private String repositoryPath = System.getProperty("java.io.tmpdir");
	
	private HttpServletRequest request;
	
	/**
	 * construct function
	 * @param request
	 */
	public FileUpload(HttpServletRequest request) {
		this.request = request;
	}

  	/**
  	 * get disk file upload
  	 * @return DiskFileUpload
  	 * @throws Exception
  	 */
  	public ServletFileUpload getFileUpload() throws Exception {
  		FileItemFactory factory = new DiskFileItemFactory();
  		ServletFileUpload upload = new ServletFileUpload(factory);
  		upload.setSizeMax(getMaxSize());
  	
		return upload;
  	}
  	
  	/**
  	 * get items list
  	 * @return List
  	 * @throws Exception
  	 */
	public List getItemList() throws Exception {
		ServletFileUpload upload = getFileUpload();
		
		List list = null;
		try {
			list = upload.parseRequest(request);
		} catch (SizeLimitExceededException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * get item list
	 * @param isFormField
	 * @return List
	 * @throws Exception
	 */
	public List getItemList(boolean isFormField) throws Exception {
		List items = new ArrayList();
		
		Iterator it = getItemList().iterator();
		while (it.hasNext()) {
			FileItem item = (FileItem) it.next();
			if (item.isFormField() == isFormField) items.add(item);
		}
		
		return items;
	}
	
	/**
	 * get file item
	 * @param field
	 * @return FileItem
	 * @throws Exception
	 */
	public FileItem getFileItem(String field) throws Exception {
		Iterator it = getItemList().iterator();	
		while (it.hasNext()) {
			FileItem item = (FileItem) it.next();
			if (!item.isFormField() && item.getFieldName().equals(field)) return item;
		}
		return null;
	}
	
	/**
	 * is multipart content
	 * @param request
	 * @return boolean
	 */
	public static boolean isMultipartContent(HttpServletRequest request){
		return org.apache.commons.fileupload.FileUpload.isMultipartContent(request);
	}

	public int getMaxSize() {
		return maxSize;
	}
  	public void setMaxSize(int maxSize) {
  		this.maxSize = maxSize;
    }

    public int getThresholdSize() {
    	return thresholdSize;
    }
    public void setThresholdSize(int thresholdSize) {
    	this.thresholdSize = thresholdSize;
    }

    public String getRepositoryPath() {
    	return repositoryPath;
    }
    public void setRepositoryPath(String repositoryPath) {
    	this.repositoryPath = repositoryPath;
    }

}