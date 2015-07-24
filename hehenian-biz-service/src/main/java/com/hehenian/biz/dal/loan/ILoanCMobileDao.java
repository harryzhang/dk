package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanCMobileDo;

/**
 * 财管家 联系人信息
 * @author zhengyfmf
 *
 */
public interface ILoanCMobileDao {

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

	List<LoanCMobileDo> getAllBusinessDept();
}
