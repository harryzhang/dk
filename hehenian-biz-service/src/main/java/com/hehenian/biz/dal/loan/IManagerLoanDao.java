package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.CertificateDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.common.loan.dataobject.LoanUserBankDo;
import com.hehenian.biz.common.loan.dataobject.PropertyDo;

/**
 * 贷款订单
 * @author zhengyfmf
 *
 */
public interface IManagerLoanDao {
	
	LoanDo getLoanByLoanId(Long loanId);

	int updateLoanForRepayPlan(LoanDo loanDo);
	
	int updateLoanStatus(LoanDo loanDo);
	
	int updateLoanStatusByMap(Map<String,Object> param);
	
	List<Map<String,Object>> getLoanPage(Map<String,Object> param);
	
	LoanPersonDo getLoanDetailByLoanId(Long loanId);
	/**   
	 * @Description 标的导出数据
	 *
	 * @author huangzl   
	 *
	 * @date 2015年4月15日下午2:24:20
	 */
	List<Map<String, Object>> getLabelExportData(Map<String, Object> param);
	
	LoanPersonDo getLoanInfoForSbByLoanId(Long loanId);
	
	LoanPersonDo getLoanInfoForFkByOrderCode(String orderCode);
	
	Map<String,Object> getBankAccountForHF(String idNo);
	Map<String,Object> getBankAccountForTL(String idNo);
	
	List<Map<String,Object>> getSbNameForHF(String orderCode);
	List<Map<String,Object>> getSbNameForTL(String orderCode);

	List<LoanDo> getLoanListPage(Map<String, Object> param);

	LoanPersonDo getLoanPersonByLoanId(Long loanId);

	int update(LoanDo loanDo);

	LoanDo getDetailById(Long loanId);

	List<LoanUserBankDo> getTbcInfo(Long userId);

	List<LoanUserBankDo> getTdbcInfo(Long userId);

	LoanDo getLoanforUpdate(Map<String, Object> param);

	/**
	 * 获取渠道列表
	 * @return
	 */
	List<Map<String, Object>> getChannelTypeList();
	
	List<LoanRelationDo> getLoanRelationDoList(Long loanId);
	PropertyDo getPropertyDo(Long loanId);
	List<CertificateDo> getCertificateDoList(Long loanId);
}
