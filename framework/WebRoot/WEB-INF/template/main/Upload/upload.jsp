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
											<div>文件名<s:property value="_ASSIGN_.uploadResult.UPLOAD_FILE_NAME" /></div>	
											<div>原始文件名<s:property value="_ASSIGN_.uploadResult.FILE_NAME" /></div>
											<div>文件类型<s:property value="_ASSIGN_.uploadResult.FILE_TYPE" /></div>
											<div>完整路径<s:property value="_ASSIGN_.uploadResult.FILE_PATH" /></div>
											<div>相对路径<s:property value="_ASSIGN_.uploadResult.FILE_RELATIVE_PATH" /></div>
											<div>文件大小<s:property value="_ASSIGN_.uploadResult.FILE_SIZE" /></div>
									</div>
								</div>
							
							</div>
						
				</div>
			</div>
		</div>
		
		<%@ include file="../public/resources_js.inc.jsp" %>
		<script type="text/javascript">
			
		
		</script>
</body>