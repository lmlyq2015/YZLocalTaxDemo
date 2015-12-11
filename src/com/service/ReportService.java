package com.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vos.Report;
import com.vos.ReportSearchVO;

public interface ReportService {

	public List<Report> getAllReport(int firstRow, Integer pageSize,
			ReportSearchVO reportSearchVO) throws SQLException;

	public int getReportCount(ReportSearchVO reportSearchVO) throws SQLException;

	public int batchImport(String name, MultipartFile file) throws SQLException;

}
