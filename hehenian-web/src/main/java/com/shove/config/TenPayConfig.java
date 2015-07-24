package com.shove.config;

import java.util.HashMap;
import java.util.Map;

/**
 * �Ƹ�ͨ
 */
public class TenPayConfig {
	public static String tenpay_online_bargainor_id = "";
	public static String tenpay_online_callBack = "";
	public static String tenpay_online_reqUrl = "";
	public static String tenpay_online_key = "";
	public static String tenpay_online_notifyReceiver = "";
	public static Map<String, String> bankMap = new HashMap<String, String>();

	static {
		com.shove.io.file.PropertyFile pf = new com.shove.io.file.PropertyFile();
		tenpay_online_bargainor_id = pf.read("tenpay_online_bargainor_id");
		tenpay_online_reqUrl = pf.read("tenpay_online_reqUrl");
		tenpay_online_callBack = pf.read("tenpay_online_callBack");
		tenpay_online_notifyReceiver = pf.read("tenpay_online_notifyReceiver");// �Ƹ�ͨ
		tenpay_online_key = pf.read("tenpay_online_key");
		
		bankMap.put("ICBC", "工商银行");
		bankMap.put("CCB", "建设银行");
		bankMap.put("ABC", "农业银行");
		bankMap.put("CMB", "招商银行");
		bankMap.put("SPDB", "上海浦发银行");
		bankMap.put("SDB", "深圳发展银行");
		bankMap.put("CIB", "兴业银行");
		bankMap.put("BOB", "北京银行");
		bankMap.put("CEB", "光大银行");
		bankMap.put("CMBC", "民生银行");
		bankMap.put("CITIC", "中信银行");
		bankMap.put("GDB", "广东发展银行");
		bankMap.put("PAB", "平安银行");
		bankMap.put("BOC", "中国银行");
		bankMap.put("COMM", "交通银行");
		bankMap.put("NJCB", "南京银行");
		bankMap.put("NBCB", "宁波银行");
		bankMap.put("SRCB", "上海农商");
		bankMap.put("BEA", "东亚银行");
		bankMap.put("POSTGC", "邮储");
		bankMap.put("ICBCB2B", "工商银行（企业）");
		bankMap.put("CMBB2B", "招商银行（企业）");
		bankMap.put("CCBB2B", "建设银行（企业）");
		bankMap.put("ABCB2B", "农业银行（企业）");
		bankMap.put("SPDBB2B", "浦发银行（企业）");
		bankMap.put("CEBB2B", "光大银行");
		
	}

}
