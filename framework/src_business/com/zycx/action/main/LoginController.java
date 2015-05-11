package com.zycx.action.main;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.zycx.frame.struts.AppSafeController;
import com.zycx.frame.struts.GlobalParameters;
import com.zycx.frame.util.DPUtil;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-19 下午7:22:19 
 * 类说明 
 */
public class LoginController extends AppSafeController
{



	@Action(value="/login")
	public String loginAction() throws Exception {

		
		return displayTemplate();
	}
	
	
	public String logonAction() throws Exception {
		String staffId = _REQUEST_.getParameter("staffId");
		String password = _REQUEST_.getParameter("password");
		if(StringUtils.isNotEmpty(password))
		{
			
		}
		Map<String,Object> staffInfo = this.staffService.getStaffInfo(staffId, password);
		System.out.println(staffInfo);
		String url = convertForward(get("forward").toString());
		if(staffInfo != null)
		{
			this.staffLogon(staffInfo);
			return redirect(url);
		}
		else
		{
			
		}
		return redirect(url);
	}
	
	
	
	
	/**
	 * URL 转换
	 * @param forward
	 * @return
	 * @throws Exception
	 */
	private String convertForward(String forward) throws Exception {
		if(DPUtil.empty(forward)) {
			return GlobalParameters.WEB_URL + "/main";
		} else {
			if("back".equals(forward)) {
				String backUrl = _REQUEST_.getHeader("Referer");
				if(null == backUrl) {
					return GlobalParameters.WEB_URL + "/main";
				} else {
					return backUrl;
				}
			} else {
				return forward;
			}
		}
	}

}
