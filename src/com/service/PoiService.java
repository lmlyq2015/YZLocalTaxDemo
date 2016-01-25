package com.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.PoiDao;
import com.poi.FillReportManager;
import com.poi.Layouter;
import com.vos.MessageSearchVO;
import com.vos.NotificationVo;
import com.vos.Pay;
import com.vos.Report;
import com.vos.TaxId;
import com.poi.Writer;


public interface PoiService {

  
    public void exportXLS(int msgId,HttpServletResponse response) throws SQLException; 
    
    /** 
     * 读取报表 
     * @throws ParseException 
     */  
    public List<Report> readReport(InputStream inp) throws SQLException, ParseException;
  
    /** 
     * 从数据库获得所有的List信息. 
     * @throws SQLException 
     */  
    public List<NotificationVo> getFailMsg(int msgId) throws SQLException;
    
    /** 
     * 读取报表的数据后批量插入 
     * @throws SQLException 
     */  
    public int[] insertReport(List<Report> list) throws SQLException;
  
    /** 
     * 获得单元格的数据添加至report 
     *  
     * @param j 
     *            列数 
     * @param report 
     *            添加对象 
     * @param cellStr 
     *            单元格数据 
     * @return 
     * @throws ParseException 
     */  
    public Report addingReport(int j, Report report, String cellStr) throws SQLException, ParseException;
    
    /** 
     * 把单元格内的类型转换至String类型 
     */  
    public String ConvertCellStr(Cell cell, String cellStr) throws SQLException;

	public List<MessageSearchVO> readComp(InputStream inputStream) throws SQLException, ParseException;

	public int[] insertComp(List<MessageSearchVO> list) throws SQLException;
	
	public MessageSearchVO addingComp(int j, MessageSearchVO messageSearchVO,
			String cellStr) throws SQLException, ParseException;

	public List<Pay> readPay(InputStream inputStream) throws SQLException, ParseException;

	public Pay addingPay(int j, Pay pay, String cellStr) throws SQLException,
			ParseException ;

	public int[] insertPay(List<Pay> list) throws SQLException;

	public int selectUnequalNum() throws SQLException;

	public List<TaxId> selectUnequalTaxId() throws SQLException;
	
	public int selectUnequalNumForpay() throws SQLException;

	public List<TaxId> selectUnequalTaxIdForpay() throws SQLException;

}
