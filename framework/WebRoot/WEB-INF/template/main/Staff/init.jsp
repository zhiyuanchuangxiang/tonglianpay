<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ include file="../public/header.inc.jsp"%>

<body class="noskin">
			
		<div class="main-container" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
						
							<div class="page-content">
								
								<form action="<s:url action="Staff" method="staffList"></s:url>"  id="queryForm">
									<div class="row">
											
										<div class="col-sm-12">
											
													<input type="text"  name="cond_staff_name"  placeholder="请输入员工姓名" />
											
													<button class="btn btn-white btn-info btn-bold" type="submit" value="查询">
														<i class="ace-icon fa fa-search"></i>
														查询													
													</button>
													
													<a class="btn btn-white btn-info btn-bold"  href="<s:url action="Staff" method="create"></s:url>"  target="_self">
														<i class="ace-icon fa fa-search"></i>
														新建员工
													</a>
													<a class="btn btn-white btn-info btn-bold"  href="<s:url action="Staff" method="exportStaffList"></s:url>"  onclick="exportExcel('queryForm',this)">
														<i class="ace-icon fa fa-search"></i>
														导出
													</a>
													
													<a class="btn btn-white btn-info btn-bold"    target="_self" onclick="popDialog('<s:url action="Staff" method="createForPup"></s:url>','弹出窗口 ',true,400,350);">
														<i class="ace-icon fa fa-search"></i>
														弹出窗口新增
													</a>
											
										</div>
									
									</div>
										
										
									
								</form>
								
							
								<hr></hr>
								<div class="row">
									<div class="col-xs-12">
										<!-- PAGE CONTENT BEGINS -->
										<div class="row">
											<div class="col-xs-12" id="stafflist">
												<%@ include file="staffList.jsp"%>
											</div>
										</div>
										
									</div>
								</div>
								
							
							</div>
						
				</div>
			</div>
		</div>
		
		<%@ include file="../public/resources_js.inc.jsp" %>

		<script type="text/javascript">
			$(function(){
				 var options = {
				 	 target:  '#stafflist',
				 	 beforeSubmit:startLoading,
				 	 success:endLoading,
				 	 error:function(){
				 	 	alert('加载出错');
				 	 }
				}
				$('#queryForm').submit(function() {   
			        $(this).ajaxSubmit(options);   
			   
			        // !!! Important !!!   
			        // always return false to prevent standard browser submit and page navigation   
			       return false;   
			    });   
			
			
			});
			
		
		</script>
</body>