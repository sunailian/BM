package com.htdz.utms.vo;

import org.apache.commons.lang.StringUtils;

public class Sort {
	
	private String orderField;
	private String orderDirection;
	
	public Sort(){}
	
	public Sort(String orderField,String orderDirection){
		this.orderField = orderField;
		this.orderDirection = orderDirection;
	}
	
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getOrderDirection() {
		return orderDirection;
	}
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
	public boolean isEmpty() {
		if(StringUtils.isEmpty(orderField)&&StringUtils.isEmpty(orderDirection)){
			return true;
		}
		return false;
	}
	
}
