<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ include file="../public/header.inc.jsp"%>

<body class="noskin">
			
		<div class="main-container" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
						
							<div class="page-content">
								
								
									<div class="row">
											
										<div class="col-xs-12">
											<div class="alert alert-block alert-success">
											<p>
												<strong>
													<s:if test='_ASSIGN_.MSG_TYPE == "0"'>
														<i class="ace-icon fa fa-check"></i>
													</s:if>
													<s:elseif test='_ASSIGN_.MSG_TYPE == "1"'>
														<i class="ace-icon fa fa-check"></i>
													</s:elseif>
													<s:elseif test='_ASSIGN_.MSG_TYPE == "2"'>
														<i class="ace-icon fa fa-check"></i>
														
													</s:elseif>
													
												</strong>
												${_ASSIGN_.MSG_TIP}
											</p>
											<p class="align-center">
												<a class="btn btn-sm"  id="close_btn" target="_self">关闭 </a>
											</p>
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
				if(window.parent.dialog)
				{
					var dialog = window.parent.dialog.get(window);
					dialog.reset();
				}
				
				$("#close_btn").on("click",function(){
					if(window.parent.dialog)
					{
						var dialog = window.parent.dialog.get(window);
						dialog.title('打放大发放大发 ');
						dialog.close();
						dialog.remove();
					}
				})
			});
		</script>
</body>