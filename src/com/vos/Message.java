package com.vos;

import java.util.List;

public class Message {

	private int id;
	private String content;
	private String sign;
	private String sendDate;
	private List<NotificationVo> voList;
	private String sendBy;
	private String msgType;
	private int successCount;
	private String sendDateEnd;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSendDateEnd() {
		return sendDateEnd;
	}
	public void setSendDateEnd(String sendDateEnd) {
		this.sendDateEnd = sendDateEnd;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	private int failCount;
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
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
	public List<NotificationVo> getVoList() {
		return voList;
	}
	public void setVoList(List<NotificationVo> voList) {
		this.voList = voList;
	}
	public String getSendBy() {
		return sendBy;
	}
	public void setSendBy(String sendBy) {
		this.sendBy = sendBy;
	}
	
}
