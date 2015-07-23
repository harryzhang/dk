package com.hehenian.biz.common.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.dataobject.LoanCMobileDo;

/**
 * 
 * @author zhengyfmf
 */
public interface ILoanCMobileService {

	int addLoanCMobile(LoanCMobileDo loanCMobileDo);
	
	int updateLoanCMoblie(LoanCMobileDo loanCMobileDo);
	
	/**
	 * 查询管家信息
	 * @param id  
	 * @return
	 */
	LoanCMobileDo getById(Integer id);
	
	/**
	 * 管家信息列表
	 * @param param
	 * @return
	 */
	PageDo<LoanCMobileDo> getLoanCMobilePage(Map<String,Object> param, PageDo<LoanCMobileDo> page);
	
	int deleteLoanCMobileById(Integer id);
	
	/**
	 * 事业部下拉框
	 * @param response
	 */
	List<LoanCMobileDo> getAllBusinessDept();
}
