/**
 * 传统终端、品牌、产品、物料四级下拉列表级联
 * @@author qinzhichao
 */

$(function(){
	var terminal = "#terminalId";
	var brand = "#brandId";
	var product = "#productId";
	var bom = "#bomId";
	
	var brandId = $(brand);
	var productId = $(product);
	var bomId = $(bom);
	
	brandId.append('<option value="-1">全部</option>');
    productId.append('<option value="-1">全部</option>');
    bomId.append('<option value="-1">全部</option>');
	
	$(terminal).click(function(){
		var terminalId = $(terminal).val();
		$.ajax({
            type:'post',
            dataType: "json", 
            data:{"selectId":terminalId,"flag":2},
            url:'/traditionSelect/getTraditionProductSelect.c',
            success:function(data){
                var brandSelect = $(brand);
                	if(brandSelect!=undefined){
                		brandSelect.empty();
                		brandId.empty();
                		productId.empty();
                		bomId.empty();
                		brandId.append('<option value="-1">全部</option>');
                		productId.append('<option value="-1">全部</option>');
                		bomId.append('<option value="-1">全部</option>');
                		if(undefined != data && null!=data){
	                		for(var i=0;i<data.length;i++){
	                			if(undefined !=data[i] && null != data[i]){
	                				var tempDate = '"'+data[i]+'"';
	                				var msgDate  = tempDate.split(",");
	                				brandSelect.append("<option value="+msgDate[0]+'"'+">"+msgDate[1].replace('"',"")+"</option>");
	                			}
	                		}
                		}
                	}
           	}
          });
	});
	
	$(brand).click(function(){
		var brandId = $(brand).val();
		$.ajax({
            type:'post',
            dataType: "json", 
            data:{"selectId":brandId,"flag":3},
            url:'/traditionSelect/getTraditionProductSelect.c',
            success:function(data){
                var productSelect = $(product);
                	if(productSelect!=undefined){
                		productId.empty();
                		bomId.empty();
                		productId.append('<option value="-1">全部</option>');
                		bomId.append('<option value="-1">全部</option>');
	                	if(undefined != data && null!=data){
	                		for(var i=0;i<data.length;i++){
	                			if(undefined !=data[i] && null != data[i]){
	                				var tempDate = '"'+data[i]+'"';
	                				var msgDate  = tempDate.split(",");
	                				productSelect.append("<option value="+msgDate[0]+'"'+">"+msgDate[1].replace('"',"")+"</option>");
	                			}
	                		}
	                	}
                	}
           	}
          });
	});
	
	$(product).click(function(){
		var brandId = $(product).val();
		$.ajax({
            type:'post',
            dataType: "json", 
            data:{"selectId":brandId,"flag":4},
            url:'/traditionSelect/getTraditionProductSelect.c',
            success:function(data){
                var bomSelect = $(bom);
                	if(bomSelect!=undefined){
                		bomId.empty();
                		bomId.append('<option value="-1">全部</option>');
                		if(undefined != data && null!=data){
	                		for(var i=0;i<data.length;i++){
	                			if(undefined !=data[i] && null != data[i]){
	                				var tempDate = '"'+data[i]+'"';
	                				var msgDate  = tempDate.split(",");
	                				bomSelect.append("<option value="+msgDate[0]+'"'+">"+msgDate[1].replace('"',"")+"</option>");
	                			}
	                		}
                		}
                	}
           	}
          });
	});
	
	
})