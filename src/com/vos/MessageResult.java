package com.vos;

public class MessageResult {

	private String errid;
	private String msgid;
	private String fails;//失败手机列表
	public String getErrid() {
		return errid;
	}
	public void setErrid(String errid) {
		this.errid = errid;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getFails() {
		return fails;
	}
	public void setFails(String fails) {
		this.fails = fails;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private String msg;
}
