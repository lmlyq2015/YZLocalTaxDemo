package com.service;

import java.sql.SQLException;
import java.util.List;


import com.vos.Report;
import com.vos.ReportSearchVO;
import com.vos.ReportVO;

public interface ReportService {

	public List<Report> getAllReport(int firstRow, Integer pageSize,
			ReportSearchVO reportSearchVO) throws SQLException;

	public int getReportCount(ReportSearchVO reportSearchVO) throws SQLException;

	public int sendReportMsg(ReportVO msg) throws SQLException;

	public void deleteReport(String taxId) throws SQLException;




}
