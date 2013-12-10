<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<%--比如以下是你的页面的控件--%>
<dl class="nowrap">
	<dt>部门名称A1：</dt>
	<dd>
		<input id="inputOrg1" name="关键字.id" value="" type="hidden"/>
		<input class="required" name="关键字.orgName" type="text" postField="keyword" suggestFields="orgNum,orgName" suggestUrl="your_url" lookupGroup="关键字"/>
	</dd>
</dl>
<%--
    点击控件将进行联想：
    联想时，要求suggestUrl请求返回json数组,比如返回json数组如下：
    [
		{"id":"1", "orgName":"技术部", "orgNum":"1001"},
		{"id":"2", "orgName":"人事部", "orgNum":"1002"},
		{"id":"3", "orgName":"销售部", "orgNum":"1003"}
	]
	
	suggestFields属性值指定显示格式,比如这里指定为orgNum,orgName的顺序，将以
	1001-技术部
	1002-人事部
	1003-销售部
	的格式让用户选择。
	
	用户选择后,将通过指定的关键字动态更新各控件的值：
	比如这里你选择了“1002-人事部”，那么对应的数据是{"id":"2", "orgName":"人事部", "orgNum":"1002"}，
	你选择的数据将通过“关键字”动态更新到name="关键字.id"和name="关键字.orgName"控件上。
	
	注意：请不要自行拼接json数组，后台***Action继承了BaseAction，直接调用基类的responseArray(Map map)方法，传map进去就可以了。
--%>
