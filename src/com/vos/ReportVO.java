package com.vos;

import java.util.List;

public class ReportVO {
	private int id;
	private String content;
	private String sign;
	private String sendDate;
	private List<ReportNotificationVo> voList;
	private String sendBy;
	private String msgType;
	private int successCount;
	private String mobile;
	private String sendToSelf;
	
	public String getSendToSelf() {
		return sendToSelf;
	}
	public void setSendToSelf(String sendToSelf) {
		this.sendToSelf = sendToSelf;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
		return voList;
	}
	public void setVoList(List<ReportNotificationVo> voList) {
		this.voList = voList;
	}
	public String getSendBy() {
		return sendBy;
	}
	public void setSendBy(String sendBy) {
		this.sendBy = sendBy;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	
}
