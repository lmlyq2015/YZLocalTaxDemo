package com.vos;

public class PaySearchVO {
	private Long id;
	private String taxId;
	private String taxName;
	private String taxAgentName;
	private String taxAgentMobile;
	private String adminName;
	private String adminMobile;
	private String rep;
	private String repMobile;
	private String startTime;
	private String endTime;
	private String totalTax;
	private String imposeType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(String totalTax) {
		this.totalTax = totalTax;
	}

	public String getImposeType() {
		return imposeType;
	}

	public void setImposeType(String imposeType) {
		this.imposeType = imposeType;
	}

}
