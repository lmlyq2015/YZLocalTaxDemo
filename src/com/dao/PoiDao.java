package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.vos.MessageSearchVO;
import com.vos.NotificationVo;
import com.vos.Report;

public interface PoiDao {
	

	public List<NotificationVo> getFailMsg(int msgId) throws SQLException;

	public int[] insertReport(List<Report> list) throws SQLException;

	public int[] insertComp(List<MessageSearchVO> list) throws SQLException;

	public int compareTaxId(String compareTaxId) throws SQLException;
}
