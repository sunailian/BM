<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<div class="pageHeader">
	<form method="post" action="${main}/repair/linkStandardErr.c" onsubmit="return dwzSearch(this, 'dialog');">  
	    <div class="searchBar">  
		    <table class="searchContent">
				<tr>
					<td>故障类别：</td>
					<td>
						<select id="errTypeSelect" name="errTypeID">
				         	<option value="">请选择故障类型</option>
							<c:forEach var="et" items="${errTypeList}">
								<option value="${et.paraID}">${et.paraName}</option>
							</c:forEach>
				        </select>
					</td>
					<td>故障代码：</td>
					<td><input type="text" size="12" id="searchErrCode" name="errCode" value="${errCode}"/></td>
			        <td>
			        	<span class="button"><span class="buttonContent"><button type="submit">查询</button></span></span>
			        	<span class="button"><span class="buttonContent"><button type="button" multLookup="err" warn="请先选择故障">确定</button></span></span>
			        </td>
				</tr>
			</table>
        </div>  
    </form>
</div>
<div class="pageContent">
	<table class="table" layoutH="70" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th width="30"><input type="checkbox" class="checkboxCtrl" group="err" /></th>
				<th>故障代码</th>
				<th>故障描述</th>
				<th>故障类别</th>
			</tr>
		</thead>
		<tbody>
		    <c:forEach var="serr" items="${standardErrList}">
		    <tr>
				<td><input type="checkbox" name="err" value="{errIDs:'${serr.errID}', errInfos:'${serr.errInfo}'}"/></td>
				<td>${serr.errCode}</td>
				<td>${serr.errInfo}</td>
				<td>${serr.errTypeName}</td>
			</tr>
		    </c:forEach>
		</tbody>
	</table>
</div>
<script type="text/javascript">
	$(function(){
		if('${errTypeID}' != '')
			$('#errTypeSelect').val(${errTypeID});
	})
</script>