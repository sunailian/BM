/**
 * @author 16530202@qq.com
 */
 
/**
 * 普通ajax表单提交
 * @param {Object} form
 * @param {Object} callback
 */
function navTabSearch(form, navTabId){
	var $form = $(form);
	if (!$form.valid()) {
		return false;
	}
	
	if (form[DWZ.pageInfo.pageNum]) form[DWZ.pageInfo.pageNum].value = 1;
	navTab.reload($form.attr('action'), {data: $form.serializeArray(), navTabId:navTabId});
	return false;
}

DWZ.ajaxError = function(xhr, ajaxOptions, thrownError){
    var error = eval('('+xhr.responseText+')');
	if (alertMsg) {
		alertMsg.error("【代号"+error.errorCode+"】"+error.message+"！");
	} else {
		alert("【代号"+error.errorCode+"】"+error.message+"！");
	}
};

//局部分页查询

/**
 * 处理navTab中的分页和排序-带查询参数
 * targetType: navTab 或 dialog
 * rel: 可选 用于局部刷新div id号
 * data: pagerForm参数 {pageNum:"n", numPerPage:"n", orderField:"xxx", orderDirection:""}
 * callback: 加载完成回调函数
 */
function dwzPageBreakSearch(options){//局部刷新或重新载入
	var op = $.extend({ targetType:"navTab", rel:"", data:{pageNum:"", numPerPage:"", orderField:"", orderDirection:""}, callback:null}, options);
	var $parent = navTab.getCurrentPanel();
	if (op.rel) {//对rel容器进行局部刷新
		var $box = $parent.find("#" + op.rel);
		var form = $("#search_form", $parent).get(0);
		var $form = $(form);
		if(op.data["pageNum"])form[DWZ.pageInfo.pageNum].value=op.data["pageNum"];
		if(op.data["numPerPage"])
		{
			form[DWZ.pageInfo.numPerPage].value=op.data["numPerPage"];
			form[DWZ.pageInfo.pageNum].value = 1;
		}
		$box.ajaxUrl({
			type:"POST", url:$form.attr("action"), data: $form.serializeArray(), callback:function(){
				$box.find("[layoutH]").layoutH();
				var searchDiv =$box.parent().find('div.pageHeader');
				var tableHeight = $box.find('div').first(); 
				if(searchDiv.length>0 && searchDiv.css('display')=='none'){
					tableHeight.height(tableHeight.height()+searchDiv.outerHeight());
				}
			}
		});
	} 
}
/**
 * 处理navTab中的分页和排序
 * @param args {pageNum:"n", numPerPage:"n", orderField:"xxx", orderDirection:""}
 * @param rel： 可选 用于局部刷新div id号
 */
function navTabPageBreakSearch(args, rel){
	dwzPageBreakSearch({targetType:"navTab", rel:rel, data:args});
}

/**
 * 扩展DWZ框架中预定义的表单提交回调函数
 * 增加回调类型forwardConfirmOrClose
 * @param json
 */
function navTabAjaxDone(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		if (json.navTabId){ //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
			navTab.reloadFlag(json.navTabId);
		} else { //重新载入当前navTab页面
			var $pagerForm = $("#pagerForm", navTab.getCurrentPanel());
			var args = $pagerForm.size()>0 ? $pagerForm.serializeArray() : {}
			navTabPageBreak(args, json.rel);
		}
		
		if ("closeCurrent" == json.callbackType) {
			setTimeout(function(){navTab.closeCurrentTab(json.navTabId);}, 100);
		} else if ("forward" == json.callbackType) {
			navTab.reload(json.forwardUrl);
		} else if ("forwardConfirm" == json.callbackType || "forwardConfirmOrClose" == json.callbackType) {
			var msg = json.confirmMsg || DWZ.msg("forwardConfirmMsg");
			var options = {
				okCall: function(){
					if(json.forwardUrl.StartWith("/"))
						navTab.reload(main+json.forwardUrl);
					else
						navTab.reload(main+"/"+json.forwardUrl);
				}
			};
			
			if ("forwardConfirmOrClose" == json.callbackType) {
				options.cancelCall = function(){
					setTimeout(function(){navTab.closeCurrentTab(json.navTabId);}, 100);
				};
			}
			
			alertMsg.confirm(msg, options);
		} else {
			navTab.getCurrentPanel().find(":input[initValue]").each(function(){
				var initVal = $(this).attr("initValue");
				$(this).val(initVal);
			});
		}
	}
}