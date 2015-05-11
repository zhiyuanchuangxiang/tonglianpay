package com.zycx.frame.struts;

import java.util.Hashtable;
import java.util.Map;

import com.zycx.frame.util.ServletUtil;

/**
 * 全局默认参数
 */
public class GlobalParameters {
	
	private static GlobalParameters globalParameters;
	private static Map<String, Object> cache = new Hashtable<String, Object>(0);
	
	public static String STRUTS_SUFFIX; // 控制器Controller后缀
	public static String STRUTS_PACKAGES; // 控制器Controller所在包名称
	public static String STRUTS_ACTION_SIGN = "!"; // 动态方法调用标识
	public static String STRUTS_ACTION_SUFFIX = "Action"; // 动态方法Action后缀
	
	public static String SKIN_FOLDER = "skin"; // 主题资源所在目录
	public static String SKIN_NAME = "default"; // 主题名称
	public static String TABLE_PREFIX = "oa_"; // 数据库表前缀
	public static String TEMPLATE_SUFFIX = "jsp"; // 视图模板后缀
	public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"; // 时间格式
	public static String DEFAULT_PASSWORD = "admin888"; // 默认密码
	
	public static String WEB_URL = "http://localhost:8089/framework"; // 系统访问路径（结尾不含斜杠）
	public static String WEB_NAME = "IISquare.com"; // 系统名称
	
	public static int COOKIE_MAX_AGE = -1; // Cookie生存周期
	public static int PAGE_SIZE = 10; // 默认分页大小
	public static String UPLOAD_FILE_PATH = "/upload/";
	public static String UPLOAD_FILE_PATH_TEMP = "/upload/temp";
	
	public static final int EXCEL_PAGE_COUNT = 5000;
	
	public static String SESSION_NAME = ServletUtil.getSessionName(); // Session名称

	public String getSTRUTS_SUFFIX() {
		return STRUTS_SUFFIX;
	}

	public String getSTRUTS_PACKAGES() {
		return STRUTS_PACKAGES;
	}

	public String getSTRUTS_ACTION_SIGN() {
		return STRUTS_ACTION_SIGN;
	}

	public String getSTRUTS_ACTION_SUFFIX() {
		return STRUTS_ACTION_SUFFIX;
	}

	public String getSKIN_FOLDER() {
		return SKIN_FOLDER;
	}

	public void setSKIN_FOLDER(String sKIN_FOLDER) {
		SKIN_FOLDER = sKIN_FOLDER;
	}

	public String getSKIN_NAME() {
		return SKIN_NAME;
	}

	public void setSKIN_NAME(String sKIN_NAME) {
		SKIN_NAME = sKIN_NAME;
	}

	public String getTABLE_PREFIX() {
		return TABLE_PREFIX;
	}

	public void setTABLE_PREFIX(String tABLE_PREFIX) {
		TABLE_PREFIX = tABLE_PREFIX;
	}

	public String getTEMPLATE_SUFFIX() {
		return TEMPLATE_SUFFIX;
	}

	public void setTEMPLATE_SUFFIX(String tEMPLATE_SUFFIX) {
		TEMPLATE_SUFFIX = tEMPLATE_SUFFIX;
	}

	public String getDATE_FORMAT() {
		return DATE_FORMAT;
	}

	public void setDATE_FORMAT(String dATE_FORMAT) {
		DATE_FORMAT = dATE_FORMAT;
	}

	public String getDEFAULT_PASSWORD() {
		return DEFAULT_PASSWORD;
	}

	public void setDEFAULT_PASSWORD(String dEFAULT_PASSWORD) {
		DEFAULT_PASSWORD = dEFAULT_PASSWORD;
	}

	public String getWEB_URL() {
		return WEB_URL;
	}

	public void setWEB_URL(String wEB_URL) {
		WEB_URL = wEB_URL;
	}

	public String getWEB_NAME() {
		return WEB_NAME;
	}

	public void setWEB_NAME(String wEB_NAME) {
		WEB_NAME = wEB_NAME;
	}

	public int getCOOKIE_MAX_AGE() {
		return COOKIE_MAX_AGE;
	}

	public void setCOOKIE_MAX_AGE(int cOOKIE_MAX_AGE) {
		COOKIE_MAX_AGE = cOOKIE_MAX_AGE;
	}

	public int getPAGE_SIZE() {
		return PAGE_SIZE;
	}

	public void setPAGE_SIZE(int pAGE_SIZE) {
		PAGE_SIZE = pAGE_SIZE;
	}

	public String getSESSION_NAME() {
		return SESSION_NAME;
	}

	private GlobalParameters() {
		
	}
	
	public static GlobalParameters getInstance() {
		if(null == globalParameters) {
			globalParameters = new GlobalParameters();
		}
		return globalParameters;
	}
	
	public static Object get(String key) {
		return cache.get(key);
	}
	
	public static void put(String key, Object value) {
		if(null == value) {
			cache.remove(key);
		} else {
			cache.put(key, value);
		}
	}
}
