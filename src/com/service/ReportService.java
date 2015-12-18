package com.service;

import java.sql.SQLException;
import java.util.List;


import com.vos.Report;
import com.vos.ReportSearchVO;
import com.vos.ReportSend;
import com.vos.ReportVO;

public interface ReportService {

	public List<Report> getAllReport(int firstRow, Integer pageSize,
			ReportSearchVO reportSearchVO) throws SQLException;

	public int getReportCount(ReportSearchVO reportSearchVO) throws SQLException;

	//public List<ReportSend> getReportList(String taxId,ReportSend reportSend) throws SQLException;

	public List<Report> getImposeTypeList() throws SQLException;

	public int sendReport(String taxId, ReportSend reportSend) throws SQLException;

	public int sendReportMsg(ReportSend msg) throws SQLException;

	int sendReportMsg(ReportVO msg) throws SQLException;




}
