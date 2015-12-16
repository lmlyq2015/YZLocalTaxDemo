package com.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dao.ReportDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.vos.Report;
import com.vos.ReportSearchVO;

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
			map.put("year", reportSearchVO.getYear());
			map.put("month", reportSearchVO.getMonth());
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

	
}
