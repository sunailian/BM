package com.htdz.utms.common.util;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AppContextManage {

    private AppContextManage(){}

    private static ApplicationContext appContext;

    public static synchronized ApplicationContext getAppContext(){
        if(appContext == null)
            appContext = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
        return appContext;
    }
    public static Object getBean(String beanName){
        return getAppContext().getBean(beanName);
    }
}
