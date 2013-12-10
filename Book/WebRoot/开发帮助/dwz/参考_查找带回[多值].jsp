<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<%--比如以下是你的页面的控件--%>
<dl class="nowrap">
	<dt>部门名称C：</dt>
	<dd>
		<input name="关键字.id" value="" type="hidden">
		<input name="关键字.orgName" type="text"/>
		<a class="btnLook" href="lookup2.jsp" lookupGroup="关键字">查找带回</a>
	</dd>
</dl>
<%--
    点击“查找带回”，弹出页面参考“lookup2.jsp”(移步到此页面，从34行开始看注释部分)。
    带回后，则本页将通过“关键字”将带回的id值绑定在name="关键字.id"控件上,将orgName值绑定在name="关键字.orgName"控件上
--%>
