
$(function(){
	$(".submenu").on(ace.click_event,".leaf",function(e){
		$(".submenu > li").removeClass("active");
		$(this).parent().addClass("active");
		var action_name=$(this).attr("action_name");
		var method_name = $(this).attr("method_name");
		var param = $(this).attr("page_param");
		
		var menu_name = $(this).attr("menu_name");
		var parent_menu_name = $(this).attr("parent_menu_name");
		var name_space = $(this).attr("name_space");
		
		if(action_name && method_name 
			&& action_name !="undefined" 
			&& method_name && method_name!="undefined" 
			&& name_space && name_space !="undefined")
		{
			var uri = getPageUrl(name_space,action_name,method_name,param);			
			$(this).attr("href",uri);
		}
		else
		{
			return false;
		}
		
		var target = "contentframe";
		$(this).attr("target",target);
		
		var breadcrumb_content = "<li><i class='ace-icon fa fa-home home-icon'></i><a href='#'>"+parent_menu_name+"</a></li>";
			breadcrumb_content  += "<li class='active'><a href='#'>"+menu_name+"</a></li>";
			
		if($("#breadcrumb"))
		{
			$("#breadcrumb").html("");
			$("#breadcrumb").html(breadcrumb_content);
		}
		
	});
	
});


function getPageUrl(name_space,action_name,method_name,param) {
	var contextUrl =""; //buildContextName();
    var url = CONTEXT_URL + "/"+name_space +"/" +action_name +"!"+ method_name;
    if (param && param != null && param != "") {
        url += "&" + parseUrlParams(param);
    }

    return url;
}


function parseUrlParams(string){
	if(!string||!string.length){
		return"";
	}
	string=string.replace(/&amp;/g,"&");
	string=string.replace(/\n/g,"\\n");
	return string.replace(/&([^=&]+)=([^=&]+)/g,function(match){
			return match.replace(/[^=&\u4E00-\u9FA5\u3000-\u303F\uFF00-\uFFEF\s]+/g,function(match){return encodeURIComponent(decodeURIComponent(match));
			});
		}
	);
}



