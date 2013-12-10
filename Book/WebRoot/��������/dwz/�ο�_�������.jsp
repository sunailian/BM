<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<%--1.首先分页表单里要有以下2个分页参数(建议每次都包含，省事)--%>
<input type="hidden" name="sort.orderField" value="${sort.orderField}"/>
<input type="hidden" name="sort.orderDirection" value="${sort.orderDirection}" />
<%--2.给要排序的表格列th元素指定排序数据库字段--%>
<th orderField="record_time" <c:if test="${sort.orderField=='record_time'}">class="${sort.orderDirection}"</c:if>>记录时间</th>
<%--3.后台参考GlobalAction的findHtepayLog()--%>

<%--
    DWZ表格排序原理：
    1、 给要排序的表格表头th加上orderField=”fieldName”属性，这样点击该表头才能出发提交事件。
        th的class=”asc”/class=”desc”会分别显示向上和向下的箭头，这个不是只显示这么简单，往下看。
    2、 在#pageForm加上sort.orderField和sort.orderDirection，点击排序后提交的依然是pagerForm，
        sort.orderField会绑定点击的th的orderField，而sort.orderDirection则会反向绑定th的class，
        这是dwz智能的地方，也就是你不用手动记住状态来反向，class=”asc”就会提交sort.orderDirection = desc。
        注意每次要将sort.orderDirection绑定回th的class
--%>