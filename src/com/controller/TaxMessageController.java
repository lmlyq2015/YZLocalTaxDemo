package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.service.MessageService;
import com.service.PayService;
import com.service.ReportService;
import com.util.TaxUtil;
import com.vos.JsonResult;
import com.vos.Message;
import com.vos.MessageSearchVO;
import com.vos.NotificationVo;
import com.vos.Pay;
import com.vos.PayNotificationVo;
import com.vos.PaySearchVO;
import com.vos.PayVO;
import com.vos.Report;
import com.vos.ReportNotificationVo;
import com.vos.ReportSearchVO;
import com.vos.ReportVO;
import com.vos.User;
import com.vos.UserSearchVo;

@Controller
@SessionAttributes
public class TaxMessageController {
	@Resource(name = "messageService")
	private MessageService messageService;

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	@RequestMapping("/sendNotificationMsg")
	public void sendNotificationMsg(@RequestParam("data") String data,HttpServletResponse response,HttpSession session) {
		PrintWriter pw= null;
		try {
			pw = response.getWriter();
			String str = URLDecoder.decode(data,"UTF-8");
			String[]arrData = str.split("=");
			String msgData = arrData[0];
			String taxEntArr = arrData[1];
			JSONObject object = JSONObject.fromObject(msgData);
			Message msg = (Message) object.toBean(JSONObject.fromObject(msgData), Message.class);
			JSONArray json = JSONArray.fromObject(taxEntArr);
			List <NotificationVo> list  = json.toList(json, NotificationVo.class);
			msg.setVoList(list);
			msg.setSendBy(((User)session.getAttribute("current_user")).getEmpId());
			int id = messageService.sendNotificationMsg(msg);
			if (id > 0) {
				pw.print(messageSuc());
			} else {
				pw.print(messageErr());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String messageSuc() {
		JsonResult jr = new JsonResult();
		jr.setResult(TaxUtil.RESULT_INFO);
		jr.setMsg(TaxUtil.MESSAEG_SEND_SUCCESS);
		JSONObject json = JSONObject.fromObject(jr);
		return json.toString();
	}
	public String messageErr() {
		JsonResult jr = new JsonResult();
		jr.setResult(TaxUtil.RESULT_ERROR);
		jr.setMsg(TaxUtil.MESSAGE_SEND_FAILED);
		JSONObject json = JSONObject.fromObject(jr);
		return json.toString();
	}
	@RequestMapping("/getMessageResultList")
	@ResponseBody
	public Map<String,Object> getMessageResultList(@RequestParam("rows") Integer pageSize,
			@RequestParam("page") Integer pageNumber,@ModelAttribute Message message) {
		int count = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		List<Message> pageList = new ArrayList<Message>();
		List<Message> totalList = new ArrayList<Message>();
		int intPageNum=pageNumber==null||pageNumber<=0?1:pageNumber;
		int intPageSize=pageSize==null||pageSize<=0?2:pageSize;
		int firstRow = (pageNumber - 1) * pageSize;
		try{
			pageList = messageService.getMessageResultList(firstRow, pageSize,message);
			
			count = messageService.getMessageResultCount(message);
			map.put("rows", pageList);
			map.put("total", count);
			return map;
		}catch(Exception e) {
			e.printStackTrace();
			map.put("error", false);
		}
		return null;
	}
	
	@Resource(name = "reportService")
	private ReportService reportService;

	public ReportService getReportService() {
		return reportService;
	}
	@RequestMapping("/getAllReport")
	@ResponseBody
	public Map<String, Object> getAllReport(@RequestParam("rows") Integer pageSize,@RequestParam("page") Integer pageNumber,HttpServletResponse response,@ModelAttribute ReportSearchVO reportSearchVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Report> pageList = new ArrayList<Report>();
		int intPageNum=pageNumber==null||pageNumber<=0?1:pageNumber;
		int intPageSize=pageSize==null||pageSize<=0?10:pageSize;
		int firstRow = (pageNumber - 1) * pageSize;
		try {
			pageList = reportService.getAllReport(firstRow, pageSize,reportSearchVO);
			int count = reportService.getReportCount(reportSearchVO);
			map.put("rows", pageList);
			map.put("total", count);
			return map;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("error", false);
		}
		return null;
		
	}

	@RequestMapping("/sendReportMsg")
	public void sendReportMsg(@RequestParam("data") String data,HttpServletResponse response) {
		PrintWriter pw= null;
		try {
			pw = response.getWriter();
			String str = URLDecoder.decode(data,"UTF-8");
			String[]arrData = str.split("=");
			String msgData = arrData[0];
			String taxEntArr = arrData[1];
			JSONObject object = JSONObject.fromObject(msgData);
			ReportVO msg = (ReportVO) object.toBean(JSONObject.fromObject(msgData), ReportVO.class);
			JSONArray json = JSONArray.fromObject(taxEntArr);
			List <ReportNotificationVo> list  = json.toList(json, ReportNotificationVo.class);
			for(int i =  0 ;i < list.size()-1;i++){ 
				    for(int j = list.size()-1;j > i;j--)   { 
				      if(list.get(j).getTaxId().equals(list.get(i).getTaxId())){ 
				        list.remove(j); 
				      } 
				    } 
				  }
			msg.setVoList(list);
			int id = reportService.sendReportMsg(msg);
			if (id > 0) {
				for(int i =  0 ;i < list.size();i++){ 
				reportService.deleteReport(list.get(i).getTaxId());
				}
				pw.print(messageSuc());
			} else {
				pw.print(messageErr());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/getFailMsgState")
	@ResponseBody
	public Map<String,Object> getFailMsgState(@RequestParam("msgId") int msgId,@RequestParam("rows") Integer pageSize,@RequestParam("page") Integer pageNumber,
												HttpServletResponse response,@RequestParam("failCount") int failCount){
		Map<String, Object> map = new HashMap<String, Object>();
		List<NotificationVo> pageList = new ArrayList<NotificationVo>();
		int intPageNum=pageNumber==null||pageNumber<=0?1:pageNumber;
		int intPageSize=pageSize==null||pageSize<=0?10:pageSize;
		int firstRow = (pageNumber - 1) * pageSize;
		try {
			pageList = messageService.getFailMsgStateList(firstRow, pageSize, msgId);			
			map.put("rows", pageList);
			map.put("total", failCount);
			return map;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("error", false);
		}
		return null;
	}
	@RequestMapping("/reSendMsg")
	public void reSendMsg(@RequestParam("data") String data,HttpServletResponse response) {
		String str;
		PrintWriter pw= null;
		try {
			pw = response.getWriter();
			str = URLDecoder.decode(data,"UTF-8");
			String[]arrData = str.split("=");
			String msgData = arrData[0];
			String recData = arrData[1];
			JSONObject object = JSONObject.fromObject(msgData);
			Message msg = (Message) object.toBean(JSONObject.fromObject(msgData), Message.class);
			NotificationVo rec = (NotificationVo)object.toBean(JSONObject.fromObject(recData), NotificationVo.class);
			messageService.reSendMsg(msg, rec);
			pw.print(messageSuc());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getAllComp")
	@ResponseBody
	public Map<String, Object> getAllComp(@RequestParam("rows") Integer pageSize,@RequestParam("page") Integer pageNumber,HttpServletResponse response,@ModelAttribute MessageSearchVO messageSearchVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MessageSearchVO> pageList = new ArrayList<MessageSearchVO>();
		int intPageNum=pageNumber==null||pageNumber<=0?1:pageNumber;
		int intPageSize=pageSize==null||pageSize<=0?10:pageSize;
		int firstRow = (pageNumber - 1) * pageSize;
		try {
			pageList = messageService.getAllComp(firstRow, pageSize,messageSearchVO);
			int count = messageService.getCompCount(messageSearchVO);
			map.put("rows", pageList);
			map.put("total", count);
			return map;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("error", false);
		}
		return null;
		
	}
	@RequestMapping("/login")
	public void login(@RequestParam("loginName") String loginName,@RequestParam("password") String password,HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
		User user = new User();
		user.setLoginName(loginName);
		user.setPassword(password);
		PrintWriter pw = response.getWriter();
		String incode = (String)request.getParameter("code");   
	    String rightcode = (String)request.getSession().getAttribute("rCode");  
		try {
			User u = messageService.validateUser(user);
			if(u != null && (incode.equals(rightcode))){
				session.setAttribute("current_user", u);
				TaxUtil.CURRENT_USER = loginName;
				pw.print("{\"result\":" + true + ",\"msg\":\"" + "登录成功" + "\"}");
			} else {
				pw.print("{\"result\":" + false + ",\"msg\":\"" + "信息验证错误" + "\"}");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	@RequestMapping("/getAllUser")
	@ResponseBody
	public Map<String, Object> getAllUser(@RequestParam("rows") Integer pageSize,@RequestParam("page") Integer pageNumber,HttpServletResponse response,@ModelAttribute UserSearchVo searchVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> pageList = new ArrayList<User>();
		int intPageNum=pageNumber==null||pageNumber<=0?1:pageNumber;
		int intPageSize=pageSize==null||pageSize<=0?10:pageSize;
		int firstRow = (pageNumber - 1) * pageSize;
		try {
			pageList = messageService.getAllUser(firstRow, pageSize, searchVo);
			int count = messageService.getUserCount(searchVo);
			map.put("rows", pageList);
			map.put("total", count);
			return map;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("error", false);
		}
		return null;
		
	}
	@RequestMapping("/isExistEmp")
	@ResponseBody
	public void isExistEmp(HttpServletResponse response,@RequestParam("empId") String empId) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();		
			int count = messageService.isExistEmp(empId);
			if (count > 0) {
				pw.print("{\"result\":"+count+",\"msg\":\""+"该员工已存在"+"\"}");
			} else {
				pw.print("{\"result\":"+count+",\"msg\":\""+"该员工号可以使用"+"\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/isExistLoginName")
	@ResponseBody
	public void isExistLoginName(HttpServletResponse response,@RequestParam("loginName") String loginName) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();		
			int count = messageService.isExistLoginName(loginName);
			if (count > 0) {
				pw.print("{\"result\":"+count+",\"msg\":\""+"用户名已注册"+"\"}");
			} else {
				pw.print("{\"result\":"+count+",\"msg\":\""+"用户名可用"+"\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/addNewEmp")
	@ResponseBody
	public void addNewEmp(HttpServletResponse response,@RequestParam("data") String data) {
		PrintWriter pw = null;
		User user = new User();
		int result = 0;
		try{
			pw = response.getWriter();
			String str = URLDecoder.decode(data,"UTF-8");
			JSONObject object = JSONObject.fromObject(str);
			user.setEmpId(object.getString("empId2"));
			user.setLoginName(object.getString("loginName2"));
			user.setPassword(object.getString("password"));
			user.setEmail(object.getString("email"));
			user.setContact(object.getString("mobile"));
			user.setSendToSelf(object.getString("sendToSelf"));
			result = messageService.addNewEmp(user);
			if (result > 0) {
				pw.print("{\"result\":"+result+",\"msg\":\""+"用户添加成功"+"\"}");
			} else {
				pw.print("{\"result\":"+result+",\"msg\":\""+"用户添加失败"+"\"}");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	@RequestMapping("/saveEmpChanges")
	@ResponseBody
	public void saveEmpChanges(HttpServletResponse response,@RequestParam("data") String data) throws Exception {
		PrintWriter pw = null;
		JsonResult jr = new JsonResult();
		try {
			pw = response.getWriter();
			String str = URLDecoder.decode(data,"UTF-8");
			List<User> list = JSONArray.toList(JSONArray.fromObject(str),User.class);
			System.out.println(list);
			messageService.saveEmpChanges(list);
			jr.setResult(TaxUtil.RESULT_INFO);
			jr.setMsg(TaxUtil.OPERATE_SUCCESS_MSG);
			pw.print(JSONObject.fromObject(jr).toString());
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@RequestMapping("/getContentByWebPage")
	@ResponseBody
	public String getContentByWebPage(@RequestParam("mesId") Integer mesId,@RequestParam("taxId") String taxId,HttpServletResponse response) throws SQLException {
		
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			 response.setCharacterEncoding("utf-8");       
			    response.setContentType("text/html; charset=utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.print(reportService.getContentByWebPage(mesId,taxId));
		
		
		return null;		
	}
	@Resource(name = "payService")
	private PayService payService;

	public PayService getPayService() {
		return payService;
	}
	@RequestMapping("/getAllPay")
	@ResponseBody
	public Map<String, Object> getAllPay(@RequestParam("rows") Integer pageSize,@RequestParam("page") Integer pageNumber,HttpServletResponse response,@ModelAttribute PaySearchVO paySearchVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Pay> pageList = new ArrayList<Pay>();
		int intPageNum=pageNumber==null||pageNumber<=0?1:pageNumber;
		int intPageSize=pageSize==null||pageSize<=0?10:pageSize;
		int firstRow = (pageNumber - 1) * pageSize;
		try {
			pageList = payService.getAllPay(firstRow, pageSize,paySearchVO);
			int count = payService.getPayCount(paySearchVO);
			map.put("rows", pageList);
			map.put("total", count);
			return map;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("error", false);
		}
		return null;		
	}
	
	@RequestMapping("/sendPayMsg")
	public void sendPayMsg(@RequestParam("data") String data,HttpServletResponse response) {
		PrintWriter pw= null;
		try {
			pw = response.getWriter();
			String str = URLDecoder.decode(data,"UTF-8");
			String[]arrData = str.split("=");
			String msgData = arrData[0];
			String taxEntArr = arrData[1];
			JSONObject object = JSONObject.fromObject(msgData);
			PayVO msg = (PayVO) object.toBean(JSONObject.fromObject(msgData), PayVO.class);
			JSONArray json = JSONArray.fromObject(taxEntArr);
			List <PayNotificationVo> list  = json.toList(json, PayNotificationVo.class);
			for(int i =  0 ;i < list.size()-1;i++){ 
				    for(int j = list.size()-1;j > i;j--)   { 
				      if(list.get(j).getTaxId().equals(list.get(i).getTaxId())){ 
				        list.remove(j); 
				      } 
				    } 
				  }
			msg.setVoList(list);
			int id = payService.sendPayMsg(msg);
			if (id > 0) {
				for(int i =  0 ;i < list.size();i++){ 
				payService.deletePay(list.get(i).getTaxId());
				}
				pw.print(messageSuc());
			} else {
				pw.print(messageErr());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/changePwd")
	public void changePassword(@RequestParam("newpwd") String newPwd,HttpServletResponse response,HttpSession session) {
		PrintWriter pw = null;
		JsonResult jr = new JsonResult();
		try{
			pw = response.getWriter();
			User user  = (User) session.getAttribute("current_user");
			if (user != null) {
				int count = messageService.updatePassword(user.getEmpId(), newPwd);
				if (count > 0) {
					jr.setResult(TaxUtil.RESULT_INFO);
					jr.setMsg(TaxUtil.OPERATE_SUCCESS_MSG);
					pw.print(JSONObject.fromObject(jr).toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
