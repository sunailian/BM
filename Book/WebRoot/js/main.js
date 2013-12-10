var activeX_Err = "对不起！您目前无法导出EXCEL！\n\n成功导出EXCEL的前提：\n1、您的电脑必须安装EXCEL；\n" + "2、必须使用IE浏览器；\n3、必须对IE开启ActiveX。\n\n开启ActiveX步骤：\n"
		+ "IE浏览器：工具->Internet选项->安全->“Internet”和“本地Intranet”->自定义级别：ActiveX控件和插件->对没有标记为安全的ActiveX控件进行初始化和脚本运行：设置为“启用”。"
		+ "(或者将ActiveX控件和插件->所有与ActiveX相关的参数都设置为“启用”。)\n\n以上设置确定后，再重新登录UTAS，即可导出EXCEL。";
		
function excelExport() {
	var mytable = document.getElementById("mytable");
	var mytablename = "mytable";
	if (mytable == null) {
		mytable = document.getElementById("mydetail");
		mytablename = "mydetail";
	}
	if (mytable == null) {
		mytable = document.getElementById("myexcel");
		mytablename = "myexcel";
	}
	if (mytable == null)
		return;
	excelExportByTableName(mytablename);
}
function excelExportByTableName(tableName) {
	var mytable = document.getElementById(tableName);
	if (mytable != null) {
		var oXL = null;
		try {
			oXL = new ActiveXObject("Excel.Application");
			var oWB = oXL.Workbooks.Add();
			var oSheet = oWB.ActiveSheet;
			var sel = document.body.createTextRange();
			sel.moveToElementText(mytable);
			sel.select();
			sel.execCommand("Copy");
			oSheet.Paste();
			oXL.Visible = true;
		} catch (e) {
		alertMsg.info(activeX_Err);
		//alert(activeX_Err);
		}
		oXL = null;
	}
}

function printMe() {
	var mytable = document.getElementById("mytable");
	var mytablename = "mytable";
	if (mytable == null) {
		mytable = document.getElementById("mydetail");
		mytablename = "mydetail";
	}
	if (mytable == null) {
		mytable = document.getElementById("myexcel");
		mytablename = "myexcel";
	}
	if (mytable == null)
		return;
	printMeByTableName(mytablename);
}

function printMeByTableName(tablename) {
	var mydate = new Date();
	var dateStr = mydate.getFullYear() + "-" + (mydate.getMonth() + 1) + "-" + mydate.getDate() + " " + mydate.getHours() + ":" + mydate.getMinutes()
			+ ":" + mydate.getSeconds();
	var mytable = document.getElementById(tablename);
	if (mytable == null)
		return;
	var print = "<html><head><title>广东移动UTMS版权所有 [打印时间：" + dateStr
			+ "]<\/title><link href='\/css\/print.css' rel='stylesheet' type='text\/css' \/><STYLE>BODY{FILTER: gray;}</STYLE>";
	print += "<SCRIPT language='javascript'>function showErr(operation){alertMsg.info('对不起！您目前无法通过此按钮进行“'+operation+'”！\\n\\n请您直接使用浏览器自带的菜单进行“'+operation+'”；\\n\\n或者将IE浏览器开启ActiveX后再试试："
			+ "\\n\\n开启ActiveX步骤：\\nIE浏览器：工具->Internet选项->安全->“Internet”和“本地Intranet”->自定义级别：ActiveX控件和插件->对没有标记为安全的ActiveX控件进行初始化和脚本运行：设置为“启用”。"
			+ "(或者将ActiveX控件和插件->所有与ActiveX相关的参数都设置为“启用”。)\\n\\n以上设置确定后，再重新登录UTMS，即可进行“'+operation+'”。');}"
			+ " function printView(){hide();try{document.all.WebBrowser.ExecWB(7,1)}catch(e){showErr('打印预览');};show();}function toPrint(){if (confirm('您确定打印了吗？')){hide();window.print();	alertMsg.info('正在打印中，请您稍候……\\n\\n打印完成后请您按确定返回。');}show();} function pageSetup(){hide();try{document.all.WebBrowser.ExecWB(8,1)}catch(e){showErr('页面设置');};show();}function hide(){document.getElementById('printView').style.display='none';document.getElementById('print').style.display='none';document.getElementById('pageSetup').style.display='none';document.getElementById('closeWin').style.display='none';}function show(){document.getElementById('printView').style.display='';document.getElementById('print').style.display='';document.getElementById('pageSetup').style.display='';document.getElementById('closeWin').style.display='';}";
	print += "<\/script><\/head><body oncontextmenu='alertMsg.info(\"欢迎使用UTMS打印页面~\");return false;'><OBJECT classid='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2' id='WebBrowser' width='87' height='21'></OBJECT><div style='text-align:center'>";
	print += mytable.outerHTML;
	print += "<br><input id='printView' onClick='printView()' type='button' value='打印预览' class=\"button\" onmouseover=\"this.className='button_over'\" onmouseout=\"this.className='button'\"> <input id ='print' onClick='toPrint()' type='button' value='直接打印' class=\"button\" onmouseover=\"this.className='button_over'\" onmouseout=\"this.className='button'\"> <input id='pageSetup' onClick='pageSetup()' type='button' value='页面设置' class=\"button\" onmouseover=\"this.className='button_over'\" onmouseout=\"this.className='button'\"> <input id='closeWin' onClick='javascript:window.close()' type='button' value='关闭窗口' class=\"button\" onmouseover=\"this.className='button_over'\" onmouseout=\"this.className='button'\">";
	print += "<\/div><\/body><\/html>";
	var newWindow = window.open(null, '_blank',
			'width=980,height=620, top=0, left=0, toolbar=no, menubar=yes, scrollbars=yes, resizable=yes,location=no, status=yes');
	newWindow.document.open("text/html");
	newWindow.document.write(print);
	newWindow.document.close();
}

 