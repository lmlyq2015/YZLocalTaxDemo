package com.service.imp;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
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
	public void exportXLS(HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		// 1.创建一个 workbook
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 2.创建一个 worksheet
		HSSFSheet worksheet = workbook.createSheet("Report");

		// 3.定义起始行和列
		int startRowIndex = 0;
		int startColIndex = 0;

		// 4.创建title,data,headers
		Layouter.buildReport(worksheet, startRowIndex, startColIndex);

		// 5.填充数据
		FillReportManager.fillReport(worksheet, startRowIndex, startColIndex,
				getReport());

		// 6.设置reponse参数
		String fileName = "Report.xls";
		response.setHeader("Content-Disposition", "inline; filename="
				+ fileName);
		// 确保发送的当前文本格式
		response.setContentType("application/vnd.ms-excel");

		// 7. 输出流
		Writer.write(response, worksheet);
	}

	@Override
	public List<Report> readReport(InputStream inp) throws SQLException {
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

				for (int j = 0; j < row.getLastCellNum(); j++) {

					Cell cell = row.getCell(j); // 获得单元格(cell)对象

					// 转换接收的单元格
					cellStr = ConvertCellStr(cell, cellStr);

					// 将单元格的数据添加至一个对象
					addReport = addingReport(j, report, cellStr);

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
	public List<Report> getReport() throws SQLException {
		// TODO Auto-generated method stub
		return poiDao.getReport();
	}

	@Override
	public int[] insertReport(List<Report> list) throws SQLException {
		// TODO Auto-generated method stub
		return poiDao.insertReport(list);
	}

	@Override
	public Report addingReport(int j, Report report, String cellStr)
			throws SQLException {
		// TODO Auto-generated method stub
		switch (j) {
		case 0:
			report.setId(null);
			break;
		case 1:
			report.setTaxId(cellStr);
			break;
		case 2:
			report.setImposeType(cellStr);
			break;
		case 3:
			report.setYear(cellStr);
			break;
		case 4:
			report.setMonth(cellStr);
			break;
		case 5:
			report.setPeriod(cellStr);
			break;
		
		case 9:
			report.setDeadLine(cellStr);
			break;
		case 10:
			report.setDeclareWay(cellStr);
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
}
