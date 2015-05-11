package com.zycx.service.staff;

import java.util.List;
import java.util.Map;

import com.zycx.frame.struts.Pagination;
import com.zycx.service.BaseService;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-20 上午11:35:10 
 * 类说明 
 */
public interface StaffService extends BaseService{
	
	public Map<String,Object> getStaffInfoById(String staffId);
	
	public Map<String,Object> getStaffInfo(String staffId,String password);
	
	public List<Map<String,Object>> getStaffFunctionList(String staffId);
	
	public List<Map<String,Object>> getStaffMenuList(String staffId);
	
	public List<Map<String,Object>> getStaffDataRightList(String staffId) ;
	
	public List<Map<String,Object>> getAllStaff(Pagination pg) throws Exception;
	
	

}
