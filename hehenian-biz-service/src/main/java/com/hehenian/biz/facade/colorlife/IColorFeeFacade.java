/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.facade.colorlife
 * @Title: IFeeCallback.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月29日 下午5:59:52
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.facade.colorlife;

import java.util.Date;

/**
 * 扣费成功回调彩生活的接口
 * 
 * @author: zhangyunhmf
 * @date 2014年10月29日 下午5:59:52
 */
public interface IColorFeeFacade {

    /**
     * 扣物业管理费成功接口
     * 
     * 此接口为E理财将彩之云用户账号（user_手机号码），订单号，扣费金额，扣费日期， 扣费所属那个月份 提交给彩之云
     * 
     * @param colorUserId
     *            彩之云用户账号
     * @param orderSN
     *            订单号
     * @param feeAmount
     *            扣费金额
     * @param feeTime
     *            扣费日期
     * @param month
     *            扣费所属那个月份
     * @param status
     *            状态值
     * @param remark
     *            备注
     * @return 0 成功， 1失败， 2 参数不合法
     * @author: zhangyunhmf
     * @date: 2014年10月30日上午10:58:58
     */
    int sendFeeStatus(long colorUserId, String orderSN, double feeAmount, Date feeTime, int month, String status,
 String remark);

}
