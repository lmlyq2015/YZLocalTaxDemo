package com.service;

import java.sql.SQLException;
import java.util.List;

import com.vos.Message;

public interface MessageService {

	public int sendNotificationMsg(Message msg) throws SQLException;
	
	public List getMessageResultList(int firstRow, int pageSize) throws SQLException;

	public int getMessageResultCount() throws SQLException;
}
