package com.vos;

import java.util.List;

public class Message {

	private int id;
	private String content;
	private String sign;
	private String sendDate;
	private List<NotificationVo> voList;
	private String sendBy;
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
