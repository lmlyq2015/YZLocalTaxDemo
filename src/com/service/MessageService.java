package com.service;

import java.sql.SQLException;
import java.util.List;

import com.vos.Message;
import com.vos.MessageSearchVO;
import com.vos.NotificationVo;
import com.vos.User;

public interface MessageService {

	public int sendNotificationMsg(Message msg) throws SQLException;
	
	public List getMessageResultList(int firstRow, int pageSize) throws SQLException;

	public int getMessageResultCount() throws SQLException;
	
	public List<NotificationVo> getFailMsgStateList(int firstRow,int pageSize,int msgId) throws SQLException;
	
	public int reSendMsg(Message msg,NotificationVo vo) throws SQLException;
	
	public List<MessageSearchVO> getAllComp(int firstRow, Integer pageSize,
			MessageSearchVO messageSearchVO) throws SQLException;

	public int getCompCount(MessageSearchVO messageSearchVO) throws SQLException;
	
	public User validateUser(User user) throws SQLException;
}
