package com.zycx.frame.struts;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.inject.Inject;
import com.zycx.frame.file.FileWrapper;
import com.zycx.frame.util.StringUtils;
import com.zycx.frame.util.parser.ExcelParser;

/**
 * 控制器超类
 */
public class ControllerBase extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 控制器返回类型，此处没有采用枚举类型的原因是Struts只支持String类型返回值
	 */
	public static class ControllerResultType {
		public static final String _FREEMARKER_ = "_FREEMARKER_";
		public static final String _REDIRECT_ = "_REDIRECT_";
		public static final String _TEXT_ = "_TEXT_";
		public static final String _STREAM_ = "_STREAM_";
		public static final String _PLAIN_TEXT_ = "_PLAIN_TEXT_";
	}
	
	public static final String TIP_SUCCESS = "0";
	public static final String TIP_ERROR = "1";
	public static final String TIP_WARNING = "2";
	
	
	
	public String conventionResultPath;
	public ControllerBase _BASE_;
	public HttpServletRequest _REQUEST_;
	public HttpServletResponse _RESPONSE_;
	public ServletContext _CONTEXT_;
	public Map<String, Object>  _ASSIGN_;//_ASSIGN_ 存储结果
	
	public Map<String,FileWrapper> fileList = new HashMap<String,FileWrapper>();
	
	public Pagination pager;
	
	
	
/*	public Map<String, Object> get_ASSIGN_() {
		return _ASSIGN_;
	}
*/

	public Map<String, Object> _SESSION_MAP_;
	public Map<String, Object> _PARAMETERS_;
	
