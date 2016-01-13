package com.service.imp;

import java.sql.SQLException;
import java.util.List;

import com.dao.PayDao;
import com.service.PayService;
import com.util.DateUtils;
import com.util.PayUtil;
import com.vos.ImposeType;
import com.vos.MessageResult;
import com.vos.Pay;
import com.vos.PayNotificationVo;
import com.vos.PaySearchVO;
import com.vos.PayVO;

public class PayServiceImp implements PayService {

	private PayDao payDao;

	public PayDao getPayDao() {
		return payDao;
	}

	public void setPayDao(PayDao payDao) {
		this.payDao = payDao;
	}

	@Override
	public List<Pay> getAllPay(int firstRow, Integer pageSize,
			PaySearchVO paySearchVO) throws SQLException {
		// TODO Auto-generated method stub
		return payDao.getAllPay(firstRow, pageSize, paySearchVO);
	}

	@Override
	public int getPayCount(PaySearchVO paySearchVO) throws SQLException {
		// TODO Auto-generated method stub
		return  payDao.getPayCount(paySearchVO);
	}

	@Override
	public int sendPayMsg(PayVO msg) throws SQLException {
		// TODO Auto-generated method stub
		int id = 0;
		try {
			String result = null;
			MessageResult mr = null;	
//			String sendDate = msg.getSendDate();
			String sendDate = DateUtils.getNowTime();
			List<PayNotificationVo> list = msg.getVoList();
			for (PayNotificationVo vo : list) {
				List<ImposeType> imposeTypes = payDao.getImposeType(vo.getTaxId());
				String contents = "";
				for (int i = 0; i < imposeTypes.size(); i++) {
					contents = contents + imposeTypes.get(i).getName() + ",";
				}
				vo.setImposeType(contents);
				int key = payDao.savePayMsg(msg,PayUtil.getPaySqlContent(vo));
				msg.setId(key);
				vo.setMesId(key);	
				result = PayUtil.sendPay(PayUtil.getPayContent(vo), vo.getTaxAgentMobile(), sendDate);
				mr = PayUtil.parseResult(result);
				vo.setStatus(mr.getErrid());
				setResultMsg(vo, mr);
				vo.setReceiver(PayUtil.MESSAGE_RECEVIER_TAXER); 
//				id = payDao.savePayMsgResult(key, vo, msg.getSendDate());
				id = payDao.savePayMsgResult(key, vo, sendDate);
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return id;
	}
	
	private PayNotificationVo setResultMsg(PayNotificationVo vo, MessageResult mr) {	
		// TODO Auto-generated method stub
		if (mr.getErrid().equals(PayUtil.MESSAGE_STATUS_SUCCESS)) {
			vo.setResultMsg(PayUtil.MESSAGE_STATUS_SUCCESS_MSG);
		 } else if (mr.getErrid().equals(PayUtil.MESSAGE_STATUS_SYSTEM_ISSUE)) {
			 vo.setResultMsg(PayUtil.MESSAGE_STATUS_SYSTEM_ISSUE_MSG);
		 } else if (mr.getErrid().equals(PayUtil.MESSAGE_STATUS_PASSWORD_ISSUE)) {
			 vo.setResultMsg(PayUtil.MESSAGE_STATUS_PASSWORD_ISSUE_MSG);
		 } else if (mr.getErrid().equals(PayUtil.MESSAGE_STATUS_MOBILE_ISSUE)) {
			 vo.setResultMsg(PayUtil.MESSAGE_STATUS_MOBILE_ISSUE_MSG);
		 } else if (mr.getErrid().equals(PayUtil.MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE)) {
			 vo.setResultMsg(PayUtil.MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE_MSG);
		 } else if (mr.getErrid().equals(PayUtil.MESSAGE_STATUS_CONTENT_CHAR_ISSUE_MSG)) {
			 vo.setResultMsg(PayUtil.MESSAGE_STATUS_CONTENT_CHAR_ISSUE_MSG);
		 } else if (mr.getErrid().equals(PayUtil.MESSAGE_STATUS_BALANCE_ISSUE)) {
			 vo.setResultMsg(PayUtil.MESSAGE_STATUS_BALANCE_ISSUE_MSG);
		 } else if (mr.getErrid().equals(PayUtil.MESSAGE_STATUS_ACCOUNT_ISSUE)){
			 vo.setResultMsg(PayUtil.MESSAGE_STATUS_ACCOUNT_ISSUE_MSG);
		 } else {
			 vo.setResultMsg(PayUtil.MESSAGE_STATUS_UNKNOW_MSG);
		 }
		 return vo;
	}

	@Override
	public void deletePay(String taxId) throws SQLException {
		// TODO Auto-generated method stub
		payDao.deletePay(taxId);
	}
	
}
