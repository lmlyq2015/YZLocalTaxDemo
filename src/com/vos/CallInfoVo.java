package com.vos;

import java.util.List;

public class CallInfoVo {

	private String callSheetId;
	private String originCalledNo;
	private String originCallNo;
	private String callerProvince;
	private String callerCity;
	private String RingTime;
	private String offeringTime;
	private String callType;
	private String status; //default notDeal
	private String recordFile;
	private String callId;
	private String agent;
	private String beginTime;
	private String endTime;
	private String satisfactionDegree;//满意度
	public String getSatisfactionDegree() {
		return satisfactionDegree;
	}
	public void setSatisfactionDegree(String satisfactionDegree) {
		this.satisfactionDegree = satisfactionDegree;
	}
	private List<Consults> questions;
	public String getCallSheetId() {
		return callSheetId;
	}
	public void setCallSheetId(String callSheetId) {
		this.callSheetId = callSheetId;
	}
	public String getOriginCalledNo() {
		return originCalledNo;
	}
	public void setOriginCalledNo(String originCalledNo) {
		this.originCalledNo = originCalledNo;
	}
	public String getOriginCallNo() {
		return originCallNo;
	}
	public void setOriginCallNo(String originCallNo) {
		this.originCallNo = originCallNo;
	}
	public String getCallerProvince() {
		return callerProvince;
	}
	public void setCallerProvince(String callerProvince) {
		this.callerProvince = callerProvince;
	}
	public String getCallerCity() {
		return callerCity;
	}
	public void setCallerCity(String callerCity) {
		this.callerCity = callerCity;
	}
	public String getRingTime() {
		return RingTime;
	}
	public void setRingTime(String ringTime) {
		RingTime = ringTime;
	}
	public String getOfferingTime() {
		return offeringTime;
	}
	public void setOfferingTime(String offeringTime) {
		this.offeringTime = offeringTime;
	}
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRecordFile() {
		return recordFile;
	}
	public void setRecordFile(String recordFile) {
		this.recordFile = recordFile;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public List<Consults> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Consults> questions) {
		this.questions = questions;
	}
}
