package com.zycx.frame.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.zycx.frame.struts.GlobalParameters;
import com.zycx.frame.util.DPUtil;
import com.zycx.frame.util.StringUtils;

public class FileMan {

	public static final String FILE_OPERMODE_SIMPLE = "simple";
	public static final String FILE_OPERMODE_FTP = "ftp";
	
	public static final String UPLOAD_TYPE_ATTACH = "1";
	public static final String UPLOAD_TYPE_IMAGE = "2";
	public static final String UPLOAD_TYPE_EXPORT = "3";
	public static final String UPLOAD_TYPE_IMPORT = "4";
	public static final String UPLOAD_TYPE_TEMP = "5";
	public static final String UPLOAD_TYPE_EXPORT_FAIL = "6";
	
	public static final String UPLOAD_KIND_USER = "1";
	public static final String UPLOAD_KIND_SYSTEM = "2";
	
	public static final String FILE_TYPE_JPEG = "JPEG";
	public static final String FILE_TYPE_JPG = "JPG";
	public static final String FILE_TYPE_GIF = "GIF";
	public static final String FILE_TYPE_PNG = "PNG";
	public static final String FILE_TYPE_DOC = "DOC";
	public static final String FILE_TYPE_XLS = "XLS";
	public static final String FILE_TYPE_PPT = "PPT";
	public static final String FILE_TYPE_PDF = "PDF";
		            
	public static final String CONTENT_TYPE_IMAGE_JPEG = "image/jpeg";
	public static final String CONTENT_TYPE_IMAGE_GIF = "image/gif";
	public static final String CONTENT_TYPE_IMAGE_PNG = "image/png";
	public static final String CONTENT_TYPE_WORD = "application/vnd.msword";
	public static final String CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";
	public static final String CONTENT_TYPE_POWERPOINT = "application/vnd.ms-powerpoint";
	public static final String CONTENT_TYPE_PDF = "application/pdf";
	

	
  	/**
  	 * get upload type
  	 * @param upload_type
  	 * @return String
  	 */
  	public static String getUploadPath(String upload_type) {
		if (UPLOAD_TYPE_ATTACH.equals(upload_type)) return "attach";
		if (UPLOAD_TYPE_IMAGE.equals(upload_type)) return "image";
		if (UPLOAD_TYPE_EXPORT.equals(upload_type)) return "export";
		if (UPLOAD_TYPE_IMPORT.equals(upload_type)) return "import";
		if (UPLOAD_TYPE_TEMP.equals(upload_type)) return "temp";
		return null;
  	}
	
	/**
	 * flush output
	 * @param out
	 * @throws Exception
	 */
    private static void flush(OutputStream out) throws Exception {
        try {
        	out.flush();
        } catch (SocketException e) {
        }
    }

  	/**
  	 * get content type
  	 * @param file_type
  	 * @return String
  	 */
  	public static String getContentType(String file_type) {
		String content_type = null;
		
		if (FILE_TYPE_JPG.equals(file_type) || FILE_TYPE_JPEG.equals(file_type)) content_type = CONTENT_TYPE_IMAGE_JPEG;
		if (FILE_TYPE_GIF.equals(file_type)) content_type = CONTENT_TYPE_IMAGE_GIF;
		if (FILE_TYPE_PNG.equals(file_type)) content_type = CONTENT_TYPE_IMAGE_PNG;
		
		if (FILE_TYPE_DOC.equals(file_type)) content_type = CONTENT_TYPE_WORD;
		if (FILE_TYPE_XLS.equals(file_type)) content_type = CONTENT_TYPE_EXCEL;
		if (FILE_TYPE_PPT.equals(file_type)) content_type = CONTENT_TYPE_POWERPOINT;
		
		if (FILE_TYPE_PDF.equals(file_type)) content_type = CONTENT_TYPE_PDF;
		
		return content_type;
  	}

  	/**
  	 * get main file name
  	 * @param file_name
  	 * @return String
  	 */
  	public static String getMainFileName(String file_name) {
  		if (file_name.lastIndexOf(".") == -1) return file_name;
  		return file_name.substring(0, file_name.lastIndexOf("."));
  	}

  	/**
  	 * get expand file name
  	 * @param file_name
  	 * @return String
  	 */
  	public static String getExpandFileName(String file_name) {
  		if (file_name.lastIndexOf(".") == -1) return null;
  		return file_name.substring(file_name.lastIndexOf(".") + 1, file_name.length());
  	}
  	
  	/**
  	 * get file type
  	 * @param file_name
  	 * @return String
  	 */
  	public static String getFileType(String file_name) {
  		if (file_name.lastIndexOf(".") == -1) return null;
  		String file_type = file_name.substring(file_name.lastIndexOf(".") + 1, file_name.length());
  		return file_type.toUpperCase();
  	}
  	
  	/**
  	 * get content type by file name
  	 * @param file_name
  	 * @return String
  	 */
  	public static String getContentTypeByFileName(String file_name) {
  		return getContentType(getFileType(file_name));
  	}

