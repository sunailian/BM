<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  		
    <title></title>
    <script>
		TreeMenu._url="/createTree.c";
		TreeMenu.getChildren('0','0','','1','rootDiv','findDepot');
		var id;
		var position;
		function tj(id,position){
		window.parent.document.getElementById("depotId").value=id;
		window.parent.document.getElementById("depotName").value=position;
		$.pdialog.closeCurrent(); 
		}
	</script>
	<!-- getChildren方法中的参数第一个是变量、
	第二个是checkBox、
	第三个同步还是异步（0：同步；1异步）、
	第四个是树加载的DIV 
	第5个是xml文件中 用于调取SQL的ID值 -->
  </head>
  <body>
  <div class="tabsContent">
  	<div id="rootDiv" style="float: left;  width: 30%; ">
	</div>
	</div>
  </body>
</html>
