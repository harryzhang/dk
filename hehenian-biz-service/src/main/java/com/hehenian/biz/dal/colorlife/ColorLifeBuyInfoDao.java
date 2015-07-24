package com.hehenian.biz.dal.colorlife;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description 查询彩生活订购记录数据库操作
 * @Author chenzhwmf@hehenian.com
 * @Date:2015年4月13日
 * @Version 1.0.0
 */
@Repository
public interface ColorLifeBuyInfoDao {

	/**
	 * 获取彩生活产品订购信息
	 * 
	 * @param colorLifeBuyInfo
	 *            订单查询条件
	 * @return 订单集合
	 */
	public List<Map<String, Object>> listBuyInfo(Map<String, Object> conditon);
	
	/**
	 * 获取总记录数
	 * @param conditon
	 * @return
	 */
	public long countBuyInfo(Map<String, Object> conditon);
	
	/**
	 * 获取彩生活产品订购信息
	 * 
	 * @param colorLifeBuyInfo
	 *            订单查询条件
	 * @return 订单集合
	 */
	public List<Map<String, Object>> weblistBuyInfo(Map<String, Object> conditon);

	/**
	 * 保存彩生活购买信息
	 * 
	 * @param corolLifeBuyInfo
	 *            订单信息
	 */
	public void saveBuyInfo(Map<String, Object> corolLifeBuyInfo);

	/**
	 * 更新彩生活订单信息
	 * 
	 * @param corolLifeBuyInfo
	 *            订单信息
	 * @return 更新的订单记录数
	 */
	public int updateBuyInfo(@Param("orderId") Long orderId, @Param("status") int status, @Param("verifier") Long verifier);
	
	/**
	 * 修改订单状态
	 * @param orderId
	 * @param status
	 * @return
	 */
	public int updateStatus(@Param("orderId") Long orderId, @Param("status") int status);
	
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



	/**
	 * 根据订单id获取订单信息
	 * @param orderId
	 * @return
	 */
	public Map<String, Object> findById(@Param("orderId") Long orderId);
}
