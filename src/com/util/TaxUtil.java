package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.vos.MessageResult;
import com.vos.NotificationVo;
import com.vos.PayNotificationVo;
import com.vos.ReportNotificationVo;

public class TaxUtil {

	public static String MESSAGE_ACCOUNT = null;
	
	public static String MESSAGE_PASSWORD = null;
	
	public static String MESSAGE_INTERFACE = null;
	
	public static String SEND_URL = null;
	
	public static String CURRENT_USER;
	
	public final static String RESULT_INFO = "info";
	
	public final static String RESULT_ERROR = "error";
	
	public final static String	MESSAEG_SEND_SUCCESS = "操作成功，请在消息列表查看回执状态";
	
	public final static String MESSAGE_SEND_FAILED = "操作失败";
	
	public final static String MESSAGE_NOTIFICATION_MESSAGE_TYPE = "1";
	
	public final static String MESSAGE_REPORT_REMINDER_TYPE = "2";
	
	public final static String MESSAGE_PAYMENT_REMINDER_TYPE = "3";
	
	public final static String MESSAGE_STATUS_SUCCESS = "1";
	
	public final static String MESSAGE_STATUS_SYSTEM_ISSUE = "0";
	
	public final static String MESSAGE_STATUS_ACCOUNT_ISSUE = "-1";
	
	public final static String MESSAGE_STATUS_PASSWORD_ISSUE = "-2";
	
	public final static String MESSAGE_STATUS_MOBILE_ISSUE = "-3";
	
	public final static String MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE = "-4";
	
	public final static String MESSAGE_STATUS_CONTENT_CHAR_ISSUE = "-6";
	
	public final static String MESSAGE_STATUS_BALANCE_ISSUE = "-7";
	
	public final static String MESSAGE_STATUS_UNKNOW_ISSUE = "-8";
	
	public final static String MESSAGE_STATUS_SUCCESS_MSG = "发送成功";
	
	public final static String MESSAGE_STATUS_SYSTEM_ISSUE_MSG = "服务器出错";
	
	public final static String MESSAGE_STATUS_ACCOUNT_ISSUE_MSG = "账户不存在或禁用";
	
	public final static String MESSAGE_STATUS_PASSWORD_ISSUE_MSG = "账户密码不正确";
	
	public final static String MESSAGE_STATUS_MOBILE_ISSUE_MSG = "接收号码有误";
	
	public final static String MESSAGE_STATUS_CONTENT_TOOLONG_ISSUE_MSG = "短信内容长度超出规定字符";
	
	public final static String MESSAGE_STATUS_CONTENT_CHAR_ISSUE_MSG = "短信内容含有非法字符";
	
	public final static String MESSAGE_STATUS_BALANCE_ISSUE_MSG = "八信账号余额不足";
	
	public final static String MESSAGE_STATUS_UNKNOW_MSG = "提交过于频繁";
	
	public final static String OPERATE_SUCCESS_MSG = "操作成功";
	
	public final static String OPERATE_FAIL_MSG = "操作失败";
	
	public final static String MESSAGE_RECEVIER_TAXER = "1";
	public final static String MESSAGE_RECEVIER_ADMIN = "2";
	public final static String MESSAGE_RECEVIER_LAWER = "3";
	
	public final static String CALL_TYPE_NORMAL = "普通来电";
	
	public final static String CALL_TYPE_DIALOUT = "外呼通话";
	
	public final static String CALL_TYPE_TRANSFER = "转接电话";
	
	public final static String CALL_TYPE_DIALTRANSFER = "外呼转接";

	public static String path = "C:/"; 
	public static String filenameTemp; 
	
	static{
		FileInputStream in = null;
		Properties pro = new Properties();
		try {
			in = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath()  + "util.properties");
			pro.load(in);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MESSAGE_ACCOUNT = pro.getProperty("MESSAGE_ACCOUNT");
		MESSAGE_PASSWORD = pro.getProperty("MESSAGE_PASSWORD");
		MESSAGE_INTERFACE = pro.getProperty("MESSAGE_INTERFACE");
		SEND_URL = pro.getProperty("SEND_URL");	
		// TODO Auto-generated constructor stub
	}
	
	public static String getPayContent(NotificationVo vo) {
//		String content = "尊敬的" + vo.getTaxAgentName() + "会计，您所在的企业名称为：" + vo.getTaxName()
//				+ "（识别号为" + vo.getTaxId() + "），目前尚有如下税款未缴纳,查看链接："+ SEND_URL +"/YZLocalTaxDemo/getContentByWebPage?mesId="+vo.getMesId()+"&taxId="+vo.getTaxId()+"，请尽快缴纳。联系电话：28862886。";
//		System.out.println(content);
//		return content;
		String content = "尊敬的会计，您所在的企业名称为：" + vo.getTaxName()
				+ "（识别号为" + vo.getTaxId() + "），目前尚有如下税款未缴纳,查看链接："+ SEND_URL +"/YZLocalTaxDemo/YZ/"+vo.getMesId()+"/"+vo.getTaxId()+"，请尽快缴纳。联系电话：4000235082。";
		System.out.println(content);
		return content;
	}

