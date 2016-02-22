package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.service.PayService;
import com.service.PoiService;
import com.service.ReportService;
import com.vos.JsonResult;
import com.vos.MessageSearchVO;
import com.vos.Pay;
import com.vos.Report;
import com.vos.TaxId;

@Controller
@RequestMapping("/report")
public class PoiController {
	private static Logger logger = Logger.getLogger("controller");

	@Resource(name = "poiService")
	private PoiService poiService;

	public PoiService getPoiService() {
		return poiService;
	}

	public void setPoiService(PoiService poiService) {
		this.poiService = poiService;
	}

	/**
	 * 跳转到主页.
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getIndex() {
		logger.info("index!");
		return "report";
	}

	/**
	 * 导出excel报表
	 * 
	 * @throws SQLException
	 */
	@RequestMapping(value = "/exportFailMsg", method = RequestMethod.POST)
	public void getXLS(@RequestParam("msgId") int msgId,
			HttpServletResponse response) throws SQLException {
		poiService.exportXLS(msgId, response);
	}

	@Resource(name = "reportService")
	private ReportService reportService;

	public ReportService getReportService() {
		return reportService;
	}
	
	/**
	 * 读取excel报表（Report）
	 * 
	 * @throws SQLException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/read", method = RequestMethod.POST)
	@ResponseBody
	public void getReadReport(@RequestParam MultipartFile file,
			HttpServletResponse response) throws IOException, SQLException,
			ParseException {

		List<Report> list = null;
		try {
			list = poiService.readReport(file.getInputStream());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			if (list.size() != 0) {
				for(int i = 0;i < list.size();i++){
					System.out.println(list.get(i).getTaxId());
				}
				poiService.insertReport(list);
			}

			int unequal = poiService.selectUnequalNum();
			List<TaxId> taxIds = poiService.selectUnequalTaxId();
			String str = "";
			JsonResult jr = new JsonResult();
			String msg = "导入成功";
			if (unequal != 0) {
				for (int i = 0; i < taxIds.size(); i++) {
					str += taxIds.get(i).getTaxId() + "、";
					reportService.deleteReport(taxIds.get(i).getTaxId());
				}
				str = str.substring(0, str.length() - 1);
				msg += "，其中有" + unequal + "家企业未录入企业信息导入失败，税号为" + str + " 。";
			}
			response.setContentType("text/json;charset=utf-8");
			jr.setMsg(msg);
			JSONObject json = JSONObject.fromObject(jr);
			pw.print(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult jr = new JsonResult();
			jr.setMsg("导入失败");
			JSONObject json = JSONObject.fromObject(jr);
			pw.print(json.toString());
		}
	}

	/**
	 * 读取excel报表（Message）
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/readComp", method = RequestMethod.POST)
	public void getReadComp(@RequestParam MultipartFile file,
			HttpServletResponse response) throws Exception {
		List<MessageSearchVO> list = null;
		try {
			Calendar c1 = Calendar.getInstance();
			long times = c1.getTimeInMillis();
			list = poiService.readComp(file.getInputStream());
			Calendar c2 = Calendar.getInstance();
			long total = c2.getTimeInMillis() - times;
			System.out.println("导入时间：" + total + "ms");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			if (list.size() != 0) {
				Calendar c1 = Calendar.getInstance();
				long times = c1.getTimeInMillis();
				poiService.insertComp(list);
				Calendar c2 = Calendar.getInstance();
				long total = c2.getTimeInMillis() - times;
				System.out.println("插入数据库时间：" + total + "ms");
			}

			JsonResult jr = new JsonResult();
			String msg = "共" + list.size() + "家企业导入成功";
			jr.setMsg(msg);
			JSONObject json = JSONObject.fromObject(jr);
			pw.print(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult jr = new JsonResult();
			jr.setMsg("导入失败");
			JSONObject json = JSONObject.fromObject(jr);
			pw.print(json.toString());
		}
	}

	@Resource(name = "payService")
	private PayService payService;

	public PayService getPayService() {
		return payService;
	}
	
	/**
	 * 读取excel报表（Pay）
	 * 
	 * @throws SQLException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/readPay", method = RequestMethod.POST)
	@ResponseBody
	public void getReadPay(@RequestParam MultipartFile file,
			HttpServletResponse response) throws IOException, SQLException,
			ParseException {
		List<Pay> list = null;
		try {
			list = poiService.readPay(file.getInputStream());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			if (list.size() != 0) {
				poiService.insertPay(list);
			}

			int unequal = poiService.selectUnequalNumForpay();
			List<TaxId> taxIds = poiService.selectUnequalTaxIdForpay();
			String str = "";
			JsonResult jr = new JsonResult();
			String msg = "导入成功";
			if (unequal != 0) {
				for (int i = 0; i < taxIds.size(); i++) {
					str += taxIds.get(i).getTaxId() + "、";
					payService.deletePay(taxIds.get(i).getTaxId());
				}
				str = str.substring(0, str.length() - 1);
				msg += "，其中有" + unequal + "家企业未录入企业信息导入失败，税号为" + str + " 。";
			}
			response.setContentType("text/json;charset=utf-8");
			jr.setMsg(msg);
			JSONObject json = JSONObject.fromObject(jr);
			pw.print(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult jr = new JsonResult();
			jr.setMsg("导入失败");
			JSONObject json = JSONObject.fromObject(jr);
			pw.print(json.toString());
		}
	}
}
