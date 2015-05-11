<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ include file="../public/header.inc.jsp"%>

<body class="noskin">
			
		<div class="main-container" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
						
							<div class="page-content">
							
								<s:if test="_ASSIGN_.staff == null || _ASSIGN_.staff.ST_STAFF_ID==null">
									<form action="<s:url action="Staff" method="insert"></s:url>"  id="queryForm" class="form-horizontal">
								
								</s:if> 
								<s:else>
									<form action="<s:url action="Staff" method="update"></s:url>"  id="queryForm" class="form-horizontal">
								</s:else>
								
									<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 工号 </label>
	
											<div class="col-sm-9">
												<input type="text" name="ST_STAFF_ID"  value="${_ASSIGN_.staff.ST_STAFF_ID}"  class="col-xs-10 col-sm-5" />
											</div>
									</div>
									
									<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right" for="form-field-1">姓名 </label>
	
											<div class="col-sm-9">
												<input type="text" name="ST_STAFF_NAME"  value="${_ASSIGN_.staff.ST_STAFF_NAME}"   class="col-xs-10 col-sm-5" />
											</div>
									</div>
									
									<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right" for="form-field-1">所属部门 </label>
	
											<div class="col-sm-9">
												<input type="text" name="ST_DEPART_ID"  value="${_ASSIGN_.staff.ST_DEPART_ID}"  desc="员工工号" class="col-xs-10 col-sm-5" />
											</div>
									</div>
									
									<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right" for="form-field-1">员工角色</label>
	
											<div class="col-sm-9">
												<input type="text" name="ST_DEPART_ID"  value="${_ASSIGN_.staff.ST_DEPART_ID}"  desc="员工工号" class="col-xs-10 col-sm-5" />
											</div>
									</div>
										
									<div class="clearfix form-actions">
												<div class="col-md-offset-3 col-md-9">
												
														<button type="submit" 
													     
														  	class="btn btn-info">
														  	<i class="ace-icon fa fa-undo bigger-110"></i>
														  	提交 
														 </button>
												
																								
											</div>
									</div>	
									
								</form>
								
							
								
							
							</div>
						
				</div>
			</div>
		</div>
		
		<%@ include file="../public/resources_js.inc.jsp" %>
		
</body>