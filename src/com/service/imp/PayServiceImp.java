package com.service.imp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.dao.PayDao;
import com.service.PayService;
import com.util.DateUtils;
import com.util.TaxUtil;
import com.vos.ImposeType;
import com.vos.MessageResult;
import com.vos.NotificationVo;
import com.vos.Pay;
import com.vos.PayNotificationVo;
import com.vos.PaySearchVO;
import com.vos.PayVO;
import com.vos.TotalTax;

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
		return payDao.getPayCount(paySearchVO);
	}

	@Override
	public int sendPayMsg(PayVO msg) throws SQLException {
		// TODO Auto-generated method stub
		int id = 0;
		try {
			String result = null;
			MessageResult mr = null;
			// String sendDate = msg.getSendDate();
			String sendDate = DateUtils.getNowTime();
			List<PayNotificationVo> list = msg.getVoList();
			String empId = msg.getSendBy();
			int sucNum = 0;
			for (PayNotificationVo vo : list) {
				vo.setEmpId(empId);
				List<ImposeType> imposeTypes = payDao.getImposeType(vo
						.getTaxId());
				String contents = "";
				for (int i = 0; i < imposeTypes.size(); i++) {
					contents = contents + imposeTypes.get(i).getName() + "、";
				}
				contents = contents.substring(0, contents.length() - 1);
				vo.setImposeType(contents);

				List<TotalTax> unpaidTaxs = payDao.getUnpaidTax(vo.getTaxId());
				String contents1 = "";
				for (int i = 0; i < unpaidTaxs.size(); i++) {
					contents1 = contents1 + unpaidTaxs.get(i).getTotalTax()
							+ "元、";
				}
				contents1 = contents1.substring(0, contents1.length() - 1);
				vo.setTotalTax(contents1);

				int key = payDao.savePayMsg(msg, TaxUtil.getPaySqlContent(vo));
				msg.setId(key);
				vo.setMesId(key);
				result = TaxUtil.sendMessage(TaxUtil.getPayContent(vo),
						vo.getTaxAgentMobile(), sendDate);
				mr = TaxUtil.parseResult(result);
				vo.setStatus(mr.getErrid());
				setResultMsg(vo, mr);
				vo.setReceiver(TaxUtil.MESSAGE_RECEVIER_TAXER);
				// id = payDao.savePayMsgResult(key, vo, msg.getSendDate());
				id = payDao.savePayMsgResult(key, vo, sendDate);
				boolean a = vo.getResultMsg().equals("发送成功");
				if (a) {
					sucNum = sucNum + 1;
					payDao.deletePay(vo.getTaxId());
				}
			}

			if (sucNum != 0) {
				String sendToSelf = msg.getSendToSelf();
				if (sendToSelf != null && sendToSelf.equals("是")) {
					TaxUtil.sendMessage("通知于" + sendDate + "发送成功！",
							msg.getMobile(), sendDate);
				}
			}
			// TaxUtil.creatTxtFile();
			// boolean bo;
			// for (PayNotificationVo vo : list) {
			// bo = TaxUtil.writeTxtFile(TaxUtil.getPayContent(vo));
			// if(!bo){
			// TaxUtil.writeTxtFile("未插入测试数据");
			// }
			// }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	private PayNotificationVo setResultMsg(PayNotificationVo vo,
			MessageResult mr) {
		// TODO Auto-generated method stub
		if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_SUCCESS)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_SUCCESS_MSG);
		} else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_SYSTEM_ISSUE)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_SYSTEM_ISSUE_MSG);
		} else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_PASSWORD_ISSUE)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_PASSWORD_ISSUE_MSG);
		} else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_MOBILE_ISSUE)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_MOBILE_ISSUE_MSG);
		} else if (mr.getErrid().equals(
				TaxUtil.MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE_MSG);
		} else if (mr.getErrid().equals(
				TaxUtil.MESSAGE_STATUS_CONTENT_CHAR_ISSUE_MSG)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_CONTENT_CHAR_ISSUE_MSG);
		} else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_BALANCE_ISSUE)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_BALANCE_ISSUE_MSG);
		} else if (mr.getErrid().equals(TaxUtil.MESSAGE_STATUS_ACCOUNT_ISSUE)) {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_ACCOUNT_ISSUE_MSG);
		} else {
			vo.setResultMsg(TaxUtil.MESSAGE_STATUS_UNKNOW_MSG);
		}
		return vo;
	}

	@Override
	public void deletePay(String taxId) throws SQLException {
		// TODO Auto-generated method stub
		payDao.deletePay(taxId);
	}

}
