package com.zycx.frame.struts;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionInvocation;
import com.zycx.frame.exception.CodeExpiredException;
import com.zycx.frame.exception.NoLoginException;
import com.zycx.frame.exception.PermitDeniedException;
import com.zycx.service.staff.StaffService;


/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-19 下午6:26:09 
 * 类说明 
 */
public class AppSafeController extends ControllerBase{
	
	public Map<String,Object> currentStaff;
	
	@Autowired
	public StaffService  staffService;
	
	public void init(ActionInvocation invocation) throws Exception 
	{
		super.init(invocation);

	}
	
	
	
	
	@Override
	protected String memberException(Exception exception) throws Exception {
		if(exception instanceof NoLoginException) {
			return displayTemplate("main", "Member", "noLogin");
		} else if(exception instanceof CodeExpiredException) {
			return displayTemplate("main", "Member", "codeExpired");
		} else if(exception instanceof PermitDeniedException) {
			return displayTemplate("main", "Member", "permitDenied");
		} else {
			return null;
		}
	}
	
	public boolean hasPermit(String action) throws NoLoginException {
		return false;
	}
	
	
	public boolean hasPermit(Map<String,Object> currentStaff) throws NoLoginException
	{
		if(null == currentStaff) throw new NoLoginException();
		return false;
	}

	
	
	public void staffLogon(Map<String,Object> staffInfo)
	{
		_SESSION_MAP_.put("ST_STAFF_ID", staffInfo.get("ST_STAFF_ID"));
		_SESSION_MAP_.put("ST_STAFF_NAME", staffInfo.get("ST_STAFF_NAME"));
		_SESSION_MAP_.put("ST_DEPART_ID", staffInfo.get("ST_DEPART_ID"));
		List<Map<String,Object>> functionList = this.staffService.getStaffFunctionList((String) staffInfo.get("ST_STAFF_ID"));
		_SESSION_MAP_.put("ST_FUNCTION_LIST", functionList);
		
		List<Map<String,Object>> dataRightList = this.staffService.getStaffDataRightList((String) staffInfo.get("ST_STAFF_ID"));
		_SESSION_MAP_.put("ST_DATARIGHT_LIST", dataRightList);
	}
	
	/**
	 * 主要处理用户在别处登录的逻辑。1.0版本暂时不处理
	 * @param staffId
	 * @return
	 */
	public Map<String,Object> getCurrentStaff(String staffId)
	{
		if(StringUtils.isBlank(staffId))
		{
			return null;
		}
		Map<String,Object> staffInfo = staffService.getStaffInfoById(staffId);
		if(staffInfo == null)
		{
			return null;
		}
		else
		{
			
		}
		return staffInfo;
	}

}
