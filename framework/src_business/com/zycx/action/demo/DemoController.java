package com.zycx.action.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zycx.frame.struts.AppController;
import com.zycx.frame.struts.GlobalParameters;
import com.zycx.service.demo.DemoService;

public class DemoController extends AppController{
	
	
	private  DemoService demoService; 

	
	private Map<String,Object> param;



	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public DemoService getDemoService() {
		return demoService;
	}

	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}

	public String populateAction() throws Exception
	{
		System.out.println("===================" + GlobalParameters.WEB_URL);
		List<Map<String,Object>> forumList = demoService.getForumList();
		System.out.println(forumList);
		assign("test111", "test111");
		this.title ="111111111";
		this.param = new HashMap<String,Object>();
		assign("forumList",forumList);
		return displayTemplate();
	}
	
	public String saveAction()
	{
		List<Map<String,Object>> paramList = new ArrayList<Map<String,Object>>();
		Map<String,Object> paramOne = new HashMap<String,Object>();
		paramOne.put("FORUMID", "3");
		paramOne.put("FORUMNAME",  "测试批量");
		paramOne.put("FORUMDESC", "测试批量");
		paramList.add(paramOne);
		
		Map<String,Object> paramTwo = new HashMap<String,Object>();
		paramTwo.put("FORUMID", "4");
		paramTwo.put("FORUMNAME",  "测试批量");
		paramTwo.put("FORUMDESC", "测试批量");
		paramList.add(paramTwo);
		
		Map<String,Object> paramThree = new HashMap<String,Object>();
		paramThree.put("FORUMID", "4");
		paramThree.put("FORUMNAME",  "测试批量");
		paramThree.put("FORUMDESC", "测试批量");
		paramList.add(paramThree);
		
		int[] result = demoService.saveForumList(paramList);
		this.demoService.getForumList();
		return super.SUCCESS;
	}
	
	public String execute() throws Exception {
		return "success";
	}
}
