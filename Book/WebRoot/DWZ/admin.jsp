<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=7" />
    <title>临沂大学图书管理系统</title>
	
    <link href="/DWZ/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/DWZ/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/DWZ/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
    <link href="/DWZ/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
    <!--[if IE]>
    <link href="DWZ/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
    <![endif]-->
    <style type="text/css">
		#header{height:73px}
		#leftside, #container, #splitBar, #splitBarProxy{top:75px}
	</style>
	<script src="/DWZ/js/speedup.js" type="text/javascript"></script>
	<script src="/DWZ/js/jquery-1.7.2.js" type="text/javascript"></script>
	<script src="/DWZ/js/jquery.cookie.js" type="text/javascript"></script>
	<script src="/DWZ/js/jquery.validate.js" type="text/javascript"></script>
	<script src="/DWZ/js/jquery.bgiframe.js" type="text/javascript"></script>
	<script src="/DWZ/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>
    <script src="/DWZ/uploadify/scripts/swfobject.js" type="text/javascript"></script>
    <script src="/DWZ/uploadify/scripts/jquery.uploadify.v2.1.0.js" type="text/javascript"></script>
	
	<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
	<script src="/DWZ/chart/raphael.js" type="text/javascript"></script>
	<script src="/DWZ/chart/g.raphael.js" type="text/javascript"></script>
	<script src="/DWZ/chart/g.bar.js" type="text/javascript"></script>
	<script src="/DWZ/chart/g.line.js" type="text/javascript"></script>
	<script src="/DWZ/chart/g.pie.js" type="text/javascript"></script>
	<script src="/DWZ/chart/g.dot.js" type="text/javascript"></script>

	<script src="/DWZ/js/dwz.core.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.util.date.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.validate.method.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.regional.zh.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.barDrag.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.drag.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.tree.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.accordion.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.ui.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.theme.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.switchEnv.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.alertMsg.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.contextmenu.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.navTab.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.tab.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.resize.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.dialog.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.dialogDrag.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.sortDrag.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.cssTable.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.stable.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.taskBar.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.ajax.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.pagination.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.database.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.datepicker.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.effects.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.panel.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.checkbox.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.history.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.combox.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.print.js" type="text/javascript"></script>
	<script src="/DWZ/js/dwz.my.js" type="text/javascript"></script>
	<!--
	<script src="DWZ/bin/dwz.min.js" type="text/javascript"></script>
	-->
	<script src="/DWZ/js/dwz.regional.zh.js" type="text/javascript"></script>
	<script src="js/calendar/WdatePicker.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	var main = '${pageContext.request.contextPath}';
	$(function(){
		DWZ.init("DWZ/dwz.frag.xml", {
			loginUrl:"DWZ/login_dialog.jsp", loginTitle:"登录",	// 弹出登录对话框
	//		loginUrl:"DWZ/login.jsp",	// 跳到登录页面
			statusCode:{ok:200, error:300, timeout:301}, //【可选】
			pageInfo:{pageNum:"page.currPageNum", numPerPage:"page.pageRowSize", orderField:"sort.orderField", orderDirection:"sort.orderDirection"}, //【可选】
			debug:false,	// 调试模式 【true|false】
			callback:function(){
				initEnv();
				$("#themeList").theme({themeBase:"DWZ/themes"});
			}
		});
	});
	function _formatFull(data){
		   if(((String)(data)).length==1){
		       return "0"+data;
		   }else{
		       return data;
		   }
		}

	</script>
  </head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="http://j-ui.com">标志</a>
				<ul class="nav">
					<li><a href="login.html">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul>
				<ul class="nav">
			       <li><div>学生 ：${student.name}</div></li>
			       <li><div><a href="${main}/outSystem.c" title="退出" style="color: black">退出</a></div></li>
		        </ul>
			</div>
			<!-- navMenu -->
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div><!-- 一级菜单 -->
				<div class="accordion" fillSpace="sidebar">
					<div class="accordionHeader">
						<h2><span>Folder</span>读者信息</h2><!-- 二级菜单 -->
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
						    <li><a href="findStuInfo.c" target="navTab" rel="main" title="我的个人信息">我的个人信息</a></li>
						</ul>
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>书刊借阅</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href=" " target="navTab" rel="main">书刊借阅</a></li>
						</ul>
					</div>
					
					<div class="accordionHeader">
						<h2><span>Folder</span>预约通知</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href=" " target="navTab">预约通知</a></li>
						</ul>
					</div>
					
						<div class="accordionHeader">
						<h2><span>Folder</span>书刊检索</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href=" " target="navTab">书刊检索</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
			</div>
		</div>

	</div>

	<div id="footer">Copyright &copy; 2013 临沂大学图书管理系统 <a href="demo_page2.html" target="dialog"> </div>


</body>
</html>