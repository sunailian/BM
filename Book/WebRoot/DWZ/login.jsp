<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <title>我的个人网站</title>
    	<link rel="stylesheet" href="/DWZ/assets/css/styles.css" />
	</head>
	<body  id="login" scroll="no" onkeydown="javascript:if(event.keyCode && event.keyCode == 13) document.getElementById('submitBtn').click();">
 <div id="main">
        	<h1>临沂大学图书管理系统</h1>
        	<form id="loginForm" name="loginForm" method="post" action="supportLogin.c">
        		<div class="row email">
	    			<input type="text" id="studentno" name="student.studentno" placeholder="用户名" />
        		</div>
        		<div class="row pass">
        			<input type="password" id="password1" name="student.password1" placeholder="密码" />
        		</div>
        		<div><font style="color:red;font-weight:bold">${message}</font></div>
        		
        		<!-- The rotating arrow -->
        		<div class="arrowCap"></div>
        		<div class="arrow"></div>
        		
        		<p class="meterText">密码强度检测</p>
        		<input type="button" id="submitBtn" value="登   录"  onclick="submitLogin();" />
        	</form>
        </div>
        <!-- JavaScript includes - jQuery, the complexify plugin and our own script.js -->
		<script src="/DWZ/assets/js/jquery-1.7.2.min.js"></script>
		<script src="/DWZ/assets/js/jquery.complexify.js"></script>
		<script src="/DWZ/assets/js/script.js"></script>
	<script type="text/javascript">
		function submitLogin(){
			var name = $('#studentno').val();
			var pwd = $('#password1').val();
			if(!name || name == ""){
				alert('请输入用户名！');
				return;
			}
			if(!pwd || pwd == ""){
				alert('请输入密码！');
				return;
			}
			$('#loginForm').submit();
		}
	</script> 
</body>
</html>
