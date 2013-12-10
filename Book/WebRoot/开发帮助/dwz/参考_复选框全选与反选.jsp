<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<input type="checkbox" class="checkboxCtrl" group="c1" />全选
<button type="button" class="checkboxCtrl" group="c1" selectType="invert">反选</button>
<label><input type="checkbox" name="c1" value="1" />选项1</label>
<label><input type="checkbox" name="c1" value="2" />选项2</label>
<label><input type="checkbox" name="c1" value="3" />选项3</label>
<%--
    将所有name值为group指定值的复选框作为一个组来进行关联
--%>