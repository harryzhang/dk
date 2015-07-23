/**
 * @fileName CommonUtil.java
 * @auther liminglmf
 * @createDate 2015年6月9日
 */
package com.hehenian.biz.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.hehenian.biz.common.util.constant.ConstantText;

/**
 * @author liminglmf
 *
 */
public class CommonReqUtil {
	
	private static Logger logger = Logger.getLogger(CommonReqUtil.class);
	
	/**
	 *  取接口数据
	 * @auther liminglmf
	 * @date 2015年6月9日
	 * @param repayDate 时间 格式 "2015/06/08"
	 * @param reqUrl 例如: "http://www.hehenian.com/loan.do"
	 * @return
	 */
	public static String pushServiceData(String reqUrl,String dateKey,String dateValue){
		String sign = DigestUtils.md5Hex(ConstantText.MDSIGNKEY+dateValue+ConstantText.MDSIGNKEY);
		StringBuffer url = new StringBuffer(reqUrl+"?"+dateKey+"="+dateValue);
		url.append("&sign=").append(sign);
		logger.info("====================================");
		logger.info("URL>>>>>>>>"+url.toString());
		logger.info("====================================");
		String res = null;
		try {
			res = HttpClientUtils.get(url.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 小贷接口
	 * @auther liminglmf
	 * @date 2015年7月3日
	 * @param reqUrl
	 * @param params
	 * @return
	 */
	public static String pushServiceToPost(String reqUrl,String jsonString){
		String sign = DigestUtils.md5Hex(ConstantText.MDSIGNKEY+jsonString+ConstantText.MDSIGNKEY);
		String res = null;
		try {
			Map<String,String> params = new HashMap<String,String>();
			params.put("sign",sign);
			params.put("params",jsonString);
			res = HttpClientUtils.commonPost(reqUrl,params);
			logger.info("res>>>>>>>>>>"+res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
}
