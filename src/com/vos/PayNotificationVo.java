package com.vos;

public class PayNotificationVo {
	private String taxId;
	private String taxName;
	private String rep;
	private String repMobile;
	private String taxAgentName;
	private String taxAgentMobile;
	private String adminName;
	private String adminMobile;
	private int mesId;
	private String deadline;
	private Float unpaidTax;
	private String imposeType;
	private String status;
	private String resultMsg;
	private String address;
	private String receiver;
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getTaxName() {
		return taxName;
	}
	public void setTaxName(String taxName) {
		this.taxName = taxName;
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
	public int getMesId() {
		return mesId;
	}
	public void setMesId(int mesId) {
		this.mesId = mesId;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public Float getUnpaidTax() {
		return unpaidTax;
	}
	public void setUnpaidTax(Float unpaidTax) {
		this.unpaidTax = unpaidTax;
	}
	public String getImposeType() {
		return imposeType;
	}
	public void setImposeType(String imposeType) {
		this.imposeType = imposeType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
}
