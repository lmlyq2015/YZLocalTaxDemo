package com.service.imp;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.dao.PoiDao;
import com.poi.FillReportManager;
import com.poi.Layouter;
import com.poi.Writer;
import com.service.PoiService;
import com.vos.MessageSearchVO;
import com.vos.NotificationVo;
import com.vos.Pay;
import com.vos.Report;
import com.vos.TaxId;

public class PoiServiceImp implements PoiService {

	private PoiDao poiDao;

	public PoiDao getPoiDao() {
		return poiDao;
	}

	public void setPoiDao(PoiDao poiDao) {
		this.poiDao = poiDao;
	}

	private static Logger logger = Logger.getLogger("service");

	@Override
	public void exportXLS(int msgId, HttpServletResponse response)
			throws SQLException {
		// TODO Auto-generated method stub
		// 1.创建一个 workbook
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 2.创建一个 worksheet
		HSSFSheet worksheet = workbook.createSheet();

		// 3.定义起始行和列
		int startRowIndex = 0;
		int startColIndex = 0;

		// 4.创建title,data,headers
		Layouter.buildReport(worksheet, startRowIndex, startColIndex);

		// 5.填充数据
		FillReportManager.fillReport(worksheet, startRowIndex, startColIndex,
				getFailMsg(msgId));

		// 6.设置response参数
		String fileName = "Export.xls";
		response.setHeader("Content-Disposition", "attachment; filename="
				+ fileName);
		// 确保发送的当前文本格式
		response.setContentType("application/vnd.ms-excel;charset=utf-8");

		// 7. 输出流
		try {
			Writer.write(response, worksheet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Report> readReport(InputStream inp) throws SQLException,
			ParseException {
		// TODO Auto-generated method stub
		List<Report> reportList = new ArrayList<Report>();

		try {
			String cellStr = null;

			Workbook wb = WorkbookFactory.create(inp);

			Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets

			// 从第二行开始读取数据
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				Report report = new Report();
				Report addReport = new Report();

				Row row = sheet.getRow(i); // 获取行(row)对象

				if (row == null) {
					// row为空的话,不处理
					continue;
				}

				int[] cellInt = { 0, 4, 2, 3 };
				for (int j = 0; j < cellInt.length; j++) {

					Cell cell = row.getCell(cellInt[j]); // 获得单元格(cell)对象

					// 转换接收的单元格
					cellStr = ConvertCellStr(cell, cellStr);

					// 将单元格的数据添加至一个对象
					addReport = addingReport(cellInt[j], report, cellStr);

				}
				// 将添加数据后的对象填充至list中
				reportList.add(addReport);
				if (addReport.getTaxId() == null) {
					reportList.remove(reportList.size() - 1);
				}
			}

		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inp != null) {
				try {
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				logger.info("没有数据流!");
			}
		}
		return reportList;
	}

	@Override
	public List<NotificationVo> getFailMsg(int msgId) throws SQLException {
		// TODO Auto-generated method stub
		return poiDao.getFailMsg(msgId);
	}

	@Override
	public int[] insertReport(List<Report> list) throws SQLException {
		// TODO Auto-generated method stub
		return poiDao.insertReport(list);
	}

	@Override
	public Report addingReport(int j, Report report, String cellStr)
			throws SQLException, ParseException {
		// TODO Auto-generated method stub
		switch (j) {
//		case 10:
//			report.setId(null);
//			break;
		case 0:
			report.setTaxId(cellStr);
			break;
		case 4:
			report.setImposeType(cellStr);
			break;
		case 2:
			report.setStartTime(cellStr);
			break;
		case 3:
			report.setEndTime(cellStr);
			break;
		}
		
		return report;
	}

	@Override
	public String ConvertCellStr(Cell cell, String cellStr) throws SQLException {
		// TODO Auto-generated method stub
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			return null;
		}
		switch (cell.getCellType()) {

		case Cell.CELL_TYPE_STRING:
			// 读取String
			cellStr = cell.getStringCellValue().toString();
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			// 得到Boolean对象的方法
			cellStr = String.valueOf(cell.getBooleanCellValue());
			break;

		case Cell.CELL_TYPE_NUMERIC:

			// 先看是否是日期格式
			if (DateUtil.isCellDateFormatted(cell)) {

				// 读取日期格式
				cellStr = cell.getDateCellValue().toString();

			} else {

				// 读取数字
				cellStr = String.valueOf(cell.getNumericCellValue());
			}
			break;

		case Cell.CELL_TYPE_FORMULA:
			// 读取公式
			cellStr = cell.getCellFormula().toString();
			break;
		}
		return cellStr;
	}

	@Override
	public List<MessageSearchVO> readComp(InputStream inp) throws SQLException,
			ParseException {
		// TODO Auto-generated method stub
		List<MessageSearchVO> compList = new ArrayList<MessageSearchVO>();

		try {
			String cellStr = null;

			Workbook wb = WorkbookFactory.create(inp);

			Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets

			// 从第二行开始读取数据
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				MessageSearchVO messageSearchVO = new MessageSearchVO();
				MessageSearchVO addMessageSearchVO = new MessageSearchVO();

				Row row = sheet.getRow(i); // 获取行(row)对象

				if (row == null) {
					// row为空的话,不处理
					continue;
				}

				for (int j = 1; j < row.getLastCellNum(); j++) {

					Cell cell = row.getCell(j); // 获得单元格(cell)对象

					// 转换接收的单元格
					cellStr = ConvertCellStr(cell, cellStr);

					// 将单元格的数据添加至一个对象
					addMessageSearchVO = addingComp(j, messageSearchVO, cellStr);

				}
				// 将添加数据后的对象填充至list中
				String compareTaxId = addMessageSearchVO.getTaxId();
				// System.out.println(compareTaxId);
				int count = poiDao.compareTaxId(compareTaxId);
				// System.out.println(count);
				if (compareTaxId != null && count == 0) {
					compList.add(addMessageSearchVO);
				}
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inp != null) {
				try {
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				logger.info("没有数据流!");
			}
		}
		for (int i = 0; i < compList.size() - 1; i++) {
			for (int j = compList.size() - 1; j > i; j--) {
				if (compList.get(i).getTaxId()
						.equalsIgnoreCase(compList.get(j).getTaxId())) {
					compList.remove(i);
				}
			}
		}
		return compList;
	}

	public MessageSearchVO addingComp(int j, MessageSearchVO messageSearchVO,
			String cellStr) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		switch (j) {
		case 1:
			messageSearchVO.setEid(cellStr);
			break;
		case 2:
			messageSearchVO.setTaxId(cellStr);
			break;
		case 3:
			messageSearchVO.setTaxName(cellStr);
			break;
		case 4:
			messageSearchVO.setAddress(cellStr);
			break;
		case 5:
			messageSearchVO.setTaxAdmin(cellStr);
			break;
		case 6:
			messageSearchVO.setState(cellStr);
			break;
		case 7:
			messageSearchVO.setRep(cellStr);
			break;
		case 8:
			messageSearchVO.setRepMobile(cellStr);
			break;
		case 9:
			messageSearchVO.setTaxAgentName(cellStr);
			break;
		case 10:
			messageSearchVO.setTaxAgentMobile(cellStr);
			break;
		case 11:
			messageSearchVO.setAdminName(cellStr);
			break;
		case 12:
			messageSearchVO.setAdminMobile(cellStr);
			break;
		}
		return messageSearchVO;
	}

