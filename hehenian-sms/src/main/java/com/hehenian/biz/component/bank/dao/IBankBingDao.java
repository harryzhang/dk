/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.component.bank.dao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.bank.dataobject.BankBingDo;

public interface IBankBingDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public BankBingDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
    public List<BankBingDo> selectBankBing(Map<String, Object> parameterMap);
	
	/**
	 * 更新
	 */
    public int updateBankBingById(BankBingDo newBankBingDo);
	
	/**
	 * 新增
	 */
    public int addBankBing(BankBingDo newBankBingDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

    /**
     * 获取用户的所有绑卡充值记录
     * 
     * @param userId
     * @return
     * @author: zhangyunhmf
     * @date: 2015年1月16日下午3:03:59
     */
    public List<BankBingDo> getByUserId(long userId);

    /**
     * 通过用户和卡取最后一次的验证记录
     * 
     * @param userId
     * @param bankCode
     * @return
     * @author: zhangyunhmf
     * @date: 2015年1月16日下午3:04:09
     */
    public BankBingDo getLastBankBingByUserIdBankCode(@Param("userId") long userId, @Param("bankCode") String bankCode);

    /**
     * 查询用户验过的卡的数量
     * @param userId
     * @return
     */
    public Long countUserVerifyCard( Long userId);

    /**
     * 查询某张卡是否绑定成功
     * @param bankCode
     * @return
     */
    public Long getSucCardBingCount(@Param("bankCode")String bankCode);

    /**
     * 查询某张卡发钱的次数
     * @param bankCode
     * @return
     */
    public Long getCardSendCount( @Param("bankCode") String bankCode);

}
