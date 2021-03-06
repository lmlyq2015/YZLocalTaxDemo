package com.dao.imp;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.dao.ReportDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.util.TaxUtil;
import com.vos.ImposeType;
import com.vos.NotificationVo;
import com.vos.Report;
import com.vos.ReportNotificationVo;
import com.vos.ReportSearchVO;
import com.vos.ReportVO;

public class ReportDaoImp implements ReportDao {

	private SqlMapClient sqlMapClient;

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Report> getAllReport(int firstRow, Integer pageSize,
			ReportSearchVO reportSearchVO) throws SQLException {
		try{
			Map<String,Object> map = new HashMap<String, Object>();
			List<Report> list = new ArrayList<Report>();
			map.put("taxId", reportSearchVO.getTaxId());
			map.put("taxName", reportSearchVO.getTaxName());
			map.put("taxAgentName", reportSearchVO.getTaxAgentName());
			map.put("taxAgentMobile", reportSearchVO.getTaxAgentMobile());
			map.put("adminName", reportSearchVO.getAdminName());
			map.put("adminMobile", reportSearchVO.getAdminMobile());
			map.put("rep", reportSearchVO.getRep());
			map.put("repMobile", reportSearchVO.getRepMobile());
			map.put("startTime", reportSearchVO.getStartTime());
			map.put("endTime", reportSearchVO.getEndTime());
			map.put("imposeType", reportSearchVO.getImposeType());
			map.put("firstRow",firstRow);
			map.put("pageSize",pageSize);
			
			list = sqlMapClient.queryForList("getAllReport",map);
			return list;

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		// TODO Auto-generated method stub
	}

	@Override
	public int getReportCount(ReportSearchVO reportSearchVO) throws SQLException {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject("getReportCount",reportSearchVO);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<ReportSend> getReportList(int taxId,ReportSend reportSend) throws SQLException {
//		// TODO Auto-generated method stub
//		try{
//			Map<String,Object> map = new HashMap<String, Object>();
//			List<ReportSend> list = new ArrayList<ReportSend>();
//			map.put("taxAgentName", reportSend.getTaxAgentName());
//			map.put("taxName", reportSend.getTaxName());
//			map.put("taxId", taxId);
//			map.put("year", reportSend.getYear());
//			map.put("month", reportSend.getMonth());
//			map.put("imposeType", reportSend.getImposeType());
//			
//			list = sqlMapClient.queryForList("sendReport",map);
//			return list;
//
//		}catch(SQLException e) {
//			e.printStackTrace();
//			throw e;
//		}
//	}

	@Override
	public List<Report> getImposeTypeList() throws SQLException {
		// TODO Auto-generated method stub
		try {
			return sqlMapClient.queryForList("getImposeTypeList");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public int saveReportMsg(ReportVO msg,String content) throws SQLException {
		// TODO Auto-generated method stub
		int key = 0;
		try{
			//msg.setContent(msg.getContent() + " " + msg.getSign());
//			msg.setContent(content + " " + msg.getSign());
			msg.setContent(content);
			msg.setMsgType("4");		
			key = (Integer) sqlMapClient.insert("saveReportMsg",msg);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return key;
	}

	@Override
	public int saveReportMsgResult(int msgKey, NotificationVo vo, String sendDate)
			throws SQLException {
		// TODO Auto-generated method stub
		int id = 0;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mesId", msgKey);
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
			sqlMapClient.insert("saveReportMsgResult",map);
			return (Integer) map.get("Id");
		}catch(SQLException e){
			e.printStackTrace();
			//throw e;
		}
		return id;
	}

	@Override
	public List<ImposeType> getImposeTypes(String taxId) throws SQLException {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taxId", taxId);
		map.put("type", null);
		return sqlMapClient.queryForList("getImposeTypes",map);
	}

	@Override
	public void deleteReport(ReportSearchVO reportSearchVO) throws SQLException {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taxId", reportSearchVO.getTaxId());
		map.put("imposeType", reportSearchVO.getImposeType());
		map.put("startTime", reportSearchVO.getStartTime());
		sqlMapClient.delete("deleteReportByImposeType", map);
	}

	@Override
	public String getContentByWebPage(Integer mesId, String taxId)
			throws SQLException {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mesId", mesId);
		map.put("taxId", taxId);
		return (String) sqlMapClient.queryForObject("getContentByWebPage",map);
	}

	@Override
	public int selectReport(ReportSearchVO reportSearchVO) throws SQLException {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taxId", reportSearchVO.getTaxId());
		map.put("imposeType", reportSearchVO.getImposeType());
		map.put("startTime", reportSearchVO.getStartTime());
		return (Integer) sqlMapClient.queryForObject("selectReport",map);
	}

	@Override
	public void deleteReport(String taxId) throws SQLException {
		// TODO Auto-generated method stub
		sqlMapClient.delete("deleteReport", taxId);
	}
	
}
