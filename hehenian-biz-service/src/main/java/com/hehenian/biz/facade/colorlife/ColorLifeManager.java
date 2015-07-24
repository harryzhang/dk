package com.hehenian.biz.facade.colorlife;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.hehenian.biz.common.util.HttpClientUtils;
import com.hehenian.biz.common.util.Md5Utils;

public class ColorLifeManager {

	public static void main1(String[] args) throws Exception {
		String baseUrl = "http://capi.5ker.com:6888";
		String businessURL = "/1.0/activity/paySyntony";
		String key = "3";
		String secret = "%21%40%23JSD";
		// String baseUrl = "http://capi.colourlife.com";
		//login(baseUrl, key, secret);
		
		Map<String, String> parameterMap = new LinkedHashMap<String, String>();
		parameterMap.put("userid", "186489");
		parameterMap.put("orderSn", "1040000141103142411620");
		parameterMap.put("orderAmount", "10000");
		parameterMap.put("orderSuccessTime", "1415070168");
		parameterMap.put("orderPaySn", "paysn0001");
		
		String ret = callColorOperate(baseUrl, businessURL, key, secret,
				parameterMap);
		
		System.out.println(getVal("Status", ret));

	}

	public static void main(String[] args) throws Exception {
		String base64 = new String(Base64.encodeBase64("{\"userId\":\"25\",\"userName\":\"大哥\",\"areaName\":\"碧水龙庭\"}".getBytes()));
		System.out.println(base64);
		String s = DigestUtils.md5Hex("{\"userId\":\"25\",\"userName\":\"大哥\",\"areaName\":\"碧水龙庭\"}");
		
		
		String baseUrl = "http://capi.5ker.com:6888";
		String businessURL = "/1.0/activity/feesuccess";
		String key = "3";
		String secret = "%21%40%23JSD";
		// String baseUrl = "http://capi.colourlife.com";
		// login(baseUrl, key, secret);

		Map<String, String> parameterMap = new LinkedHashMap<String, String>();
		parameterMap.put("userId", "186489");
		parameterMap.put("orderSN", "1040000141103142411620");
		parameterMap.put("feeAmount", "10000");
		parameterMap.put("feeTime", "1415070168");
		parameterMap.put("status", "1");
		parameterMap.put("remark", "");

		String ret = callColorOperate(baseUrl, businessURL, key, secret,
				parameterMap);

		System.out.println(getVal("Status", ret));

	}

