package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.vos.Message;
import com.vos.MessageSearchVO;
import com.vos.NotificationVo;
import com.vos.Pay;
import com.vos.Report;
import com.vos.TaxId;

public interface PoiDao {
	

	public List<NotificationVo> getFailMsg(Message message) throws SQLException;

	public int[] insertReport(List<Report> list) throws SQLException;

	public int[] insertComp(List<MessageSearchVO> list) throws SQLException;

	public int compareTaxId(String compareTaxId) throws SQLException;

	public int[] insertPay(List<Pay> list) throws SQLException;

	public int selectUnequalNum() throws SQLException;

	public List<TaxId> selectUnequalTaxId() throws SQLException;
	
	public int selectUnequalNumForpay() throws SQLException;

	public List<TaxId> selectUnequalTaxIdForpay() throws SQLException;

	public List<MessageSearchVO> getComp() throws SQLException;

	public List<Report> getReport() throws SQLException;

	public List<Pay> getPay() throws SQLException;
	
}
