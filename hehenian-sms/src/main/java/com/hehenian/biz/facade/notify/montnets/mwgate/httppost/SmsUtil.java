package com.hehenian.biz.facade.notify.montnets.mwgate.httppost;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hehenian.biz.facade.notify.montnets.mwgate.common.StaticValue;

/**
 * 后台获取连接处理请求的类
 * @author Administrator
 *
 */
public class SmsUtil {
	
	private String host = "http://"+StaticValue.ip+":"+StaticValue.port;
	private String url = "/MWGate/wmgw.asmx/";
	
	/**
	 * 获取上行/状态报告等
	 * 返回值：
			　　null 无信息
			接收信息内容列表(最高维数为500)格式说明：
			信息类型(上行标志0),日期,时间,上行源号码,上行目标通道号,*,*,上行信息内容 
			或
			信息类型(状态报告标志1),日期,时间,信息编号,通道号,手机号,MongateSendSubmit时填写的MsgId,*,状态值,详细错误原因
			例如：
			1,2008-01-23 15:43:34,15986756631,10657302056780408,*,*,上行信息内容1
			2,2008-01-23 15:43:34,0518153837115735,10657302056780408,13265661403,456123457895210124,
			*,0,DELIVRD
			注：格式中的*号是备用字段 第一标志位的0表示上行和状态报告，1表示上行，2表示状态报告
	 */
	public String[] MongateGetDeliverForPost(Params params,  boolean bKeepAlive, Object connection){
		String[] Result = null;
		List<String> Lists = new ArrayList<String>();
		try {
			String Message =null;
			if(!bKeepAlive){
				 Message = executePost(params, host+url+"MongateGetDeliver");
			}else{
				 Message = executePost(params, host+url+"MongateGetDeliver",(HttpClient)connection);
			}
			  if(Message != null && Message !="" && Message.contains("<string>"))
			  {
				  Document doc=DocumentHelper.parseText(Message);
		          Element el = doc.getRootElement();
		          Iterator it = el.elementIterator();
		          while(it.hasNext()) 
		          {    
					     Element elm = (Element) it.next();    
					     Lists.add(elm.getText());
			      } 
		          if(Lists != null && Lists.size() >0)
		          {
		        	  Result = new String[Lists.size()];
		        	  for(int i=0;i<Lists.size();i++)
		        	  {
		        		  Result[i]= Lists.get(i);
		        	  }
		          }
			  }
			  else 
			  {
				  return null;
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return Result;
	}

	/**
	 * 查询余额接口
	 * 返回值：正数或0：帐户可发送条数
			       -1:登陆失败
			                 其他错误见附录
	 */
	public Integer MongateQueryBalanceForPost(Params params,  boolean bKeepAlive, Object connection) {
		Integer result =null;
	    
		try {
			String Message=null;
			if(!bKeepAlive){
				Message = executePost(params, host+url+"MongateQueryBalance");
			}else{
				Message = executePost(params, host+url+"MongateQueryBalance",(HttpClient)connection);
			}
			  if(Message != null && Message != "")
			  {
				  Document doc=DocumentHelper.parseText(Message);
		          Element el = doc.getRootElement();
		          result = Integer.parseInt(el.getText());
			  } 
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return result;
	}
	

	
	/**
	 * 短信息发送接口
	 * 返回值：错误描述对应说明
			发送成功：信息编号，如：-8485643440204283743或1485643440204283743
			错误返回：-1	参数为空。信息、电话号码等有空指针，登陆失败
					-2	电话号码个数超过100
					-10	申请缓存空间失败
					-11	电话号码中有非数字字符
					-12	有异常电话号码
					-13	电话号码个数与实际个数不相等
					-14	实际号码个数超过100
					-101	发送消息等待超时
					-102	发送或接收消息失败
					-103	接收消息超时
					-200	其他错误
					-999	web服务器内部错误
	 */
	public String MongateSendSubmitForPost(Params params, boolean bKeepAlive, Object connection) {
		String result =null;
		try {
			String Message=null;
			 if(!bKeepAlive){
				 Message = executePost(params, host+url+"MongateSendSubmit");
			 }else{
				 Message = executePost(params, host+url+"MongateSendSubmit",(HttpClient)connection);
			 }
			 if(Message != null && Message != "")
			  {
		          Document doc=DocumentHelper.parseText(Message);
		          Element el = doc.getRootElement();
		          result = el.getText();
			  } 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	/**
	 * 短信息发送接口（不同内容群发，可自定义不同流水号，自定义不同扩展号）
	 * 注意：由于单个短信结构体中各元素间，以及短信包中各个短信结构体间采用的分隔符分隔，所以要求短信结构体中各元素的值中不能包含分隔符（,和/），否则将导致解析错误。
                   短信包中最大包含200个短信结构体，超过200个时200个以后的将丢失。
                   返回值：
			错误描述对应说明
			发送成功：信息编号，如：-8485643440204283743或1485643440204283743
			错误返回： 
			-1 	参数为空。信息、电话号码等有空指针，登陆失败
			-11	电话号码中有非数字字符
			-12	有异常电话号码
			-101	发送消息等待超时
			-102	发送或接收消息失败
			-103	接收消息超时
			-200	其他错误
			-999	web服务器内部错误
	 */
	public String MongateMULTIXSendForPost(Params params, boolean bKeepAlive, Object connection)
	{
		String result =null;
		try {
			String Message=null;
			if(!bKeepAlive){
				Message = executePost(params, host+url+"MongateMULTIXSend");
			}else {
				Message = executePost(params, host+url+"MongateMULTIXSend",(HttpClient)connection);
			}
			 if(Message != null && Message != "")
			  {
		          Document doc=DocumentHelper.parseText(Message);
		          Element el = doc.getRootElement();
		          result = el.getText();
			  } 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 使用post请求
	 * @param obj
	 * @param httpUrl
	 * @return
	 * @throws Exception
	 */
	private String executePost(Object obj, String httpUrl) throws Exception {

		String result = "";
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

		String fieldName = null;
		String fieldNameUpper = null;
		Method getMethod = null;
		String value = null;
		for (int i = 0; i < fields.length; i++)   {
			fieldName = fields[i].getName();
			fieldNameUpper = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
			getMethod = cls.getMethod("get" + fieldNameUpper);
			value = (String) getMethod.invoke(obj);
			if(value != null) {
				params.add(new BasicNameValuePair(fieldName, value)); 
			}
		}
		HttpPost httppost = new HttpPost(httpUrl);
		
		// 设置参数的编码UTF-8
		httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
	
		HttpClient httpclient = new DefaultHttpClient();
		
		HttpEntity entity = httpclient.execute(httppost).getEntity();
		
		if(entity != null && entity.getContentLength() != -1) {
			result=EntityUtils.toString(entity);
		}
		httpclient.getConnectionManager().shutdown();
		return result;

	}
	
	/**
	 * 使用post请求
	 * @param obj
	 * @param httpUrl
	 * @return
	 * @throws Exception
	 */
	private String executePost(Object obj, String httpUrl,HttpClient httpclient) throws Exception {

		String result = "";
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

		String fieldName = null;
		String fieldNameUpper = null;
		Method getMethod = null;
		String value = null;
		for (int i = 0; i < fields.length; i++)   {
			fieldName = fields[i].getName();
			fieldNameUpper = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
			getMethod = cls.getMethod("get" + fieldNameUpper);
			value = (String) getMethod.invoke(obj);
			if(value != null) {
				params.add(new BasicNameValuePair(fieldName, value)); 
			}
		}
		HttpPost httppost = new HttpPost(httpUrl);
		 //设置为长连接，服务端判断有此参数就不关闭连接。
		httppost.setHeader("Connection", "Keep-Alive");    

		// 设置参数的编码UTF-8
		httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
	
		//连接从方法中传入
		//HttpClient httpclient = new DefaultHttpClient();
		
		HttpEntity entity = httpclient.execute(httppost).getEntity();
		
		if(entity != null && entity.getContentLength() != -1) {
			result=EntityUtils.toString(entity);
		}
		//长连接不需要关闭连接
		//httpclient.getConnectionManager().shutdown();
		return result;

	}

}
