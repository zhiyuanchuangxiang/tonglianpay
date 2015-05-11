package com.zycx.service.demo;

import java.util.List;
import java.util.Map;

import com.zycx.service.BaseService;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-17 下午3:03:03 
 * 类说明 
 */
public interface DemoService  extends BaseService{
	
	public List<Map<String,Object>> getForumList();
	
	public int[] saveForumList(List<Map<String,Object>> paramList);
	
	
	public int[] updateForumList(List<Map<String, Object>> paramList);
}
