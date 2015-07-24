/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.facade.colorlife
 * @Title: FeeCallbackImpl.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月30日 上午11:06:18
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.facade.colorlife;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.activity.dataobject.ActivityConfig;
import com.hehenian.biz.common.util.CalculateUtils;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月30日 上午11:06:18
 */
@Component("colorFeeFacade")
public class ColorFeeFacadeImpl implements  IColorFeeFacade{
    
    private static final Logger logger = Logger.getLogger(ColorOrderFacadeImpl.class);
    
    @Autowired
    private ActivityConfig activityConfig;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	/*
	 * 数据：{ mobile: orderSn: orderAmount: orderSuccessTime: orderPaySn: }
	 * 
	 * (no-Javadoc) <p>Title: sendFeeStatus</p> <p>Description: </p>
	 * 
	 * @param colorUserId
	 * 
	 * @param orderSN
	 * 
	 * @param feeAmount
	 * 
	 * @param feeTime
	 * 
	 * @param month
	 * 
	 * @param status
	 * 
	 * @param remark
	 * 
	 * @return
	 * 
	 * @see
	 * com.hehenian.biz.facade.colorlife.IColorFeeFacade#sendFeeStatus(long,
	 * java.lang.String, double, java.util.Date, int, java.lang.String,
	 * java.lang.String)
	 */
    @Override
    public int sendFeeStatus(long colorUserId, String orderSN, double feeAmount, Date feeTime, int month,
 String status,
			String remark) {

        int retVal = checkPara(colorUserId, orderSN, feeAmount, feeTime, month, status);
        if( 0 != retVal){return retVal;}

		final Map<String, String> parameterMap = new LinkedHashMap<String, String>();
		// 转unix 时间戳
		long feeDeductTime = feeTime.getTime() / 1000;
        parameterMap.put("userId", String.valueOf(colorUserId));
        parameterMap.put("orderSN", orderSN);
        parameterMap.put("feeAmount", CalculateUtils.round(feeAmount));
		parameterMap.put("feeTime", String.valueOf(feeDeductTime));
		// parameterMap.put("month",String.valueOf(month));
        parameterMap.put("status", status);

		String urlEncodeRemark;
		try {
			urlEncodeRemark = URLEncoder.encode(remark, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			urlEncodeRemark = remark;
		}

		parameterMap.put("remark", urlEncodeRemark);

		try {

			taskExecutor.execute(new Runnable() {
				public void run() {
					try {
						String jsonString = ColorLifeManager.callColorOperate(
								activityConfig.getColorServiceURL(),
								activityConfig.getPaySyntonyURL(),
								activityConfig.getColorKey(),
								activityConfig.getColorSignSecret(),
								parameterMap);
						parameterMap.clear();
					} catch (Exception e) {
						logger.error("投资回调彩生活的扣费回调通知接口失败");
						logger.error(e);
					}
				}

			});
			return 0;

			// String jsonString = ColorLifeManager.callColorOperate(
			// activityConfig.getColorServiceURL(),
			// activityConfig.getPaySyntonyURL(),
			// activityConfig.getColorKey(),
			// activityConfig.getColorSignSecret(), parameterMap);
			// return ColorLifeReturnProcessor.parse(jsonString);
		} catch (Exception e) {
			logger.error("投资回调彩生活的扣费回调通知接口失败");
			logger.error(e);

		}
        return 1;
    }
    
    /**
     * 检查参数合法性
     * @param colorUserId
     * @param orderSN
     * @param orderAmount
     * @param orderSubmitTime
     * @param investStatus
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月30日下午12:37:44
     */
    private int checkPara(long colorUserId, String orderSN, double feeAmount, Date feeTime, int month,
            String status) {
        if(colorUserId <= 0){
            return 2;
        }
        if(null == orderSN || "".equals(orderSN)){
            return 2;
        }
        if(feeAmount <= 0){
            return 2;
        }
        if(null == feeTime ){
            return 2;
        }
        if(null == status){
            return 2;
        }
        if( month <1 || month > 12){
            return 2;
        }
        return 0;
    }

}
