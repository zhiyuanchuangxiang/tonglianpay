package com.zycx.service.demo.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.zycx.dao.DemoDao;
import com.zycx.service.demo.DemoService;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-17 下午3:19:49 
 * 类说明 
 */
public class DemoServiceImpl implements DemoService
{
	public DemoDao demoDao;
	
	public void setDemoDao(DemoDao demoDao) {
		this.demoDao = demoDao;
	}

	@Override
	public List<Map<String, Object>> getForumList() {
		// TODO Auto-generated method stub
		return this.demoDao.getDemoList();
	}

	@Override
	@Transactional
	public int[] saveForumList(List<Map<String, Object>> paramList) {
		// TODO Auto-generated method stub
		int[] result=this.demoDao.saveForumList(paramList);
		this.demoDao.getDemoList();
		return result;
	}
	
	@Transactional
	public int[] updateForumList(List<Map<String, Object>> paramList) {
		// TODO Auto-generated method stub
		int[] result=this.demoDao.updateForumList(paramList);
//		this.demoDao.getDemoList();
		return result;
	}
	
	
	
}