	public static String getPaySqlContent(NotificationVo vo) {
		// TODO Auto-generated method stub
		String content = "尊敬的会计，您所在的企业名称为：" + vo.getTaxName()
				+ "（识别号为" + vo.getTaxId() + "），目前尚有如下税款未缴纳：缴款期限为" + vo.getStartTime() + "至" + vo.getEndTime() + "的" +  vo.getImposeType() + "，未缴税款为"
				+ vo.getTotalTax() + "，请尽快缴纳。联系电话：4000235082。";
		System.out.println(content);
		return content;
	}
	
	public static String getReportContent(NotificationVo vo) {
		String content = "尊敬的会计，您所在的企业名称为：" + vo.getTaxName()
				+ "（识别号为" + vo.getTaxId() + "），目前尚有如下税款未申报,查看链接："+SEND_URL+"/YZLocalTaxDemo/YZ/"+vo.getMesId()+"/"+vo.getTaxId()+"，请尽快向鄞州地税局直属分局申报。联系电话：4000235082。";
		System.out.println(content);
		return content;
	}

	public static String getReportSqlContent(NotificationVo vo) {
		// TODO Auto-generated method stub
		String content = "尊敬的会计，您所在的企业名称为：" + vo.getTaxName()
				+ "（识别号为" + vo.getTaxId() + "），目前尚有如下税款未申报：所属日期为" + vo.getStartTime() + "至" + vo.getEndTime() + "的"
				+ vo.getImposeType() + "，请尽快向鄞州地税局直属分局申报。联系电话：4000235082。";
		System.out.println(content);
		return content;
	}
	
	public static String getMessageContent(NotificationVo vo) {
		String content = "鄞州地税发布一条新通知，查看链接："+SEND_URL+"/YZLocalTaxDemo/YZ/"+vo.getMesId()+"/"+vo.getTaxId();
		System.out.println(content);
		return content;
	}
	
	
	public static String sendMessage(String content,String mobile,String plantime) {
		String result = null;
		try {
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(MESSAGE_INTERFACE);
			post.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");// 在头文件中设置转码
			NameValuePair[] data = { new NameValuePair("action", "msgsend"),
					new NameValuePair("user", MESSAGE_ACCOUNT),
					new NameValuePair("mobile", mobile),
					new NameValuePair("content", content),
					new NameValuePair("time", formatDate(plantime)),
					new NameValuePair("msgid", "0"),
					new NameValuePair("hashcode", convertHashCode(MESSAGE_PASSWORD)) };
			post.setRequestBody(data);

			client.executeMethod(post);
			Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();
			System.out.println("statusCode:" + statusCode);
			for (Header h : headers) {
				System.out.println(h.toString());
			}
			result = new String(post.getResponseBodyAsString().getBytes(
					"UTF-8"));
			System.out.println(result);

			post.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String convertHashCode(String pwd) {
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = pwd.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString().toUpperCase();
	}
	public static String formatDate(String plantDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			if (!plantDate.equals("") && plantDate != null) {
				date = sdf.parse(plantDate);
			} else {
				return null;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf.format(date);
		
	}
	public static void main(String[] args) {
	}
	public static MessageResult parseResult(String result) {
		try {
			MessageResult mr = new MessageResult();
			String[] arr = result.split("&");
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].contains("errid=")) {
					String[] msg = arr[i].split("=");
					mr.setErrid(msg[1]);
				} else if (arr[i].contains("msgid=")) {
					String[] msg = arr[i].split("=");
					mr.setMsgid(msg[1]);
				} else if (arr[i].contains("fails=")) {
					String[] msg = arr[i].split("=");
					if (msg.length == 2) {
						mr.setFails(msg[1]);
					}
				}
			}
			return mr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
	
	
	/**
	 * 创建文件
	 * 
	 * @throws IOException
	 */
	public static void creatTxtFile() throws IOException {
		filenameTemp = path + "test.txt";
		File filename = new File(filenameTemp);
		if (!filename.exists()) {
			filename.createNewFile();
		}
	}

	/**
	 * 写文件
	 * 
	 * @param newStr
	 *            新内容
	 * @throws IOException
	 */
	public static boolean writeTxtFile(String newStr) throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		boolean flag = false;
		String filein = newStr + "  ---发送测试与"+ DateUtils.getNowTime() + "\r\n";
		String temp = "";

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			// 文件路径
			File file = new File(filenameTemp);
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

			// 保存该文件原有的内容
			for (int j = 1; (temp = br.readLine()) != null; j++) {
				buf = buf.append(temp);
				// System.getProperty("line.separator")
				// 行与行之间的分隔符 相当于“\n”
				buf = buf.append(System.getProperty("line.separator"));
			}
			buf.append(filein);

			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			flag = true;
		} catch (IOException e1) {
			// TODO 自动生成 catch 块
			throw e1;
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return flag;
	}

	}
