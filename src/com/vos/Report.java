package com.vos;

import java.util.Date;

public class Report {
	private Long id;
	private String taxId;
	private String imposeType;
	private String startTime;
	private String endTime;
	
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
	public String getImposeType() {
		return imposeType;
	}
	public void setImposeType(String imposeType) {
		this.imposeType = imposeType;
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
	
}
