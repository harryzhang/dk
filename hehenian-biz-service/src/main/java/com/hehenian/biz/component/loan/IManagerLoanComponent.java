package com.hehenian.biz.component.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanCheckedDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanModifyLogDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanProductDo;
import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.common.loan.dataobject.LoanUserBankDo;
import com.hehenian.biz.common.system.dataobject.SettDetailDo;

/**
 * @author zhengyfmf
 */
public interface IManagerLoanComponent {
	
	LoanDo getLoanByLoanId(Long loanId);

	int updateLoanStatus(LoanDo loanDo);
	
	boolean updateLoanStatusByMap(List<Map<String,Object>> paramList);
	
	List<Map<String,Object>> getLoanPage(Map<String,Object> param);
	
	LoanPersonDo getLoanDetailByLoanId(Long loanId);
	
	List<LoanProductDo> listLoanProduct(Map<String, Object> param);
	/**   
	 * @Description 标的导出数据
	 *
	 * @author huangzl   
	 *
	 * @date 2015年4月15日下午2:24:20
	 */
	List<Map<String, Object>> getLabelExportData(Map<String, Object> param);
	
	boolean initRepayPlan(LoanDo loanDo,List<SettDetailDo> list);
	
	LoanPersonDo getLoanInfoForSbByLoanId(Long loanId);
	
	LoanPersonDo getLoanInfoForFkByOrderCode(String orderCode);
	
	Map<String,Object> getBankAccountForHF(String idNo);
	Map<String,Object> getBankAccountForTL(String idNo);

	List<Map<String,Object>> getSbNameForHF(String orderCode);
	List<Map<String,Object>> getSbNameForTL(String orderCode);
	
	List<LoanDo> getLoanListPage(Map<String, Object> param);

	int updateLoan(LoanDo loanDo);


	public int addLoanModifyLog(LoanModifyLogDo modifyLog);


	LoanPersonDo getLoanPersonDetail(Long loanId);

	LoanDo getDetailLoanByLoanId(Long loanId);

	List<LoanUserBankDo> getTbcInfo(Long userId);

	List<LoanUserBankDo> getTdbcInfo(Long userId);

	List<LoanCheckedDo> getLoanCheckedByLoanId(Map<String, Object> param);

	int updateLoanChecked(LoanCheckedDo loanCheckedDo);

	int createLoanChecked(LoanCheckedDo loanCheckedDo);

	LoanDo getLoanforUpdate(Map<String, Object> param);

	/**
	 * 获取渠道列表
	 * @return
	 */
	List<Map<String, Object>> getChannelTypeList();

	/**
	 * 组装完整的订单对象，传给小贷
	 * @param loanId
	 * @return
	 */
	LoanDo getFullLoanDo(Long loanId);

	List<LoanRelationDo> getLoanRelationDoList(Long loanId);

}
