package com.hehenian.liumi.exchange;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


/**
 * 流米红包活动
 * @author duanhrmf
 *
 */
public class LiumiClient{
	
//	private static final String URL_PREFIX="https://ll.wenanji.com/server/";
	private static final String URL_PREFIX="https://api.tenchang.com/server/";
	 
	private static Result send(Parameter p,String service) throws KeyManagementException, NoSuchAlgorithmException{
		try {
			byte[] bs = HttpsUtil.post(URL_PREFIX+service, JsonUtil.parse(p));
			return JsonUtil.translate(new String(bs));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}   
		return null;
	}
	
	/** 
	 * 获取token
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static Result getToken() throws KeyManagementException, NoSuchAlgorithmException{
		return send(new Parameter(),"getToken");
	}
	
	
	
	
	/**
	 * 下订单
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * @throws IOException 
	 */
	public static Result placeOrder(String mobile,String postpackage,boolean isRegFlag) throws KeyManagementException, NoSuchAlgorithmException, IOException{
		Parameter p = new Parameter();
		p.setAppver("Http");
		p.setExtno("");
		p.setFixtime("");
		p.setMobile(mobile);
		if(postpackage!=null){
			p.setPostpackage(postpackage);//摇一摇流量是随机的
		}else{
			p.setPostpackage("YD70;DX100;LT50");//注册流量规格是固定的
		}
		Result r = getToken();
		p.setToken(r.getData().getToken());
		Result re = send(p,"placeOrder");
		if(!isRegFlag){//true 表示注册
			//将手机号作为键，订单号为值写入文件中
			PropertiesUtils.writeProperties("order.properties", mobile, re.getData().getOrderNO());
		}
		return re;
	}
	
	/**
	 * 订单明细
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public static Result getOrderInfo(String mobile) throws KeyManagementException, NoSuchAlgorithmException, IOException, ClassNotFoundException{
		Parameter p = new Parameter();
		//从属性文件中读取订单号
		String orderNo = PropertiesUtils.readValue("order.properties", mobile);
		p.setOrderNo(orderNo);
		Result r = getToken();
		p.setToken(r.getData().getToken());
		return send(p,"getOrderInfo");
	}
	
	
	/**
	 * 历史订单
	 */
//	public static String getOrderList(String mobile){
//		Map<String, String> params = new HashMap<String, String>();  
//		params.put(APPKEY, APPKET_V);  
//		params.put(APPSECRETKEY,SECRET); 
//		params.put("token",getToken());
//		params.put("mobile", mobile);
//		params.put("startDate", "2015-03-24 00:00:00");
//		params.put("endDate", "2015-03-26 23:59:59");
//		params.put("curPage", "1");
//		params.put("pageSize", "10");
//		try {
//			String result = HttpUtils.postRequest(new URL("https://ll.wenanji.com/server/getOrderList"), params);
//			return result;
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
	
}
