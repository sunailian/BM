/**
 * @author jianglong
 */
 
/* 检查IMEI是否合法 
 * IMEI
 */
function checkIMEI(IMEI) {
	if (!(IMEI.length == 15 || IMEI.length == 16))
		return false;
	var pattern = /^[0-9]{15,16}$/;
	return pattern.test(IMEI);
}

// 将列表框所有IMEI整理成字符串(多个IMEI用逗号间隔)
function IMEIList2Str() {
	var OKIMEIList =$("#"+OKIMEI)[0];;
	var IMEIs = "";
	if (OKIMEIList) {
		var len = OKIMEIList.length;
		if (len > 0) { 
			for ( var i = len - 1; i >= 0; i--) {
				if (IMEIs.length > 0)
					IMEIs += ",";
				IMEIs += OKIMEIList.options[i].value;
			}
		}
	}
	return IMEIs;
}

function joinIMEIs(arrIMEI) {
	var imeilist = "";
	if (arrIMEI == null || arrIMEI.length == 0) {
		return imeilist;
	}
	var okIMEIstr=IMEIList2Str();
	var len = arrIMEI.length;
	var imei = "";
	var count = 0;
	var isvalid = false;
	for ( var i = 0; i < len; i++) {
		imei = arrIMEI[i].trim();
		isvalid = checkIMEI(imei);
		if (isvalid && imeilist.indexOf(imei + ',') == -1 && imei.indexOf(okIMEIstr + ',') == -1) {
			imeilist += imei + ',';
			count++;
		}
	}
	if (count > 0)
		imeilist = imeilist.substr(0, imeilist.length - 1);
	return count + "`" + imeilist;
}

/**
 * 根据joinIMEIs方法返回的字符串获取IMEI数量
 * @param imeilist
 * @return
 */
function getCountInIMEIList(imeilist) {
	var count = 0;
	var index = imeilist.indexOf('`');
	if (index > 0) {
		count = parseInt(imeilist.substr(0, index), 10);
	}
	return count;
}

/**
 * 根据joinIMEIs方法返回的字符串获取IMEI字符串
 * @param imeilist
 * @return
 */
function getIMEIsInIMEIList(imeilist) {
	var IMEIs = 0;
	var index = imeilist.indexOf('`');
	if (index > 0) {
		IMEIs = imeilist.substring(index + 1);
	}
	return IMEIs;
}

