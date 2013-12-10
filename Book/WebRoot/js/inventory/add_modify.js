/**
 * 全盘盘点、异动盘点切换
 * @param id
 */
function show_type(id){
	var obj = $('#'+id);
	var display=obj.css('display');
	if(display==='none'){
		obj.show();
	}else if(display==='block'){
		obj.hide();
	}else{
		obj.show();
	}
}
/**
 * 盘点-立即发布状态事件
 * @author linbo
 */
$(function() {
	var status = $("#plan_status");
	if (status != undefined) {
		status.click(function() {
			var status = $(this).val();
			if (status === '0') {
				$(this).attr('checked', 'checked');
				$(this).val('1');
			} else {
				$(this).removeAttr('checked');
				$(this).val('0');
			}
		});
	}
});

$(function(){
	var brandSelect=$("#"+brand_list);
	if(brandSelect!=undefined){
		brandSelect.bind('change',function(){
			$.ajax({ 
                type: "post", 
                url: "/inven_plan/toGetModel.c", 
                dataType: "json", 
                data:{"brandIds":brandSelect.attr('value')},
                success: function (data) {
                	var modelSelect = $("#"+model_list);
                	if(modelSelect!=undefined){
                		modelSelect.empty();
                		modelSelect.append('<option value="0">全部</option>');
                		//循环机型列表
                		var objData = jQuery.parseJSON(data);
                		for(var i=0;i<data.length;i++){
                			modelSelect.append("<option value="+data[i].productId+">"+data[i].productName+"</option>");
                		}
                	}
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                } 
			});
		});
	}
});