package com.service.imp;

import java.sql.SQLException;
import java.util.List;


import com.dao.ReportDao;
import com.service.ReportService;
import com.util.ReportUtil;
import com.vos.ImposeType;
import com.vos.MessageResult;
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
			
			//int key = reportDao.saveReportMsg(msg);
			String sendDate = msg.getSendDate();
			List<ReportNotificationVo> list = msg.getVoList();
			//msg.setId(key);
			for (ReportNotificationVo vo : list) {
				//1办税员
				 //result = ReportUtil.sendReport(msgContent, vo.getTaxAgentMobile(), sendDate);
				

				List<ImposeType> imposeTypes = reportDao.getImposeTypes(vo.getTaxId());
				String contents = "";
				for (int i = 0; i < imposeTypes.size(); i++) {
					contents = contents + imposeTypes.get(i).getName() + "、";
				}
				vo.setImposeType(contents);
				int key = reportDao.saveReportMsg(msg,ReportUtil.getReportSqlContent(vo));
				 msg.setId(key);
				 vo.setMesId(key);
				
				result = ReportUtil.sendReport(ReportUtil.getReportContent(vo), vo.getTaxAgentMobile(), sendDate);
				 mr = ReportUtil.parseResult(result);
//				 if (mr.getErrid() != ReportUtil.MESSAGE_STATUS_SUCCESS) {//发送失败则继续发送下一人
//					 //2财务主管
//					 result = ReportUtil.sendMessage(msgContent, vo.getAdminMob(), sendDate);
//					 mr = ReportUtil.parseResult(result);
//					 if (mr.getErrid() != ReportUtil.MESSAGE_STATUS_SUCCESS) {
//						 //3法人
//						 result = ReportUtil.sendMessage(msgContent, vo.getLawRepMob(), sendDate);
//						 mr = ReportUtil.parseResult(result);
//					 }
//				 }
				 vo.setStatus(mr.getErrid());
				 setResultMsg(vo, mr);
				 vo.setReceiver(ReportUtil.MESSAGE_RECEVIER_TAXER);
					
				 
				 
				 id = reportDao.saveReportMsgResult(key, vo, msg.getSendDate());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return id;
	}

	private ReportNotificationVo setResultMsg(ReportNotificationVo vo, MessageResult mr) {	
		// TODO Auto-generated method stub
		if (mr.getErrid().equals(ReportUtil.MESSAGE_STATUS_SUCCESS)) {
			vo.setResultMsg(ReportUtil.MESSAGE_STATUS_SUCCESS_MSG);
		 } else if (mr.getErrid().equals(ReportUtil.MESSAGE_STATUS_SYSTEM_ISSUE)) {
			 vo.setResultMsg(ReportUtil.MESSAGE_STATUS_SYSTEM_ISSUE_MSG);
		 } else if (mr.getErrid().equals(ReportUtil.MESSAGE_STATUS_PASSWORD_ISSUE)) {
			 vo.setResultMsg(ReportUtil.MESSAGE_STATUS_PASSWORD_ISSUE_MSG);
		 } else if (mr.getErrid().equals(ReportUtil.MESSAGE_STATUS_MOBILE_ISSUE)) {
			 vo.setResultMsg(ReportUtil.MESSAGE_STATUS_MOBILE_ISSUE_MSG);
		 } else if (mr.getErrid().equals(ReportUtil.MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE)) {
			 vo.setResultMsg(ReportUtil.MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE_MSG);
		 } else if (mr.getErrid().equals(ReportUtil.MESSAGE_STATUS_CONTENT_CHAR_ISSUE_MSG)) {
			 vo.setResultMsg(ReportUtil.MESSAGE_STATUS_CONTENT_CHAR_ISSUE_MSG);
		 } else if (mr.getErrid().equals(ReportUtil.MESSAGE_STATUS_BALANCE_ISSUE)) {
			 vo.setResultMsg(ReportUtil.MESSAGE_STATUS_BALANCE_ISSUE_MSG);
		 } else if (mr.getErrid().equals(ReportUtil.MESSAGE_STATUS_ACCOUNT_ISSUE)){
			 vo.setResultMsg(ReportUtil.MESSAGE_STATUS_ACCOUNT_ISSUE_MSG);
		 } else {
			 vo.setResultMsg(ReportUtil.MESSAGE_STATUS_UNKNOW_MSG);
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
		return reportDao.getContentByWebPage(mesId,taxId);
	}
	
}