/*	public String _;*/
	
	public  String _MODULE_, _CONTROLLER_, _ACTION_, _CONTROLLER_RESULT_;
	public GlobalParameters _GLOBAL_PARAMETERS_;

	public  String title;

	
	private String exportFileName = "导出文件";
	
	private InputStream targetFile;
	
	
	/**
	 * 导出的文件名称
	 * @return
	 */

	public String getExportFileName() {
		return exportFileName;
	}

     /**
	 * 返回给视图的文件流
	 * @return
	 */
	public InputStream getTargetFile() {
		return targetFile;
	}
	


	
	
	public String getTitle() {
		return title;
	}
	public ControllerBase() {
		
    }
	
	/**
	 * 初始化函数，必须执行
	 * @return 是否初始化成功
	 * @throws Exception 
	 */
	public void init(ActionInvocation invocation) throws Exception {
		/* 初始化环境 */
		ActionContext context = ActionContext.getContext();
		this._BASE_ = this;
        this._REQUEST_ = ServletActionContext.getRequest();
        try {
			this._REQUEST_.setCharacterEncoding("UTF-8");
		} 
        catch (UnsupportedEncodingException e) 
		{
        	e.printStackTrace();
		}
        this._RESPONSE_ = ServletActionContext.getResponse();
        this._RESPONSE_.setContentType("text/html;charset=utf-8");
        this._CONTEXT_ = ServletActionContext.getServletContext();
        this._SESSION_MAP_ = context.getSession();
        this._PARAMETERS_ = context.getParameters();
        this._ASSIGN_ = new HashMap<String, Object>();
        /* 初始化控制器 */
        String moduleName = this.getClass().getName();
		//String controllerName = invocation.getInvocationContext().getName();
		String controllerName = this.getClass().getSimpleName();
		String actionName = invocation.getProxy().getMethod();
		moduleName = moduleName.replace(GlobalParameters.STRUTS_PACKAGES + ".", "");
		moduleName = moduleName.replace("." + controllerName, "");
		_MODULE_ = moduleName.replace(".", "/");
		_CONTROLLER_ = controllerName.replace(GlobalParameters.STRUTS_SUFFIX, "");
		_ACTION_ = actionName.replace(GlobalParameters.STRUTS_ACTION_SUFFIX, "");
		_GLOBAL_PARAMETERS_ = GlobalParameters.getInstance();
		this.initPager();//初始化分页组件
		this.initFileList();
	}
	
	/**
	 * 当Action方法执行后被调用
	 */
	public void destroy(String actionResult) {
		
	}
	
	/**
	 * 用户相关异常处理
	 * @param exception
	 * @return 视图
	 */
	protected String memberException(Exception exception) throws Exception {
		return null;
	}
	
	protected String url() {
		return url(_ACTION_);
	}
	
	protected String url(String action) {
		return url(_CONTROLLER_, action);
	}
	
	protected String url(String controller, String action) {
		return url(_MODULE_, controller, action);
	}
	
	/**
	 * 获取URL地址
	 * @param module 模块名称
	 * @param controller 控制器名称
	 * @param action 方法名称
	 * @return
	 */
	protected String url(String module, String controller, String action) {
		return GlobalParameters.WEB_URL + "/" + module
				+ "/" + controller + GlobalParameters.STRUTS_ACTION_SIGN + action;
	}
	
	protected String displayTemplate() throws Exception {
		return displayTemplate(_ACTION_);
	}
	
	protected String displayTemplate(String action) throws Exception {
		return displayTemplate(_CONTROLLER_, action);
	}
	
	protected String displayTemplate(String controller, String action) throws Exception {
		return displayTemplate(_MODULE_, controller, action);
	}
	
	/**
	 * 渲染视图
	 * @param module
	 * @param controller
	 * @param action
	 * @return
	 * @throws Exception
	 */
	protected String displayTemplate(String module, String controller, String action) throws Exception {
		String result = conventionResultPath + module + "/" + controller+ "/" + action
				+ "." + GlobalParameters.TEMPLATE_SUFFIX;
		return display(result, ControllerResultType._FREEMARKER_);
	}
	
	/**
	 * 输出文本信息
	 * @param text
	 * @return
	 * @throws Exception
	 */
	protected String displayText(String text) throws Exception {
		return display(text, ControllerResultType._TEXT_);
	}
	
	/**
	 * 输出JSON信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	protected String displayJSON(Object object) throws Exception {
		String result;
		if(object instanceof Map) {
			result = JSONObject.fromObject(object).toString();
		} else {
			result = JSONArray.fromObject(object).toString();
		}
		return display(result, ControllerResultType._TEXT_);
	}
	
	protected String redirect() throws Exception {
		return redirect(_ACTION_);
	}
	
	/**
	 * 重定向自定义URL地址
	 * @param url
	 * @return
	 * @throws Exception
	 */
	protected String redirect(String url) throws Exception {
		return display(url, ControllerResultType._REDIRECT_);
	} 
	
	protected String redirect(String action, String params) throws Exception {
		return redirect(_CONTROLLER_, action, params);
	}
	
	protected String redirect(String controller, String action, String params) throws Exception {
		return redirect(_MODULE_, controller, action, params);
	}
	
	/**
	 * 重定向至对应控制器
	 * @param module
	 * @param controller
	 * @param action
	 * @param params
	 * @return
	 * @throws Exception
	 */
	protected String redirect(String module, String controller, String action, String params) throws Exception {
		String result = GlobalParameters.WEB_URL + "/" + module + "/"
				+ controller + GlobalParameters.STRUTS_ACTION_SIGN + action + params;
		return display(result, ControllerResultType._REDIRECT_);
	}
	
	/**
	 * 根据类型输出视图
	 * @param result
	 * @param type
	 * @return
	 * @throws Exception
	 */
	protected String display(String result, String type) throws Exception {
		if(null == type) {
			return result;
		} else if(ControllerResultType._FREEMARKER_ == type) {
			_CONTROLLER_RESULT_ = result;
			return type;
		} else if(ControllerResultType._TEXT_ == type){
			PrintWriter out = _RESPONSE_.getWriter();
			out.print(result);
			out.flush();
		} else if (ControllerResultType._REDIRECT_ == type) {
			_CONTROLLER_RESULT_ = result;
			return type;
		}
		return null;
	}
	
	protected String displayResult(String resultType,String msg,String url) throws Exception
	{
		this.assign("MSG_TYPE", resultType);
		this.assign("MSG_TIP", msg);
		this.assign("REDIRECT_URL", url);
		return this.displayTemplate("main", "Common", "tips");
	}
	
	
	protected String displayResultForPopUp(String resultType,String msg) throws Exception
	{
		this.assign("MSG_TYPE", resultType);
		this.assign("MSG_TIP", msg);
		return this.displayTemplate("main", "Common", "popUpTip");
	}
	
	/**
	 * 设置视图中需要的参数
	 * @param key
	 * @param value
	 */
	protected void assign(String key, Object value) {
		_ASSIGN_.put(key, value);
	}

	/**
	 * 获取请求参数
	 * @param key 参数名称
	 * @param bReturnArray 获取参数数组
	 * @param bReturnNull 当参数不存在时返回NULL或空字符串
	 * @return
	 */
	protected Object get(String key, Boolean bReturnArray, Boolean bReturnNull) {
		Object object = _PARAMETERS_.get(key);
		if(null == object) {
			if(bReturnNull) {
				if(bReturnArray) {
					boolean bool[] = {false};
					object = bool;
				}
			} else {
				if(bReturnArray) {
					String str[] = {""};
					object = str;
				} else {
					object = "";
				}
			}
		} else {
			Object array[] = (Object[]) object;
			if(bReturnArray) {
				object = array;
			} else {
				object = array[0];
			}
		}
		return object;
	}
	
	protected Object get(String key, Boolean bReturnArray) {
		return get(key, bReturnArray, false);
	}
	
	protected Object get(String key) {
		return get(key, false, false);
	}
	
	/**
	 * 设置请求参数
	 * @param key
	 * @param value
	 */
	protected void put(String key, Object value) {
		Object object = _PARAMETERS_.get(key);
		if(null == object) {
			Object array[] = {value};
			_PARAMETERS_.put(key, array);
		} else {
			Object array[] = (Object[]) object;
			List<Object> list = Arrays.asList(array);
			list.add(value);
			_PARAMETERS_.put(key, list.toArray());
		}
	}
	
	
	@Inject
	protected void setSuffix(@Inject("struts.convention.result.path") String conventionResultPath) {
		this.conventionResultPath = conventionResultPath;
	}
	
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this._SESSION_MAP_= arg0;
		
	}
	
	private void initPager()
	{
		int page = 1;
		int rownum = GlobalParameters.PAGE_SIZE;
		if(this.pager == null)
		{
			this.pager = new Pagination();
		}
		
		 try
		 {
			 page = _REQUEST_.getParameter("page") == null? 1 :Integer.parseInt(_REQUEST_.getParameter("page"));
			 rownum  = _REQUEST_.getParameter("rownum") == null ? rownum: Integer.parseInt(_REQUEST_.getParameter("rownum"));
		 }
		 catch(Exception e)
		 {
			 page =1;
			 rownum = GlobalParameters.PAGE_SIZE;
		 }
		
		 this.pager.setPage(page);
		 this.pager.setRowNum(rownum);
		
	}
	
	public  boolean isMultipartContent(HttpServletRequest request){
		return org.apache.commons.fileupload.FileUpload.isMultipartContent(new ServletRequestContext(request));
				
	}
	/**
	 * 按配置文件导出
	 * @param sheetXML   配置文件名称
	 * @param innerFileName  压缩文件内的 文件名
	 * @param zipFileName    最终展示给用户的文件名
	 * @param exportList     需要导出的结果集
	 * @throws Exception
	 */
	public String  exportExcel(String sheetXML, String innerFileName,String zipFileName, List[] exportList ) throws Exception
	{
		this.exportFileName=StringUtils.encodeCharset(zipFileName + ".zip");
		this.targetFile = ExcelParser.exportExcel(sheetXML, innerFileName, exportList);
		return ControllerResultType._STREAM_;
	}
	
	private void initFileList() throws Exception
	{
		if(this.isMultipartContent(this._REQUEST_))
		{
			MultiPartRequestWrapper wrapper = (MultiPartRequestWrapper) this._REQUEST_;   
			Enumeration en =  wrapper.getFileParameterNames();
			if(en != null)
			{
				while(en.hasMoreElements())
				{
					String fieldName = (String)en.nextElement();
					File file = wrapper.getFiles(fieldName)[0];
					String fileName = wrapper.getFileNames(fieldName)[0];
					FileWrapper fileItem = new FileWrapper(file,fileName);
					this.fileList.put(fieldName, fileItem);
				
				}
				System.out.println("============");
			}
		}
	}
}