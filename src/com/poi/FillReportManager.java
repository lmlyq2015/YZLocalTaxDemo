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
            cell1.setCellValue(datasource.get(i-2).getTaxNo());  
            cell1.setCellStyle(bodyCellStyle);  
  
 
            HSSFCell cell2 = row.createCell(startColIndex+1);  
            cell2.setCellValue(datasource.get(i-2).getTaxName());  
            cell2.setCellStyle(bodyCellStyle);  
  
 
            HSSFCell cell3 = row.createCell(startColIndex+2);  
            cell3.setCellValue(datasource.get(i-2).getStatus());  
            cell3.setCellStyle(bodyCellStyle);  
  

            HSSFCell cell4 = row.createCell(startColIndex+3);  
            cell4.setCellValue(datasource.get(i-2).getResultMsg()); 
            cell4.setCellStyle(bodyCellStyle);  
  
 
            HSSFCell cell5 = row.createCell(startColIndex+4);  
            cell5.setCellValue(datasource.get(i-2).getReceiver());  
            cell5.setCellStyle(bodyCellStyle);  
          
//
//            HSSFCell cell6 = row.createCell(startColIndex+5);  
//            cell6.setCellValue(datasource.get(i-2).getPeriod());  
//            cell6.setCellStyle(bodyCellStyle);  
//            
//            HSSFCell cell7 = row.createCell(startColIndex+6);  
//            cell7.setCellValue(datasource.get(i-2).getStartTime());  
//            cell7.setCellStyle(bodyCellStyle); 
//            
//            HSSFCell cell8 = row.createCell(startColIndex+7);  
//            cell8.setCellValue(datasource.get(i-2).getEndTime());  
//            cell8.setCellStyle(bodyCellStyle); 
//            
//            HSSFCell cell9 = row.createCell(startColIndex+8);  
//            cell9.setCellValue(datasource.get(i-2).getDeclareDate());  
//            cell9.setCellStyle(bodyCellStyle); 
//            
//            HSSFCell cell10 = row.createCell(startColIndex+9);  
//            cell10.setCellValue(datasource.get(i-2).getDeadLine());  
//            cell10.setCellStyle(bodyCellStyle); 
//            
//            HSSFCell cell11 = row.createCell(startColIndex+10);  
//            cell11.setCellValue(datasource.get(i-2).getDeclareWay());  
//            cell11.setCellStyle(bodyCellStyle); 
//            
//            HSSFCell cell12 = row.createCell(startColIndex+11);  
//            cell12.setCellValue(datasource.get(i-2).getEntryDate());  
//            cell12.setCellStyle(bodyCellStyle); 
        }  
    }  
}
