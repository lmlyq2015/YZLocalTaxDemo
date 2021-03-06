package com.vos;

public class ReportNotificationVo {
	private Long id;
	private String taxId;
	private String taxName;
	private String rep;
	private String repMobile;
	private String taxAgentName;
	private String taxAgentMobile;
	private String adminName;
	private int mesId;
	private String empId;
	private String startTime;
	private String endTime;
	
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getMesId() {
		return mesId;
	}
	public void setMesId(int mesId) {
		this.mesId = mesId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	private String adminMobile;
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
	public String getAdminMobile() {
		return adminMobile;
	}
	public void setAdminMobile(String adminMobile) {
		this.adminMobile = adminMobile;
	}
	private String year;
	private String month;
	private String imposeType;
	private String status;
	private String resultMsg;
	private String address;
	private String receiver;
	public String getTaxName() {
		return taxName;
	}
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
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
