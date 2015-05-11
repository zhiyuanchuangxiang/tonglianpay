<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ include file="../public/header.inc.jsp"%>

<body class="noskin">
			
		<div class="main-container" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
						
							<div class="page-content">
								
								
									<div class="row">
											
										<div class="col-sm-12">
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
												<a class="btn btn-sm" href="<s:property value="_ASSIGN_.REDIRECT_URL" />" target="_self">返回</a>
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
				setTimeout( function(){
				     window.location.href = "<s:property value="_ASSIGN_.REDIRECT_URL" />";
				},3000);
			});
		</script>
</body>