/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.common.activity;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.activity.dataobject.ActivityOrderDo;
import com.hehenian.biz.common.userhome.dataobject.UserIncomeDo;


public interface IActivityOrderService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public ActivityOrderDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<ActivityOrderDo> selectActivityOrder(Map<String, Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateActivityOrderById(ActivityOrderDo newActivityOrderDo);
	
	/**
	 * 新增
	 */
	public Long addActivityOrder(ActivityOrderDo newActivityOrderDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

    /**
     * 根据订单号 查询
     * @parameter
     */
    public ActivityOrderDo getByOrdNo(ActivityOrderDo activityOrderDo);

	/**
	 * 查询有效日期范围内的所有订单总收益 有效日期是 订单结束日期>= now, 之前的表示已结清
	 * 
	 * @param userId
	 * @param queryDate
	 *            截止到 queryDate日期有效的订单
	 * @return
	 */
	public UserIncomeDo getAllOrderIncome(long userId, Date queryDate);
	
	    /**
     * 查询有效日期范围内的所有订单总收益 有效日期是 订单结束日期>= now, 之前的表示已结清
     * 
     * @param userId
     * @param queryDate
     *            截止到 queryDate日期有效的订单
     * @param orderSubType
     *            订单子类型
     * @return
     */
    public UserIncomeDo getAllOrderIncomeByOrderSubType(long userId, Date queryDate, String orderSubType);

    /**
     * 查询用户订单详情
     * 
     * @param userId
     *            彩之云用户ID
     * @param orderSN
     *            投资订单
     * @param orderType
     *            订单类型 电信为 1， 彩生活 0
     * @return json 格式的字符串
     * @author: zhangyunhmf
     * @date: 2014年10月29日下午5:39:22
     */
	ActivityOrderDo queryOrderDetail(String userId, String orderSN,String orderType);

    int updateStatus(AccountUserDo accountUserDo, ActivityOrderDo activityOrderDo);
    public long hasOrder( int ordType, long userId);

    public int addRechargeMoney( long ordid , long addMoney , long userId);

    public boolean notifyColorLife(AccountUserDo accountUserDo, ActivityOrderDo activityOrderDo);
}
