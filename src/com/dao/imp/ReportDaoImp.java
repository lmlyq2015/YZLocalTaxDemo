package com.dao.imp;

import com.dao.ReportDao;
import com.ibatis.sqlmap.client.SqlMapClient;

public class ReportDaoImp implements ReportDao {

	private SqlMapClient sqlMapClient;

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}
}
