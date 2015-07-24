package com.hehenian.biz.component.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanCMobileDo;

/**
 * @author zhengyfmf
 */
public interface ILoanCMobileComponent {

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
	List<LoanCMobileDo> getLoanCMobilePage(Map<String,Object> param);
	
	Map<String, Object> getLoanCMobileMapPage(Map<String,Object> param);
	
	int deleteLoanCMobileById(Integer id);

	/**
	 * 所有事业部
	 * @return
	 */
	List<LoanCMobileDo> getAllBusinessDept();
}
