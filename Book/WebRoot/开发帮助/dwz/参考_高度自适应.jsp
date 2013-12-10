<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<%--
    容器高度自适应, 只要增加扩展属性layoutH="xx", 单位是像素。
    LayoutH表示容器内工具栏高度。浏览器窗口大小改变时，要想计算出内容的高度，必需告诉DWZ工具栏的固定高度。
    示例：
--%>
<div class="layoutBox">
    <div layoutH="150">内容</div>
</div>