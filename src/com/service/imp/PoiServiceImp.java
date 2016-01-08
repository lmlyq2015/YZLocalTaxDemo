package com.service.imp;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import com.vos.Report;

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
	public void exportXLS(int msgId,HttpServletResponse response) throws SQLException {
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
	public List<Report> readReport(InputStream inp) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		List<Report> reportList = new ArrayList<Report>();

		try {
			String cellStr = null;

			Workbook wb = WorkbookFactory.create(inp);

			Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets

			// 从第四行开始读取数据
			for (int i = 3; i <= sheet.getLastRowNum(); i++) {

				Report report = new Report();
				Report addReport = new Report();

				Row row = sheet.getRow(i); // 获取行(row)对象

				if (row == null) {
					// row为空的话,不处理
					continue;
				}

				int[] cellInt = {0,1,20,17,18,21,22,23,24,25,26,27};
				for (int j = 0; j < cellInt.length; j++) {

					Cell cell = row.getCell(cellInt[j]); // 获得单元格(cell)对象
					
					// 转换接收的单元格
					cellStr = ConvertCellStr(cell, cellStr);
					
					// 将单元格的数据添加至一个对象
					addReport = addingReport(cellInt[j], report, cellStr);

				}
				// 将添加数据后的对象填充至list中
				reportList.add(addReport);
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
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		switch (j) {
		case 0:
			report.setId(null);
			break;
		case 1:
			report.setTaxId(cellStr);
			break;
		case 20:
			report.setImposeType(cellStr);
			break;
		case 17:
			report.setYear(cellStr);
			break;
		case 18:
			report.setMonth(cellStr);
			break;
		case 21:
			report.setPeriod(cellStr);
			break;
		case 22:
			report.setStartTime(sdf.parse(cellStr));
			break;
		case 23:
			report.setEndTime(sdf.parse(cellStr));
			break;
		case 24:
			report.setDeclareDate(sdf.parse(cellStr));
			break;
		case 25:
			report.setDeadLine(cellStr);
			break;
		case 26:
			report.setDeclareWay(cellStr);
			break;
		case 27:
			report.setEntryDate(sdf.parse(cellStr));
			break;
		
		}

		return report;
	}

	@Override
	public String ConvertCellStr(Cell cell, String cellStr) throws SQLException {
		// TODO Auto-generated method stub
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
				compList.add(addMessageSearchVO);
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
		return compList;
	}

	public MessageSearchVO addingComp(int j, MessageSearchVO messageSearchVO,
			String cellStr) throws SQLException, ParseException{
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
	
}