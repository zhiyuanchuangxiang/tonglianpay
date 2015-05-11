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
public class AppController extends ControllerBase{
	
	public Map<String,Object> currentStaff;
	
	@Autowired
	public StaffService  staffService;
	
	public void init(ActionInvocation invocation) throws Exception 
	{
		super.init(invocation);
		String staffId = _SESSION_MAP_.get("ST_STAFF_ID") == null ? "": (String)_SESSION_MAP_.get("ST_STAFF_ID") ;
		currentStaff = this.getCurrentStaff(staffId);
		if(!hasPermit(currentStaff))
		{
			throw new PermitDeniedException();
		}
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
	
	public boolean hasPermit(String rightCode) throws NoLoginException{
		
		if(null == currentStaff) throw new NoLoginException();
		
		List<Map<String,Object>> dataRightList =  (_SESSION_MAP_.get("ST_DATARIGHT_LIST") ==null ? null :(List<Map<String, Object>>)_SESSION_MAP_.get("ST_DATARIGHT_LIST"));
		if(dataRightList == null)
		{
			return false;
		}
		for(Map<String,Object> rightItem : dataRightList)
		{
			String code = rightItem.get("RIGHT_CODE").toString();
			if(code.equals(rightCode))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	public boolean hasPermit(Map<String,Object> currentStaff) throws NoLoginException
	{
		if(null == currentStaff) throw new NoLoginException();
		
		
		return true;
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
