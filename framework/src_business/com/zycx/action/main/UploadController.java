package com.zycx.action.main;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import com.zycx.frame.file.FileMan;
import com.zycx.frame.file.FileWrapper;
import com.zycx.frame.struts.AppController;
import com.zycx.frame.util.parser.ExcelParser;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-19 下午7:22:19 
 * 类说明 
 */
public class UploadController extends AppController
{
	
	public String initAction() throws Exception
	{
		return displayTemplate();
	}
	
	public String uploadAction() throws Exception {
		if(super.fileList.size()>0)
		{
			FileWrapper item = fileList.get("fileup");
			Map<String,String> uploadresult = FileMan.uploadFile(item,this._REQUEST_);
			System.out.println();
			assign("uploadResult", uploadresult);
		}
		
		return displayTemplate();
	}
	
	public String importExcelAction() throws Exception 
	{
		List[] importList = null;
		if(super.fileList.size()>0)
		{
			FileWrapper item = fileList.get("fileup");
			File importFile = item.getFile();
			importList = ExcelParser.importExcel("VipcustImport.xml", new FileInputStream(importFile));
			System.out.println(importList);
			
		}
		if(importList != null &&  importList.length>0)
		{
			assign("exportResultList", importList[0]);
		}
		
		return displayTemplate("importResult");
	}

}
