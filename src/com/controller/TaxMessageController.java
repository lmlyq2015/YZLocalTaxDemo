package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.service.MessageService;
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
			Message vo = (Message) object.toBean(JSONObject.fromObject(msgData), Message.class);
			JSONArray json = JSONArray.fromObject(taxEntArr);
			List <NotificationVo> list  = json.toList(json, NotificationVo.class);
			pw.print(vo.toString() + list.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
