package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.vos.Report;

public interface PoiDao {
	

	public List<Report> getReport() throws SQLException;

	public int[] insertReport(List<Report> list) throws SQLException;
}
