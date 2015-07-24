package com.shove.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * http请求工具类
 */
public class HttpUtil {
	private static Log log = LogFactory.getLog(FormUtil.class);

	/**
	 * 发送请求 ,返回请求结果
	 */
	public static String http(String url, Map<String, String> params) {
		URL u = null;
		HttpURLConnection con = null;

		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for (Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
				log.info(e.getKey() + "=============" + e.getValue());
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		// 发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "utf-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject json = null;
		try {
			json = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			json = new JSONObject();
			json.put("RespCode", 3838);
			String str = buffer.toString();
			try {
				int index = 0;
				if(str.contains("<p class=\"title\">")){
					str = str.substring(str.indexOf("<p class=\"title\">")+17);
					index = str.indexOf("</p>");
					str = str.substring(0, index);
				}else if (str.contains("<p class=\"desc\">")){
					str = str.substring(str.indexOf("<p class=\"desc\">") + 17);
					index = str.indexOf("</p>");
					str = str.substring(0, index);
				}
			} catch (Exception ee) {
				str = "未知异常";
			}
			json.put("RespDesc", "汇付返回结果:"+str);
		}
		return json.toString();
	}
}