//验证手工输入的IMEI
function checkIMEIs(oIMEIInput, countlimited) {
	if (oIMEIInput == null || oIMEIOutput == null) {
		return false;
	}
	var sr = oIMEIInput.value;
	var sr_length = sr.length;
	var ok = false;
	if (sr_length >= 15) {
		var arrIMEI = sr.split('\n');
		var imeilist = joinIMEIs(arrIMEI);
		var count = getCountInIMEIList(imeilist);
		var IMEIs = getIMEIsInIMEIList(imeilist);
		if (countlimited > 0 && count > countlimited) {
			alertMsg.info('对不起！您输入了' + count + '个有效IMEI，但最多只能输入' + countlimited + '个IMEI！');
			oIMEIInput.focus();
			return false;
		} else {
			if (IMEIs.length >= 15) {
				
				ok = true;
			}
		}
	}
	if (!ok) {
		alertMsg.info("请输入有效的IMEI！");
		oIMEIInput.focus();
	}
	return ok;
}

   $(document).ready(function(){
     initImei();
     
     $("#submit1").click(function(){
       var  IMEIStr=IMEIList2Str();
       var okImeiCount=$("#"+OKIMEICOUNT).html();
        var indexStr=$("#indexStr").val();
        var   imeiName=indexStr+".imei";  
         var   amountName=indexStr+".amount";  
        var parentDocument=window.top.document;  
        $("input[name='"+imeiName+"']",parentDocument).val(IMEIStr);
		$("input[name='"+amountName+"']",parentDocument).val(okImeiCount);
      $.pdialog.closeCurrent();
     });
     
     
     
     $("#verifyButton").click(function(){
   
            var noimeiList=$("#"+NOIMEI)[0];
            
          if (noimeiList == null){
		    return;
		    }
		    
	 var sr = noimeiList.value;
	var sr_length = sr.length;
	if (sr_length >= 15) {

		var arrIMEI = sr.split('\n');

		var imeilist = joinIMEIs(arrIMEI);

		var count = getCountInIMEIList(imeilist);
		var IMEIs = getIMEIsInIMEIList(imeilist);

		if (countlimited > 0 && count > countlimited) {
			alertMsg.info('对不起！您输入了' + count + '个有效IMEI，但最多只能输入' + countlimited + '个IMEI！');
			oIMEIInput.focus();
			return false;
		} else {
			if (IMEIs.length >= 15) {
				 //调用Ajax验证
				  
		var bomId=$("#bomId").val();
     var boxId=$("#boxId").val();
     var deviceId=$("#deviceId").val();
     var url="/outstock/distuibution/verifyIMEI.c?taskDetail.imei="+IMEIs+"&taskDetail.bomId="+bomId+"&taskDetail.boxId="+boxId+"&taskDetail.deviceId="+deviceId;
     if(IMEIs&& IMEIs!='' ){
        $.ajax({
			async: false,
			url: url, 
			type : "post", 
            dataType : "json",
			success: function(data){
			  
			 var count = data.length;
			 if (count == 0) {
				alertMsg.info("您输入的IMEI无效！");
				return;
			}
		   var  okImeiCountObj=$("#"+OKIMEICOUNT);
			var okImeiCount=parseInt(okImeiCountObj.html(), 10);
			var noPickAmount=$("#noPickAmount").val();
			var  okImeiCountAll=count+okImeiCount;	
			if(okImeiCountAll>noPickAmount){
			     alertMsg.info("您输入的IMEI号太多，已超过未配货树！");
				 return;
			 }
		 var noimeiListValue=noimeiList.value;
            var okImei=$("#"+OKIMEI);
			var imei = "";
			for ( var i = 0; i < count; i++) {
				imei = data[i];
				alert("imei");
				okImei.append("<option value='"+imei+"'>"+imei+"</option>")
				noimeiListValue = noimeiListValue.replaceAll(imei, '');
			 }
			 okImeiCountObj.html(okImeiCountAll);
			 
			 noimeiList.value=noimeiListValue;
			
			
			
			   
			}  
	  })
	 }
	 
	 
				 
			}
		}
	}
 
  		
          
  });
     
      $("#moveokImei").click(function(){
          var okImeiCount=$("#"+OKIMEICOUNT).html();
         if( okImeiCount == null || okImeiCount==''){
		   return;
		   }
		   
		  var imeiList=$("#"+OKIMEI)[0];
		  if(imeiList){
		   var length=imeiList.length;
		   var selected = false;
		   
		   	for ( i = length - 1; i >= 0; i--) {
		   	 if (imeiList.options[i].selected) {
			  imeiList.options[i] = null;
			   selected = true;
			}
		   }
		   	if (!selected) {
			 alertMsg.info("请选择要移除的IMEI");
		    }
		   $("#"+OKIMEICOUNT).html(imeiList.length); 
		  }
		   
		   
		   
     });
     
     $("#clearokImei").click(function(){
         var okImeiCount=$("#"+OKIMEICOUNT).html();
         if( okImeiCount == null || okImeiCount==''){
		   return;
		   }
		  var imeiList=$("#"+OKIMEI)[0];
		  if(imeiList){
		   var length=imeiList.length;
		   	for ( i = length - 1; i >= 0; i--) {
			imeiList.options[i] = null;
		   } 
		  } 
         $("#"+OKIMEICOUNT).html(0);
     });
     
     
   });
   
    function initImei(){
     var  imeiList=$("#"+IMEI).val().split(",");
     if(!imeiList||imeiList[0]=='' ){
     $("#"+OKIMEICOUNT).html(0);
     return ;
     }
     var length=imeiList.length;
    $("#"+OKIMEICOUNT).html(length);
    var okImei=$("#"+OKIMEI);
     var imei;
     for (i=0;i<length;i++){
        imei=imeiList[i];
        okImei.append("<option value='"+imei+"'>"+imei+"</option>")
     }
   }