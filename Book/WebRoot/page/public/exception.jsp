<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>错误</title>
	<style type="text/css">
    * {
	    margin: 0px;
	    padding: 0px;
    }
    </style>
  </head>
  <body>
	<div style="height:150px;width:400px;border:1px solid #000000;margin:30px auto;padding:20px;">
	  <div style="width:300px;height:110px;float:left;">
	    <span style="display:block;text-align:left;font-size:14px;font-weight:bold;color:#d13119;">出错了</span>
	    <span style="display:block;color:gray;font-size:12px;margin-top:15px;text-indent:20px;">【代号${error.errorCode}】${error.message}</span>
	    <a style="display:block;font-size:14px;margin-left:30px;margin-top:70px;height:30px;width:127px;background:url('../images/common/errorbutton.gif') no-repeat;" href="javascript:void(0);" onclick="window.history.go(-1);"></a>
	  </div>
	  <div style="float:right;height:110px;width:88px;background:url('../images/common/x.gif') no-repeat center center"></div>
	</div>
  </body>
</html>