	@Override
	public int[] insertComp(List<MessageSearchVO> list) throws SQLException {
		// TODO Auto-generated method stub
		return poiDao.insertComp(list);
	}

	@Override
	public List<Pay> readPay(InputStream inp) throws SQLException,
			ParseException {
		// TODO Auto-generated method stub
		List<Pay> payList = new ArrayList<Pay>();

		try {
			String cellStr = null;

			Workbook wb = WorkbookFactory.create(inp);

			Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets

			// 从第二行开始读取数据
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				Pay pay = new Pay();
				Pay addPay = new Pay();

				Row row = sheet.getRow(i); // 获取行(row)对象

				if (row == null) {
					// row为空的话,不处理
					continue;
				}

				int[] cellInt = { 0, 2, 4, 5, 6 };
				for (int j = 0; j < cellInt.length; j++) {

					Cell cell = row.getCell(cellInt[j]); // 获得单元格(cell)对象

					// 转换接收的单元格
					cellStr = ConvertCellStr(cell, cellStr);

					// 将单元格的数据添加至一个对象
					addPay = addingPay(cellInt[j], pay, cellStr);
				}
				// 将添加数据后的对象填充至list中
				payList.add(addPay);
				if (addPay.getTaxId() == null) {
					payList.remove(payList.size() - 1);
				}
			}

		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inp != null) {
				try {
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				logger.info("没有数据流!");
			}
		}
		return payList;
	}

	@Override
	public Pay addingPay(int j, Pay pay, String cellStr) throws SQLException,
			ParseException {
		// TODO Auto-generated method stub
		switch (j) {
//		case 10:
//			pay.setId(null);
//			break;
		case 0:
			pay.setTaxId(cellStr);
			break;
		case 2:
			pay.setImposeType(cellStr);
			break;
		case 4:
			pay.setStartTime(cellStr);
			break;
		case 5:
			pay.setEndTime(cellStr);
			break;
		case 6:
			pay.setTotalTax(cellStr);
			break;
		}

		return pay;
	}

	@Override
	public int[] insertPay(List<Pay> list) throws SQLException {
		// TODO Auto-generated method stub
		return poiDao.insertPay(list);
	}

	@Override
	public int selectUnequalNum() throws SQLException {
		// TODO Auto-generated method stub
		return poiDao.selectUnequalNum();
	}

	@Override
	public List<TaxId> selectUnequalTaxId() throws SQLException {
		// TODO Auto-generated method stub
		return poiDao.selectUnequalTaxId();
	}

	@Override
	public int selectUnequalNumForpay() throws SQLException {
		// TODO Auto-generated method stub
		return poiDao.selectUnequalNumForpay();
	}

	@Override
	public List<TaxId> selectUnequalTaxIdForpay() throws SQLException {
		// TODO Auto-generated method stub
		return poiDao.selectUnequalTaxIdForpay();
	}

}
