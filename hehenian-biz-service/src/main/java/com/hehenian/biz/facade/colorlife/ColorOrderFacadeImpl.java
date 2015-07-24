/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.facade.colorlife
 * @Title: ColorOrderFacadeImpl.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月30日 上午11:13:59
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.facade.colorlife;

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
 * @date 2014年10月30日 上午11:13:59
 */
@Component("colorOrderFacade")
public class ColorOrderFacadeImpl implements IColorOrderFacade {

    private static final Logger logger = Logger.getLogger(ColorOrderFacadeImpl.class);
    @Autowired
    private ActivityConfig activityConfig;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

    /*
     * (no-Javadoc) <p>Title: sendOrderStatus</p> <p>Description: </p>
     * 
     * @param colorUserId
     * 
     * @param orderSN
     * 
     * @param orderAmount
     * 
     * @param orderSubmitTime
     * 
     * @param investStatus
     * 
     * @param remark
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.facade.colorlife.IColorOrderFacade#sendOrderStatus(long,
     * java.lang.String, double, java.util.Date, java.lang.String,
     * java.lang.String)
     */
    @Override
    public int sendOrderStatus(long colorUserId, String orderSN, double orderAmount, Date orderSubmitTime,
 String investStatus,
			String remark, long orderPaySn) {

		int retVal = checkPara(colorUserId, orderSN, orderAmount,
				orderSubmitTime, investStatus, orderPaySn);
        if( 0 != retVal){return retVal;}

		// 转unix 时间戳
		long orderTime = orderSubmitTime.getTime() / 1000;

		final Map<String, String> parameterMap = new LinkedHashMap<String, String>();
		parameterMap.put("userid", String.valueOf(colorUserId));
		parameterMap.put("orderSn", orderSN);
        parameterMap.put("orderAmount", CalculateUtils.round(orderAmount));
		parameterMap.put("orderSuccessTime", String.valueOf(orderTime));
		parameterMap.put("orderPaySn", String.valueOf(orderPaySn));

		parameterMap.put("orderStatus", investStatus);
		// parameterMap.put("remark", remark);

		try {

			taskExecutor.execute(new Runnable() {
				public void run() {
					try {
						String jsonString = ColorLifeManager.callColorOperate(
								activityConfig.getColorServiceURL(),
								activityConfig.getOrderSuccessURL(),
								activityConfig.getColorKey(),
								activityConfig.getColorSignSecret(),
								parameterMap);
						parameterMap.clear();
					} catch (Exception e) {
						logger.error("投资回调彩生活的订单回调通知接口失败");
						logger.error(e);
					}
				}

			});
			return 0;

			// String jsonString = ColorLifeManager.callColorOperate(
			// activityConfig.getColorServiceURL(),
			// activityConfig.getOrderSuccessURL(),
			// activityConfig.getColorKey(),
			// activityConfig.getColorSignSecret(), parameterMap);
			//
			// // jsonString = HttpClientUtils.post(operationURL, parameterMap);
			// return ColorLifeReturnProcessor.parse(jsonString);

		} catch (Exception e) {
			logger.error("投资回调彩生活的订单回调通知接口失败");
			logger.error(e);
		}
        return 1;
    }

    /**
	 * 检查参数合法性
	 * 
	 * @param colorUserId
	 * @param orderSN
	 * @param orderAmount
	 * @param orderSubmitTime
	 * @param investStatus
	 * @param orderPaySn
	 * @return
	 * @author: zhangyunhmf
	 * @date: 2014年10月30日下午12:37:44
	 */
    private int checkPara(long colorUserId, String orderSN, double orderAmount, Date orderSubmitTime,
 String investStatus, long orderPaySn) {
        if(colorUserId <= 0){
            return 2;
        }
        if(null == orderSN || "".equals(orderSN)){
            return 2;
        }
        if(orderAmount <= 0){
            return 2;
        }
        if(null == orderSubmitTime ){
            return 2;
        }
        if(null == investStatus){
            return 2;
        }
		if (orderPaySn <= 0) {
			return 2;
		}

        return 0;
    }

}
