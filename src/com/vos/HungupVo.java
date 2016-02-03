package com.vos;

public class HungupVo {

	private String CallSheetID;
	private String RingTime;
	private String CallType;
	private String State; // default notDeal
	private String RecordFile;
	private String Agent;
	private String Begin;
	private String End;
	private String satisfactionDegree;// 满意度

	public String getCallSheetID() {
		return CallSheetID;
	}

	public void setCallSheetID(String callSheetID) {
		CallSheetID = callSheetID;
	}

	public String getRingTime() {
		return RingTime;
	}

	public void setRingTime(String ringTime) {
		RingTime = ringTime;
	}

	public String getCallType() {
		return CallType;
	}

	public void setCallType(String callType) {
		CallType = callType;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getAgent() {
		return Agent;
	}

	public void setAgent(String agent) {
		Agent = agent;
	}

	public String getRecordFile() {
		return RecordFile;
	}

	public void setRecordFile(String recordFile) {
		RecordFile = recordFile;
	}

	public String getBegin() {
		return Begin;
	}

	public void setBegin(String begin) {
		Begin = begin;
	}

	public String getEnd() {
		return End;
	}

	public void setEnd(String end) {
		End = end;
	}

	public String getSatisfactionDegree() {
		return satisfactionDegree;
	}

	public void setSatisfactionDegree(String satisfactionDegree) {
		this.satisfactionDegree = satisfactionDegree;
	}

}