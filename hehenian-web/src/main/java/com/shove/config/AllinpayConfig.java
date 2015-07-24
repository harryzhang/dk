package com.shove.config;

import java.util.HashMap;
import java.util.Map;

public class AllinpayConfig {
	public static String alllin_version = "v1.0";// 固定值
	public static String alllin_language = "1";// 1 代表中文显示
	public static String alllin_signType = "1";// 签名类型,1 代表证书签名验签方式
	public static String alllin_merchantId = "109007551112077";// 商户号
	public static String alllin_pickUrl = "";
	public static String alllin_receiveUrl = "";
	public static String alllin_signkey = "";
	public static String alllin_gate_way = "";
	public static Map<String, String> bankMap = new HashMap<String, String>();

	static {
		com.shove.io.file.PropertyFile pf = new com.shove.io.file.PropertyFile();
		alllin_version = pf.read("alllin_version");
		alllin_language = pf.read("alllin_language");
		alllin_signType = pf.read("alllin_signType");
		alllin_merchantId = pf.read("alllin_merchantId");
		alllin_pickUrl = pf.read("alllin_pickUrl");
		alllin_receiveUrl = pf.read("alllin_receiveUrl");
		alllin_signkey = pf.read("alllin_signkey");
		alllin_gate_way = pf.read("alllin_gate_way");
		// 银行代码
		bankMap.put("cmb", "招商银行");
		bankMap.put("icbc", "工商银行");
		bankMap.put("ccb", "建设银行");
		bankMap.put("abc", "农业银行");
		bankMap.put("spdb", "浦东发展银行");
		
		bankMap.put("ceb", "光大银行");
		bankMap.put("comm", "交通银行");
		bankMap.put("boc", "中国银行");
		bankMap.put("bos", "上海银行");
		bankMap.put("pingan", "平安银行");
		bankMap.put("hxb", "华夏银行 ");
		bankMap.put("cib", "兴业银行");
		bankMap.put("telpshx", "通联电话支付 ");
	}
}
