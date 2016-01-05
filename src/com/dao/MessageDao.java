package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.vos.Message;
import com.vos.MessageSearchVO;
import com.vos.NotificationVo;

public interface MessageDao {

	public int saveMessage(Message msg) throws SQLException;
	
	public int saveMsgResult(int msgKey,NotificationVo vo,String sendDate) throws SQLException;
	
	public List<Message> getMessageResultList(int firstRow, int pageSize,Message message) throws SQLException;

	public int getMessageResultCount(Message message) throws SQLException;
	
	public List<NotificationVo> getFailMsgStateList(int firstRow, int pageSize, int msgId) throws SQLException;
	
	public int updateMsgResult(int msgKey,NotificationVo vo,String sendDate,String oldErrCode) throws SQLException;

	public List<MessageSearchVO> getAllComp(int firstRow, Integer pageSize,
			MessageSearchVO messageSearchVO) throws SQLException;

	public int getCompCount(MessageSearchVO messageSearchVO) throws SQLException;
}
