package com.test;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

//public class SendMessageTest {
//
//	public static void main(String[] args)throws Exception
//	{
//
//	HttpClient client = new HttpClient();
//	PostMethod post = new PostMethod("http://18dx.cn/API/Services.aspx"); 
//	post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");//在头文件中设置转码
//	NameValuePair[] data ={ new NameValuePair("action", "msgsend"),new NameValuePair("user", "本站用户名"),new NameValuePair("mobile", "手机号码"),new NameValuePair("content","短信内容"),new NameValuePair("time","发送时间"),new NameValuePair("msgid","批次ID"),new NameValuePair("hashcode","校验码")};
//	post.setRequestBody(data);
//
//	client.executeMethod(post);
//	Header[] headers = post.getResponseHeaders();
//	int statusCode = post.getStatusCode();
//	System.out.println("statusCode:"+statusCode);
//	for(Header h : headers)
//	{
//	System.out.println(h.toString());
//	}
//	String result = new String(post.getResponseBodyAsString().getBytes("UTF-8")); 
//	System.out.println(result);
//
//
//	post.releaseConnection();
//
//	}
//
//}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/*
功能:		web.cr6868.com HTTP接口 发送短信

说明:		http://web.cr6868.com/asmx/smsservice.aspx?name=登录名&pwd=接口密码&mobile=手机号码&content=内容&sign=签名&stime=发送时间&type=pt&extno=自定义扩展码
*/
public class xioo {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//发送内容
		String content = "77祝贺亲爱的妈妈感恩节快乐"; 
		String sign="宁波汇民科技";
		
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer("http://web.cr6868.com/asmx/smsservice.aspx?");

		// 向StringBuffer追加用户名
		sb.append("name=15258117490");

		// 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
		sb.append("&pwd=3690A88E7A3F360D5B076864417A");

		// 向StringBuffer追加手机号码
		sb.append("&mobile=13634184445,15258117490");

		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content="+URLEncoder.encode(content,"UTF-8"));
		
		//追加发送时间，可为空，为空为及时发送
		sb.append("&stime=");
		
		//加签名
		sb.append("&sign="+URLEncoder.encode(sign,"UTF-8"));
		
		//type为固定值pt  extno为扩展码，必须为数字 可为空
		sb.append("&type=pt&extno=");
		// 创建url对象
		//String temp = new String(sb.toString().getBytes("GBK"),"UTF-8");
		System.out.println("sb:"+sb.toString());
		URL url = new URL(sb.toString());

		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		InputStream is =url.openStream();

		//转换返回值
		String returnStr = xioo.convertStreamToString(is);
		
		// 返回结果为‘0，20140009090990,1，提交成功’ 发送成功   具体见说明文档
		System.out.println(returnStr);
		// 返回发送结果

		

	}
	/**
	 * 转换返回值类型为UTF-8格式.
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {    
        StringBuilder sb1 = new StringBuilder();    
        byte[] bytes = new byte[4096];  
        int size = 0;  
        
        try {    
        	while ((size = is.read(bytes)) > 0) {  
                String str = new String(bytes, 0, size, "UTF-8");  
                sb1.append(str);  
            }  
        } catch (IOException e) {    
            e.printStackTrace();    
        } finally {    
            try {    
                is.close();    
            } catch (IOException e) {    
               e.printStackTrace();    
            }    
        }    
        return sb1.toString();    
    }

}

