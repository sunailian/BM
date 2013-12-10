<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
 <style>
     .pageHeader ul li{float:left; margin-left: 10px;} 
     .pageHeader div {float:left}
    </style>
    <div class="pageHeader">
       <form class="pageForm required-validate"  action="${main}/innerLogin.c" method="post">
	        <ul>
			    <li style="width:190px;">
			         用户名：
			        <input type="text" name="username" value="" class="required"/>
			    </li>
			    <li>
			        <span style="color:red;text-align:right;margin-top:3px;line-height:24px;">注意：模拟登录后，只能查询不能提交！</span>
			    </li>
			    <li>
			        <input type="submit" name="submit" value="提 交"  class="button_me">
			    </li>
				<li>
				    <span style="color:red;text-align:right;margin-top:3px;line-height:24px;">${message}</span>
				</li>
	        </ul>
	    </form>
    </div>
