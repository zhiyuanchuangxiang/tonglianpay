package com.zycx.action.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zycx.frame.struts.AppController;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-19 下午7:22:19 
 * 类说明 
 */
public class StaffController extends AppController
{
	
	public String initAction() throws Exception
	{
		Map<String,String> condition = new HashMap<String,String>();
		assign("cond",condition);
		return displayTemplate();
	}
	
	public String staffListAction() throws Exception {
		List<Map<String,Object>> staffList = this.staffService.getAllStaff(this.pager);
		Map<String,String> condition = new HashMap<String,String>();
		assign("cond",condition);
		assign("STAFF_LIST",staffList);
		return displayTemplate();
	}
	
	public String exportStaffListAction() throws Exception
	{
		List<Map<String,Object>> staffList = this.staffService.getAllStaff(null);
		if(staffList == null)
		{
			staffList = new ArrayList();
		}
		List[] exportList = {staffList};
//		super.exportFileName="test.zip";
//		super.targetFile = ExcelParser.exportExcel("VipcustImport.xml", "testStaffList.xls", exportList);
		return super.exportExcel("VipcustImport.xml", "testStaffList.xls","test", exportList);
		
	}
	
	public String createAction() throws Exception
	{
		Map<String,String> condition = new HashMap<String,String>();
//		condition.put("ST_STAFF_ID", "11111");
//		assign("staff",condition);
		return displayTemplate("modify");
	}
	
	public String createForPupAction() throws Exception
	{
		Map<String,String> condition = new HashMap<String,String>();
//		condition.put("ST_STAFF_ID", "11111");
//		assign("staff",condition);
		return displayTemplate("modifyInDialog");
	}
	
	
	public String insertAction() throws Exception
	{
		return this.displayResult(super.TIP_SUCCESS, "新增成功 ", super.url("main", "Staff", "init"));
	}
	
	
	public String insertForPopAction() throws Exception
	{
		return this.displayResultForPopUp(super.TIP_SUCCESS, "新增成功 ");
	}

}
