package com.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.vos.CallInfoVo;
import com.vos.Consults;
import com.vos.Message;
import com.vos.MessageSearchVO;
import com.vos.NotificationVo;
import com.vos.User;
import com.vos.UserSearchVo;

public interface MessageService {

	public int sendNotificationMsg(Message msg) throws SQLException;
	
	public List<Message> getMessageResultList(int firstRow, int pageSize,Message message) throws SQLException;

	public int getMessageResultCount(Message message) throws SQLException;
	
	public List<NotificationVo> getFailMsgStateList(int firstRow,int pageSize,int msgId) throws SQLException;
	
	public int reSendMsg(Message msg,NotificationVo vo) throws SQLException;
	
	public List<MessageSearchVO> getAllComp(int firstRow, Integer pageSize,
			MessageSearchVO messageSearchVO) throws SQLException;

	public int getCompCount(MessageSearchVO messageSearchVO) throws SQLException;
	
	public User validateUser(User user) throws SQLException;
	
	public List<User> getAllUser(int firstRow, Integer pageSize, UserSearchVo searchVo) throws SQLException;
	
	public int getUserCount(UserSearchVo searchVo) throws SQLException;
	
	public int isExistEmp(String empId) throws SQLException;
	
	public int isExistLoginName(String loginName) throws SQLException;
	
	public int addNewEmp(User user) throws SQLException;
	
	public void saveEmpChanges(List<User> list) throws SQLException;
	
	public int updatePassword(String empId,String newPwd) throws SQLException;

	public int sendNotificationMsgWithURL(Message msg) throws SQLException;
	
	public void saveCallInfoWhenRing(CallInfoVo callvo) throws SQLException;
	
	public int queryCallInfo(String callSheetId) throws SQLException;
	
	public int updateCallInfoWhenRing(CallInfoVo callvo) throws SQLException;
	
	public List<CallInfoVo> getCallList(String account) throws SQLException;
	
	public List<CallInfoVo> getCallInfoByCallNo(String callNo) throws SQLException;
	
	public List<Consults> getConsultInfoByCallSheetNo(String callSheetNo) throws SQLException;
	
	public String getStatusWhenAddConsults(CallInfoVo callvo) throws SQLException;
	
	public void addConsults(String callSheetId,String question, String answer) throws SQLException;

	public void reSendMsg(List<Message> list) throws SQLException;

	public void deleteComp(List<MessageSearchVO> list,HttpServletResponse response) throws SQLException;
	
	public List<Consults> getKnowledgeContent(String title) throws Exception;

	public int selectComp(List<MessageSearchVO> list,HttpServletResponse response) throws SQLException;

}
