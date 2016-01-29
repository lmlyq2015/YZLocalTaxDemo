package com.service.imp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dao.MessageDao;
import com.service.MessageService;
import com.util.DateUtils;
import com.util.TaxUtil;
import com.vos.Message;
import com.vos.MessageResult;
import com.vos.MessageSearchVO;
import com.vos.NotificationVo;
import com.vos.User;
import com.vos.UserSearchVo;

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
			String sendDate = DateUtils.getNowTime();
			List<NotificationVo> list = msg.getVoList();
			msg.setId(key);
			for (NotificationVo vo : list) {
				// 1办税员
				result = TaxUtil.sendMessage(vo.getTaxName() + " : "
						+ msgContent, vo.getTaxAgentMobile(), sendDate);
				mr = TaxUtil.parseResult(result);
				vo.setState(mr.getErrid());
				setResultMsg(vo, mr);
				vo.setReceiver(TaxUtil.MESSAGE_RECEVIER_TAXER);
				id = messageDao.saveMsgResult(key, vo, sendDate,
						msg.getSendBy());
			}
			String sendToSelf = msg.getSendToSelf();
			if (sendToSelf != null && sendToSelf.equals("是")) {
				TaxUtil.sendMessage("通知于" + sendDate + "发送成功！",
						msg.getMobile(), sendDate);
			}

			// TaxUtil.creatTxtFile();
			// boolean bo = TaxUtil.writeTxtFile(msgContent);
			// if (!bo) {
			// TaxUtil.writeTxtFile("未插入测试数据");
			// }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public List<Message> getMessageResultList(int firstRow, int pageSize,
			Message message) throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.getMessageResultList(firstRow, pageSize, message);
	}

	@Override
	public int getMessageResultCount(Message message) throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.getMessageResultCount(message);
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
			String oldErrCode = vo.getState();
			int id = 0;
			if (receiver.equals(TaxUtil.MESSAGE_RECEVIER_TAXER)) {
				mobile = vo.getTaxAgentMobile();
			} else if (receiver.equals(TaxUtil.MESSAGE_RECEVIER_ADMIN)) {
				mobile = vo.getAdminMobile();
			} else if (receiver.equals(TaxUtil.MESSAGE_RECEVIER_LAWER)) {
				mobile = vo.getRepMobile();
			}
			result = TaxUtil.sendMessage(msg.getContent(), mobile,
					DateUtils.getNowTime());
			mr = TaxUtil.parseResult(result);
			vo.setState(mr.getErrid());
			setResultMsg(vo, mr);
			id = messageDao.updateMsgResult(msg.getId(), vo,
					DateUtils.getNowTime(), oldErrCode, msg.getSendBy());
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public NotificationVo setResultMsg(NotificationVo vo, MessageResult mr) {
		if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_SUCCESS)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_SUCCESS_MSG);
		} else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_SYSTEM_ISSUE)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_SYSTEM_ISSUE_MSG);
		} else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_PASSWORD_ISSUE)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_PASSWORD_ISSUE_MSG);
		} else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_MOBILE_ISSUE)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_MOBILE_ISSUE_MSG);
		} else if (mr.getErrid().equals(
				TaxUtil.MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE_MSG);
		} else if (mr.getErrid().equals(
				TaxUtil.MESSAGE_STATUS_CONTENT_CHAR_ISSUE_MSG)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_CONTENT_CHAR_ISSUE_MSG);
		} else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_BALANCE_ISSUE)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_BALANCE_ISSUE_MSG);
		} else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_ACCOUNT_ISSUE)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_ACCOUNT_ISSUE_MSG);
		} else {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_UNKNOW_MSG);
		}
		return vo;
	}

	@Override
	public List<MessageSearchVO> getAllComp(int firstRow, Integer pageSize,
			MessageSearchVO messageSearchVO) throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.getAllComp(firstRow, pageSize, messageSearchVO);
	}

	@Override
	public int getCompCount(MessageSearchVO messageSearchVO)
			throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.getCompCount(messageSearchVO);
	}

	@Override
	public User validateUser(User user) throws SQLException {
		// TODO Auto-generated method stub
		user = messageDao.validateUser(user);
		if (user != null) {
			messageDao.updateLoginDate(user);
		}
		return user;
	}

	@Override
	public List<User> getAllUser(int firstRow, Integer pageSize,
			UserSearchVo searchVo) throws SQLException {
		return messageDao.getAllUser(firstRow, pageSize, searchVo);
	}

	@Override
	public int getUserCount(UserSearchVo searchVo) throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.getUserCount(searchVo);
	}

	@Override
	public int isExistEmp(String empId) throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.isExistEmp(empId);
	}

	@Override
	public int isExistLoginName(String loginName) throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.isExistLoginName(loginName);
	}

	@Override
	public int addNewEmp(User user) throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.addNewEmp(user);
	}

	@Override
	public void saveEmpChanges(List<User> list) throws SQLException {

		messageDao.saveEmpChanges(list);
	}

	@Override
	public int updatePassword(String empId, String newPwd) throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.updatePassword(empId, newPwd);
	}

	@Override
	public int sendNotificationMsgWithURL(Message msg) throws SQLException {
		// TODO Auto-generated method stub
		int id = 0;
		try {
			String result = null;
			MessageResult mr = null;
			String sendDate = DateUtils.getNowTime();
			List<NotificationVo> list = msg.getVoList();

			for (NotificationVo vo : list) {
				msg.setTaxName(vo.getTaxName());
				int key = messageDao.saveMessage(msg);
				vo.setMesId(key);
				msg.setId(key);
				result = TaxUtil
						.sendMessage(
								vo.getTaxName() + " : "
										+ TaxUtil.getMessageContent(vo),
								vo.getTaxAgentMobile(), sendDate);
				mr = TaxUtil.parseResult(result);
				vo.setState(mr.getErrid());
				setResultMsg(vo, mr);
				vo.setReceiver(TaxUtil.MESSAGE_RECEVIER_TAXER);
				id = messageDao.saveMsgResult(key, vo, sendDate,
						msg.getSendBy());
			}
			String sendToSelf = msg.getSendToSelf();
			if (sendToSelf != null && sendToSelf.equals("是")) {
				TaxUtil.sendMessage("通知于" + sendDate + "发送成功！",
						msg.getMobile(), sendDate);
			}
			// TaxUtil.creatTxtFile();
			// boolean bo;
			// for (NotificationVo vo : list) {
			// bo = TaxUtil.writeTxtFile(vo.getTaxName() + " : "
			// + TaxUtil.getMessageContent(vo));
			// if (!bo) {
			// TaxUtil.writeTxtFile("未插入测试数据");
			// }
			// }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
}
