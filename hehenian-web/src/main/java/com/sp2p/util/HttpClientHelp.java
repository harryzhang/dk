package com.sp2p.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class HttpClientHelp {

	// 创建默认的httpClient客户端
	private HttpClient httpClient = null;

	// get模式
	private HttpGet httpGet = null;

	// post模式
	private HttpPost httpPost = null;

	// 执行请求，获取服务器响应
	private HttpResponse response = null;

	// 请求的实体
	private HttpEntity entity = null;

	// 输入流
	private InputStream is = null;
	
	/**
	 * 释放资源
	 * @param httpGet
	 * @param httpPost
	 * @param httpClient
	 */
	public void releaseSource(HttpGet httpGet,
			HttpPost httpPost, HttpClient httpClient) {
		if (httpGet != null) {
			httpGet.abort();
		}
		if (httpPost != null) {
			httpPost.abort();
		}
		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * get方式提交并返回InputStream
	 * @param url 提交的url
	 * @param client HttpClient
	 * @param get HttpGet
	 * @return
	 */
	public InputStream byGetMethod(HttpClient client,HttpGet get) {
		try {
			// 执行请求
			response = client.execute(get);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				entity = response.getEntity();
				// 将entity返回InputStream
				is = entity.getContent();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return is;
	}

	/**   
	 * @MethodName: byGetMethodToInputStream  
	 * @Param: HttpClientHelp  
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午08:20:05
	 * @Return:    
	 * @Descb: 提交返回流
	 * @Throws:
	*/
	public InputStream byGetMethodToInputStream(String url){
		StringBuffer buff = new StringBuffer();
		// 创建线程安全的httpClient
		httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		// 创建一个HttpGet请求，作为目标地址。
		httpGet = new HttpGet(url);

		try {
			response = httpClient.execute(httpGet);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				entity = response.getEntity();
				is = entity.getContent();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			releaseSource(httpGet, null, httpClient);
		}
		return is;
	}
	/**
	 * get方式提交并且返回Entity字符串
	 * @param url 提交的url
	 * @return
	 */
	public String byGetMethodToHttpEntity(String url) {
		StringBuffer buff = new StringBuffer();
		// 创建线程安全的httpClient
		httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		// 创建一个HttpGet请求，作为目标地址。
		httpGet = new HttpGet(url);

		try {
			response = httpClient.execute(httpGet);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				entity = response.getEntity();
				buff.append(EntityUtils.toString(entity)); 
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			releaseSource(httpGet, null, httpClient);
		}
		return buff.toString();
	}

	/**
	 * Post方式提交并且返回InputStream
	 * @param url 提交的url
	 * @param client HttpClient
	 * @param post  HttpPost
	 * @param params 队列参数
	 * @param urlEncoded  url编码
	 * @return
	 */
	public InputStream byPostMethod(HttpClient client,HttpPost post, List<NameValuePair> params,String urlEncoded) {
		try {
			if(params != null){
				// 格式化参数列表并提交
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params,
						urlEncoded);
				response.setEntity(uefEntity);
			}
			response = client.execute(post);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				entity = response.getEntity();
				// 将entity返回InputStream
				is = entity.getContent();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return is;
	}

	/**
	 * Post方式提交并且返回Entity字符串
	 * @param url 提交的url
	 * @param client HttpClient
	 * @param post  HttpPost
	 * @param params 队列参数
	 * @param urlEncoded  url编码
	 * @return
	 */
	public String byPostMethodToHttpEntity(String url,
			List<NameValuePair> params,String urlEncoded) {
		StringBuffer buff = new StringBuffer();
		// 创建线程安全的httpClient
		httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		// 创建一个HttpGet请求，作为目标地址。
		httpPost = new HttpPost(url);
		try {
			if(params != null){
				// 格式化参数列表并提交
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params,
						urlEncoded);
				response.setEntity(uefEntity);
			}
			response = httpClient.execute(httpPost);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				entity = response.getEntity();
				buff.append(EntityUtils.toString(entity)); 
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			releaseSource(null, httpPost, httpClient);
		}
		return buff.toString();
	}
}
