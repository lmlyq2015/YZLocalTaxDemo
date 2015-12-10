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
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.service.MessageService;
import com.util.TaxUtil;
import com.vos.JsonResult;
import com.vos.Message;
import com.vos.NotificationVo;

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
	public void sendNotificationMsg(@RequestParam("data") String data,HttpServletResponse response) {
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
			@RequestParam("page") Integer pageNumber) {
		int count = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		List<Message> pageList = new ArrayList<Message>();
		List<Message> totalList = new ArrayList<Message>();
		int intPageNum=pageNumber==null||pageNumber<=0?1:pageNumber;
		int intPageSize=pageSize==null||pageSize<=0?2:pageSize;
		int firstRow = (pageNumber - 1) * pageSize;
		try{
			pageList = messageService.getMessageResultList(firstRow, pageSize);
			
			count = messageService.getMessageResultCount();
			map.put("rows", pageList);
			map.put("total", count);
			return map;
		}catch(Exception e) {
			e.printStackTrace();
			map.put("error", false);
			return map;
		}
		
	}
}
