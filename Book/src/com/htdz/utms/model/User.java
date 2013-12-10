package com.htdz.utms.model;

import java.util.Date;


public class User {
	
	private static final long serialVersionUID = 8482290456795695213L;
    private Long userId;
    private Long orgId;
    private Long deptId;
    private String userCode;
    private String userName;
    private String passWD;
    private String mobile;
    private String email;
    private String status;
    private String logInIp;
    private Date logInDate;
    private String realName;
    private String IDCard;
    private Date birthDate;
    private String gender;
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getIDCard() {
		return IDCard;
	}
	public void setIDCard(String card) {
		IDCard = card;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWD() {
		return passWD;
	}
	public void setPassWD(String passWD) {
		this.passWD = passWD;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLogInIp() {
		return logInIp;
	}
	public void setLogInIp(String logInIp) {
		this.logInIp = logInIp;
	}
	public Date getLogInDate() {
		return logInDate;
	}
	public void setLogInDate(Date logInDate) {
		this.logInDate = logInDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
}
