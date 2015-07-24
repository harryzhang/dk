package com.sp2p.action.front;


import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.json.simple.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
import com.sp2p.service.FinanceToolsService;

/**   
 * @ClassName: FinanceToolsAction.java
 * @Author: li.hou
 * @Descrb: 我要理财，工具箱
 */
public class FinanceToolsAction extends BaseFrontAction {
	protected Map<String, String> paramMap = new HashMap<String, String>();
	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public static Log log = LogFactory.getLog(FrontMyFinanceAction.class);
	private static final long serialVersionUID = 1L;
	
	private FinanceToolsService financeToolsService;


	/**
	 * 天标计算
	 */
	public String toolsCalculateDay() throws Exception{
		
		double borrowSum = Convert.strToDouble(paramMap.get("borrowSum"), -1);
		double yearRate = Convert.strToFloat(paramMap.get("yearRate"), -1);//接收百分比
		int borrowTime = Convert.strToInt(paramMap.get("borrowTime"), -1);//接收的数字是月
		

		if(yearRate < 0.00001){
			JSONUtils.printStr("2");
			return null;
		}
		
		try{
			double yearRateVal = yearRate*1.0f/100;
			
			Map<String, Object> map = financeToolsService.rateCalculateDay(borrowSum, yearRateVal, borrowTime);
			
			if(map == null){
				JSONUtils.printStr("1");
				return null;
			}
			
			JSONObject object = new JSONObject();
			object.put("map", map);
			
			JSONUtils.printObject(object);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 工具箱，利息计算器
	 * @return
	 * @throws Exception 
	 */
	public String toolsCalculate() throws Exception{
		
		double borrowSum = Convert.strToDouble(paramMap.get("borrowSum"), -1);
		double yearRate = Convert.strToFloat(paramMap.get("yearRate"), -1);//接收百分比
		int borrowTime = Convert.strToInt(paramMap.get("borrowTime"), -1);//接收的数字是月
		int repayWay = Convert.strToInt(paramMap.get("repayWay"), -1);//还款方式
		

		if(yearRate < 0.00001){
			JSONUtils.printStr("2");
			return null;
		}
		
		
		double yearRateVal = yearRate*1.0f/100;
		
		
		List<Map<String, Object>> lists = null;
		
		if(repayWay == 0){//等额本金 合和年还款方式 (下拉框的下标从0开始)
			lists = financeToolsService.rateCalculateHHN(borrowSum, yearRateVal, borrowTime);
		}else if(repayWay == 1){//按月还款
			lists = financeToolsService.rateCalculate2Month(borrowSum, yearRateVal, borrowTime);
		}else{//先息后本
			lists = financeToolsService.rateCalculate2Sum(borrowSum, yearRateVal, borrowTime);
		}
		if(lists == null){
			JSONUtils.printStr("1");
			return null;
		}
		String jsonStr = JSONArray.toJSONString(lists);
		JSONUtils.printStr(jsonStr);
		
		return null;
	}
	
	/**
	 * 收益计算器
	 * @return
	 * @throws Exception
	 */
    public String incomeCalculate() throws Exception{
		
		double borrowSum = Convert.strToDouble(paramMap.get("borrowSum"), -1);
		double yearRate = Convert.strToDouble(paramMap.get("yearRate"), -1);//接收百分比
		int borrowTime = Convert.strToInt(paramMap.get("borrowTime"), -1);//接收的数字是月
		int repayWay = Convert.strToInt(paramMap.get("repayWay"), -1);//还款方式
	    double bidReward = Convert.strToDouble(paramMap.get("bidReward"), 0);
	    double bidRewardMoney = Convert.strToDouble(paramMap.get("bidRewardMoney"), 0);
		if(yearRate < 0.00001){
			JSONUtils.printStr("2");
			return null;
		}
		
		double yearRateVal = yearRate*1.0f/100;
		
		List<Map<String, Object>> lists = null;
		if(repayWay == 0){//按月还款 (下拉框的下标从0开始)
			lists = financeToolsService.rateIncome2Month(borrowSum, yearRateVal, borrowTime,bidReward,bidRewardMoney);
		}else if (repayWay == 1){//先息后本
			lists = financeToolsService.rateIncome2Sum(borrowSum, yearRateVal, borrowTime,bidReward,bidRewardMoney);
		}
		if(lists == null){
			JSONUtils.printStr("1");
			return null;
		}
		String jsonStr = JSONArray.toJSONString(lists);
		JSONUtils.printStr(jsonStr);
		
		return null;
	}
	
/**
 * 手机归属地查询
 */
	public String queryPhoneInfo() throws Exception{
		 String address = "";
		 try{
			 String mobile = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("phoneNum")), null);
				if(mobile == null){
					getOut().print("请输入手机号码进行查询");
		        	return null;
				}
				//手机号码验证
				Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");       
		        Matcher m = p.matcher(mobile); 
		        if(!m.matches()){
		        	getOut().print("请输入正确的手机号码进行查询");
		        	return null;
		        }
			   String url = "http://www.youdao.com/smartresult-xml/search.s?jsFlag=true&keyfrom=163.com&event=fYodaoCallBack&type=mobile&q=" + mobile;
			   address = getLocation(url);
		 }catch(Exception e){
			  address = "未知";
		 }
		 getOut().print(address);
		 return null;
	}