	/**
	 * get file name
	 * @param file_name
	 * @return String
	 */
	public static String getFileName(String file_name) {
		file_name = file_name.replaceAll("\\\\", "/");
		int index = file_name.lastIndexOf("/");
		return index == -1 ? file_name : file_name.substring(index + 1);	
	}
	
	/**
	 * get file path
	 * @param file_name
	 * @return String
	 */
	public static String getFilePath(String file_name) {
		file_name = file_name.replaceAll("\\\\", "/");
		int index = file_name.lastIndexOf("/");
		return index == -1 ? null : file_name.substring(0, index);
	}
  	
    /**
     * get file list
     * @param path
     * @return File[]
     * @throws Exception
     */
    public static File[] getFileList(String path) throws Exception {
		File file = new File(path);
		return file.exists() ? file.listFiles() : null;
    }

    /**
     * delete file
     * @param file
     * @throws Exception
     */
    public static void deleteFiles(File file) throws Exception {
        if (file.exists()) {
	    	if (file.isDirectory()) {
            	File[] fileList = file.listFiles();
          		for (int i=0; i<fileList.length; i++) {
            		deleteFiles(fileList[i]);
          		}
			} else {
				file.delete();
	    	}
	    	file.delete();
	  	}
    }
    

	
	/**
	 * get output stream
	 * @param response
	 * @param contenet_type
	 * @return OutputStream
	 * @throws Exception
	 */
	public static OutputStream getOutputStreamByShow(HttpServletResponse response, String contenet_type) throws Exception {
		//weblogic 10 not support
		//response.reset();
		response.setContentType(contenet_type);
		/* no cache */
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		return response.getOutputStream();
	}

	/**
	 * write the input stream to the output stream
	 * @param in
	 * @param out
	 * @throws Exception
	 */
	public static void writeInputToOutput(InputStream in, OutputStream out) throws Exception {
		writeInputToOutput(in, out, false);
	}
	
