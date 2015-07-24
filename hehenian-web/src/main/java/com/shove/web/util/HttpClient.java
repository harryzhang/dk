package com.shove.web.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpClient {

	
	public static final String REQUEST_METHOD_POST = "POST";
	public static final String REQUEST_METHOD_GET = "GET";

	private HttpURLConnection connection;
	private String paramStr;
	private boolean error;

	public HttpClient(String urlStr, String method) {

		this(urlStr, method, 30 * 1000);

	}

	/**
	 * @param urlStr
	 * @param method
	 * @param timerout
	 */
	public HttpClient(String urlStr, String method, int timerout) {

		URL url = null;

		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setUseCaches(false);
			connection.setConnectTimeout(timerout);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Cache-Control", "no-cache");
			connection.connect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			error = true;
		} catch (IOException e) {
			e.printStackTrace();
			error = true;
		}

	}

	public HttpClient addParams(String key, String value) {
		if (paramStr == null || paramStr.length() == 0)
			paramStr = key + "=" + value;
		else
			paramStr += "&" + key + "=" + value;
		return this;
	}

	public void addParams(Map<String, Object> params) {
		Set<String> keys = params.keySet();
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			String key = it.next();
			addParams(key, params.get(key).toString());
		}
	}

	public InputStream excuteInputStream() {
		InputStream in = null;
		if (!error) {
			try {
				if (paramStr != null && paramStr.length() > 0) {
					connection.getOutputStream().write(paramStr.getBytes());
				}
				in = connection.getInputStream();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return in;
	}

	public String excute() throws IOException {
		String info = "";
		if (!error) {
			if (paramStr != null && paramStr.length() > 0) {
				connection.getOutputStream().write(paramStr.getBytes());
			}
			InputStream inputStream = connection.getInputStream();

			BufferedReader bf = new BufferedReader(new InputStreamReader(
					inputStream));
			String s = null;
			while ((s = bf.readLine()) != null) {
				info += s;
			}

		} else
			info = null;
		return info;
	}

	public int getLength() {
		return connection.getContentLength();
	}

	public boolean isError() {
		return error;
	}


	/**
	 *  创建服务端 交互 并 返回响应
	 * @param form 参数 格式 "?xx=x1&bb=b1"
	 * @param toUrl 访问 地址
	 * @param method  访问方式  默认POST
	 * @param charset  编码方式 默认UTF-8
	 * @return
	 * @throws Exception
	 */
	public static HttpURLConnection getHttpURLConnection(String form, String toUrl,
			String method, String charset) throws Exception {

		URL url = new URL(toUrl);
		byte[] data = form.getBytes();
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setUseCaches(false);
		conn.setDoOutput(true);
		conn.setRequestMethod(method.equals("") ? REQUEST_METHOD_POST : REQUEST_METHOD_GET);
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Charset", charset.equals("") ? "UTF-8"
				: charset);
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(data);
		outStream.flush();
		return conn;
	}
	/**
	 *  创建服务端 交互 并 返回响应
	 * @param form 参数 格式 "?xx=x1&bb=b1"
	 * @param toUrl 访问 地址
	 * @param name 查找的关键字
	 * @param method  访问方式  默认POST
	 * @param charset  编码方式 默认UTF-8
	 * @return
	 * @throws Exception
	 */
	public static HttpURLConnection getHttpURLSearch(String form, String toUrl,String name,
			String method, String charset) throws Exception {

		byte[] data = form.getBytes();
		URL url = new URL(toUrl+java.net.URLEncoder.encode(name));
		  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setUseCaches(false);
		conn.setDoOutput(true);
		conn.setRequestMethod(method.equals("") ? REQUEST_METHOD_POST : REQUEST_METHOD_GET);
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Charset", charset.equals("") ? "UTF-8"
				: charset);
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(data);
		outStream.flush();
		return conn;
	}
	
}
