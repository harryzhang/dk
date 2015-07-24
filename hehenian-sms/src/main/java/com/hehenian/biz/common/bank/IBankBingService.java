/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.common.bank;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.bank.dataobject.BankBingDo;


public interface IBankBingService{
    
    public  enum returnCode {
        SUCCESS, UNDO, LOCK
    }
    

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
     * 向用户的卡随机发送验证金额
     * 
     * @param userId
     * @param bankCode
     * @param userRealName
     *            用户真实姓名
     * @param bankType
     * @return 00表示成功 01 数据无效 02 系统繁忙重试 03银行卡被锁定 04 达到最多5次 05 次卡已经绑定过不能重复绑定
     * @author: zhangyunhmf
     * @date: 2015年1月16日上午10:29:10
     */
    public Map<String, String> sendBankIdentifyCode(long userId, String userRealName, String bankCode, String bankType,
            BigDecimal amount);

    /**
     * 验证用户提交的银行随机验证码
     * 
     * @param userId
     * @param bankCode
     * @param bankType
     * @param identifyCode
     * @return 00表示成功 01 数据无效 02 系统繁忙重试 03银行卡被锁定 07 验证码不正确
     * @author: zhangyunhmf
     * @date: 2015年1月16日上午10:29:20
     */
    public String checkBankIdentifyCode(long userId, String bankCode, String bankType, String amount);

    /**
     * 充值前检查
     * 
     * @param userBankBingList
     *            用户所有的绑卡记录
     * @param currentBankCode
     *            当次需要绑定的卡号
     * @return boolean false 不能发送， true 可以发送
     * @author: zhangyunhmf
     * @date: 2015年1月16日上午11:13:12
     */
    public String checkUserBankBingRecord(String bankCode, long userId);
    // public String checkUserBankBingRecord(List<BankBingDo> userBankBingList,
    // String currentBankCode);

}
