package com.dao.imp;

import com.dao.MessageDao;
import com.ibatis.sqlmap.client.SqlMapClient;

public class MessageDaoImp implements MessageDao {

	private SqlMapClient sqlMapClient;

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}
}