	/**
	 * write the input stream to the output stream
	 * @param in
	 * @param out
	 * @param persist
	 * @throws Exception
	 */
	public static void writeInputToOutput(InputStream in, OutputStream out, boolean ispersist) throws Exception {
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int len = -1;
		/* if no arrive the end(len is -1) in the data stream then write */
		while((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
			flush(out);
		}
		if (!ispersist) {
			in.close();
			out.close();
		}
	}
  	
    /**
     * show file
     * @param response
     * @param full_name
     * @param real_name
     * @throws Exception
     */
    public static void showFile(HttpServletResponse response, String full_name, String real_name) throws Exception {
		String file_name = real_name == null ? full_name : real_name;
		
		File file = getFile(full_name);
		
		OutputStream out = getOutputStreamByShow(response, getContentTypeByFileName(file_name));
		writeInputToOutput(new FileInputStream(file), out);
	}


  	
    /**
     * get file
     * @param full_name
     * @return File
     * @throws Exception
     */
    public static File getFile(String full_name) throws Exception {
    	File file = new File(full_name);
		if (!file.exists()) {
			return null;
		};
		return file;
    }
    

 
    /**
     * delete file
     * @param full_name
     * @return boolean
     * @throws Exception
     */
    public static boolean deleteFile(String full_name) throws Exception {
    	File file = new File(full_name);
    	if (file.exists()) return file.delete();
    	return false;
    }
    
    /**
     * delete file
     * @param file_path
     * @param file_name
     * @return boolean
     * @throws Exception
     */
    public static boolean deleteFile(String file_path, String file_name) throws Exception {
    	File file = new File(file_path, file_name);
    	if (file.exists()) return file.delete();
    	return false;
    }
    
    /**
     * upload file
     * @param item:FileItem or IUploadFile
     * @param file_path
     * @param file_name
     * @throws Exception
     */
    public static void uploadFile(Object item, String file_path, String file_name) throws Exception {
    	if (!new File(file_path).isDirectory()) {
    		
    	};
    	
    	File file = new File(file_path, file_name);
    	if (item instanceof FileItem) {
    		((FileItem) item).write(file);
    	}     	
    }
    
    public static void uploadFile(FileItem item) throws Exception
    {
    	String file_path = GlobalParameters.UPLOAD_FILE_PATH + "/" + FileMan.getUploadPath(FileMan.UPLOAD_TYPE_TEMP);
		
		String file_name = FileMan.getFileName(item.getName());
		String uniqeName = DPUtil.getUniqeName();
		uploadFile(item, file_path , uniqeName);
    }
    
    
    public static Map<String,String> uploadFile(FileWrapper item,ServletRequest request) throws Exception
    {
    	 SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");  
    	 String datePath = df.format(new Date());
    	String file_absolute_path = request.getRealPath("/")+GlobalParameters.UPLOAD_FILE_PATH + "/" + FileMan.getUploadPath(FileMan.UPLOAD_TYPE_ATTACH) + "/" + datePath;
    	String file_relative_path = GlobalParameters.UPLOAD_FILE_PATH + "/" + FileMan.getUploadPath(FileMan.UPLOAD_TYPE_ATTACH) + "/" + datePath;
    	//String path = request.getRealPath(file_path);  
    	String fileExt = item.getFileExt();  
    	String uniqeName = DPUtil.getUniqeName();
    	String newFileName = uniqeName + "." + fileExt;
 
	            InputStream in = new FileInputStream(item.getFile()); 
	            if(FileMan.createDirectory(file_absolute_path))
	            {
	            
		            File uploadFile = new File(file_absolute_path, newFileName);    
		            OutputStream out = new FileOutputStream(uploadFile);  
		            
		            byte[] buffer = new byte[1024 * 1024];    
		            int length;    
		            while ((length = in.read(buffer)) > 0) {    
		                out.write(buffer, 0, length);    
		            }    
	    
		            in.close();    
		            out.close(); 
	            } 
	            else
	            {
	            	throw new Exception("文件上传失败" );
	            }
       
    	
		    	Map<String,String> result = new HashMap<String,String>();
		    	result.put("UPLOAD_FILE_NAME", uniqeName);
		    	result.put("FILE_TYPE", fileExt);
		    	result.put("FILE_NAME", item.getFileName());
		    	result.put("FILE_PATH", file_absolute_path);
		    	result.put("FILE_RELATIVE_PATH", file_relative_path);
		    	result.put("FILE_SIZE", String.valueOf(item.getFile().length()));
		    	
		    	return result;
    }
    
	/** 
	 * write object
	 * @param file
	 * @param out
	 * @throws Exception
	 */
	public static void writeObject(OutputStream out, Object obj) throws Exception {
		ObjectOutputStream oout = new ObjectOutputStream(out);
		oout.writeObject(obj);
		out.close();
		oout.close();
	}
    
	/** 
	 * write object
	 * @param file
	 * @param obj
	 * @throws Exception
	 */
	public static void writeObject(File file, Object obj) throws Exception {
		writeObject(new FileOutputStream(file), obj);
	}
	
	/** 
	 * write object
	 * @param file_name
	 * @param obj
	 * @throws Exception
	 */
	public static void writeObject(String file_name, Object obj) throws Exception {
		writeObject(new File(file_name), obj);
	}

	/** 
	 * write object
	 * @param in
	 * @return Object
	 * @throws Exception
	 */
	public static Object readObject(InputStream in) throws Exception {
		ObjectInputStream oin = new ObjectInputStream(in);
		Object obj = oin.readObject();
		in.close();
		oin.close();
		return obj;
	}
	
	/** 
	 * write object
	 * @param file
	 * @return Object
	 * @throws Exception
	 */
	public static Object readObject(File file) throws Exception {
		return readObject(new FileInputStream(file));
	}
	
	/** 
	 * write object
	 * @param file_name
	 * @return Object
	 * @throws Exception
	 */
	public static Object readObject(String file_name) throws Exception {
		File file = getFile(file_name);
		return readObject(file);
	}
	
	/**
	 * create directory
	 * @param file_path
	 * @return boolean
	 * @throws Exception
	 */
    public static boolean createDirectory(String file_path) throws Exception {	    	
    	File dir = new File(file_path);
    	System.out.println(dir.getAbsolutePath());
		if (!dir.exists()) return dir.mkdirs();
		return true;
  	}
	
    /**
     * remove directory
     * @param file_path
     * @param isall
     * @return boolean
     * @throws Exception
     */
    public static boolean removeDirectory(String file_path, boolean isall) throws Exception {
    	File file = new File(file_path);
    	if (!file.exists()) return false;
    	
    	if (isall) {
	    	File[] fileList = file.listFiles();
	    	for (int i=0; i<fileList.length; i++) {
	    		File fileItem = fileList[i];
	    		if (fileItem.isDirectory()) {
	    			removeDirectory(fileItem.getPath(), isall);
	    		} else {
	    			fileItem.delete();
	    		}
	    	}
    	}
	    
	    return file.delete();
    }
	
    
    public static void downFile(HttpServletResponse response, String full_name, String real_name) throws Exception {
 		String file_name = real_name == null ? full_name : real_name;
 		
 		File file = getFile(full_name);
 		
 		OutputStream out = getOutputStreamByDown(response, file_name);
 		writeInputToOutput(new FileInputStream(file), out);
   	}
    
    
    public static InputStream downFile( String full_name, String real_name) throws Exception 
    {
 		
 		File file = getFile(full_name);
 		return new FileInputStream(file);
    }
    
    
	public static OutputStream getOutputStreamByDown(HttpServletResponse response, String file_name) throws Exception {
		//weblogic 10 not support
		//response.reset();
		response.setContentType("application/octet-stream; charset=GBK");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + StringUtils.encodeCharset(file_name) + "\"");
		return response.getOutputStream();
	}
}