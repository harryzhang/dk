/**  
 * @Project: memcache-util
 * @Package com.hehenian.common.utils
 * @Title: HttpClientUtils.java
 * @Description: HTTP GET&POST
 *
 * @author: zhanbmf
 * @date 2015-4-14 上午11:20:55
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
	public static final String UTF8 = "UTF-8";
	public static final String GBK = "GBK";
	
	/**
	 * get
	 * @param url 请求url
	 * @param charset 请求编码
	 * @return
	 * @author: zhanbmf
	 * @date 2015-4-14 下午3:17:29
	 */
	public static String get(String url, String charset) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url); 
		try{
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String result = "";
			if(null != entity){
				result = EntityUtils.toString(entity, charset);
			}
			//System.out.println("响应内容: " + result);
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown(); //关闭连接,释放资源
		}
		return null;
	}
	
	/**
	 * post
	 * @param url
	 * @param map 参数键值对
	 * @param charset 请求编码
	 * @return
	 * @author: zhanbmf
	 * @date 2015-4-14 下午3:17:39
	 */
	public static String post(String url, Map map, String charset) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost hp = new HttpPost(url); 
		try{
			if(map != null && map.size() > 0) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				Set keys = map.keySet();
				for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
					String name = (String) iterator.next();
					String value = (String) map.get(name);
					params.add(new BasicNameValuePair(name, value));
				}
				
				hp.setEntity(new UrlEncodedFormEntity(params, charset));
			}

			HttpResponse response = httpClient.execute(hp);
			HttpEntity entity = response.getEntity();
			String result = "";
			if(null != entity){
				result = EntityUtils.toString(entity, charset);
			}
			//System.out.println("响应内容: " + result);
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown(); //关闭连接,释放资源
		}
		return null;
	}
}
