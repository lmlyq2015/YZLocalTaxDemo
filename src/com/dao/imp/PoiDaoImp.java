package com.dao.imp;

import java.sql.SQLException;
import java.util.List;

import com.dao.PoiDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.vos.Report;

public class PoiDaoImp implements PoiDao{
	private SqlMapClient sqlMapClient;

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	@Override
	public List<Report> getReport() throws SQLException {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("getReport");
	}

	@Override
	public int[] insertReport(List<Report> list) throws SQLException {
		// TODO Auto-generated method stub
		return (int[]) sqlMapClient.insert("insertReport", list);
	}
	
	
}
