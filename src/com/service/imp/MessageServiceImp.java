package com.service.imp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.dao.MessageDao;
import com.service.MessageService;
import com.util.DateUtils;
import com.util.TaxUtil;
import com.vos.CallInfoVo;
import com.vos.Consults;
import com.vos.ContactVo;
import com.vos.FoldTree;
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
		int sucNum = 0;
		try {
			String result = null;
			MessageResult mr = null;

			String msgContent = msg.getContent();
			String sendDate = DateUtils.getNowTime();
			List<NotificationVo> list = msg.getVoList();

			for (NotificationVo vo : list) {
				// 1办税员
				int key = messageDao.saveMessage(msg);
				msg.setId(key);
				result = TaxUtil.sendMessage(vo.getTaxName() + " : "
						+ msgContent, vo.getTaxAgentMobile(), sendDate);
				mr = TaxUtil.parseResult(result);
				vo.setState(mr.getErrid());
				setResultMsg(vo, mr);
				vo.setReceiver(TaxUtil.MESSAGE_RECEVIER_TAXER);
				id = messageDao.saveMsgResult(key, vo, sendDate,
						msg.getSendBy());

				boolean a = vo.getResultMsg().equals("发送成功");
				if (a) {
					sucNum = sucNum + 1;
				}
			}

			if (sucNum != 0) {
				String sendToSelf = msg.getSendToSelf();
				if (sendToSelf != null && sendToSelf.equals("是")) {
					TaxUtil.sendMessage("通知于" + sendDate + "发送成功！",
							msg.getMobile(), sendDate);
				}
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

			String type = msg.getMsgType();
			System.out.println(type);
			if (type.equals("1")) {
				result = TaxUtil.sendMessage(msg.getContent(), mobile,
						DateUtils.getNowTime());
			} else if (type.equals("2")) {
				result = TaxUtil
						.sendMessage(
								vo.getTaxName() + " : "
										+ TaxUtil.getMessageContent(vo),
								mobile, DateUtils.getNowTime());
			} else if (type.equals("3")) {
				result = TaxUtil.sendMessage(TaxUtil.getPayContent(vo), mobile,
						DateUtils.getNowTime());
			} else if (type.equals("4")) {
				result = TaxUtil.sendMessage(TaxUtil.getReportContent(vo),
						mobile, DateUtils.getNowTime());
			}
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
		int sucNum = 0;
		try {
			String result = null;
			MessageResult mr = null;
			String sendDate = DateUtils.getNowTime();
			List<NotificationVo> list = msg.getVoList();

			for (NotificationVo vo : list) {
				msg.setTaxName(vo.getTaxName());
				int key = messageDao.saveMessageWithURL(msg);
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
				boolean a = vo.getResultMsg().equals("发送成功");
				if (a) {
					sucNum = sucNum + 1;
				}
			}

			if (sucNum != 0) {
				String sendToSelf = msg.getSendToSelf();
				if (sendToSelf != null && sendToSelf.equals("是")) {
					TaxUtil.sendMessage("通知于" + sendDate + "发送成功！",
							msg.getMobile(), sendDate);
				}
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

	@Override
	public void saveCallInfoWhenRing(CallInfoVo callvo) throws SQLException {
		try {
			messageDao.saveCallInfoWhenRing(callvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int queryCallInfo(String callSheetId) throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.queryCallInfo(callSheetId);
	}

	@Override
	public int updateCallInfoWhenRing(CallInfoVo callvo) throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.updateCallInfoWhenRing(callvo);
	}

	@Override
	public List<CallInfoVo> getCallList(String account) throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.getCallList(account);
	}

	@Override
	public List<CallInfoVo> getCallInfoByCallNo(String callNo,int firstRow, int pageSize)
			throws SQLException {

		return messageDao.getCallInfoByCallNo(callNo,firstRow, pageSize);
	}

	@Override
	public List<Consults> getConsultInfoByCallSheetNo(String callSheetNo)
			throws SQLException {
		// TODO Auto-generated method stub
		return messageDao.getConsultInfoByCallSheetNo(callSheetNo);
	}

	@Override
	public String getStatusWhenAddConsults(CallInfoVo callvo)
			throws SQLException {
		return messageDao.getStatusWhenAddConsults(callvo);
	}

	@Override
	public void addConsults(String callSheetId, String question, String answer)
			throws SQLException {
		messageDao.addConsults(callSheetId, question, answer);
	}

	@Override
	public void reSendMsg(List<Message> list) throws SQLException {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getFailCount() == 0) { // 状态成功的跳过
					continue;
				}
				List<NotificationVo> listNo = messageDao.getReSendList(list
						.get(i).getId()); // 后台读取失败短信信息
				String result = null;
				String receiver = list.get(i).getReceiver();
				listNo.get(0).setReceiver(receiver);
				String mobile = null;
				MessageResult mr = null;
				String oldErrCode = listNo.get(0).getState();
				int id = 0;
				if (receiver.equals(TaxUtil.MESSAGE_RECEVIER_TAXER)) {
					mobile = listNo.get(0).getTaxAgentMobile();
				} else if (receiver.equals(TaxUtil.MESSAGE_RECEVIER_ADMIN)) {
					mobile = listNo.get(0).getAdminMobile();
				} else if (receiver.equals(TaxUtil.MESSAGE_RECEVIER_LAWER)) {
					mobile = listNo.get(0).getRepMobile();
				}

				String type = listNo.get(0).getMsgType();
				System.out.println(type);
				if (type.equals("1")) {
					result = TaxUtil.sendMessage(list.get(i).getContent(),
							mobile, DateUtils.getNowTime());
				} else if (type.equals("2")) {
					result = TaxUtil.sendMessage(listNo.get(0).getTaxName()
							+ " : " + TaxUtil.getMessageContent(listNo.get(0)),
							mobile, DateUtils.getNowTime());
				} else if (type.equals("3")) {
					result = TaxUtil.sendMessage(
							TaxUtil.getPayContent(listNo.get(0)), mobile,
							DateUtils.getNowTime());
				} else if (type.equals("4")) {
					result = TaxUtil.sendMessage(
							TaxUtil.getReportContent(listNo.get(0)), mobile,
							DateUtils.getNowTime());
				}
				mr = TaxUtil.parseResult(result);
				listNo.get(0).setState(mr.getErrid());
				setResultMsg(listNo.get(0), mr);
				id = messageDao.updateMsgResult(list.get(i).getId(),
						listNo.get(0), DateUtils.getNowTime(), oldErrCode,
						listNo.get(0).getEmpId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteComp(List<MessageSearchVO> list,
			HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			messageDao.deleteState(list.get(i).getTaxId());
			messageDao.deleteComp(list.get(i).getTaxId());
		}
	}

	@Override

	public List<Consults> getKnowledgeContent(String title) throws Exception {
		// TODO Auto-generated method stub
		return messageDao.getKnowledgeContent(title);
	}
	public int selectComp(List<MessageSearchVO> list,
			HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		int id = 0;
		for (int i = 0; i < list.size(); i++) {
			String t = messageDao.selectComp(list.get(i).getTaxId());
			if (t == null) {
				continue;
			} else {
				id += 1;
			}
		}
		return id;
	}

	@Override
	public List<ContactVo> getContactList() throws Exception {
		// TODO Auto-generated method stub
		return messageDao.getContactList();
	}

	@Override
	public int getCallInfoByCallNo(String callNo) throws Exception {
		// TODO Auto-generated method stub
		return messageDao.getCallInfoByCallNo(callNo);
	}

	@Override
	public List<FoldTree> getFoldTree() throws Exception {
		// TODO Auto-generated method stub
		return messageDao.getFoldTree();
	}

	@Override
	public void addNode(FoldTree node) throws Exception {
		// TODO Auto-generated method stub
		messageDao.addNode(node);
		
	}

	@Override
	public void addContentByNode(Consults consult) throws Exception {
		messageDao.addContentByNode(consult);
	}

	@Override
	public List<Consults> getContentByNode(int nodeId, int firstRow,
			int pageSize) throws Exception {
		// TODO Auto-generated method stub
		return messageDao.getContentByNode(nodeId, firstRow, pageSize);
	}

	@Override
	public List<Consults> searchContentByKeyWords(String keywords,
			int firstRow, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		return messageDao.searchContentByKeyWords(keywords, firstRow, pageSize);
	}
}
