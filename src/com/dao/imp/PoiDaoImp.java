package com.dao.imp;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.PoiDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.vos.MessageSearchVO;
import com.vos.NotificationVo;
import com.vos.Pay;
import com.vos.Report;
import com.vos.TaxId;

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
		int[] a = null;
		try {
			a = (int[]) sqlMapClient.insert("insertComp", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public int compareTaxId(String compareTaxId) throws SQLException {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("compareTaxId",compareTaxId);
		return (Integer) sqlMapClient.queryForObject("compareTaxId", map);
	}

	@Override
	public int[] insertPay(List<Pay> list) throws SQLException {
		// TODO Auto-generated method stub
		return (int[]) sqlMapClient.insert("insertPay", list);
	}

	@Override
	public int selectUnequalNum() throws SQLException {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject("selectUnequalNum");
	}

	@Override
	public List<TaxId> selectUnequalTaxId() throws SQLException {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("selectUnequalTaxId");
	}

	@Override
	public int selectUnequalNumForpay() throws SQLException {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject("selectUnequalNumForpay");
	}

	@Override
	public List<TaxId> selectUnequalTaxIdForpay() throws SQLException {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("selectUnequalTaxIdForpay");
	}

	@Override
	public List<MessageSearchVO> getComp() throws SQLException {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("getComp");
	}

	@Override
	public List<Report> getReport() throws SQLException {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("getReport");
	}

	@Override
	public List<Report> getPay() throws SQLException {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("getPay");
	}
	
	
}
