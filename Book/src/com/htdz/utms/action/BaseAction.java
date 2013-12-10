package com.htdz.utms.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.google.gson.Gson;
import com.htdz.exception.BusiException;
import com.htdz.page.NumberPageBuilder;
import com.htdz.page.Page;
import com.htdz.page.PageItem;
import com.htdz.utms.common.util.MyUtil;
import com.htdz.utms.vo.SessionUser;
import com.htdz.utms.vo.Sort;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware {
    private static final long serialVersionUID = -1234234234795695213L;
	protected static final String SUCCESS = "success";
	protected static final String FAILURE = "failure";
	protected String message;
	public boolean reloadParent ;	//模态窗口操作后是否需要刷新父窗口
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected Page page = new Page();
	protected Sort sort = new Sort();
    private transient PrintWriter out;

	//获得ip地址（防止代）                                                                                                                                                                                                                 
	public String getIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    System.out.println(ip);
	    return ip;
	}
    
    public PrintWriter getOut() {
        if (out == null) {
            try {
                out = getResponse().getWriter();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return out;
    }

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	public SessionUser getSessionUser() {
		SessionUser user = (SessionUser)request.getSession().getAttribute("SESSION_USER");
		try {
			user.getOrgId();
		} catch (NullPointerException e) {
			throw new BusiException("请登录");
		}
		return user;
	}
	/**
	 * 获取session中的组织id
	 * @author tcl [Dec 18, 2012]
	 * @return
	 */
	public Long getOrgId() {
		SessionUser user = getSessionUser();
		try {
			user.getOrgId();
		} catch (NullPointerException e) {
			throw new BusiException("请登录");
		}
		return user.getOrgID();
	}
	/**
	 * 获取session中的userid
	 * @author tcl [Dec 20, 2012]
	 * @return
	 */
	public Long getUserIdFromSession() {
		return getSessionUser().getUserId();
	}
	
	@SuppressWarnings("unchecked")
	public Map getSession() {
		return ActionContext.getContext().getSession();
	}
    public Map getApplication() {
        return ActionContext.getContext().getApplication();
    }
	
	public void responseJson(Object obj){
		Gson gson = new Gson();

		String result = gson.toJson(obj);

		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");

		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			throw new BusiException("002",e);
		}
		out.write(result);

		out.flush();
		out.close();
	}
	
	public void responseJson(String result){

		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");

		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			throw new BusiException("00#000",e);
		}
		out.write(result);

		out.flush();
		out.close();
	}
	
	public void responseArray(String result){

		response.setContentType("application/text; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");

		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			throw new BusiException("00#000",e);
		}
		out.write(result);

		out.flush();
		out.close();
	}
	
	public void reponseArray(Map<? extends Object,? extends Object> temp,String firstOptionText){
		Map<Object,Object> map = new HashMap<Object, Object>();
		map.put(-1L,firstOptionText);
		map.putAll(temp);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		int i = 0;
		
		for(Map.Entry<Object,Object> entry : map.entrySet()){
			if(i>0){
				sb.append(",");
			}
			sb.append("[");
			sb.append("\""+entry.getKey()+"\"");
			sb.append(",");
			sb.append("\""+entry.getValue()+"\"");
			sb.append("]");
			i++;
		}
		
		sb.append("]");
		responseJson(sb.toString());
	}
	
	public void reponseArray(Map<? extends Object,? extends Object> temp){
		this.reponseArray(temp,"请选择");
	}
	
	
	/**
	 * 得到分页控件
	 */
	public List<PageItem> getPageItems(){
		return new NumberPageBuilder(page).buildPageItems();
	}
	
	public Page getPage() {
		return this.page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession();
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public boolean isReloadParent() {
		return reloadParent;
	}

	public void setReloadParent(boolean reloadParent) {
		this.reloadParent = reloadParent;
	}
	
}
