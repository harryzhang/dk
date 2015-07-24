/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.component.bank;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.bank.dataobject.BankBingDo;
import org.apache.ibatis.annotations.Param;

public interface IBankBingComponent {

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
     * 获取用户所有的绑卡记录
     * 
     * @param userId
     * @return
     * @author: zhangyunhmf
     * @date: 2015年1月16日上午11:37:30
     */
    public List<BankBingDo> getByUserId(long userId);


    /**
     * 通过用户和卡取最后一次的验证记录
     * 
     * @param userId
     * @param bankCode
     * @return
     * @author: zhangyunhmf
     * @date: 2015年1月16日下午2:49:02
     */
    public BankBingDo getLastBankBingByUserIdBankCode(long userId, String bankCode);

    /**
     * 查询用户验过的卡的数量
     * @param userId
     * @return
     */
    public Long countUserVerifyCard(Long userId);

    /**
     * 查询某张卡是否绑定成功
     * @param bankCode
     * @return
     */
    public Long getSucCardBingCount(String bankCode);

    /**
     * 查询某张卡发钱的次数
     * @param bankCode
     * @return
     */
    public Long getCardSendCount( String bankCode);
}
