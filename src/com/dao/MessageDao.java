package com.dao;

import java.sql.SQLException;

import com.vos.Message;
import com.vos.NotificationVo;

public interface MessageDao {

	public int saveMessage(Message msg) throws SQLException;
	
	public int saveMsgResult(int msgKey,NotificationVo vo,String sendDate) throws SQLException;
}
