package com.vos;

import java.util.Date;

public class Pay {
	private Long id;
	private String taxId;
	private String imposeType;
	private String paymentDates;
	private String deadline;
	private Float totaleTax;
	private Float paidTax;
	private Float unpaidTax;
	private String pushDate;
	
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
	
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public Float getTotaleTax() {
		return totaleTax;
	}
	public void setTotaleTax(Float totaleTax) {
		this.totaleTax = totaleTax;
	}
	public Float getPaidTax() {
		return paidTax;
	}
	public void setPaidTax(Float paidTax) {
		this.paidTax = paidTax;
	}
	public Float getUnpaidTax() {
		return unpaidTax;
	}
	public void setUnpaidTax(Float unpaidTax) {
		this.unpaidTax = unpaidTax;
	}
	public String getPaymentDates() {
		return paymentDates;
	}
	public void setPaymentDates(String paymentDates) {
		this.paymentDates = paymentDates;
	}
	public String getPushDate() {
		return pushDate;
	}
	public void setPushDate(String pushDate) {
		this.pushDate = pushDate;
	}

	
}
