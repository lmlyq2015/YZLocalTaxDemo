package com.vos;



public class User {
	private String empId;
	private String loginName;
	private String password;
	private String lastLoginDate;
	private String email;
	private String callCenterAccount;
	private String callCenterPwd;
	public String getCallCenterAccount() {
		return callCenterAccount;
	}
	public void setCallCenterAccount(String callCenterAccount) {
		this.callCenterAccount = callCenterAccount;
	}
	public String getCallCenterPwd() {
		return callCenterPwd;
	}
	public void setCallCenterPwd(String callCenterPwd) {
		this.callCenterPwd = callCenterPwd;
	private String mobile;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSendToSelf() {
		return sendToSelf;
	}
	public void setSendToSelf(String sendToSelf) {
		this.sendToSelf = sendToSelf;
	}
	private String contact;
	private int roleId;
	private String sendToSelf;
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