	/**
	 * 手机归属地查询
	 * @param phoneNumber
	 * @return
	 * @throws Exception
	 */
//	public String queryPhoneInfo() throws Exception {
//		String phoneNumber = Convert.strToStr(paramMap.get("phoneNum"), null);
//		if(phoneNumber == null){
//			getOut().print("请输入手机号码进行查询|false");
//        	return null;
//		}
//		//手机号码验证
//		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");       
//        Matcher m = p.matcher(phoneNumber); 
//        if(!m.matches()){
//        	getOut().print("请输入正确的手机号码进行查询|false");
//        	return null;
//        }
//        
//        getOut().print("http://www.yodao.com/smartresult-xml/search.s?type=mobile&q="+phoneNumber+"|true");
//        
//        return null;
//        /*Map<String,String> map = financeToolsService.getPhoneNumInfo(phoneNumber);
//        if(map == null){
//        	getOut().print("暂无该手机号码信息");
//        	return null;
//        }
//        String str = "归属地："+map.get("province")+"  卡类型："+map.get("cardType")+"  区号："
//        +map.get("areaCode")+"  邮编："+map.get("postCode");
//        getOut().print(str);
//		return null;*/
//	}
	
	/**
	 * ip地址查询
	 */
	public String queryIPInfo() throws Exception{
		 String address = "";
		 try{
			 String ipAddr = Convert.strToStr(paramMap.get("ipAddress"), null);
				if(ipAddr == null){
					getOut().print("请输入IP进行查询");
		        	return null;
				}
				//手机号码验证
				Pattern p = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
						"((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
						"((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");       
		        Matcher m = p.matcher(ipAddr); 
		        if(!m.matches()){
		        	getOut().print("请输入正确的IP地址进行查询");
		        	return null;
		        }
		        String url = "http://www.youdao.com/smartresult-xml/search.s?jsFlag=true&keyfrom=163.com&" +
		   		"event=fYodaoCallBack&type=ip&q="+ipAddr;
			    address = getLocation(url);
		 }catch(Exception e){
			  address = "未知";
		 }
		 getOut().print(address);
		 return null;
	}
	
	/**
	 * 解析url返回的文件，获得值
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private String getLocation(String url) throws Exception{
		String address = "";
	    URLConnection connection = (URLConnection) new URL(url).openConnection();
	    connection.setDoOutput(true);
	    InputStream os = connection.getInputStream();
	    Thread.sleep(100);
	    int length = os.available();
	    byte[] buff = new byte[length];
	    os.read(buff);
	    String s = new String(buff, "gbk");
	    //返回数据fYodaoCallBack(1, {‘product’:'ip’,'ip’:’192.168.1.1′,’location’:'局域网 对方和您在同一内部网’} , ”);
	    int len = s.lastIndexOf(":");
	    s = s.substring(len+2);
	    int endLen = s.lastIndexOf("}");
	    s = s.substring(0,endLen -1);
	    address = s;
	    s = null;
	    buff = null;
	    os.close();
	    connection = null;
	    return address;
	}
	
	/**
	 * ip地址查询
	 * @return
	 * @throws Exception
	 */
	/*public String queryIPInfo() throws Exception {
		String ipAddr = Convert.strToStr(paramMap.get("ipAddress"), null);
		if(ipAddr == null){
			getOut().print("请输入IP进行查询|false");
        	return null;
		}
		//手机号码验证
		Pattern p = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
				"((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
				"((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");       
        Matcher m = p.matcher(ipAddr); 
        if(!m.matches()){
        	getOut().print("请输入正确的IP地址进行查询|false");
        	return null;
        }
        getOut().print("http://www.yodao.com/smartresult-xml/search.s?type=ip&q="+ipAddr+"|true");
        
        return null;
	}*/
	
	public String howToFinanceInit() throws SQLException, DataException{
		/**
		 * 初始化 从bt_config表中加载静态内容
		 */
		List<Map<String,Object>> map = financeToolsService.queryConfigList();
		if(map != null){
			request().setAttribute("name", map.get(0).get("name"));
			request().setAttribute("var", map.get(0).get("var"));
		}
		return SUCCESS;
	}
	

	public FinanceToolsService getFinanceToolsService() {
		return financeToolsService;
	}

	public void setFinanceToolsService(FinanceToolsService financeToolsService) {
		this.financeToolsService = financeToolsService;
	}
}
