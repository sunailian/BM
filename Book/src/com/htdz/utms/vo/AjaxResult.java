package com.htdz.utms.vo;

public class AjaxResult {
	
	private String statusCode;
	private String message;
	private String navTabId;
	private String rel;
	private String callbackType;
	private String confirmMsg;
	private String forwardUrl;
	
	public AjaxResult(String statusCode,String message){
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public AjaxResult(String statusCode,String message,String navTabId){
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
	}
	
	public AjaxResult(String statusCode,String message,String navTabId,String rel){
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
		this.rel = rel;
	}
	
	public AjaxResult(String statusCode,String message,String navTabId,String rel,String callbackType){
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
		this.rel = rel;
		this.callbackType = callbackType;
	}
	
	public AjaxResult(String statusCode,String message,String navTabId,String rel,String callbackType,String confirmMsg){
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
		this.rel = rel;
		this.callbackType = callbackType;
		this.confirmMsg = confirmMsg;
	}
	
	public AjaxResult(String statusCode,String message,String navTabId,String rel,String callbackType,String confirmMsg,String forwordUrl){
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
		this.rel = rel;
		this.callbackType = callbackType;
		this.confirmMsg = confirmMsg;
		this.forwardUrl = forwordUrl;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNavTabId() {
		return navTabId;
	}
	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	public String getCallbackType() {
		return callbackType;
	}
	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
	public String getConfirmMsg() {
		return confirmMsg;
	}
	public void setConfirmMsg(String confirmMsg) {
		this.confirmMsg = confirmMsg;
	}

}
