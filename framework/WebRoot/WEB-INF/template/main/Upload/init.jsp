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
											<form  class="form-horizontal" method="post" action="<s:url action="Upload" method="importExcel"></s:url>" enctype="multipart/form-data">
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 上传文件测试</label>
													<div class="col-sm-9">
														<div class="col-sm-5">
															<input type="file" name="fileup" id="id-input-file-1" />
														</div>
														
													</div>
												</div>
												
												
												<div class="clearfix form-actions">
													<div class="col-md-offset-3 col-md-9">
														
														<button  type="submit" 
														 		value="上传"
															 	class="btn">
														 	<i class="ace-icon fa fa-check bigger-110"></i>上传
														</button>
													</span>
												</div>
											</div>
											</form>
									</div>
								</div>
							
							</div>
						
				</div>
			</div>
		</div>
		
		<%@ include file="../public/resources_js.inc.jsp" %>
		<script type="text/javascript">
			$(function(){
				$('#id-input-file-1').ace_file_input({
					no_file:'未选择 ...',
					btn_choose:'选择',
					btn_change:'选择',
					droppable:false,
					onchange:null,
					thumbnail:false //| true | large
					//whitelist:'gif|png|jpg|jpeg'
					//blacklist:'exe|php'
					//onchange:''
					//
				});
			});
		
		</script>
</body>