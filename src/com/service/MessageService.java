package com.service;

import java.sql.SQLException;

import com.vos.Message;

public interface MessageService {

	public int sendNotificationMsg(Message msg) throws SQLException;
}
