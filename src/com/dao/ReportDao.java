package com.dao;

import java.sql.SQLException;
import java.util.List;


import com.vos.Report;
import com.vos.ReportSearchVO;

public interface ReportDao {
	public List<Report> getAllReport(int firstRow, Integer pageSize,
			ReportSearchVO reportSearchVO) throws SQLException;

	public int getReportCount(ReportSearchVO reportSearchVO) throws SQLException;
	
}
