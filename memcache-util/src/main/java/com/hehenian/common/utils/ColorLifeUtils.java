/**  
 * @Project: hehenian-mobile
 * @Package com.hehenian.mobile.common.utils
 * @Title: ColorLifeUtils.java
 * @Description: 彩之云红包理财接口
 *
 * @author: zhanbmf
 * @date 2015-4-14 上午11:05:54
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0
 */
package com.hehenian.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.hehenian.common.utils.HttpClientUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class ColorLifeUtils {

	/**
	 * prod:http://capi.colourlife.com/
	 * test:http://capi.5ker.com:6888
	 */
	public static String API_URL = "http://capi.colourlife.com";
	public static final String KEY = "2";
	public static final String SECRET = "TestColourApi";
	
	static {
		String tomcatHome = System.getProperty("catalina.home");
		String setupFile = tomcatHome + File.separatorChar + "conf" + File.separatorChar + "hehenian-dqlc-config.properties";
		System.out.println(setupFile);
		
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(new File(setupFile));
			prop.load(is);
			System.out.println("prop.colorlife_api_url" + prop.get("colorlife_api_url"));
			API_URL = (String) prop.get("colorlife_api_url");
			if(StringUtils.isBlank(API_URL)) {
				API_URL = "http://capi.5ker.com:6888";
			}
			System.out.println("colorlife_api_url::::" + API_URL);
		} catch (Exception e) {
			e.printStackTrace();
			API_URL = "http://capi.colourlife.com";
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	/**
	 * 获取 API 版本信息
	 * @return
	 * @author: zhanbmf
	 * @date 2015-4-14 上午11:08:51
	 */
	public static String getAPIVersion() {
		return HttpClientUtils.get(API_URL + "/1.0", HttpClientUtils.UTF8);
	}
	
	/**
	 * 客户端与服务端对时
	 * @return
	 * @author: zhanbmf
	 * @date 2015-4-14 上午11:09:33
	 */
	public static String getTS() {
		return HttpClientUtils.get(API_URL + "/1.0/ts?ts=", HttpClientUtils.UTF8);
	}
	
	/**
	 * 检测客户端认证（其中diff为第二部获取的diff值，key和secret为彩之云平台分配给合作方的授权码）
	 * @param diff
	 * @return
	 * @author: zhanbmf
	 * @date 2015-4-14 上午11:11:19
	 * @return ts服务端收到的客户端时间戳|now服务端收到该请求时服务端当前时间戳|diff上述两个值的差值
	 * 
	 * 获取时间戳 ts = 1428987415
	 * 调整时间戳 ts = 2857969602
	 * 准备 MD5 的 url = "/1.0/auth?key=&ts=2857969602&ve=1.0.0&secret="
	 * MD5 签名值 md5("/1.0/auth?key=&ts=2857969602&ve=1.0.0&secret=") = "4684c8c7cdce88095e07e39943832c91"
	 * MD5 签名后的 url ="/1.0/auth?key=&ts=2857969602&ve=1.0.0&sign=4684c8c7cdce88095e07e39943832c91"
	 * 请求：GET /1.0/auth?key=&ts=2857969602&ve=1.0.0&sign=4684c8c7cdce88095e07e39943832c91
	 * 失败 [400]：{
	 * code: 400
	 * message: Forbidden: Invalid timestamp
	 * }
	 */
	public static String checkAuthentication() {
		String ts = getTS();
		JSONObject jb = JSONObject.fromObject(ts);
		String diff = jb.getString("diff");
		return HttpClientUtils.get(API_URL + "/1.0/auth", HttpClientUtils.UTF8);
	}
	
	/**
	 * 订单生成名称
	 * @param oa
	 * @return
	 * @author: zhanbmf
	 * @date 2015-4-14 下午1:32:37
	 * 
	 * 获取时间戳 ts = 1428993122
	 * 调整时间戳 ts = 2857975309
	 * 准备 MD5 的 url = "/1.0/elicai/getEmpBalance?key=&ts=2857975309&ve=1.0.0&secret="
	 * MD5 签名值 md5("/1.0/elicai/getEmpBalance?key=&ts=2857975309&ve=1.0.0&secret=") = "daf5ee5ed090132d481a03c29998bc4d"
	 * MD5 签名后的 url ="/1.0/elicai/getEmpBalance?key=&ts=2857975309&ve=1.0.0&sign=daf5ee5ed090132d481a03c29998bc4d"
	 * 请求：POST /1.0/elicai/getEmpBalance?key=&ts=2857975309&ve=1.0.0&sign=daf5ee5ed090132d481a03c29998bc4d
	 */
	public static float getEmpBalance(String oauser) {
		String ts = getTS();
		JSONObject jb = JSONObject.fromObject(ts);
		String tsdiff = jb.getString("diff");
		String sign = DigestUtils.md5Hex("/1.0/elicai/getEmpBalance?key=" + KEY + "&ts=" + tsdiff + "&ve=1.0.0&secret=" + SECRET);
		Map map = new HashMap();
		map.put("oauser", oauser);
		String result = HttpClientUtils.post(API_URL + "/1.0/elicai/getEmpBalance?key=" + KEY + "&ts=" + tsdiff + "&ve=1.0.0&sign=" + sign, map , HttpClientUtils.UTF8);
		JSONObject emp = JSONObject.fromObject(result);
		if("".equals(ObjectUtils.toString(emp.get("oauser"))) || "".equals(ObjectUtils.toString(emp.get("balance")))) {
			return 0;
		}
		
		return Float.valueOf(ObjectUtils.toString(emp.get("balance"), "0"));
	}
	
	/**
	 * 订单详情
	 * @param oauser
	 * @param thirdSn    理财订单号
	 * @param type       理财类型
	 * @param balance    消费的红包金额
	 * @param note       订单备注(可留空)
	 * @return ex        {"sn":"1080000150414163004853","thirdSn":"15242","balance":"23.50","status":"99"}
	 *                   status=99标识成功
	 * @author: zhanbmf
	 * @date 2015-4-14 下午3:41:25
	 */
	public static String elicaiSuccessSyntony(String oauser, String thirdSn, String type, String balance, String paySecret, String note) {
		String ts = getTS();
		JSONObject jb = JSONObject.fromObject(ts);
		String tsdiff = jb.getString("diff");
		String sign = DigestUtils.md5Hex("/1.0/elicai/elicaiSuccessSyntony?key=" + KEY + "&ts=" + tsdiff + "&ve=1.0.0&secret=" + SECRET);
		//String sign = DigestUtils.md5Hex("/1.0/elicai/elicaiSuccessSyntony?key=" + "" + "&ts=" + tsdiff + "&ve=1.0.0&secret=" + "");
		Map map = new HashMap();
		map.put("oauser", oauser);
		map.put("thirdSn", thirdSn);
		map.put("type", type);
		map.put("balance", balance);
		map.put("sign", DigestUtils.md5Hex(paySecret));
		map.put("note", note);
		String result = HttpClientUtils.post(API_URL + "/1.0/elicai/elicaiSuccessSyntony?key=" + KEY + "&ts=" + tsdiff + "&ve=1.0.0&sign=" + sign, map , HttpClientUtils.UTF8);
		JSONObject elicai = JSONObject.fromObject(result);
		String status = ObjectUtils.toString(elicai.get("status"));
		if(StringUtils.isBlank(status) || !status.equals("99")) {
			return null;
		}
		
		return result;
	}
	/**
	 * 
	 * @param oauser 第三方oa账号
	 * @param paySecret 支付密码
	 * @return
	 */
	public static int checkPayPwd(String oauser,String paySecret){
		String ts = getTS();
		JSONObject jb = JSONObject.fromObject(ts);
		String tsdiff = jb.getString("diff");
		String sign = DigestUtils.md5Hex("/1.0/elicai/checkPayPwd?key=" + KEY + "&ts=" + tsdiff + "&ve=1.0.0&secret=" + SECRET);
		Map map = new HashMap();
		map.put("oauser", oauser);
		map.put("sign", DigestUtils.md5Hex(paySecret));
		String result = HttpClientUtils.post(API_URL + "/1.0/elicai/checkPayPwd?key=" + KEY + "&ts=" + tsdiff + "&ve=1.0.0&sign=" + sign, map , HttpClientUtils.UTF8);
		JSONObject elicai = JSONObject.fromObject(result);
		String status = ObjectUtils.toString(elicai.get("state"));
		if("ok".equals(status)) {
			return 0;
		} else if("no".equals(status)) {
			return 1;
		} else if("noPayPwd".equals(status)) {
			return 2;
		}
		
		return -1;
	}
	
	
	public static JSONObject createColourOrder(String json){
		//colorid {"ok":1,"Sn":6605,"Status":99,"ColourSn":"1090000150508210605726","Message":"接收数据成功"}
		String ts = getTS();
		JSONObject jb = JSONObject.fromObject(ts);
		String tsdiff = jb.getString("diff");
		
		String sign = DigestUtils.md5Hex("/1.0/elicai/createColourOrder?key=" + KEY + "&ts=" + tsdiff + "&ve=1.0.0&secret=" + SECRET);
		Map map = new HashMap();
		map.put("json", json);
		String result = HttpClientUtils.post(API_URL + "/1.0/elicai/createColourOrder?key=" + KEY + "&ts=" + tsdiff + "&ve=1.0.0&sign=" + sign, map , HttpClientUtils.UTF8);
		JSONArray jsonArray = JSONArray.fromObject(result);
		JSONObject elicai = jsonArray.getJSONObject(0);
		return elicai;
		
	}
	/**  
	 * @Description: TODO
	 * @param args
	 * @author: zhanbmf
	 * @throws UnsupportedEncodingException 
	 * @date 2015-4-14 上午11:05:54
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String result = "";
//		getAPIVersion();
//		getTS();
//		checkAuthentication();
//		Float balance = getEmpBalance("xiongjian");
//		System.out.println("getEmpBalance:::" + balance);
//		result = elicaiSuccessSyntony("xiongjian", "115249", "12", "10", "paySecret", "test");
//		System.out.println("elicaiSuccessSyntony:::" + result);
//		
//		balance = getEmpBalance("test2");
//		System.out.println("getEmpBalance:::" + balance);
//		
//		result = elicaiSuccessSyntony("test2", "115259", "12", "10", "123456", "test");
//		JSONObject jb = JSONObject.fromObject(result);
//		System.out.println("elicaiSuccessSyntony:::" + result);
//		//System.out.println(jb.get("message"));
//		//http://m.hehenian.com/product/index.do?appkey=0001&channel=2&mobilePhone=13066839936&oa=xiongjian&realName=熊见&sign=0087fe15a2470df05dd772fce16b400d
//		System.out.println(DigestUtils.md5Hex("GnKPe2BMT7PV" + "2" + "15012703798" + "zhangjhmf123" + "张三"));
//		System.out.println(DigestUtils.md5Hex("GnKPe2BMT7PV" + "2" + "13066839936" + "xiongjian" + "熊见"));
//		
//		HttpClientUtils.get(API_URL + "/1.0/ts?ts=", HttpClientUtils.UTF8);
//		System.out.println(DigestUtils.md5Hex("GnKPe2BMT7PV" + "2" + "13800138001" + "test2"+"张继华"));
//		result = HttpClientUtils.get("http://m.hehenian.com/product/index.do?appkey=0001&channel=2&mobilePhone=13800138001&oa=test2&realName=张继华&sign=aa40cf3a5fd3665348cd71b761f0078a", "UTF-8");
//		System.out.println("+++++++++++++++++++" + result);
		
		//checkPayPwd("test2", "123456");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("e_sn", 1111);
		jsonMap.put("customer_id", 12345);
		jsonMap.put("name", "乌鸦");
		jsonMap.put("mobile", "18576777777");
		jsonMap.put("type", "elicai");
		jsonMap.put("amount", 153.3);
		jsonMap.put("pay_time", 1430903340);
		jsonMap.put("inviter_id", 4582);
		jsonMap.put("inviter_name", "酱爆");
		jsonMap.put("inviter_mobile", "13437223221");
		list.add(jsonMap);
		Map<String,Object> listMap = new HashMap<String, Object>();
		listMap.put("list", list);
		//System.out.println(JSONObject.fromObject(listMap).toString());
		String str ="{\"list\":[{\"amount\":153.3,\"inviter_mobile\":\"13437223221\",\"inviter_name\":\"\u718a\u89c1\",\"name\":\"\u718a\u89c1\",\"e_sn\":1111,\"inviter_id\":4582,\"pay_time\":1430903340,\"type\":\"elicai\",\"customer_id\":12345,\"mobile\":\"18576777777\"}]}";
		System.out.println(str);
		createColourOrder(str);
		
		//http://m.hehenian.com/product/index.do?appkey=0001&channel=2&mobilePhone=13066839936&oa=xiongjian&realName=%E7%86%8A%E8%A7%81&sign=6343f9b5c95d3bb0574d20b4b19abaa7
		/*
		String   mytext   =   java.net.URLEncoder.encode("张继华",   "UTF-8");
		String    oa  =   java.net.URLEncoder.encode("test2",   "UTF-8");
		String sign = DigestUtils.md5Hex(WebConstants.CGJ_PASS_KEY+2+"13800138001"+oa+"张继华");
		String result = HttpClientUtils.get("http://m.hehenian.com/product/index.do?appkey=0001&channel=2&mobilePhone=13800138001&oa="+oa+"&realName="+mytext+"&sign=" + sign, "UTF-8");
		System.out.println("+++++++++++++++++++" + result);
		
		
		String   mytext   =   java.net.URLEncoder.encode("熊见",   "UTF-8");
		String    oa  =   java.net.URLEncoder.encode("xiongjian",   "UTF-8");
		System.out.println(DigestUtils.md5Hex("GnKPe2BMT7PV" + "2" + "13066839936" + "xiongjian"+"熊见"));
		//String result = HttpClientUtils.get("http://m.hehenian.com/product/index.do?appkey=0001&channel=2&mobilePhone=13800138001&oa="+oa+"&realName="+mytext+"&sign=43dbf5878674d503ff0a7215f910b8e8", "UTF-8");
		//String result = HttpClientUtils.get("http://m.hehenian.com/product/index.do?appkey=0001&channel=2&mobilePhone=13066839936&oa="+oa+"&realName="+"zhanbiao詹彪"+"&sign=6343f9b5c95d3bb0574d20b4b19abaa7", "UTF-8");
		String result = HttpClientUtils.get("http://m.hehenian.com/product/index.do?appkey=0001&channel=2&mobilePhone=13066839936&oa="+oa+"&realName="+mytext+"&sign=6343f9b5c95d3bb0574d20b4b19abaa7", "UTF-8");
		System.out.println("+++++++++++++++++++" + result);*/
		//new BigDecimal(null);

	}
}
