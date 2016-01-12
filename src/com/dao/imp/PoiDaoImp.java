package com.dao.imp;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.PoiDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.vos.MessageSearchVO;
import com.vos.NotificationVo;
import com.vos.Report;

public class PoiDaoImp implements PoiDao{
	private SqlMapClient sqlMapClient;

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationVo> getFailMsg(int msgId) throws SQLException {
		// TODO Auto-generated method stub
		List<NotificationVo> list = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msgId",msgId);
		list =  sqlMapClient.queryForList("getFailMsg",map);
		return list;
	}

	@Override
	public int[] insertReport(List<Report> list) throws SQLException {
		// TODO Auto-generated method stub
		return (int[]) sqlMapClient.insert("insertReport", list);
	}

	@Override
	public int[] insertComp(List<MessageSearchVO> list) throws SQLException {
		// TODO Auto-generated method stub
		return (int[]) sqlMapClient.insert("insertComp", list);
	}

	@Override
	public int compareTaxId(String compareTaxId) throws SQLException {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("compareTaxId",compareTaxId);
		return (Integer) sqlMapClient.queryForObject("compareTaxId", map);
	}
	
	
}
