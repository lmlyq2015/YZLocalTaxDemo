package com.vos;

import java.util.List;

public class ReportSend {
	private String taxAgentName;
	private String taxName;
	private String taxId;
	private String year;
	private String month;
	private String imposeType;
	private String sendDate;
	private String taxAgentMobile;
	private String adminName;
	private String adminMobile;
	private List <ReportNotificationVo> VoList;
	private String sign;
	private String repMobile;
	private String rep;
	
	
	public String getRep() {
		return rep;
	}
	public void setRep(String rep) {
		this.rep = rep;
	}
	public String getAdminMobile() {
		return adminMobile;
	}
	public void setAdminMobile(String adminMobile) {
		this.adminMobile = adminMobile;
	}
	public String getRepMobile() {
		return repMobile;
	}
	public void setRepMobile(String repMobile) {
		this.repMobile = repMobile;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getTaxAgentMobile() {
		return taxAgentMobile;
	}
	public void setTaxAgentMobile(String taxAgentMobile) {
		this.taxAgentMobile = taxAgentMobile;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public List<ReportNotificationVo> getVoList() {
		return VoList;
	}
	public void setVoList(List<ReportNotificationVo> voList) {
		VoList = voList;
	}
	public String getTaxAgentName() {
		return taxAgentName;
	}
	public void setTaxAgentName(String taxAgentName) {
		this.taxAgentName = taxAgentName;
	}
	public String getTaxName() {
		return taxName;
	}
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
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
	public void setStatus(String errid) {
		// TODO Auto-generated method stub
		
	}
	public void setReceiver(String messageRecevierTaxer) {
		// TODO Auto-generated method stub
		
	}
	public Object getReceiver() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setResultMsg(String messageStatusSuccessMsg) {
		// TODO Auto-generated method stub
		
	}
	
}
