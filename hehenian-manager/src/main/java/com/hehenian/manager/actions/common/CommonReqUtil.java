/**
 * @fileName CommonUtil.java
 * @auther liminglmf
 * @createDate 2015年6月9日
 */
package com.hehenian.manager.actions.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.hehenian.biz.common.util.HttpClientUtils;

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
	
}
