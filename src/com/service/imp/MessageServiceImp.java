package com.service.imp;

import com.dao.MessageDao;
import com.service.MessageService;

public class MessageServiceImp implements MessageService {

	private MessageDao messageDao;

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}
}
