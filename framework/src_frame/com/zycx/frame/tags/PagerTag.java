package com.zycx.frame.tags;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
  
public class PagerTag extends TagSupport {  
    private static final long serialVersionUID = 5729832874890369508L;  
	private int rowNum=10;//每页显示数目
	

	private int count = 0 ;//总记录数 
	private int page=1;//当前页数 
	private int pageTotal;//总页数
	private String refreshId;
	private String nameSpace;
	private String action;
	private String method;
	private Map   parttest;
  
  


	public Map getParttest() {
		return parttest;
	}


	public void setParttest(Map parttest) {
		this.parttest = parttest;
	}


	public int doStartTag() throws JspException {  
  
        StringBuilder sb = new StringBuilder();  
  
  
      
        if (this.count == 0) {
        	  sb.append("<div class=\"pagination\">\r\n");  
        	  	sb.append("<strong>没有可显示的项目</strong>\r\n");  
        	  sb.append("</div>");
        } else {  
            if (this.page > this.pageTotal)  
                this.page = this.pageTotal;  
            if (this.page < 1)  
                this.page = 1;  
  
            String url = buildUrl();
            sb.append("<form method=\"post\" action=\"").append(url)  
                    .append("\" >\r\n");  
  
            HttpServletRequest request = (HttpServletRequest) this.pageContext  
                    .getRequest();  
            
            request.getContextPath();
            Enumeration enumeration = request.getParameterNames();  
            String name = null;  
            String value = null;  
  
            while (enumeration.hasMoreElements()) {  
                name = (String) enumeration.nextElement();  
                value = request.getParameter(name);  
 
                if(!name.equals("page") && !name.equals("rowNum") ){
                    sb.append("<input type=\"hidden\" name=\"").append(name)  
                            .append("\" value=\"").append(value).append(  
                                    "\"/>\r\n");  
                }  
            }  
  
        	sb.append("<div class=\"row\">");
        	
        		sb.append("<div class=\"col-xs-6\">");
        	
        			sb.append("<div class=\"dataTables_info\">");
        				sb.append("共" + this.pageTotal+"页");
        			sb.append("</div>");
        		sb.append("</div>");
        	
        	
        	
        	sb.append("<div class=\"col-xs-6\">");
        			sb.append("<div class=\"dataTables_paginate paging_simple_numbers\">");
        				sb.append("<ul class=\"pagination\">");
            if (this.page == 1) {
                sb.append("<li class=\"paginate_button previous disabled\">");
	                sb.append("<a href=\"javascript:void(0)\">");
	                sb.append("上一页");
	                sb.append("</a>");
                sb.append("</li>");	
            }
            else {  
            	 sb.append("<li class=\"paginate_button previous\">");
	            	sb.append("<a href=\"javascript:void(0)\" onclick=\"trunToPage(").append(this.page-1).append(",").append("'" + this.refreshId + "'").append(",").append("this);\">");
	            	sb.append("上一页");
	            	sb.append("</a>");
	            sb.append("</li>");	
            }  
  
            int start = 1; 
          
            if (this.page > 4 && this.pageTotal >10) {  
            	
                start = this.page - 1;  
                
                sb.append("<li class=\"paginate_button previous\">");
	            	sb.append("<a href=\"javascript:void(0)\" onclick=\"trunToPage(").append("1").append(",").append("'" + this.refreshId + "'").append(",").append("this);\">");
	            		sb.append("1");
	            	sb.append("</a>");
            	sb.append("</li>");	
            	
            	sb.append("<li class=\"paginate_button previous\">");
	            	sb.append("<a href=\"javascript:void(0)\" onclick=\"trunToPage(").append("2").append(",").append("'" + this.refreshId + "'").append(",").append("this);\">");
	            		sb.append("2");
	            	sb.append("</a>");
	            sb.append("</li>");	
	            
	            sb.append("<li>");
        			sb.append("<a class=\"paging-dot\">...</a>");
        		sb.append("</li>");
            	
/*                sb.append("<a href=\"javascript:turnOverPage(1)\">1</a>\r\n");  
                sb.append("<a href=\"javascript:turnOverPage(2)\">2</a>\r\n");  
                sb.append("…");  */
            }  
            int end = this.page + 1;  
            if (end > this.pageTotal) {  
                end = this.pageTotal;  
            }  
            if(this.pageTotal < 10)
            {
            	end = this.pageTotal;
            }	
           
            for (int i = start; i <= end; i++) {  
                if (this.page == i)  {
                	sb.append("<li class=\"paginate_button  active\">");
                	sb.append("<a href=\"javascript:void(0)\">");
                		sb.append(i);
                	sb.append("</a>");
                   }
                else {  
                	sb.append("<li class=\"paginate_button\">");
                		sb.append("<a  href=\"javascript:void(0)\" onclick=\"trunToPage(").append(i).append(",").append("'" + this.refreshId + "'").append(",").append("this);\">");
                			sb.append(i);
                		sb.append("</a>");
                	sb.append("</li>");	
                }  
            }  
  
            if (end < this.pageTotal - 2) {  
            	sb.append("<li>");
            		sb.append("<a class=\"paging-dot\">...</a>");
            	sb.append("</li>");
            }  
            if (end < this.pageTotal - 1) {  
                sb.append("<li class=\"paginate_button\">");
	        		sb.append("<a href=\"javascript:void(0)\" onclick=\"trunToPage(").append( this.pageTotal - 1).append(",").append("'" + this.refreshId + "'").append(",").append("this);\">");
	        			sb.append( this.pageTotal - 1);
	        		sb.append("</a>");
        		sb.append("</li>");	
            }  
            if (end < this.pageTotal) {  
            	 sb.append("<li class=\"paginate_button\">");
	        		sb.append("<a href=\"javascript:void(0)\" onclick=\"trunToPage(").append( this.pageTotal ).append(",").append("'" + this.refreshId + "'").append(",").append("this);\">");
	        			sb.append( this.pageTotal);
	        		sb.append("</a>");
	        	sb.append("</li>");	
            }  
  
            if (this.page == this.pageTotal)  
            {
            	 sb.append("<li class=\"paginate_button previous disabled\">");
		            sb.append("<a href=\"javascript:void(0)\">");
		            sb.append("下一页");
		            sb.append("</a>");
		        sb.append("</li>");	
            }
            else {  
            	 sb.append("<li class=\"paginate_button previous\">");
	            	sb.append("<a href=\"javascript:void(0)\" onclick=\"trunToPage(").append(this.page+1).append(",").append("'" + this.refreshId + "'").append(",").append("this);\">");
	            	sb.append("下一页");
	            	sb.append("</a>");
	            sb.append("</li>");	
            }  
            sb.append("</ul>");  
            sb.append("</div>");  
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</form>");
    	}
            

   
        try {  
            this.pageContext.getOut().println(sb.toString());  
        } catch (IOException e) {  
            throw new JspException(e);  
        }  
        return 0;  
    }  
    
    
    public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}


	public String getRefreshId() {
		return refreshId;
	}

	public void setRefreshId(String refreshId) {
		this.refreshId = refreshId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
   
	
	  public String getNameSpace() {
			return nameSpace;
		}


		public void setNameSpace(String nameSpace) {
			this.nameSpace = nameSpace;
		}


		public String getAction() {
			return action;
		}


		public void setAction(String action) {
			this.action = action;
		}


		public String getMethod() {
			return method;
		}


		public void setMethod(String method) {
			this.method = method;
		}
		
		private String  buildUrl()
		{
			 HttpServletRequest request = (HttpServletRequest) this.pageContext  
	                    .getRequest();  
	            
	        String contextpath = request.getContextPath();
	        StringBuffer urlBuffer = new StringBuffer();
	        if(!"".equals(contextpath))
	        {
	        	urlBuffer.append(contextpath);
	        }
	        if(!"/".equals(this.nameSpace))
	        {
	        	urlBuffer.append("/").append(this.nameSpace);
	        }
	        urlBuffer.append("/").append(this.action);
	        if(this.method != null && !"".equals(this.method))
	        {
	        	urlBuffer.append("!").append(this.method);
	        }
	        return urlBuffer.toString();
		}
}  