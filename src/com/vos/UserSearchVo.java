package com.vos;

public class UserSearchVo {
	private String empId;
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	private String loginName;
	private String beginDate;
	private String endDate;
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
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
	public String getSendToSelf() {
		return sendToSelf;
	}
	public void setSendToSelf(String sendToSelf) {
		this.sendToSelf = sendToSelf;
	}
	private String email;
	private String contact;
	private String sendToSelf;

}
