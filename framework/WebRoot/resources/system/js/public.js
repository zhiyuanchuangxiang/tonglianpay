
function startLoading()
{
	var loading = $("#masker");
	if(loading){
		loading.remove();
	}
	var loadingDiv = $('<div id="masker" class="ajax-loading-overlay ajax-overlay-body"><div class="loading"><i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i></div></div>');
	loadingDiv.append();
	$('body').append(loadingDiv);
}

function endLoading()
{
	var loading = $("#masker");
	if(loading){
		loading.remove();
	}
}

function trunToPage(pageNumber,refreshId,eventSource)
{
	var jqueryThis = $(eventSource);
	var form = jqueryThis.closest("form");
	if(form)
	{
		var options = {
			 	 target:  '#' + refreshId,
			 	 beforeSubmit:startLoading,
			 	 success:endLoading,
			 	 data:{'page':pageNumber},
			 	 error:function(){
			 	 	alert('加载出错');
			 	 	endLoading();
			 	 }
			};
		form.ajaxSubmit(options);
	}
}

function exportExcel(formId,domElement)
{
	var hrefurl = $(domElement).attr("href");
	var pairs=hrefurl.split("?");
	var url="";
	if(pairs.length<=1)
	{
		url+="?"
	}
	
	
	$("#" +formId).find("input[name^='cond_']").each(function(index,item){
		url +="&" + $(item).attr("name") +"=" +$(item).val();
	});
	$(domElement).attr("href",hrefurl + url);
	/* $('#'+formId).("input[name^='cond_']").each(function(index){
		
	}); */
}


function popDialog(openUrl,dialogTitle,isModal,dialogWidth,dialogHeight)
{
	var option = {
			title: dialogTitle,
			url:openUrl
		}
	if(dialogWidth)
	{
		option.width=dialogWidth ;
	}
	if(dialogHeight)
	{
		option.height = dialogHeight;
	}	
	var dialog_Obj = dialog(option);
    if(isModal)
    {
    	dialog_Obj.showModal();
    }
    else
    {
    	dialog_Obj.show();
    }
	
}