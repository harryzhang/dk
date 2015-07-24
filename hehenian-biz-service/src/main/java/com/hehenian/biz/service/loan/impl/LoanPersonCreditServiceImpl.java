/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.service.loan.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.loan.ILoanPersonCreditService;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonCreditDo;
import com.hehenian.biz.component.loan.ILoanPersonCreditComponent;
import com.hehenian.common.redis.SpringRedisCacheService;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Service("loanPersonCreditService")
public class LoanPersonCreditServiceImpl implements ILoanPersonCreditService {

	private final Logger logger = Logger.getLogger(this.getClass());
	private static String CREDIT_SORT="_CREDIT_SORT";
	@Autowired	
    private ILoanPersonCreditComponent  loanPersonCreditComponent;
	
	@Autowired
	private SpringRedisCacheService localCacheService;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanPersonCreditDo getById(int id){
	  return loanPersonCreditComponent.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanPersonCreditDo> selectLoanPersonCredit(Map<String,Object> parameterMap){
		return loanPersonCreditComponent.selectLoanPersonCredit(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanPersonCreditById(LoanPersonCreditDo newLoanPersonCreditDo){
		return loanPersonCreditComponent.updateLoanPersonCreditById(newLoanPersonCreditDo);
	}
	
	
	/**
	 * 新增
	 */
	public int addLoanPersonCredit(LoanPersonCreditDo newLoanPersonCreditDo){
		return loanPersonCreditComponent.addLoanPersonCredit(newLoanPersonCreditDo);
	}
	
	
	/**
	 * 新增
	 */
	public int addLoanPersonCreditWithLoan(LoanPersonCreditDo newLoanPersonCreditDo,LoanDo loanDo){
		return loanPersonCreditComponent.addLoanPersonCredit(newLoanPersonCreditDo,loanDo);
	}
	
	/**
	 * 保存用户的信用额度到Redis的有序集合里
	 * @param cid
	 * @param userId
	 * @param creditAmt
	 */
	@Override
	public void saveCreditToSet(Long cid, Long userId, double creditAmt){
		logger.debug("排名cid:"+cid+" userId:"+userId+" creditAmt:"+creditAmt);
		localCacheService.addToSortedSet(cid.toString()+CREDIT_SORT, userId.toString(), creditAmt);
	}
	

	/**
	 * 根据小区和用户ID取排名
	 * @param cid
	 * @param userId
	 * @return
	 */
	@Override
	public Long getSortedByMember(Long cid, Long userId){
		return localCacheService.getSortByMember(cid.toString()+CREDIT_SORT, userId.toString());
	}
	
	
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return loanPersonCreditComponent.deleteById(id);
	}

	@Override
	public List<LoanPersonCreditDo> selectLoanPersonCreditWithDetail(
			Map<String, Object> parameterMap) {
		return loanPersonCreditComponent.selectLoanPersonCreditWithDetail(parameterMap);
	}
}
