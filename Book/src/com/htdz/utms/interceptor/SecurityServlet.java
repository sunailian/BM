package com.htdz.utms.interceptor;

import java.io.IOException;  
import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  

public class SecurityServlet extends HttpServlet implements Filter {  
	
    private static final long serialVersionUID = 1L;  
  
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {  
          
    	HttpServletRequest request=(HttpServletRequest)arg0;     
           HttpServletResponse response  =(HttpServletResponse) arg1;      
           HttpSession session = request.getSession(true);       
           Object sessionUser = session.getAttribute("SESSION_USER");
           String url=request.getRequestURI();    
           
           if(sessionUser == null) { 
        	   //判断获取的路径不为空且不是访问登录页面或 验证码页面
               if(url!=null && !url.equals("") && url.indexOf("login") < 0 && url.indexOf("makeCertPic") < 0) {     
                   response.sendRedirect(request.getContextPath() + "/DWZ/login.jsp"); 
                   return ;     
               }           
            }else{
            	//在已创建了会话的前提下，非以下请求的，系统直接跳转到主页面
            	if(url!=null && !url.equals("") && url.indexOf("makeCertPic") < 0   //验证码
            			&& url.indexOf("login") < 0 && url.indexOf("admin") < 0		//访问登录或退出
            			&& url.indexOf("viewlog") < 0 								//日志下载
            			&& url.indexOf("stopSendCause") < 0 						//终止发货原因
            			&& url.indexOf("purchInDepotImei") < 0 						//采购入库IMEI录入
            			&& url.indexOf("exportDataBySQL") < 0){						//SQL导数
            		
            		response.sendRedirect(request.getContextPath() + "/admin.c"); 	//跳转到系统主页面
                    return ;
            	}
            }
            arg2.doFilter(arg0, arg1);     
            return;     
    }  
    
    public void init(FilterConfig arg0) throws ServletException {  
    }  
  
} 