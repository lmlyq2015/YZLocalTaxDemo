package com.service;

import java.sql.SQLException;
import java.util.List;

import com.vos.Message;
import com.vos.NotificationVo;

public interface MessageService {

	public int sendNotificationMsg(Message msg) throws SQLException;
	
	public List getMessageResultList(int firstRow, int pageSize) throws SQLException;

	public int getMessageResultCount() throws SQLException;
	
	public List<NotificationVo> getFailMsgStateList(int firstRow,int pageSize,int msgId) throws SQLException;
	
	public int reSendMsg(Message msg,NotificationVo vo) throws SQLException;
}
