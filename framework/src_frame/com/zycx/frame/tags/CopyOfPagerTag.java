package com.zycx.frame.tags;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
  
public class CopyOfPagerTag extends TagSupport {  
    private static final long serialVersionUID = 5729832874890369508L;  
	private int rowNum=10;//每页显示数目
	

	private int count = 0 ;//总记录数 
	private int page=1;//当前页数 
	private int pageTotal;//总页数
	private String url;// url
	private String refreshId;
  
    public int doStartTag() throws JspException {  
  
        StringBuilder sb = new StringBuilder();  
  
  
        /*sb.append("<div class=\"pagination\">\r\n");  
        if (this.count == 0) {  
            sb.append("<strong>没有可显示的项目</strong>\r\n");  
        } else {  
            if (this.page > this.pageTotal)  
                this.page = this.pageTotal;  
            if (this.page < 1)  
                this.page = 1;  
  
            sb.append("<form method=\"post\" action=\"").append(this.url)  
                    .append("\" >\r\n");  
  
            HttpServletRequest request = (HttpServletRequest) this.pageContext  
                    .getRequest();  
            Enumeration enumeration = request.getParameterNames();  
            String name = null;  
            String value = null;  
  
            while (enumeration.hasMoreElements()) {  
                name = (String) enumeration.nextElement();  
                value = request.getParameter(name);  
 
                if(!name.equals("page") && name.equals("rowNum") ){
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
	            	sb.append("<a href=\"javascript:trunToPage(").append(this.page-1).append(",").append(this.refreshId).append(",").append("this);\">");
	            	sb.append("上一页");
	            	sb.append("</a>");
	            sb.append("</li>");	
            }  
  
            int start = 1;  
            if (this.page > 4) {  
            	
                start = this.page - 1;  
                
                sb.append("<li class=\"paginate_button previous\">");
	            	sb.append("<a href=\"javascript:trunToPage(").append("1").append(",").append(this.refreshId).append(",").append("this);\">");
	            		sb.append("1");
	            	sb.append("</a>");
            	sb.append("</li>");	
            	
            	sb.append("<li class=\"paginate_button previous\">");
	            	sb.append("<a href=\"javascript:trunToPage(").append("2").append(",").append(this.refreshId).append(",").append("this);\">");
	            		sb.append("2");
	            	sb.append("</a>");
	            sb.append("</li>");	
            	
                sb.append("<a href=\"javascript:turnOverPage(1)\">1</a>\r\n");  
                sb.append("<a href=\"javascript:turnOverPage(2)\">2</a>\r\n");  
                sb.append("…");  
            }  
  
            int end = this.page + 1;  
            if (end > this.pageTotal) {  
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
                		sb.append("<a href=\"javascript:trunToPage(").append(i).append(",").append(this.refreshId).append(",").append("this);\">");
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
	        		sb.append("<a href=\"javascript:trunToPage(").append( this.pageTotal - 1).append(",").append(this.refreshId).append(",").append("this);\">");
	        			sb.append( this.pageTotal - 1);
	        		sb.append("</a>");
        		sb.append("</li>");	
            }  
            if (end < this.pageTotal) {  
            	 sb.append("<li class=\"paginate_button\">");
	        		sb.append("<a href=\"javascript:trunToPage(").append( this.pageTotal ).append(",").append(this.refreshId).append(",").append("this);\">");
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
	            	sb.append("<a href=\"javascript:trunToPage(").append(this.page+1).append(",").append(this.refreshId).append(",").append("this);\">");
	            	sb.append("下一页");
	            	sb.append("</a>");
	            sb.append("</li>");	
            }  
            sb.append("</ul>");  
            sb.append("</div>");  
            sb.append("</div>");
            sb.append("</form>");
    	}
            

   
        try {  
            this.pageContext.getOut().println(sb.toString());  
        } catch (IOException e) {  
            throw new JspException(e);  
        }  */
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
   
}  