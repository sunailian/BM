<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<%--比如说下面是你的带回页面--%>
<dl class="nowrap">
	<dt>城市：</dt>
	<dd>
		<input class="required" name="district.cityName" type="text" readonly/>
	</dd>
</dl>
<dl class="nowrap">
	<dt>区县：</dt>
	<dd>
		<input name="关键词.id" value="" type="hidden"/>
		<input class="required" name="关键词.districtName" type="text" readonly/>
		<%--DWZ通过lookupGroup的属性值来关联控件，动态更新控件的值--%>
		<a class="btnLook" href="your_url" lookupGroup="关键词">查找带回</a>	
	</dd>
</dl>

<%--下面是你的查找页面--%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<ul class="tree expand">
			<li><a href="javascript:">北京</a>
				<ul>
				    <%--查找带回后：前个页面将通过“关键词”将id值绑定到name="关键词.id"的控件上,将districtName值绑定到name="关键词.districtName"控件上。--%>
					<li><a href="javascript:" onclick="$.bringBack({id:'1', districtName:'东城', cityName:'北京'})">东城</a></li>
					<li><a href="javascript:" onclick="$.bringBack({id:'2', districtName:'西城', cityName:'北京'})">西城</a></li>
				</ul>
			</li>
			<li><a href="javascript:">上海</a>
				<ul>
					<li><a href="javascript:" onclick="$.bringBack({id:'1', districtName:'崇明', cityName:'上海'})">崇明</a></li>
					<li><a href="javascript:" onclick="$.bringBack({id:'2', districtName:'黄浦', cityName:'上海'})">黄浦</a></li>
				</ul>
			</li>
		</ul>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
</div>