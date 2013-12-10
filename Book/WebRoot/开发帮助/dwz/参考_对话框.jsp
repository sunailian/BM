<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<script type="text/javascript">
function closedialog(param) {
	alert(param.msg);
	return true;
}
</script>
<%--
    A所指向页面将会在dialog 弹出层中打开，rel标识此弹出层的ID，rel为可选项。
	html标签扩展方式示例: 
--%>
<a class="button" href="your_action_url" target="dialog" rel="your_tab_id" [max="true" title="自定义标题" width="800" height="480" fresh="false" close="closedialog" param="{msg:'gogo'}" mask="true"]><span>打开窗口1</span></a>

<%--
	            max         dialog打开时默认最大化, 
	            mask        dialog打开层后将背景遮盖,
	            maxable:    dialog 是否可最大化， 
	            minable:    dialog 是否可最小化，
	            mixable:    dialog 是否可最大化 
	            resizable:  dialog 是否可变大小 
	            drawable:   dialog 是否可拖动 
	            width:      dialog 打开时的默认宽度 
	            height:     dialog 打开时默认的高度
	            title:      dialog 打开时显示的标题
	            fresh:      重复打开dialog时是否重新载入数据，默认值true,
	            close:      关闭dialog时的监听函数，需要有boolean类型的返回值，
	            param:      close监听函数的参数列表，以json格式表示，例{msg:’message’}
	 
	关闭窗口：在弹出窗口页面内放置<button class="close" value="关闭"></button>即可。
	
	JS调用方式示例: 
	$.pdialog.open(url, dlgId, title);
	或
	$.pdialog.open(url, dlgId, title, options);　
	options:{width:100px,height:100px,max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true,close:”function”, param:”{msg:’message’}”}, 所有参数都是可选项。
	关闭dialog层：
	$.pdialog.close(dialog); 参数dialog可以是弹出层jQuery对象或者是打开dialog层时的dlgId.
	或者 
	$.pdialog.closeCurrent(); 关闭当前活动层。
	$.pdialog.reload(url,data,dlid) 刷新dlid指定的dialog，url：刷新时可重新指定加载数据的url, data：为加载数据时所需的参数。
--%>