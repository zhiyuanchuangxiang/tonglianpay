<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ include file="../public/header.inc.jsp"%>
<body class="login-layout light-login">

	<div class="main-container">
						<div class="main-content">
						<!-- login container start -->
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="ace-icon fa fa-leaf green"></i>
									<span class="red">Cloud</span>
									<span class="dark" id="id-text2">综合管理平台</span>
								</h1>
								<h4 class="blue" id="id-company-text">&copy; 致远创想</h4>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
												请输入用户名与密码
											</h4>

											<div class="space-6"></div>
												
											<form action="<s:url namespace="main" action="Login" method="logon"></s:url>" >
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="用户名" id="staffId" name="staffId"  datatype="text"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control"  placeholder="密码" id="password" name="password" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>
												
													
													
													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" class="ace" id="LOGIN_FLAG" name="LOGIN_FLAG" value="1" checked />
															<span class="lbl"> 记住我</span>
														</label>

														<button type="submit"  class="width-35 pull-right btn btn-sm btn-primary" >
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">登陆</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>

											

											
										</div><!-- /.widget-main -->

									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->


								
							</div><!-- /.position-relative -->
						</div><!-- login container end -->
				</div>
		</div>
	
</body>