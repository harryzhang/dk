/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.component.bank.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.bank.dataobject.BankBingDo;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.bank.IBankBingComponent;
import com.hehenian.biz.component.bank.dao.IBankBingDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("bankBingComponent")
public class BankBingComponentImpl implements IBankBingComponent {

	@Autowired
    private IBankBingDao  bankBingDao;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public BankBingDo getById(int id){
        return bankBingDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
    public List<BankBingDo> selectBankBing(Map<String, Object> parameterMap) {
        return bankBingDao.selectBankBing(parameterMap);
	}
	
	/**
	 * 更新
	 */
    public int updateBankBingById(BankBingDo newBankBingDo) {
        int result = bankBingDao.updateBankBingById(newBankBingDo);
		if(result < 1){
			throw new BusinessException("更新失败");
		}
		return result;
	}
	
	/**
	 * 新增
	 */
    public int addBankBing(BankBingDo newBankBingDo) {
        return bankBingDao.addBankBing(newBankBingDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
        return bankBingDao.deleteById(id);
	}

    /*
     * (no-Javadoc) <p>Title: getByUserId</p> <p>Description: </p>
     * 
     * @param userId
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.bank.IBankBingComponent#getByUserId(long)
     */
    @Override
    public List<BankBingDo> getByUserId(long userId) {
        return bankBingDao.getByUserId(userId);
    }

    /*
     * (no-Javadoc) <p>Title: getLastBankBingByUserIdBankCode</p>
     * <p>Description: 通过用户和卡取最后一次的验证记录</p>
     * 
     * @param userId
     * 
     * @param bankCode
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.bank.IBankBingComponent#
     * getLastBankBingByUserIdBankCode(long, java.lang.String)
     */
    @Override
    public BankBingDo getLastBankBingByUserIdBankCode(long userId, String bankCode) { 
        return bankBingDao.getLastBankBingByUserIdBankCode(userId, bankCode);
    }

    @Override public Long countUserVerifyCard(Long userId) {
        return bankBingDao.countUserVerifyCard(userId);
    }

    @Override public Long getSucCardBingCount(String bankCode) {
        return bankBingDao.getSucCardBingCount(bankCode);
    }

    @Override public Long getCardSendCount(String bankCode) {
        return bankBingDao.getCardSendCount(bankCode);
    }

}
