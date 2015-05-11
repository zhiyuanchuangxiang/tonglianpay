package com.zycx.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-15 上午12:39:24 
 * 类说明 
 */
public class DemoDao extends BaseDao{
    
	public List<Map<String, Object>> getDemoList(){
		
		String query = "SELECT * FROM FORUMS";
		Map namedParameters = new HashMap();
		namedParameters.put("forumId", new Integer(1));
		
		List<Map<String, Object>> result = excuteQueryList(query,null);
		return result;
		
	}
	
	/**
	 * 批量插入测试
	 */
	
	public int[] saveForumList(List<Map<String,Object>> paramList)
	{
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("INSERT INTO FORUMS(FORUMID, FORUMNAME, FORUMDESC)");
		sqlBuffer.append("VALUES (:FORUMID,:FORUMNAME,:FORUMDESC)");
		Map<String,Object>[] array = new HashMap[paramList.size()];
		return super.excuteBatchUpdate(sqlBuffer.toString(),  paramList.toArray(array));
	}
	
	public int[] updateForumList(List<Map<String,Object>> paramList)
	{
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("UPDATE  FORUMS SET FORUMNAME=:FORUMNAME,FORUMDESC=:FORUMDESC WHERE FORUMID=:FORUMID");
		Map<String,Object>[] array = new HashMap[paramList.size()];
		return super.excuteBatchUpdate(sqlBuffer.toString(),  paramList.toArray(array));
	}

}
