package com.poi;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class Writer {
	private static Logger logger = Logger.getLogger("service");  
	  
    public static void write(HttpServletResponse response, HSSFSheet worksheet) {  
  
        logger.debug("Writing report to the stream");  
        try {  
            // Retrieve the output stream  
            ServletOutputStream outputStream = response.getOutputStream();  
            // Write to the output stream  
            worksheet.getWorkbook().write(outputStream);  
            // 清除缓存  
            outputStream.flush();  
  
        } catch (Exception e) {  
            logger.error("报表输入失败!");  
        }  
    }  
}
