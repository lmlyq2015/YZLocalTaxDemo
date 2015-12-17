package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.vos.Message;
import com.vos.NotificationVo;

public interface MessageDao {

	public int saveMessage(Message msg) throws SQLException;
	
	public int saveMsgResult(int msgKey,NotificationVo vo,String sendDate) throws SQLException;
	
	public List getMessageResultList(int firstRow, int pageSize) throws SQLException;

	public int getMessageResultCount() throws SQLException;
	
	public List<NotificationVo> getFailMsgStateList(int firstRow, int pageSize, int msgId) throws SQLException;
	
	public int updateMsgResult(int msgKey,NotificationVo vo,String sendDate,String oldErrCode) throws SQLException;
}
