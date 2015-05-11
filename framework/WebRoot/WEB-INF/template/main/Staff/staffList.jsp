<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/zycxtag" prefix="zc" %>

<table id="simple-table" class="table table-striped table-bordered table-hover">
			<thead>
				<th>员工工号</th>
				<th>员工姓名</th>
				<th>部门编码</th>
			</thead>
			<tbody>
				<s:iterator value="_ASSIGN_.STAFF_LIST" status="statu" id="item">  
					<tr>
						<td><s:property value="#item.ST_STAFF_ID" /></td>
						<td><s:property value="#item.ST_STAFF_NAME" /></td>
						<td><s:property value="#item.ST_DEPART_ID" /></td>
					</tr>
				</s:iterator>
			</tbody>
</table>

<zc:pager action="Staff" method="staffList" nameSpace="main"
	pageTotal="${pager.pageTotal}" count="${pager.count}"
	page="${pager.page}" rowNum="${pager.rowNum}" refreshId="stafflist" />
<%-- <div class="row">
	<div class="col-xs-6">
		<div class="dataTables_info">
			<s:property value="pager.pageTotal"/>
		</div>
	</div>
	<div class="col-xs-6">
		<div id="dynamic-table_paginate" class="dataTables_paginate paging_simple_numbers">
			<ul class="pagination">
				<li id="dynamic-table_previous"  aria-controls="dynamic-table" class="paginate_button previous disabled">
					<a href="#">上一页</a>
				</li>
				 <s:iterator value="new int[pager.pageTotal]" status="i">
				 	<li tabindex="0" aria-controls="dynamic-table" class="paginate_button">	
				     	<a  page="<s:property value="#i.index+1"/>" onclick="trunToPage(<s:property value="#i.index+1"/>,'stafflist',this);"><s:property value="#i.index+1"/></a>
					     </li>
					 </s:iterator>
					
					<li id="dynamic-table_next" tabindex="0" aria-controls="dynamic-table" class="paginate_button next">
						<a href="#">下一页</a>
					</li>
				</ul>
			</div>
		</div>
</div> --%>

