package com.zycx.dao.staff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zycx.dao.BaseDao;
import com.zycx.frame.struts.Pagination;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-20 上午11:39:32 
 * 类说明 
 */
@Service
public class StaffDao extends BaseDao{
	
	public Map<String,Object> getStaffInfoById(String staffId)
	{
		String SQL = "SELECT * FROM TF_F_STAFF WHERE ST_STAFF_ID=:STAFF_ID";
		Map namedParameters = new HashMap();
		namedParameters.put("STAFF_ID", staffId);
		
		List<Map<String, Object>> staffList = excuteQueryList(SQL,namedParameters);
		if(staffList != null && staffList.size() >0)
		{
			return staffList.get(0);
		}
		return null;
	}
	
	
	public Map<String,Object> getStaffInfo(String staffId,String password)
	{
		String SQL = "SELECT * FROM TF_F_STAFF WHERE ST_STAFF_ID=:STAFF_ID AND ST_PASSWORD=:STAFF_PASSWORD";
		Map namedParameters = new HashMap();
		namedParameters.put("STAFF_ID", staffId);
		namedParameters.put("STAFF_PASSWORD", password);
		List<Map<String, Object>> staffList = excuteQueryList(SQL,namedParameters);
		if(staffList != null && staffList.size() >0)
		{
			return staffList.get(0);
		}
		return null;
	}
	
	public List<Map<String,Object>> getStaffMenuList(String staffId)
	{
				List<Map<String,Object>> menuLevel1;
				//查询父菜单
				Map namedParameters = new HashMap();
				namedParameters.put("STAFF_ID", staffId);
				StringBuffer menu1Sql = new  StringBuffer();
				menu1Sql.append("SELECT DISTINCT MENU_ID,MENU_NAME,MENU_DESC,ACTION_NAME,METHOD_NAME,PAGE_PARAM,MENU_ICON,NAME_SPACE FROM TD_S_MENU WHERE MENU_ID IN (");
				menu1Sql.append(" SELECT DISTINCT C.MENU_PARENT_ID FROM TD_S_MENU C,(");
				menu1Sql.append(" SELECT B.RIGHT_CODE FROM TD_B_STAFF_ROLE A, TD_B_ROLE_RIGHT B");
				menu1Sql.append(" WHERE A.STAFF_ID = :STAFF_ID AND A.STATUS = '0' AND A.ROLE_CODE = B.ROLE_CODE AND B.TYPE = '0' AND B.STATUS = '0') D");
				menu1Sql.append(" WHERE C.MENU_ID=D.RIGHT_CODE AND C.MENU_TYPE='1' AND C.MENU_STATUS='0'");
				menu1Sql.append(" ) AND MENU_TYPE='0' AND MENU_STATUS='0' ORDER BY MENU_ID ASC");
				//查询子菜单
				
				StringBuffer menu2Sql = new  StringBuffer();
				menu2Sql.append("SELECT DISTINCT C.MENU_ID,C.MENU_NAME,C.MENU_DESC,ACTION_NAME,METHOD_NAME,PAGE_PARAM,MENU_ICON,NAME_SPACE ");
				menu2Sql.append( "   FROM TD_S_MENU C,(");
				menu2Sql.append(" SELECT B.RIGHT_CODE FROM TD_B_STAFF_ROLE A, TD_B_ROLE_RIGHT B");
				menu2Sql.append(" WHERE A.STAFF_ID = :STAFF_ID  AND A.STATUS = '0' AND A.ROLE_CODE = B.ROLE_CODE AND B.TYPE = '0' AND B.STATUS = '0') D");
				menu2Sql.append(" WHERE C.MENU_ID=D.RIGHT_CODE AND C.MENU_TYPE='1' AND C.MENU_STATUS='0'");
				menu2Sql.append(" AND C.MENU_PARENT_ID=:MENU_ID ORDER BY C.MENU_ID ASC");
				
				menuLevel1 = excuteQueryList(menu1Sql.toString(),namedParameters);
				if(menuLevel1!=null && menuLevel1.size()>0)
				{
					for(int i=0;i<menuLevel1.size();i++)
					{
						Map childParam = new HashMap();
						childParam.put("STAFF_ID", staffId);
						childParam.put("MENU_ID", menuLevel1.get(i).get("MENU_ID"));
						
						List<Map<String,Object>> childList = excuteQueryList(menu2Sql.toString(),childParam);
						menuLevel1.get(i).put("CHILD_MENU", childList);
					
					}
				}
				
				return menuLevel1;
	}
	/**
	 * 获取员工所有的数据权限
	 * @param staffId
	 * @return
	 */
	public List<Map<String,Object>> getStaffDataRightList(String staffId) 
	{
		List<Map<String,Object>> dataRightList = null;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT C.* ");
		sqlBuffer.append(" FROM TD_B_STAFF_ROLE A, ");
		sqlBuffer.append(" TD_B_ROLE_RIGHT B, ");
		sqlBuffer.append(" TD_B_DATARIGHT C ");
		sqlBuffer.append("  WHERE A.STAFF_ID =:STAFF_ID");
		sqlBuffer.append("  AND   A.ROLE_CODE = B.ROLE_CODE");
		sqlBuffer.append("  AND   B.TYPE = '1'");
		sqlBuffer.append("  AND   C.RIGHT_CODE = B.RIGHT_CODE");
		
		Map namedParameters = new HashMap();
		namedParameters.put("STAFF_ID", staffId);
		
		dataRightList = excuteQueryList(sqlBuffer.toString(),namedParameters);
		return dataRightList;
	}

	public List<Map<String,Object>> getAllStaff(Pagination pg) throws Exception
	{
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT * FROM TF_F_STAFF");
		return excuteQueryList(sqlBuffer.toString(), null,pg);
	}

}
