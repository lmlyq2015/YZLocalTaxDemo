package com.service.imp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.dao.ReportDao;
import com.service.ReportService;
import com.util.DateUtils;
import com.util.TaxUtil;
import com.util.TaxUtil;
import com.vos.ImposeType;
import com.vos.MessageResult;
import com.vos.PayNotificationVo;
import com.vos.Report;
import com.vos.ReportNotificationVo;
import com.vos.ReportSearchVO;
import com.vos.ReportVO;

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
	public int sendReportMsg(ReportVO msg) throws SQLException {
		int id = 0;
		try {
			String result = null;
			MessageResult mr = null;

			// int key = reportDao.saveReportMsg(msg);
			// String sendDate = msg.getSendDate();
			String sendDate = DateUtils.getNowTime();
			List<ReportNotificationVo> list = msg.getVoList();
			String empId = msg.getSendBy();
			for (ReportNotificationVo vo : list) {
				// 1办税员
				vo.setEmpId(empId);
				List<ImposeType> imposeTypes = reportDao.getImposeTypes(vo
						.getTaxId());
				String contents = "";
				for (int i = 0; i < imposeTypes.size(); i++) {
					contents = contents + imposeTypes.get(i).getName() + "、";
				}
				contents = contents.substring(0, contents.length() - 1);
				vo.setImposeType(contents);
				int key = reportDao.saveReportMsg(msg,
						TaxUtil.getReportSqlContent(vo));
				msg.setId(key);
				vo.setMesId(key);

				result = TaxUtil.sendMessage(TaxUtil.getReportContent(vo),
						vo.getTaxAgentMobile(), sendDate);
				mr = TaxUtil.parseResult(result);
				vo.setStatus(mr.getErrid());
				setResultMsg(vo, mr);
				vo.setReceiver(TaxUtil.MESSAGE_RECEVIER_TAXER);
				id = reportDao.saveReportMsgResult(key, vo, sendDate);
			}
			String sendToSelf = msg.getSendToSelf();
			if (sendToSelf != null && sendToSelf.equals("是")) {
				TaxUtil.sendMessage("通知于" + sendDate + "发送成功！",
						msg.getMobile(), sendDate);
			}
			// TaxUtil.creatTxtFile();
			// boolean bo;
			// for (ReportNotificationVo vo : list) {
			// bo = TaxUtil.writeTxtFile(TaxUtil.getReportContent(vo));
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

	private ReportNotificationVo setResultMsg(ReportNotificationVo vo,
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
	public void deleteReport(String taxId) throws SQLException {
		// TODO Auto-generated method stub
		reportDao.deleteReport(taxId);
	}

	@Override
	public String getContentByWebPage(Integer mesId, String taxId)
			throws SQLException {
		// TODO Auto-generated method stub
		return reportDao.getContentByWebPage(mesId, taxId);
	}

}
