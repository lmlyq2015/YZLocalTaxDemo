package com.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.PoiDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.util.DateUtils;
import com.vos.Message;
import com.vos.MessageSearchVO;
import com.vos.NotificationVo;
import com.vos.Pay;
import com.vos.Report;
import com.vos.TaxId;

public class PoiDaoImp implements PoiDao {
	private SqlMapClient sqlMapClient;

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	@Override
	public List<NotificationVo> getFailMsg(Message message) throws SQLException {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		if(message.getSendDate() == null || message.getSendDate().isEmpty()){
			message.setSendDate("1970-01-01");
		}
		if(message.getSendDateEnd() == null || message.getSendDateEnd().isEmpty()){
			message.setSendDateEnd("2050-01-01");
		}
		map.put("sendDate", message.getSendDate());
		map.put("sendDateEnd", message.getSendDateEnd());
		System.out.println(message.getSendDate());
		System.out.println(message.getSendDateEnd());
		List<NotificationVo> list = sqlMapClient.queryForList("getFailMsg", map);
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
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setImportDate(DateUtils.getNowTime());
		}
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compareTaxId", compareTaxId);
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
	public List<Pay> getPay() throws SQLException {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("getPay");
	}

}
