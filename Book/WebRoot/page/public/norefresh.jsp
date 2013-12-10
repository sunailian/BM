<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/include/meta.jsp"%>
	<title>操作有误</title>
</head>

<body>
	<div id="mainContainer">
		<div id="mainContent">
		<div class="error">
          <p class="sorry">很抱歉,您的操作有误，请勿重复提交!</p>
          <p class="sorry">您可以:</p>
          <p class="return"><a href="javascript:history.go(-1);">返回上一页</a></p>
          </div>		
		</div>
	</div>
</body>
</html>