package com.dao.imp;

import java.sql.SQLException;

import com.dao.PhoneDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.vos.HungupVo;

public class PhoneDaoImp implements PhoneDao {

	private SqlMapClient sqlMapClient;

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	@Override
	public int hungup(HungupVo vo) throws SQLException {
		// TODO Auto-generated method stub
		int result = 0;
		result = (Integer) sqlMapClient.update("hungup", vo);
		return result;
	}

	@Override
	public int survey(HungupVo vo) throws SQLException {
		// TODO Auto-generated method stub
		int result = 0;
		result = (Integer) sqlMapClient.update("survey", vo);
		return result;
	}

	@Override
	public int link(HungupVo vo) throws SQLException {
		// TODO Auto-generated method stub
		int result = 0;
		result = (Integer) sqlMapClient.update("link", vo);
		return result;
	}

}
