package com.zycx.frame.struts;
/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-22 下午9:46:30 
 * 类说明 
 */
public class Pagination 
{
	private int rowNum=10;//每页显示数目
	private int count = 0 ;//总记录数 
	private int page=1;//当前页数 
	private int pageTotal;//总页数
	
	public int getPageTotal() {
		
		return this.pageTotal;
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
		if(this.count%rowNum>0)
		{
			this.pageTotal =  count/rowNum  + 1;
		}
		else
		{
			this.pageTotal =  count/rowNum ; 
		}
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	


}
