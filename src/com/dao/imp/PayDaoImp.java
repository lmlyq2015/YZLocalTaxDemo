package com.dao.imp;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.PayDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.util.TaxUtil;
import com.vos.ImposeType;
import com.vos.NotificationVo;
import com.vos.Pay;
import com.vos.PayNotificationVo;
import com.vos.PaySearchVO;
import com.vos.PayVO;
import com.vos.TotalTax;


public class PayDaoImp implements PayDao {

	private SqlMapClient sqlMapClient;

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pay> getAllPay(int firstRow, Integer pageSize,
			PaySearchVO paySearchVO) throws SQLException {
		// TODO Auto-generated method stub
		try{
			Map<String,Object> map = new HashMap<String, Object>();
			List<Pay> list = new ArrayList<Pay>();
			map.put("taxId", paySearchVO.getTaxId());
			map.put("taxName", paySearchVO.getTaxName());
			map.put("taxAgentName", paySearchVO.getTaxAgentName());
			map.put("taxAgentMobile", paySearchVO.getTaxAgentMobile());
			map.put("adminName", paySearchVO.getAdminName());
			map.put("adminMobile", paySearchVO.getAdminMobile());
			map.put("rep", paySearchVO.getRep());
			map.put("repMobile", paySearchVO.getRepMobile());
			map.put("startTime", paySearchVO.getStartTime());
			map.put("endTime", paySearchVO.getEndTime());
			map.put("totalTax", paySearchVO.getTotalTax());
			map.put("imposeType", paySearchVO.getImposeType());
			map.put("firstRow",firstRow);
			map.put("pageSize",pageSize);
			
			list = sqlMapClient.queryForList("getAllPay",map);
			return list;

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int getPayCount(PaySearchVO paySearchVO) throws SQLException {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject("getPayCount",paySearchVO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImposeType> getImposeType(String taxId) throws SQLException {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taxId", taxId);
		map.put("type", null);
		return sqlMapClient.queryForList("getImposeType",map);
	}

	@Override
	public int savePayMsg(PayVO msg, String paySqlContent) throws SQLException {
		// TODO Auto-generated method stub
		int key = 0;
		try{
			//msg.setContent(msg.getContent() + " " + msg.getSign());
//			msg.setContent(paySqlContent + " " + msg.getSign());
			msg.setContent(paySqlContent);
			msg.setMsgType("3");		
			key = (Integer) sqlMapClient.insert("savePayMsg",msg);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return key;
	}

	@Override
	public int savePayMsgResult(int key, NotificationVo vo, String sendDate)
			throws SQLException {
		// TODO Auto-generated method stub
		int id = 0;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mesId", key);
			map.put("taxId", vo.getTaxId());
			map.put("status", vo.getStatus());
			map.put("msg", vo.getResultMsg());
			map.put("empId", vo.getEmpId());
			map.put("receiver", vo.getReceiver());
			if (sendDate == null || sendDate.equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sendDate = sdf.format(new Date()).toString();
			}
			map.put("sendDate", sendDate);
			map.put("Id", 0);
			sqlMapClient.insert("savePayMsgResult",map);
			return (Integer) map.get("Id");
		}catch(SQLException e){
			e.printStackTrace();
			//throw e;
		}
		return id;
	}

	@Override
	public void deletePay(String taxId) throws SQLException {
		// TODO Auto-generated method stub
		sqlMapClient.delete("deletePay", taxId);
	}

	@Override
	public List<TotalTax> getUnpaidTax(String taxId) throws SQLException {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taxId", taxId);
		map.put("type", null);
		return sqlMapClient.queryForList("getTotalTax",map);
	}

	@Override
	public void deletePay(PaySearchVO paySearchVO) throws SQLException {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taxId", paySearchVO.getTaxId());
		map.put("imposeType", paySearchVO.getImposeType());
		map.put("startTime", paySearchVO.getStartTime());
		sqlMapClient.delete("deletePayByImposeType", map);
	}

	@Override
	public int selectPay(PaySearchVO paySearchVO) throws SQLException {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taxId", paySearchVO.getTaxId());
		map.put("imposeType", paySearchVO.getImposeType());
		map.put("startTime", paySearchVO.getStartTime());
		return (Integer) sqlMapClient.queryForObject("selectPay",map);
	}
		
}
