package com.htdz.utms.common;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.htdz.exception.BusiException;

public class ExceptionUtil {
	private static final Logger logger = Logger.getLogger(ExceptionUtil.class); 
	
	public static void changeToMessage(Exception e,HttpServletResponse response)throws Exception{
		String msg = "";
		if(e instanceof BusiException){
			msg = e.getMessage();
		}
		else if(e instanceof HibernateOptimisticLockingFailureException){
			msg = "记录已被他人修改！";
		} else if (e instanceof DataAccessException) {
			msg = "数据库操作失败！";
		} else if (e instanceof NullPointerException) {
			msg = "调用了未经初始化的对象或者是不存在的对象！";
		} else if (e instanceof IOException) {
			msg = "IO异常！";
		} else if (e instanceof ClassNotFoundException) {
			msg = "指定的类不存在！";
		} else if (e instanceof ArithmeticException) {
			msg = "数学运算异常！";
		} else if (e instanceof ArrayIndexOutOfBoundsException) {
			msg = "数组下标越界！";
		} else if (e instanceof IllegalArgumentException) {
			msg = "方法的参数错误！";
		} else if (e instanceof ClassCastException) {
			msg = "类型强制转换错误！";
		} else if (e instanceof SecurityException) {
			msg = "违背安全原则异常！";
		} else if (e instanceof SQLException) {
			msg = "操作数据库异常！";
		}else if (e instanceof Exception) {
			msg = "程序内部错误，操作失败！";
		} else if (e instanceof Throwable) {
			msg = "Java虚拟机发生了内部错误！";
		} else {
			msg = "未知错误！";
		}
		logger.error("请求:"+ServletActionContext.getRequest().getRequestURI()+ "发生错误: ",e); 
		response.setContentType("text/plain;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setStatus(500);
		response.getWriter().write(msg);
		response.getWriter().flush();
		response.getWriter().close();
	}

}
