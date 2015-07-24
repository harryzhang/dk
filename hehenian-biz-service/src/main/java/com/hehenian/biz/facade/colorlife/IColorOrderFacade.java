/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.facade.colorlife
 * @Title: IOrderCallBack.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月29日 下午5:59:02
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.facade.colorlife;

import java.util.Date;

/**
 * 投资回调彩生活的接口
 * 
 * @author: zhangyunhmf
 * @date 2014年10月29日 下午5:59:02
 */
public interface IColorOrderFacade {

    /**
	 * 此接口为E理财将彩之云用户账号（user_手机号码），订单号，订单金额， 订单提交时间 提交给彩之云
	 * 
	 * @param colorUserId
	 *            彩之云用户账号
	 * @param orderSN
	 *            订单号
	 * @param orderAmount
	 *            订单金额
	 * @param orderSubmitTime
	 *            订单提交时间
	 * @param investStatus
	 *            状态
	 * @param remark
	 *            备注
	 * @param orderPaySn
	 *            订单
	 * @return 0 成功， 1失败， 2 参数不合法
	 * @author: zhangyunhmf
	 * @date: 2014年10月30日上午10:53:43
	 */
    int sendOrderStatus(long colorUserId, String orderSN, double orderAmount, Date orderSubmitTime,
 String investStatus, String remark,
			long orderPaySn);

}
