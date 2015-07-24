package com.hehenian.web.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;

public class HttpClientUtils {
	private static final Logger logger = Logger.getLogger(HttpClientUtils.class);

	public static IResult<?> post(String url, Map<String, String> params) {
		IResult<String> result = new ResultSupport<String>();
		HttpClient client = new DefaultHttpClient();
		InputStream inputStream = null;
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : params.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
			}

			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
			HttpResponse response = client.execute(post);
			inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}
			result.setSuccess(true);
			result.setModel(resultString.toString());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setModel("发送请求错误:" + e.getMessage());
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception ignore) {
			}
			client.getConnectionManager().shutdown();
		}
		return result;
	}
}
