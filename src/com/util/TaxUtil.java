package com.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class TaxUtil {

	private final static String MESSAGE_ACCOUNT = "shihui.zhu@nbhmit.com";
	
	private final static String MESSAGE_PASSWORD = "123456";
	
	private static String CURRENT_USER;
	
	private final static String RESULT_INFO = "info";
	
	private final static String RESULT_ERROR = "error";
	
	private final static String	MESSAEG_SEND_SUCCESS = "消息发送成功，请在消息列表查看回执状态";
	
	private final static String MESSAGE_SEND_FAILED = "消息发送失败，已保存至草稿箱,请重新发送";
	
	private final static String MESSAGE_INTERFACE = "http://18dx.cn/API/Services.aspx";
	
	public static String sendMessage(String content,String mobile,String plantime) {
		try {
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(MESSAGE_INTERFACE);
			post.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");// 在头文件中设置转码
			NameValuePair[] data = { new NameValuePair("action", "msgsend"),
					new NameValuePair("user", MESSAGE_ACCOUNT),
					new NameValuePair("mobile", mobile),
					new NameValuePair("content", content),
					new NameValuePair("time", plantime),
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
			String result = new String(post.getResponseBodyAsString().getBytes(
					"UTF-8"));
			System.out.println(result);

			post.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String convertHashCode(String pwd) {
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
	private static String formatDate(String plantDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(plantDate);
		return sdf.format(date);
		
	}
	public static void main(String[] args) {
		//sendMessage("你好","13634184445","2015/11/30");
	}
}
