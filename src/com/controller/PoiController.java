package com.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.service.PoiService;
import com.vos.Report;

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
	     * @throws SQLException 
	     */  
	    @RequestMapping(value = "/export", method = RequestMethod.GET)  
	    public void getXLS(HttpServletResponse response) throws SQLException {  
	    	poiService.exportXLS(response);  
	    }  
	  
	    /** 
	     * 读取excel报表 
	     * @throws SQLException 
	     */  
	    @RequestMapping(value = "/read", method = RequestMethod.POST)  
	    
	    public String getReadReport(@RequestParam MultipartFile file)  
	            throws IOException, SQLException {  
	    	
	        List<Report> list = poiService.readReport(file.getInputStream());  
	        poiService.insertReport(list);  
	        
	        
	        
	        return "message/addedReport";  
	  
	    }  
}
