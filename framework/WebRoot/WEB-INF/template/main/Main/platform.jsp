<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ include file="../public/header.inc.jsp"%>
<body class="no-skin">
	
	<%@ include file="../public/page_top.jsp" %>
	<div class="main-container" id="main-container" style="height:100%;">
		<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
		</script>
		<%@ include file="../public/sidebar.jsp" %>
		<!-- .main-content start -->
				<div class="main-content" style="height:100%;position:relative;" >
							<!-- #section:basics/content.breadcrumbs -->
						<div class="breadcrumbs" id="breadcrumbs">
							<script type="text/javascript">
								try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
							</script>
		
							<ul class="breadcrumb" id="breadcrumb">
								<li>
									<i class="ace-icon fa fa-home home-icon"></i>
									<a href="#">首页</a>
								</li>
		
							</ul><!-- /.breadcrumb -->
						</div>
						<div class="frame_container">
								<iframe width="100%" height="100%" frameborder=0  name="contentframe" id="contentframe"></iframe>
						</div> 
						<!-- /.page-content-area -->
						
				
				
				</div>
			
		</div>
		
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
			<%@ include file="../public/resources_js.inc.jsp" %>
			<script src="${_GLOBAL_PARAMETERS_.WEB_URL}/resources/system/js/sidebar.js" ></script>

</body>
</html>