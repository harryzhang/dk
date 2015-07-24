/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.dal.activity;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.activity.dataobject.ActivityOrderDo;

public interface IActivityOrderDao {

	/**
	 * 根据ID 查询
	 * 
	 * @parameter id
	 */
	public ActivityOrderDo getById(long id);

	/**
	 * 根据条件查询列表
	 */
	public List<ActivityOrderDo> selectActivityOrder(
			Map<String, Object> parameterMap);

	/**
	 * 更新
	 */
	public int updateActivityOrderById(ActivityOrderDo newActivityOrderDo);

	/**
	 * 新增
	 */
	public int addActivityOrder(ActivityOrderDo newActivityOrderDo);

	/**
	 * 删除
	 */
	public int deleteById(int id);

	/**
	 * 查询用户订单详情
	 * 
	 * @param userId
	 *            彩之云用户ID
	 * @param ordNo
	 *            投资订单
	 * @param ordType
	 *            订单类型 电信为 1， 彩生活 0
	 * @return json 格式的字符串
	 * @author: zhangyunhmf
	 * @date: 2014年10月29日下午5:39:22
	 */
	public ActivityOrderDo queryOrderDetail(@Param("userId") String userId,
			@Param("ordNo") String ordNo, @Param("ordType") String ordType);

	/**
	 * 根据订单号 查询
	 * 
	 * @parameter
	 */
	public ActivityOrderDo getByOrdNo(ActivityOrderDo activityOrderDo);

	/*
	 * 根据用户取订单
	 * 
	 * @param userId 用户ID
	 * 
	 * @return 订单列表
	 */
	public List<ActivityOrderDo> getByUserId(long userId);

	public int updateStatus(@Param("ordId") long ordid,
			@Param("ordStatus") int status);

    public long hasOrder( @Param("ordType") int ordType,@Param("userId") long userId);

    public int addRechargeMoney(@Param("ordId") long ordid ,@Param("addMoney") double addMoney ,@Param("userId") double userId);
}
