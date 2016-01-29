package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.vos.ImposeType;
import com.vos.Pay;
import com.vos.PayNotificationVo;
import com.vos.PaySearchVO;
import com.vos.PayVO;
import com.vos.TotalTax;


public interface PayDao {

	public List<Pay> getAllPay(int firstRow, Integer pageSize, PaySearchVO paySearchVO) throws SQLException;

	public int getPayCount(PaySearchVO paySearchVO) throws SQLException;

	public List<ImposeType> getImposeType(String taxId) throws SQLException;

	public int savePayMsg(PayVO msg, String paySqlContent) throws SQLException;

	public int savePayMsgResult(int key, PayNotificationVo vo, String sendDate) throws SQLException;

	public void deletePay(String taxId) throws SQLException;

	public List<TotalTax> getUnpaidTax(String taxId) throws SQLException;
	
}
