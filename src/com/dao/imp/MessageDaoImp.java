package com.dao.imp;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.ThrowsAdvice;

import com.dao.MessageDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.util.TaxUtil;
import com.vos.Message;
import com.vos.MessageSearchVO;
import com.vos.NotificationVo;
import com.vos.User;
import com.vos.UserSearchVo;

public class MessageDaoImp implements MessageDao {

	private SqlMapClient sqlMapClient;

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	@Override
	public int saveMessage(Message msg) throws SQLException {
		int key = 0;
		try{
			msg.setContent(msg.getContent() + " " + msg.getSign());
			msg.setMsgType(TaxUtil.MESSAGE_NOTIFICATION_MESSAGE_TYPE);		
			key = (Integer) sqlMapClient.insert("saveMsg",msg);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return key;

	}

	@Override
	public int saveMsgResult(int msgKey, NotificationVo vo,String sendDate)
			throws SQLException {
		int id = 0;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mesId", msgKey);
			map.put("taxId", vo.getTaxId());
			map.put("status", vo.getState());
			map.put("msg", vo.getResultMsg());
			map.put("empId", "admin");
			map.put("receiver", vo.getReceiver());
			if (sendDate == null || sendDate.equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sendDate = sdf.format(new Date()).toString();
			}
			map.put("sendDate", sendDate);
			map.put("Id", 0);
			sqlMapClient.insert("saveMsgResult",map);
			return (Integer) map.get("Id");
		}catch(SQLException e){
			e.printStackTrace();
			//throw e;
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getMessageResultList(int firstRow, int pageSize,Message message)
			throws SQLException {
		try{
		Map<String,Object> map = new HashMap<String, Object>();
		List<Message> list = new ArrayList<Message>();
		map.put("id", message.getId());
		map.put("content", message.getContent());
		map.put("sendDate", message.getSendDate());
		map.put("successCount", message.getSuccessCount());
		map.put("failCount", message.getFailCount());
		map.put("sendBy", message.getSendBy());
		map.put("operate", message.getMsgType());	
		map.put("beginRow", firstRow);
		map.put("pageSize", pageSize);
		list = sqlMapClient.queryForList("getMessageResultList",map);
		return list;
	}catch(SQLException e) {
		e.printStackTrace();
		throw e;
	}
	// TODO Auto-generated method stub
}

	@Override
	public int getMessageResultCount(Message message) throws SQLException {
		int count = 0;
		count = (Integer) sqlMapClient.queryForObject("getMessageResultCount");
		return count;
	}

	@Override
	public List<NotificationVo> getFailMsgStateList(int firstRow, int pageSize,
			int msgId) throws SQLException {
		List<NotificationVo> list = null;
		Map<String,Object> map = new HashMap<String,Object>();
		//int endRow = pageSize+firstRow;
		map.put("beginRow", firstRow);
		map.put("pageSize", pageSize);
		map.put("msgId",msgId);
		list = sqlMapClient.queryForList("getFailMsgStateList",map);
		return list;
	}

	@Override
	public int updateMsgResult(int msgKey, NotificationVo vo, String sendDate,String oldErrCode)
			throws SQLException {
		int id = 0;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mesId", msgKey);
			map.put("errCode", oldErrCode);
			map.put("status", vo.getState());
			map.put("msg", vo.getResultMsg());
			map.put("empId", "admin");
			map.put("receiver", vo.getReceiver());
			map.put("sendDate", sendDate);
			id = sqlMapClient.update("updateMsgResult",map);
			return id;
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}
		//return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageSearchVO> getAllComp(int firstRow, Integer pageSize,
			MessageSearchVO messageSearchVO) throws SQLException {
		// TODO Auto-generated method stub
		try{
			Map<String,Object> map = new HashMap<String, Object>();
			List<MessageSearchVO> list = new ArrayList<MessageSearchVO>();
			map.put("taxId", messageSearchVO.getTaxId());
			map.put("taxName", messageSearchVO.getTaxName());
			map.put("taxAgentName", messageSearchVO.getTaxAgentName());
			map.put("taxAgentMobile", messageSearchVO.getTaxAgentMobile());
			map.put("adminName", messageSearchVO.getAdminName());
			map.put("adminMobile", messageSearchVO.getAdminMobile());
			map.put("rep", messageSearchVO.getRep());
			map.put("repMobile", messageSearchVO.getRepMobile());
			map.put("address", messageSearchVO.getAddress());
			map.put("state", messageSearchVO.getState());
			map.put("eid", messageSearchVO.getEid());
			map.put("firstRow",firstRow);
			map.put("pageSize",pageSize);
			
			list = sqlMapClient.queryForList("getAllComp",map);
			return list;

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int getCompCount(MessageSearchVO messageSearchVO)
			throws SQLException {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject("getCompCount",messageSearchVO);
	}

	@Override
	public User validateUser(User user) throws SQLException {
		return (User) sqlMapClient.queryForObject("validateUser",user);
	}

	@Override
	public List<User> getAllUser(int firstRow, Integer pageSize,
			UserSearchVo searchVo) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> list = new ArrayList<User>();
		try {
			map.put("empId", searchVo.getEmpId());
			map.put("loginName", searchVo.getLoginName());
			map.put("beginDate", searchVo.getBeginDate());
			map.put("endDate", searchVo.getEndDate());
			map.put("firstRow",firstRow);
			map.put("pageSize",pageSize);
			list = sqlMapClient.queryForList("getAllUser", map);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int getUserCount(UserSearchVo searchVo) throws SQLException {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject("getUserCount",searchVo);

	}

	@Override
	public int isExistEmp(String empId) throws SQLException {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject("isExistEmp",empId);
	}

	@Override
	public int isExistLoginName(String loginName) throws SQLException {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject("isExistLoginName",loginName);
	}

	@Override
	public int addNewEmp(User user) throws SQLException {
		// TODO Auto-generated method stub
		int result = 0;
		result = (Integer) sqlMapClient.update("addNewEmp",user);
		return result;
	}

	@Override
	public void saveEmpChanges(List<User> list) throws SQLException {
		try{
			sqlMapClient.startBatch();
			for(User user : list) {
				
				sqlMapClient.update("saveEmpChanges",user);
			}
			sqlMapClient.executeBatch(); 
		} catch(Exception e) {
			e.printStackTrace();
		}

		
	}
}
