package com.vos;

import java.util.Date;

public class Pay {
	private int id;
	private String taxId;
	private String imposeType;
	private Date paymentDates;
	private String deadline;
	private Float totaleTax;
	private Float paidTax;
	private Float unpaidTax;
	private Date pushDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public Date getPaymentDates() {
		return paymentDates;
	}
	public void setPaymentDates(Date paymentDates) {
		this.paymentDates = paymentDates;
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
	public Date getPushDate() {
		return pushDate;
	}
	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}
	
}
