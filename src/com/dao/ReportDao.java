package com.dao;

import java.sql.SQLException;
import java.util.List;


import com.vos.Report;
import com.vos.ReportNotificationVo;
import com.vos.ReportSearchVO;
import com.vos.ReportSend;
import com.vos.ReportVO;

public interface ReportDao {
	public List<Report> getAllReport(int firstRow, Integer pageSize,
			ReportSearchVO reportSearchVO) throws SQLException;

	public int getReportCount(ReportSearchVO reportSearchVO) throws SQLException;
	
	public List<ReportSend> getReportList(int taxId,ReportSend reportSend) throws SQLException;

	public List<Report> getImposeTypeList() throws SQLException;
	
	public int sendReport(String taxId, ReportSend reportSend) throws SQLException;

	public int saveReportMsg(ReportVO msg) throws SQLException;

	public int saveReportMsgResult(int msgKey, ReportNotificationVo vo, String sendDate)
			throws SQLException;

	


}
