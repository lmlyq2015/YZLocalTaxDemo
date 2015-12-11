package com.service.imp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dao.ReportDao;
import com.service.ReportService;
import com.vos.Report;
import com.vos.ReportSearchVO;

public class ReportServiceImp implements ReportService {

	private ReportDao reportDao;

	public ReportDao getReportDao() {
		return reportDao;
	}

	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	@Override
	public List<Report> getAllReport(int firstRow, Integer pageSize,
			ReportSearchVO reportSearchVO) throws SQLException {
		// TODO Auto-generated method stub
		return reportDao.getAllReport(firstRow, pageSize, reportSearchVO);
	}

	@Override
	public int getReportCount(ReportSearchVO reportSearchVO)
			throws SQLException {
		// TODO Auto-generated method stub
		return reportDao.getReportCount(reportSearchVO);
	}

	@Override
	public int batchImport(String name, MultipartFile file) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	

	
}
