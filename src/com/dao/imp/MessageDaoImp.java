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
import com.util.DateUtils;
import com.util.TaxUtil;
import com.vos.CallInfoVo;
import com.vos.Consults;
import com.vos.ContactVo;
import com.vos.FoldTree;
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
		try {
			msg.setMsgType("1");
			key = (Integer) sqlMapClient.insert("saveMsg", msg);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return key;

	}

	@Override
	public int saveMsgResult(int msgKey, NotificationVo vo, String sendDate,
			String sendBy) throws SQLException {
		int id = 0;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mesId", msgKey);
			map.put("taxId", vo.getTaxId());
			map.put("status", vo.getState());
			map.put("msg", vo.getResultMsg());
			map.put("empId", sendBy);
			map.put("receiver", vo.getReceiver());
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			// if (sendDate == null || sendDate.equals("")) {
			// sendDate = sdf.format(new Date()).toString();
			// }
			map.put("sendDate", sendDate);
			map.put("Id", 0);
			sqlMapClient.insert("saveMsgResult", map);
			return (Integer) map.get("Id");
		} catch (SQLException e) {
			e.printStackTrace();
			// throw e;
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getMessageResultList(int firstRow, int pageSize,
			Message message) throws SQLException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Message> list = new ArrayList<Message>();
			map.put("id", message.getId());
			map.put("content", message.getContent());
			map.put("sendDate", message.getSendDate());
			map.put("sendDateEnd", message.getSendDateEnd());
			map.put("status", message.getStatus());
			map.put("successCount", message.getSuccessCount());
			map.put("failCount", message.getFailCount());
			map.put("sendBy", message.getSendBy());
			map.put("operate", message.getMsgType());
			map.put("beginRow", firstRow);
			map.put("pageSize", pageSize);
			list = sqlMapClient.queryForList("getMessageResultList", map);
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		// TODO Auto-generated method stub
	}

	@Override
	public int getMessageResultCount(Message message) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("getMessageResultCount",
				message);
	}

	@Override
	public List<NotificationVo> getFailMsgStateList(int firstRow, int pageSize,
			int msgId) throws SQLException {
		List<NotificationVo> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		// int endRow = pageSize+firstRow;
		map.put("beginRow", firstRow);
		map.put("pageSize", pageSize);
		map.put("msgId", msgId);
		list = sqlMapClient.queryForList("getFailMsgStateList", map);
		return list;
	}

	@Override
	public int updateMsgResult(int msgKey, NotificationVo vo, String sendDate,
			String oldErrCode, String sendBy) throws SQLException {
		int id = 0;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mesId", msgKey);
			map.put("errCode", oldErrCode);
			map.put("status", vo.getState());
			map.put("msg", vo.getResultMsg());
			map.put("empId", sendBy);
			map.put("receiver", vo.getReceiver());
			map.put("sendDate", sendDate);
			id = sqlMapClient.update("updateMsgResult", map);
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		// return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageSearchVO> getAllComp(int firstRow, Integer pageSize,
			MessageSearchVO messageSearchVO) throws SQLException {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> map = new HashMap<String, Object>();
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
			map.put("firstRow", firstRow);
			map.put("pageSize", pageSize);

			list = sqlMapClient.queryForList("getAllComp", map);
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int getCompCount(MessageSearchVO messageSearchVO)
			throws SQLException {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject("getCompCount",
				messageSearchVO);
	}

	@Override
	public User validateUser(User user) throws SQLException {
		return (User) sqlMapClient.queryForObject("validateUser", user);
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
			map.put("firstRow", firstRow);
			map.put("pageSize", pageSize);
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
		return (Integer) sqlMapClient.queryForObject("getUserCount", searchVo);

	}

	@Override
	public int isExistEmp(String empId) throws SQLException {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject("isExistEmp", empId);
	}

	@Override
	public int isExistLoginName(String loginName) throws SQLException {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject("isExistLoginName",
				loginName);
	}

	@Override
	public int addNewEmp(User user) throws SQLException {
		// TODO Auto-generated method stub
		int result = 0;
		result = (Integer) sqlMapClient.update("addNewEmp", user);
		return result;
	}

	@Override
	public void saveEmpChanges(List<User> list) throws SQLException {
		try {
			sqlMapClient.startBatch();
			for (User user : list) {

				sqlMapClient.update("saveEmpChanges", user);
			}
			sqlMapClient.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int updateLoginDate(User user) throws SQLException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("empid", user.getEmpId());
			String loginDate = DateUtils.getNowTime();
			map.put("lastLoginDate", loginDate);
			return sqlMapClient.update("updateLoginDate", map);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public int updatePassword(String empId, String newPwd) throws SQLException {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("empId", empId);
			map.put("newPwd", newPwd);
			return sqlMapClient.update("updatePassword", map);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public void saveCallInfoWhenRing(CallInfoVo callvo) throws SQLException {
		try {

			sqlMapClient.insert("saveCallInfoWhenRing", callvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int queryCallInfo(String callSheetId) throws SQLException {
		int count = 0;
		try {

			count = (Integer) sqlMapClient.queryForObject(
					"queryCallInfoBySheetId", callSheetId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int updateCallInfoWhenRing(CallInfoVo callvo) throws SQLException {
		int count = 0;
		try {

			count = (Integer) sqlMapClient.update("updateCallInfoWhenRing",
					callvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<CallInfoVo> getCallList(String account) throws SQLException {
		List<CallInfoVo> list = null;
		try {
			list = sqlMapClient.queryForList("getCallList",
					account.split("@")[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CallInfoVo> getCallInfoByCallNo(String callNo,int firstRow, int pageSize)
			throws SQLException {
		List<CallInfoVo> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("callNo", callNo);
			map.put("beginRow", firstRow);
			map.put("pageSize", pageSize);
			list = sqlMapClient.queryForList("getCallInfoByCallNo", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Consults> getConsultInfoByCallSheetNo(String callSheetNo)
			throws SQLException {
		List<Consults> list = null;
		try {
			list = sqlMapClient.queryForList("getConsultInfoByCallSheetNo",
					callSheetNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String getStatusWhenAddConsults(CallInfoVo callvo)
			throws SQLException {
		String status = null;
		try {
			status = (String) sqlMapClient.queryForObject(
					"getStatusWhenAddConsults", callvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public void addConsults(String callSheetId, String question, String answer)
			throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("callSheetId", callSheetId);
			map.put("question", question);
			map.put("answer", answer);
			sqlMapClient.insert("addConsults", map);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int saveMessageWithURL(Message msg) throws SQLException {
		// TODO Auto-generated method stub
		int key = 0;
		try {
			msg.setMsgType("2");
			key = (Integer) sqlMapClient.insert("saveMsg", msg);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return key;
	}

	@Override
	public List<NotificationVo> getReSendList(int id) throws SQLException {
		// TODO Auto-generated method stub
		List<NotificationVo> list = null;
		try {
			list = sqlMapClient.queryForList("getReSendList", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void deleteState(String taxId) throws SQLException {
		// TODO Auto-generated method stub
		sqlMapClient.delete("deleteState", taxId);
	}

	@Override
	public void deleteComp(String taxId) throws SQLException {
		// TODO Auto-generated method stub
		sqlMapClient.delete("deleteComp", taxId);
	}

	@Override
	public List<Consults> getKnowledgeContent(String title) throws Exception {
		List<Consults> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", title);
			list = sqlMapClient.queryForList("getKnowledgeContent",map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public String selectComp(String taxId) throws SQLException {
		// TODO Auto-generated method stub
		return (String) sqlMapClient.queryForObject("selectComp", taxId);
	}

	@Override
	public List<ContactVo> getContactList() throws Exception {
		List<ContactVo> list = null;
		try {
			list = sqlMapClient.queryForList("getContactList");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@Override
	public int getCallInfoByCallNo(String callNo) throws Exception {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			count = (Integer) sqlMapClient.queryForObject("getCallCount",callNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return count;
	}

	@Override
	public List<FoldTree> getFoldTree() throws Exception {
		List<FoldTree> treeList = new ArrayList<FoldTree>();
		try {
			treeList =  sqlMapClient.queryForList("getFoldTree");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return treeList;
	}

	@Override
	public void addNode(FoldTree node) throws Exception {
		try {
			sqlMapClient.insert("addNode",node);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void addContentByNode(Consults consult) throws Exception {
		// TODO Auto-generated method stub
		try {
			sqlMapClient.insert("addContentByNode",consult);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Consults> getContentByNode(int nodeId, int firstRow,
			int pageSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("nodeId", nodeId);
			map.put("beginRow", firstRow);
			map.put("pageSize", pageSize);
			List<Consults> list = sqlMapClient.queryForList("getContentByNode", map);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Consults> searchContentByKeyWords(String keywords,
			int firstRow, int pageSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("keywords", keywords);
			map.put("beginRow", firstRow);
			map.put("pageSize", pageSize);
			List<Consults> list = sqlMapClient.queryForList("searchContentByKeyWords", map);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public int getContentCountByNode(int nodeId) throws Exception {
		int count = 0;
		try {
			count = (Integer) sqlMapClient.queryForObject("getContentCountByNode",nodeId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return count;
	}

	@Override
	public int getContentCountBySearch(int keywords) throws Exception {
		int count = 0;
		try {
			count = (Integer) sqlMapClient.queryForObject("getContentCountBySearch",keywords);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return count;
	}

	@Override
	public void updateNode(FoldTree node) throws Exception {
		try {
			sqlMapClient.update("updateNode",node);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
	}

	@Override
	public void deleteNode(int nodeId,List<Integer> list) throws Exception {
		// TODO Auto-generated method stub
		try {
			sqlMapClient.delete("deleteNode",nodeId);
			for (int i : list) {//删除节点下的所有目录，级联删除知识点
				sqlMapClient.delete("deleteNode",i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
	}

}
