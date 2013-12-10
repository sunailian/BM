<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<html>
<body>
<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width='0' height='0'> 
	<embed id="LODOP_EM" type="application/x-print-lodop" width='0' height='0' pluginspage="install_lodop32.exe"></embed>
</object> 
<script language="javascript" src="${main}/page/as/repair/js/LodopFuncs.js"></script>
<script language="javascript" type="text/javascript">
  var LODOP; //声明为全局变量
  function check_CheckIsInstall() {	
		try{
		     LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
			 if ((LODOP!=null)&&(typeof(LODOP.VERSION)!="undefined")) 
				alert("本机已成功安装过Lodop控件!\n  版本号:"+LODOP.VERSION);
		 }catch(err){
			//alert("Error:本机未安装或需要升级!");
 		 }
  }
  function check_CreatePrintPage() {
    LODOP.PRINT_INIT("套打EMS的模板");
  };
  function check_CreatePrintPage() {
	LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
    

	LODOP.PRINT_INITA(-2,-4,1709,707,"工单打印");
	LODOP.ADD_PRINT_SETUP_BKIMG("D:\\Tools\\WEB打印\\Lodop\\order.jpg");
	LODOP.SET_SHOW_MODE("BKIMG_LEFT",1);
	LODOP.SET_SHOW_MODE("BKIMG_TOP",1);
	LODOP.SET_SHOW_MODE("BKIMG_WIDTH",864);
	LODOP.SET_SHOW_MODE("BKIMG_HEIGHT",494);
	LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",true);
	LODOP.SET_SHOW_MODE("BKIMG_PRINT",true);
	LODOP.ADD_PRINT_TEXT(89,120,129,20,"当前网点名称");
	LODOP.ADD_PRINT_TEXT(111,306,111,20,"当前日期");
	LODOP.ADD_PRINT_TEXT(192,148,248,30,"故障描述");
	LODOP.ADD_PRINT_TEXT(111,138,112,20,"电脑流水号");
	LODOP.ADD_PRINT_TEXT(131,307,108,20,"机主姓名");
	LODOP.ADD_PRINT_TEXT(275,125,280,40,"送修备注");
	LODOP.ADD_PRINT_TEXT(132,130,117,17,"送修单位");
	LODOP.ADD_PRINT_TEXT(223,108,291,22,"MSN");
	LODOP.ADD_PRINT_TEXT(91,308,108,20,"网点电话");
	LODOP.ADD_PRINT_TEXT(62,638,100,20,"手工单号");
	LODOP.ADD_PRINT_TEXT(152,114,134,20,"手机");
	LODOP.ADD_PRINT_TEXT(154,307,100,20,"联系电话");
	LODOP.ADD_PRINT_TEXT(245,108,291,25,"IMEI");
	LODOP.ADD_PRINT_TEXT(313,126,281,20,"手机附件");
	LODOP.ADD_PRINT_TEXT(333,295,100,20,"受理员");
	LODOP.ADD_PRINT_TEXT(112,678,122,20,"初检状态");
	LODOP.ADD_PRINT_TEXT(170,130,100,20,"购机日期");
	LODOP.ADD_PRINT_TEXT(174,304,100,20,"品牌-机型");
	LODOP.ADD_PRINT_TEXT(133,633,100,20,"限价");
	
  };    

</script> 
测试一下：<a href="javascript:check_CheckIsInstall()">查看本机是否安装控件</a><br><br>
<a href="http://mtsoftware.v053.gokao.net/samples/PrintSampIndex.html">示例</a><br><br>
进入<a href="javascript:;" onclick="javascript:check_CreatePrintPage();LODOP.PRINT_DESIGN();">模板设计</a><br><br>
进入<a href="javascript:;" onclick="javascript:check_CreatePrintPage();LODOP.PREVIEW();">打印预览</a>
</body>
</html>