	/**
	 * 从返回的JSON串里取值
	 * 
	 * @param key
	 * @param jsonStr
	 * @return
	 */
	private static Object getVal(String key, String jsonStr) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> retJson;
		try {
			retJson = mapper.readValue(jsonStr,
					new TypeReference<HashMap<String, Object>>() {
					});
			return retJson.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 彩生活登录三步骤之一， 版本
	 * 
	 * @param baseUrl
	 * @return
	 * @throws Exception
	 */
	private static String stepOne(String baseUrl) throws Exception {
		// access_token=colourlife
		String versinURL = baseUrl + "/1.0";
		Map<String, String> parameterMap = new LinkedHashMap<String, String>();
		versinURL = HttpClientUtils.makeUrl(versinURL, parameterMap);
		String ret = HttpClientUtils.get(versinURL);
		String retVal = String.valueOf(getVal("ok", ret));
		return retVal;
	}

	/**
	 * 彩生活登录三步骤之二， 时间戳
	 * 
	 * @param baseUrl
	 * @return
	 * @throws Exception
	 */
	private static int stepTwo(String baseUrl) throws Exception {
		// ts?ts=1414983525
		Map<String, String> parameterMap = new LinkedHashMap<String, String>();
		String tsUrl = baseUrl + "/1.0/ts";
		// parameterMap.put("access_token", "colourlife");
		String ts = String.valueOf(System.currentTimeMillis() / 1000);
		parameterMap.put("ts", ts);
		tsUrl = HttpClientUtils.makeUrl(tsUrl, parameterMap);
		String ret = HttpClientUtils.get(tsUrl);

		Integer diff = (Integer) getVal("diff", ret);
		return diff;

	}

	/**
	 * 彩生活登录三步骤之三， 认证
	 * 
	 * @param baseUrl
	 * @param diff
	 * @param key
	 * @param secret
	 * @throws Exception
	 */
	private static String stepThree(String baseUrl, long diff, String key,
			String secret) throws Exception {
		// "/1.0/auth?key=3&ts=1414983733&ve=1.0.0&secret=%21%40%23JSD";
		long currentTime = System.currentTimeMillis() / 1000;
		String ts = String.valueOf(currentTime + Long.valueOf(diff));

		Map<String, String> parameterMap = new LinkedHashMap<String, String>();

		parameterMap.put("key", key);
		parameterMap.put("ts", ts);
		parameterMap.put("ve", "1.0.0");
		parameterMap.put("secret", secret);
		// parameterMap.put("access_token", "colourlife");

		String authURL = "/1.0/auth";
		authURL = HttpClientUtils.makeUrl(authURL, parameterMap);
		String sign = Md5Utils.MD5(authURL, "UTF-8");

		parameterMap.remove("secret");
		parameterMap.put("sign", sign);
		authURL = HttpClientUtils.makeUrl(authURL, parameterMap);

		StringBuilder signUrl = new StringBuilder();
		signUrl.append(baseUrl).append(authURL);
		String ret = HttpClientUtils.get(signUrl.toString());

		String retVal = String.valueOf(getVal("ok", ret));
		return retVal;
	}

	/**
	 * 获取绑定客服经理绑定资源的接口
	 * @param baseUrl
	 * @param businessURL
	 * @param key
	 * @param secret
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public static String callColorResouce(String baseUrl, String businessURL,
			String key, String secret, Map<String, String> parameterMap)
			throws Exception {

		stepOne(baseUrl);
		int diff = stepTwo(baseUrl);
		stepThree(baseUrl, diff, key, secret);


		long currentTime = System.currentTimeMillis() / 1000;
		String ts = String.valueOf(currentTime + Long.valueOf(diff));

		Map<String,String> urlMap = new LinkedHashMap<String, String>();
		urlMap.put("key", key);
		urlMap.put("ts", ts);
		urlMap.put("ve", "1.0.0");
		urlMap.put("secret", secret);

		String signURL = HttpClientUtils.makeUrl(businessURL, urlMap);
		String sign = Md5Utils.MD5(signURL, "UTF-8");

		StringBuilder signUrlSB = new StringBuilder();
		signUrlSB.append(baseUrl).append(signURL).append("&sign=").append(sign);

		return HttpClientUtils.post(signUrlSB.toString(), parameterMap);

	}
	
	/**
	 * 调用彩色活的服务
	 * 
	 * @param baseUrl
	 *            彩生活地址
	 * @param businessURL
	 *            业务操作的相对地址
	 * @return
	 * @throws Exception
	 */
	public static String callColorOperate(String baseUrl, String businessURL,
			String key, String secret, Map<String, String> parameterMap)
			throws Exception {

		stepOne(baseUrl);
		int diff = stepTwo(baseUrl);
		stepThree(baseUrl, diff, key, secret);


		long currentTime = System.currentTimeMillis() / 1000;
		String ts = String.valueOf(currentTime + Long.valueOf(diff));

		parameterMap.put("key", key);
		parameterMap.put("ts", ts);
		parameterMap.put("ve", "1.0.0");
		parameterMap.put("secret", secret);

		String signURL = HttpClientUtils.makeUrl(businessURL, parameterMap);
		String sign = Md5Utils.MD5(signURL, "UTF-8");

		parameterMap.remove("secret");
		parameterMap.put("sign", sign);
		signURL = HttpClientUtils.makeUrl(businessURL, parameterMap);

		StringBuilder signUrlSB = new StringBuilder();
		signUrlSB.append(baseUrl).append(signURL);

		return HttpClientUtils.post(signUrlSB.toString(), parameterMap);

	}

	public static String login(String baseUrl, String key, String secret)
			throws Exception {
		stepOne(baseUrl);
		int diff = stepTwo(baseUrl);
		return stepThree(baseUrl, diff, key, secret);
	}

}
