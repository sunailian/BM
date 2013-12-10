package com.htdz.utms.vo;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class SessionUser {

	private long userId;
	private long operId;
	private String userCode;
	private String username;
	private String userType;
	private String realName;
	private Long orgTypeId;
	private long orgId;
	private long areaId;
	private String orgName;
	private long deptId;
	private String deptName;
	private Date lastLoginTime;
	private String status;
	private String operCode;
	private String operName;
	private String sex;
	private String telephone;
	private String email;
	private Date regDate;
	private Long orgID;
	private Long deptID;
	private Set<Long> roleIdSet;
	private Long tradeTypeID;
	private Long orgRoleID;// 组织角色 移动，售后商，厂家等
	private List<Long> menuidList;// 可访问的子菜单ID集合
	private Set<Long> permidSet;// 拥有的权限ID集合
	private Long serviceLevelID = 0L;// 服务级别
	private Long provinceID;// 省
	private Long areaID;// 市
	private Long countyID;// 区
	private boolean provinceLogin;

	private Long belongID = 0L;// 所属售后商
	private Long orgTypeID = 0L;// 组织类型 省移动，市移动，省售后，市售后，网点，等
	private Long provinceOrgID = 0L;// 省公司ID 售后商专用。默认为0
	private String orgTel;// 当前组织联系电话
	private String rolesName;// 角色名称，如有多个空格分开
	private String inner; // 是否匿名登录 1：是 0：否
	private String virtualhall = "0";// 是否省支撑中心虚拟厅

	// SunHanbin
	private Long OServiceLevelId;// 目标组织服务级别
	private Long OAreaId;// 目标地市
	private Long parentOrgID;// 父组织ID
	private String areaName;// 地市名称
	private String orgacode;// 组织编码
	
	private Long parentFillOrgId;//上级补货组织ID
	
	private String confirmStatus;
	
	private String manualNo_ch;

	public SessionUser() {
	}
	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Long getOrgTypeId() {
		return orgTypeId;
	}

	public void setOrgTypeId(Long orgTypeId) {
		this.orgTypeId = orgTypeId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Long getOrgID() {
		return orgID;
	}

	public void setOrgID(Long orgID) {
		this.orgID = orgID;
	}

	public Long getDeptID() {
		return deptID;
	}

	public void setDeptID(Long deptID) {
		this.deptID = deptID;
	}

	public Long getTradeTypeID() {
		return tradeTypeID;
	}

	public void setTradeTypeID(Long tradeTypeID) {
		this.tradeTypeID = tradeTypeID;
	}

	public Long getOrgRoleID() {
		return orgRoleID;
	}

	public void setOrgRoleID(Long orgRoleID) {
		this.orgRoleID = orgRoleID;
	}

	public List<Long> getMenuidList() {
		return menuidList;
	}

	public void setMenuidList(List<Long> menuidList) {
		this.menuidList = menuidList;
	}

	public Set<Long> getPermidSet() {
		return permidSet;
	}

	public void setPermidSet(Set<Long> permidSet) {
		this.permidSet = permidSet;
	}

	public Long getServiceLevelID() {
		return serviceLevelID;
	}

	public void setServiceLevelID(Long serviceLevelID) {
		this.serviceLevelID = serviceLevelID;
	}

	public Long getProvinceID() {
		return provinceID;
	}

	public void setProvinceID(Long provinceID) {
		this.provinceID = provinceID;
	}

	public Long getAreaID() {
		return areaID;
	}

	public void setAreaID(Long areaID) {
		this.areaID = areaID;
	}

	public Long getCountyID() {
		return countyID;
	}

	public void setCountyID(Long countyID) {
		this.countyID = countyID;
	}

	public boolean isProvinceLogin() {
		return provinceLogin;
	}

	public void setProvinceLogin(boolean provinceLogin) {
		this.provinceLogin = provinceLogin;
	}

	public Long getBelongID() {
		return belongID;
	}

	public void setBelongID(Long belongID) {
		this.belongID = belongID;
	}

	public Long getOrgTypeID() {
		return orgTypeID;
	}

	public void setOrgTypeID(Long orgTypeID) {
		this.orgTypeID = orgTypeID;
	}

	public Long getProvinceOrgID() {
		return provinceOrgID;
	}

	public void setProvinceOrgID(Long provinceOrgID) {
		this.provinceOrgID = provinceOrgID;
	}

	public String getOrgTel() {
		return orgTel;
	}

	public void setOrgTel(String orgTel) {
		this.orgTel = orgTel;
	}

	public String getRolesName() {
		return rolesName;
	}

	public void setRolesName(String rolesName) {
		this.rolesName = rolesName;
	}

	public String getInner() {
		return inner;
	}

	public void setInner(String inner) {
		this.inner = inner;
	}

	public String getVirtualhall() {
		return virtualhall;
	}

	public void setVirtualhall(String virtualhall) {
		this.virtualhall = virtualhall;
	}

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public long getOperId() {
		return operId;
	}

	public void setOperId(long operId) {
		this.operId = operId;
	}

	public Set<Long> getRoleIdSet() {
		return roleIdSet;
	}

	public void setRoleIdSet(Set<Long> roleIdSet) {
		this.roleIdSet = roleIdSet;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Long getOServiceLevelId() {
		return OServiceLevelId;
	}

	public void setOServiceLevelId(Long serviceLevelId) {
		OServiceLevelId = serviceLevelId;
	}

	public Long getOAreaId() {
		return OAreaId;
	}

	public void setOAreaId(Long areaId) {
		OAreaId = areaId;
	}

	public Long getParentOrgID() {
		return parentOrgID;
	}

	public void setParentOrgID(Long parentOrgID) {
		this.parentOrgID = parentOrgID;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getOrgacode() {
		return orgacode;
	}

	public void setOrgacode(String orgacode) {
		this.orgacode = orgacode;
	}

	public Long getParentFillOrgId() {
		return parentFillOrgId;
	}

	public void setParentFillOrgId(Long parentFillOrgId) {
		this.parentFillOrgId = parentFillOrgId;
	}

	public String getManualNo_ch() {
		return manualNo_ch;
	}

	public void setManualNo_ch(String manualNo_ch) {
		this.manualNo_ch = manualNo_ch;
	}
}
