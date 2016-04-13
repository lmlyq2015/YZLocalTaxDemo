package com.poi;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellStyle;

import com.vos.NotificationVo;

public class FillReportManager {
	public static void fillReport(HSSFSheet worksheet, int startRowIndex,  
            int startColIndex, List<NotificationVo> datasource) {  
  
        // Row offset  
        startRowIndex += 2;  
  
        // Create cell style for the body  
        HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();  
        bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);  
        bodyCellStyle.setWrapText(false); //是否自动换行.  
  
        // Create body  
        for (int i=startRowIndex; i+startRowIndex-2< datasource.size()+2; i++) {  
            // Create a new row  
            HSSFRow row = worksheet.createRow((short) i+1);  
  
            // Retrieve the id value  
            HSSFCell cell1 = row.createCell(startColIndex+0);  
            cell1.setCellValue(datasource.get(i-2).getTaxId());  
            cell1.setCellStyle(bodyCellStyle);  
  
 
            HSSFCell cell2 = row.createCell(startColIndex+1);  
            cell2.setCellValue(datasource.get(i-2).getTaxName());  
            cell2.setCellStyle(bodyCellStyle);  
  
 
            HSSFCell cell3 = row.createCell(startColIndex+2);  
            cell3.setCellValue(datasource.get(i-2).getSendDate());  
            cell3.setCellStyle(bodyCellStyle);  
  

            HSSFCell cell4 = row.createCell(startColIndex+3);  
            cell4.setCellValue(datasource.get(i-2).getResultMsg()); 
            cell4.setCellStyle(bodyCellStyle);  
  
 
            HSSFCell cell5 = row.createCell(startColIndex+4);  
            cell5.setCellValue(datasource.get(i-2).getReceiver());  
            cell5.setCellStyle(bodyCellStyle);  
        }  
    }  
}
