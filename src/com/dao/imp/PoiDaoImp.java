package com.dao.imp;

import java.sql.SQLException;
import java.util.List;

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
	public List<NotificationVo> getFailMsg() throws SQLException {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("getFailMsg");
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
	
	
}
