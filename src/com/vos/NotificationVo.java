package com.vos;

public class NotificationVo {

	private String taxId;
	private String taxName;
	private String rep;
	private String repMobile;
	private String taxAgentName;
	private String taxAgentMobile;
	private String adminName;
	private String adminMobile;
	private String state;
	private String eid;
	private int mesId;
	
	public int getMesId() {
		return mesId;
	}
	public void setMesId(int mesId) {
		this.mesId = mesId;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	private String resultMsg;
	private String address;
	private String taxAdmin;
	
	public String getTaxAdmin() {
		return taxAdmin;
	}
	public void setTaxAdmin(String taxAdmin) {
		this.taxAdmin = taxAdmin;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getRep() {
		return rep;
	}
	public void setRep(String rep) {
		this.rep = rep;
	}
	public String getRepMobile() {
		return repMobile;
	}
	public void setRepMobile(String repMobile) {
		this.repMobile = repMobile;
	}
	public String getTaxAgentName() {
		return taxAgentName;
	}
	public void setTaxAgentName(String taxAgentName) {
		this.taxAgentName = taxAgentName;
	}
	public String getTaxAgentMobile() {
		return taxAgentMobile;
	}
	public void setTaxAgentMobile(String taxAgentMobile) {
		this.taxAgentMobile = taxAgentMobile;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminMobile() {
		return adminMobile;
	}
	public void setAdminMobile(String adminMobile) {
		this.adminMobile = adminMobile;
	}
	private String receiver;
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getTaxName() {
		return taxName;
	}
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	
}
