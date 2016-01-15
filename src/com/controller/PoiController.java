package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
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
import org.springframework.web.multipart.MultipartFile;

import com.service.PoiService;
import com.vos.JsonResult;
import com.vos.MessageSearchVO;
import com.vos.Pay;
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
	    @RequestMapping(value = "/exportFailMsg", method = RequestMethod.POST)  
	    public void getXLS(@RequestParam("msgId") int msgId,HttpServletResponse response) throws SQLException {  
	    	poiService.exportXLS(msgId,response);  
	    }  
	  
	    /** 
	     * 读取excel报表（Report） 
	     * @throws SQLException 
	     * @throws ParseException 
	     */  
	    @RequestMapping(value = "/read", method = RequestMethod.POST)      
	    public void getReadReport(@RequestParam MultipartFile file,HttpServletResponse response)  
	            throws IOException, SQLException, ParseException {  
	    	
	        List<Report> list = poiService.readReport(file.getInputStream());  
	         
	        PrintWriter pw = null;
			try {
				pw = response.getWriter();
				if(list.size()!=0){
				poiService.insertReport(list);
				}
				JsonResult jr = new JsonResult();
				jr.setMsg("导入成功");
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
	     * @throws Exception 
	     */  
	    @RequestMapping(value = "/readComp", method = RequestMethod.POST)  
	    
	    public void getReadComp(@RequestParam MultipartFile file,HttpServletResponse response)  
	            throws Exception {  
	    	List<MessageSearchVO> list = null;
	    	try{
	    		list = poiService.readComp(file.getInputStream());  
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		throw e;
	    	}
	        
	         
	        PrintWriter pw = null;
			try {
				pw = response.getWriter();
				if(list.size()!=0){
				poiService.insertComp(list);
				}
				JsonResult jr = new JsonResult();
				jr.setMsg("导入成功");
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
	     * 读取excel报表（Pay） 
	     * @throws SQLException 
	     * @throws ParseException 
	     */  
	    @RequestMapping(value = "/readPay", method = RequestMethod.POST)      
	    public void getReadPay(@RequestParam MultipartFile file,HttpServletResponse response)  
	            throws IOException, SQLException, ParseException {    	
	        List<Pay> list = poiService.readPay(file.getInputStream());  
	         
	        PrintWriter pw = null;
			try {
				pw = response.getWriter();
				if(list.size()!=0){
				poiService.insertPay(list);
				}
				JsonResult jr = new JsonResult();
				jr.setMsg("导入成功");
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
