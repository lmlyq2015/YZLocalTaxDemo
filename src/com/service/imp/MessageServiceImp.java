package com.service.imp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dao.MessageDao;
import com.service.MessageService;
import com.util.DateUtils;
import com.util.TaxUtil;
import com.vos.Message;
import com.vos.MessageResult;
import com.vos.NotificationVo;
@Service
public class MessageServiceImp implements MessageService {

	private MessageDao messageDao;

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public int sendNotificationMsg(Message msg) throws SQLException {
			int id = 0;
		try {
			String result = null;
			MessageResult mr = null;
			int key = messageDao.saveMessage(msg);
			String msgContent = msg.getContent();
			String sendDate = msg.getSendDate();
			List<NotificationVo> list = msg.getVoList();
			msg.setId(key);
			for (NotificationVo vo : list) {
				//1办税员
				 result = TaxUtil.sendMessage(msgContent, vo.getTaxerMob(), sendDate);
				 mr = TaxUtil.parseResult(result);
//				 if (mr.getErrid() != TaxUtil.MESSAGE_STATUS_SUCCESS) {//发送失败则继续发送下一人
//					 //2财务主管
//					 result = TaxUtil.sendMessage(msgContent, vo.getAdminMob(), sendDate);
//					 mr = TaxUtil.parseResult(result);
//					 if (mr.getErrid() != TaxUtil.MESSAGE_STATUS_SUCCESS) {
//						 //3法人
//						 result = TaxUtil.sendMessage(msgContent, vo.getLawRepMob(), sendDate);
//						 mr = TaxUtil.parseResult(result);
//					 }
//				 }
				 vo.setStatus(mr.getErrid());			 
//				 if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_SUCCESS)) {
//					 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_SUCCESS_MSG);
//				 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_SYSTEM_ISSUE)) {
//					 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_SYSTEM_ISSUE_MSG);
//				 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_PASSWORD_ISSUE)) {
//					 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_PASSWORD_ISSUE_MSG);
//				 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_MOBILE_ISSUE)) {
//					 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_MOBILE_ISSUE_MSG);
//				 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE)) {
//					 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE_MSG);
//				 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_CONTENT_CHAR_ISSUE_MSG)) {
//					 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_CONTENT_CHAR_ISSUE_MSG);
//				 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_BALANCE_ISSUE)) {
//					 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_BALANCE_ISSUE_MSG);
//				 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_ACCOUNT_ISSUE)){
//					 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_ACCOUNT_ISSUE_MSG);
//				 } else {
//					 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_UNKNOW_MSG);
//				 }
				 setResultMsg(vo, mr);
				 vo.setReceiver(TaxUtil.MESSAGE_RECEVIER_TAXER);
				 id = messageDao.saveMsgResult(key, vo, msg.getSendDate());				 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return id;
	}

	@Override
	public List getMessageResultList(int firstRow, int pageSize)
			throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.getMessageResultList(firstRow, pageSize);
	}

	@Override
	public int getMessageResultCount() throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.getMessageResultCount();
	}

	@Override
	public List<NotificationVo> getFailMsgStateList(int firstRow, int pageSize,
			int msgId) throws SQLException {
		return messageDao.getFailMsgStateList(firstRow, pageSize, msgId);
	}

	@Override
	public int reSendMsg(Message msg, NotificationVo vo) throws SQLException {
		try {
			String result = null;
			String receiver = vo.getReceiver();
			String mobile = null;
			MessageResult mr = null;
			String oldErrCode = vo.getStatus();
			int id = 0;
			if (receiver.equals(TaxUtil.MESSAGE_RECEVIER_TAXER)) {
				mobile = vo.getTaxerMob();
			} else if (receiver.equals(TaxUtil.MESSAGE_RECEVIER_ADMIN)) {
				mobile = vo.getAdminMob();
			} else if (receiver.equals(TaxUtil.MESSAGE_RECEVIER_LAWER)) {
				mobile = vo.getLawRepMob();
			}
			result = TaxUtil.sendMessage(msg.getContent(), mobile,
					DateUtils.getNowTime());
			mr = TaxUtil.parseResult(result);
			vo.setStatus(mr.getErrid());	
			setResultMsg(vo, mr);
			id = messageDao.updateMsgResult(msg.getId(), vo, DateUtils.getNowTime(),oldErrCode);
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	public NotificationVo setResultMsg(NotificationVo vo,MessageResult mr) {
		 if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_SUCCESS)) {
			 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_SUCCESS_MSG);
		 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_SYSTEM_ISSUE)) {
			 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_SYSTEM_ISSUE_MSG);
		 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_PASSWORD_ISSUE)) {
			 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_PASSWORD_ISSUE_MSG);
		 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_MOBILE_ISSUE)) {
			 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_MOBILE_ISSUE_MSG);
		 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE)) {
			 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE_MSG);
		 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_CONTENT_CHAR_ISSUE_MSG)) {
			 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_CONTENT_CHAR_ISSUE_MSG);
		 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_BALANCE_ISSUE)) {
			 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_BALANCE_ISSUE_MSG);
		 } else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_ACCOUNT_ISSUE)){
			 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_ACCOUNT_ISSUE_MSG);
		 } else {
			 vo.setResultMsg(TaxUtil.MESSAGE_STATUS_UNKNOW_MSG);
		 }
		 return vo;
	}
}
