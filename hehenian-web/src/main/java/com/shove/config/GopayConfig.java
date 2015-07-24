package com.shove.config;

import java.util.HashMap;
import java.util.Map;

/* *
 *国付宝配置
 */
public class GopayConfig {
	/**
	 * 商户号
	 */
	public static String gopay_merchantID = "";
	
	/**
	 * 商户账户
	 */
	public static String gopay_virCardNoIn = "";
	
	/**
	 * 国付宝网关地址
	 */
	public static String gopay_gateway = "";
	
	/**
	 * 编码
	 */
	public static String gopay_input_charset = "utf-8";
	
	public static String gopay_version = "";
	
	public static String gopay_signtype = "";
	
	public static String gopay_server_time_url="";
	
	public static String gopay_tranCode = "8888";
	
	public static String gopay_see_key = "";
	/**
	 * 
	 */
	public static String gopay_backgroundMerUrl = "";
	
	public static String gopay_frontMerUrl = "";
	
	public static String gopay_verficationCode = "";
	public static Map<String, String> bankMap = new HashMap<String, String>();

	static{
		com.shove.io.file.PropertyFile pf = new com.shove.io.file.PropertyFile();
		gopay_gateway = pf.read("gopay_gateway");
		gopay_input_charset = pf.read("gopay_input_charset");
		gopay_frontMerUrl = pf.read("gopay_frontMerUrl");
		gopay_backgroundMerUrl = pf.read("gopay_backgroundMerUrl");
		gopay_version = pf.read("gopay_version");
		gopay_signtype = pf.read("gopay_signtype");
		gopay_server_time_url = pf.read("gopay_server_time_url");
		gopay_see_key = pf.read("gopay_see_key");
		
		
		bankMap.put("CCB", "建设银行");
		bankMap.put("CMB", "招商银行");
		bankMap.put("ICBC", "工商银行");
		bankMap.put("BOC", "中国银行");
		bankMap.put("ABC", "农业银行");
		bankMap.put("BOCOM", "交通银行");
		bankMap.put("CMBC", "民生银行");
		bankMap.put("HXCB", "华夏银行");
		bankMap.put("CIB", "兴业银行");
		bankMap.put("SPDB", "上海浦发银行");
		bankMap.put("GDB", "广东发展银行");
		bankMap.put("CITIC", "中信银行");
		bankMap.put("CEB", "光大银行");
		bankMap.put("PSBC", "中国邮政储蓄银行");
		bankMap.put("BOBJ", "北京银行");
		bankMap.put("TCCB", "天津银行");
		bankMap.put("BOS", "上海银行");
		bankMap.put("SRCB", "上海农商银行");
		bankMap.put("PAB", "平安银行");
		

	}
}
