package com.zycx.service.staff.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zycx.dao.staff.StaffDao;
import com.zycx.frame.struts.Pagination;
import com.zycx.frame.util.Encryptor;
import com.zycx.service.staff.StaffService;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-20 下午12:11:54 
 * 类说明 
 */
@Service
public class StaffServiceImpl implements StaffService{

	@Autowired
	private StaffDao staffDao;
	
	@Override
	public Map<String, Object> getStaffInfoById(String staffId) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(staffId))
		{
			return null;
		}
		return staffDao.getStaffInfoById(staffId);
	}

	@Override
	public Map<String, Object> getStaffInfo(String staffId, String password) {
		// TODO Auto-generated method stub
		password = Encryptor.fnEncrypt(password, "00xjoasys");
		return staffDao.getStaffInfo(staffId, password);
	}

	@Override
	public List<Map<String, Object>> getStaffFunctionList(String staffId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getStaffMenuList(String staffId) {
		// TODO Auto-generated method stub
		return staffDao.getStaffMenuList(staffId);
	}

	@Override
	public List<Map<String, Object>> getStaffDataRightList(String staffId) {
		// TODO Auto-generated method stub
		return staffDao.getStaffDataRightList(staffId);
	}

	@Override
	public List<Map<String, Object>> getAllStaff(Pagination pg) throws Exception {
		// TODO Auto-generated method stub
		return staffDao.getAllStaff(pg);
	}

}
