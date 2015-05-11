<%@ page language="java" contentType="text/html; charset=utf-8"%>

<div id="sidebar"	class="sidebar  responsive sidebar-fixed sidebar-scroll"
	data-sidebar="true" 
	data-sidebar-scroll="true"
	data-sidebar-hover="true">
	<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
	</script>
	 <ul class="nav nav-list">
		<s:iterator value="_ASSIGN_.MENU_LIST" status="statu" id="item">  
		<li class="">
			<a href="#" class="dropdown-toggle"> 
				<i  class="<s:property value="#item.MENU_ICON"/>"></i> 
				<span class="menu-text"><s:property value="#item.MENU_NAME" /></span> 
			</a> 
			<b class="arrow fa fa-angle-down"></b>
				<ul class="submenu">
					<s:iterator value="CHILD_MENU" status="childstatus" id="childitem">  
					<li>
							<a  class="leaf"
								href="#"
								method_name="${childitem.METHOD_NAME}"
								page_param = "${childitem.PAGE_PARAM}"
								menu_name = "${childitem.MENU_NAME}"
								action_name="${childitem.ACTION_NAME}"
								parent_menu_name = "${item.MENU_NAME}"
								name_space = "${childitem.NAME_SPACE}" 
								data-link="true"
								> 
								<i class="menu-icon fa fa-caret-right"></i>
								<span class="menu-text">${childitem.MENU_NAME}</span> 
							</a> 
							<b class="arrow"></b>
					</li>
					</s:iterator>
				</ul>
		</li>
		</s:iterator>

	</ul>

	<!-- #section:basics/sidebar.layout.minimize -->
	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i class="ace-icon fa fa-angle-double-left"
			data-icon1="ace-icon fa fa-angle-double-left"
			data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>

	<!-- /section:basics/sidebar.layout.minimize -->
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
</div>