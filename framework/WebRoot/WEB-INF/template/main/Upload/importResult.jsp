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
										<!-- PAGE CONTENT BEGINS -->
										<div class="row">
											<div class="col-xs-12" id="stafflist">
												<table id="simple-table" class="table table-striped table-bordered table-hover">
													<thead>
														<th>员工工号</th>
														<th>员工姓名</th>
														<th>部门编码</th>
													</thead>
													<tbody>
														<s:iterator value="_ASSIGN_.exportResultList" status="statu" id="item">  
															<tr>
																<td><s:property value="#item.USECUST_ID" /></td>
																<td><s:property value="#item.USECUST_NAME" /></td>
																<td><s:property value="#item.SERIAL_NUMBER" /></td>
															</tr>
														</s:iterator>
													</tbody>
										</table>
											</div>
										</div>
										
									</div>
								</div>
								
							
							</div>
						
				</div>
			</div>
		</div>
		
		<%@ include file="../public/resources_js.inc.jsp" %>
	
</body>