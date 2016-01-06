package com.poi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class Writer {
	private static Logger logger = Logger.getLogger("service");  
	  
    public static void write(HttpServletResponse response, HSSFSheet worksheet) throws IOException {  
  
        logger.debug("Writing report to the stream");  
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {  
        	 ByteArrayOutputStream os = new ByteArrayOutputStream();
        	 worksheet.getWorkbook().write(os);  
            // Retrieve the output stream  
            OutputStream outputStream = response.getOutputStream();  
            
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            
            // Write to the output stream  

            
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(outputStream);
            
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            
            // 清除缓存  
            outputStream.flush();  
            bos.flush();
        } catch (Exception e) {  
            logger.error("报表输入失败!");  
        }  finally {
        	if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }  
}
