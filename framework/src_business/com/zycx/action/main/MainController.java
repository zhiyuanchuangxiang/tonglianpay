package com.zycx.action.main;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.zycx.frame.struts.AppController;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-19 下午7:22:19 
 * 类说明 
 */
public class MainController extends AppController
{

	public String loginAction() throws Exception {

		
		return displayTemplate();
	}
	
	
	@Action(value="/main")
	public String platformAction() throws Exception {
		if(null == currentStaff) return redirect("login", "");
		//assign("currentPermitMap", getCurrentPermitMap());
		List<Map<String,Object>> menuList = staffService.getStaffMenuList(currentStaff.get("ST_STAFF_ID").toString());
		assign("MENU_LIST", menuList);
		return displayTemplate();
	}

}
