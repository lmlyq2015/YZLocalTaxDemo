package com.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.vos.Pay;
import com.vos.PaySearchVO;
import com.vos.PayVO;


public interface PayService {

	public List<Pay> getAllPay(int firstRow, Integer pageSize, PaySearchVO paySearchVO) throws SQLException;

	public int getPayCount(PaySearchVO paySearchVO) throws SQLException;

	public int sendPayMsg(PayVO msg) throws SQLException;

	public void deletePay(String taxId) throws SQLException;

	public void deletePay(List<PaySearchVO> list, HttpServletResponse response) throws SQLException;

	public int selectPay(List<PaySearchVO> list, HttpServletResponse response) throws SQLException;
	
}
