package com.dao.imp;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.ThrowsAdvice;

import com.dao.MessageDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.util.TaxUtil;
import com.vos.Message;
import com.vos.NotificationVo;

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
			msg.setContent(msg.getContent() + msg.getSign());
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
			map.put("taxId", vo.getTaxNo());
			map.put("status", vo.getStatus());
			map.put("msg", vo.getResultMsg());
			map.put("empId", "admin");
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

	@Override
	public List getMessageResultList(int firstRow, int pageSize)
			throws SQLException {
		List<Message> list = null;
		Map<String,Object> map = new HashMap<String,Object>();
		//int endRow = pageSize+firstRow;
		map.put("beginRow", firstRow);
		map.put("pageSize", pageSize);
		list = sqlMapClient.queryForList("getMessageResultList",map);
		return list;
	}

	@Override
	public int getMessageResultCount() throws SQLException {
		int count = 0;
		count = (Integer) sqlMapClient.queryForObject("getMessageResultCount");
		return count;
	}
}
