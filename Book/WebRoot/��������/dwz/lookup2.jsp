<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<%-- 
    分页表单的id值pagerForm是固定写法，不要去修改；
    DWZ会根据用户在分页控件上的选择，自动去修改page.currPageNum的值，从而往后台提交完成分页；
--%>
<form id="pagerForm" action="demo/database/dwzOrgLookup2.html">
	<input type="hidden" name="page.currPageNum" value="${page.currPageNum}" />  
    <input type="hidden" name="page.pageRowSize" value="${page.pageRowSize}" />   
    <%--以下跟表头排序相关(如果没有表头排序，可不写,建议每次拷过去,省事)--%>
    <input type="hidden" name="sort.orderField" value="${sort.orderField}"/>
    <input type="hidden" name="sort.orderDirection" value="${sort.orderDirection}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="demo/database/dwzOrgLookup2.html" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>部门名称:</label>
				<input class="textInput" name="orgName" value="" type="text">
			</li>	  
			<li>
				<label>部门编号:</label>
				<input class="textInput" name="orgNum" value="" type="text">
			</li>
			<li>
				<label>部门经理:</label>
				<input class="textInput" name="leader" value="" type="text">
			</li>
				<li>
				<label>上级部门:</label>
				<input class="textInput" name="parentOrg.orgName" value="" type="text">
			</li> 
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
				<%--
				    这里选择带回按钮的multLookup属性是关键，必需与本页51行的全选的复选框的group值一样,
				    这样子用户点击“选择带回”时候，DWZ会检查用户是否选择了列表项，如果没选择，DWZ会提示warn属性所指定的信息。
				    提示：本页所出现的“关键字”只作用于本页，与前一个页面的“关键字”无关，因此可命名不同。
				--%>
				<li><div class="button"><div class="buttonContent"><button type="button" multLookup="关键字" warn="请选择部门">选择带回</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
			    <%--这里表头的复选框的group属性值，必需与表格项中的复选框name值一致，DWZ才会将此类复选框作为一组来关联全选或取消--%>
				<th width="30"><input type="checkbox" class="checkboxCtrl" group="关键字" /></th>
				<th orderfield="orgName">部门名称</th>
				<th orderfield="orgNum">部门编号</th>
				<th orderfield="leader">部门经理</th>
				<th orderfield="creator">创建人</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><input type="checkbox" name="关键字" value="{id:'1', orgName:'技术部', orgNum:'1001'}"/></td>
				<td>技术部</td>
				<td>1001</td>
				<td>leader</td>
				<td>administrator</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="关键字" value="{id:'2', orgName:'人事部', orgNum:'1002'}"/></td>
				<td>人事部</td>
				<td>1002</td>
				<td>test</td>
				<td>administrator</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="关键字" value="{id:'3', orgName:'销售部', orgNum:'1003'}"/></td>
				<td>人事部</td>
				<td>1002</td>
				<td>test</td>
				<td>administrator</td>
			</tr>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<%-- 如果是dialog上的分页：onchange="dialogPageBreak({numPerPage:this.value})"--%>
			<select class="combox" name="page.pageRowSize" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" <c:if test="${page.pageRowSize==20}">selected="selected"</c:if> >20</option>
				<option value="50" <c:if test="${page.pageRowSize==50}">selected="selected"</c:if> >50</option>
				<option value="100" <c:if test="${page.pageRowSize==100}">selected="selected"</c:if> >100</option>
				<option value="200" <c:if test="${page.pageRowSize==200}">selected="selected"</c:if> >200</option>
			</select>
			<span>条，共${page.totalRowSize}条</span>
		</div>
		<%-- 如果是dialog上的分页：targetType="dialog"--%>
		<div class="pagination" targetType="navTab" totalCount="${page.totalRowSize}" numPerPage="${page.pageRowSize}" pageNumShown="${page.pageNumShown}" currentPage="${page.currPageNum}"></div>
	</div>
</div>