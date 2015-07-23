package com.hehenian.biz.common.colorlife;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description 彩生活业务接口
 * @Author chenzhwmf@hehenian.com
 * @Date:2015年4月14日
 * @Version 1.0.0
 */
public interface ColorLifeBuyService {
	
	public static class OrderStatus {
		public static int CHECK = 1; //未支付
		public static int CHECK_SUCCESS = 2; //审核成功
		public static int CHECK_FAILURE = 3; //未审失败
	}

	/**
	 * 获取彩生活产品订购信息
	 * 
	 * @param colorLifeBuyInfo
	 *            订单查询条件
	 * @return 订单集合
	 */
	public List<Map<String, Object>> listBuyInfo(Map<String, Object> conditon);
	
	
	public List<Map<String, Object>> weblistBuyInfo(Map<String, Object> conditon);
	
	/**
	 * 获取总记录数
	 * @param conditon
	 * @return
	 */
	public long countBuyInfo(Map<String, Object> conditon);
	
	/**
	 * 根据ID获取订购记录
	 * @param orderId
	 * @return
	 */
	public Map<String, Object> findById(Long orderId);
	
	/**
	 * 保存彩生活购买信息
	 * 
	 * @param corolLifeBuyInfo
	 *            订单信息
	 * @return 
	 * @exception Exception
	 */
	public Map saveBuyInfo(Map<String, Object> corolLifeBuyInfo) throws Exception;

	/**
	 * 更新彩生活订单信息
	 * @param verifierId TODO
	 * @param corolLifeBuyInfo
	 *            订单信息
	 * 
	 * @return 更新的订单记录数
	 * 
	 * @exception Exception
	 */
	public int updateBuyInfo(long orderNo, int status, long verifierId)
			throws Exception;
	
	/**
	 * 修改订单状态
	 * @param orderNo
	 * @param status
	 * @return
	 */
	public int updateStatus(long orderNo, int status);
	
	/**
	 * 投资金额
	 * @param userId
	 * @return
	 */
	public BigDecimal queryInvestment(Integer userId);
	/**
	 * 已收收益
	 * @param userId
	 * @return
	 */
	public BigDecimal queryInterest(Integer userId);
	/**
	 * 待收收益
	 * @param userId
	 * @return
	 */
	public BigDecimal queryInterested(Integer userId);
}
