package com.hehenian.biz.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.hehenian.biz.common.exception.BusinessException;

public class HttpClientUtils {
	private static final Logger logger = Logger
			.getLogger(HttpClientUtils.class);

	private static final String parameterJoin = "&";
	private static final String parameterEqual = "=";

	public static String makeUrl(String baseUrl,
			Map<String, String> parameterMap) {
		StringBuffer parasb = new StringBuffer(baseUrl);
		if (null != parameterMap) {
			parasb.append("?");
			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				parasb.append(entry.getKey()).append(parameterEqual)
						.append(entry.getValue()).append(parameterJoin);

			}
		}
		return parasb.substring(0, parasb.length() - 1);
	}

	public static String appendParameter(String baseUrl,
			Map<String, String> parameterMap) {
		StringBuffer parasb = new StringBuffer(baseUrl);
		if (null != parameterMap) {
			parasb.append(parameterJoin);
			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				String val = "";
				try {
					if(entry.getValue() != null){
						val = URLEncoder.encode(entry.getValue(), "UTF-8");
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					val = entry.getValue();
				}
				parasb.append(entry.getKey()).append(parameterEqual)
						.append(val)
						.append(parameterJoin);

			}
		}
		return parasb.substring(0, parasb.length() - 1);
	}

	public static String get(String url) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		logger.info("调用url：" + url);//http://lcdev.hehenian.cn/loan.do?loanDate=2015/06/08&sign=9991b60c7dd3407108a22638f56190a4
		try {
			HttpGet httpGet = new HttpGet(url);
			response = httpclient.execute(httpGet);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}

			logger.info(url + "回复:" + resultString.toString());

			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}
	}

	public static String get(String url, Map<String, String> params)
			throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		logger.info("调用url：" + url);
		try {
			String newUrl = appendParameter(url, params);
			HttpGet httpGet = new HttpGet(newUrl);
			response = httpclient.execute(httpGet);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}

			logger.info(url + "回复:" + resultString.toString());

			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}
	}

	public static String post(String url, Map<String, String> params)
			throws Exception {

		logger.info("调用参数：" + params);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : params.keySet()) {
				nameValuePairs
						.add(new BasicNameValuePair(key, params.get(key)));
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

			response = httpclient.execute(httpPost);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}
			logger.info("汇付回复:"
					+ URLDecoder.decode(resultString.toString(), "UTF-8"));

			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}
	}
	
	/**
	 * 上标
	 */
	public static String sbPost(String url, Map<String, String> params)
			throws Exception {
		logger.info("调用参数：" + params);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : params.keySet()) {
				nameValuePairs
						.add(new BasicNameValuePair(key, params.get(key)));
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

			response = httpclient.execute(httpPost);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}
			try {
				logger.info("上标回复:"
						+ URLDecoder.decode(resultString.toString(), "UTF-8"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}
	}
	
	/**
	 * 生成PDF协议
	 * @auther liminglmf
	 * @date 2015年7月3日
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getCallBack(String url, Map<String, String> params) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		logger.info("调用url：" + url);
		try {
			String newUrl = genNewUrl(url, params);
			HttpGet httpGet = new HttpGet(newUrl);
			response = httpclient.execute(httpGet);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}
			logger.info(newUrl + "返回:" + resultString.toString());
			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}
	}

	private static String genNewUrl(String url, Map<String, String> params) {
		StringBuffer parasb = new StringBuffer(url);
		if (null != params) {
			parasb.append("?");
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String val = "";
				try {
					if(entry.getValue() != null){
						val = URLEncoder.encode(entry.getValue(), "UTF-8");
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					val = entry.getValue();
				}
				parasb.append(entry.getKey()).append(parameterEqual)
						.append(val).append(parameterJoin);

			}
		}
		return parasb.substring(0, parasb.length() - 1);
	}
	
	/**
	 * 
	 * @auther liminglmf
	 * @date 2015年7月3日
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String commonPost(String url, Map<String, String> params)
			throws Exception {
		logger.info("请求URL:>>>"+url+"调用参数：>>>>" + params);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : params.keySet()) {
				nameValuePairs
						.add(new BasicNameValuePair(key, params.get(key)));
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

			response = httpclient.execute(httpPost);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}
			try {
				logger.info("请求"+url+"回复>>>>>:"
						+ URLDecoder.decode(resultString.toString(), "UTF-8"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}
	}
}
