package com.shove.config;

public class IPayConfig {
	
	//商户编号
	public static String ipay_mer_code = "";
	
	//前端返回地址
	public static String ipay_mer_chanurl = "";
	
	//后端地址
	public static String ipay_server_url = "";
	
	//环迅支付网关
	public static String ipay_gateway = "";
	
	//支付加密方式
	public static String ipay_order_encode_type = "";
	
	//加密方式
	public static String ipay_see_key = "";
	
	//商户内部证书
	public static String ipay_certificate = "";
	

	static{
		com.shove.io.file.PropertyFile pf = new com.shove.io.file.PropertyFile();
		ipay_mer_chanurl = pf.read("ipay_mer_chanurl");
		ipay_server_url = pf.read("ipay_server_url");
		ipay_gateway = pf.read("ipay_gateway");
		ipay_order_encode_type = pf.read("ipay_order_encode_type");
		ipay_see_key = pf.read("ipay_see_key");
	}
}
