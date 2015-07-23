package com.hehenian.biz.common.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.dataobject.LoanCheckedDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanProductDo;
import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.common.loan.dataobject.LoanUserBankDo;

/**
 * @author zhengyfmf
 */
public interface IManagerLoanService {

	LoanDo getLoanByLoanId(Long loanId);
	
	/**
	 * 修改订单
	 * @param loanDo
	 * @return
	 */
	int managerUpdateLoanStatus(LoanDo loanDo);
	
	/**
	 * 修改订单状态
	 * @param paramList
	 * @return
	 */
	boolean managerUpdateLoanStatus(List<Map<String,Object>> paramList);
	
	/**
	 * 订单列表
	 * @param param
	 * @param page
	 * @return
	 */
	PageDo<Map<String,Object>> managerGetLoanPage(Map<String,Object> param, PageDo<Map<String,Object>> page);
	
	/**
	 * 订单详情
	 * @param loanId
	 * @return
	 */
	LoanPersonDo getLoanDetailByLoanId(Long loanId);
	
	/**
	 * 产品列表
	 * @param param
	 * @return
	 */
	List<LoanProductDo> listLoanProduct(Map<String,Object> param);
	/**   
	 * @Description 标的导出数据
	 *
	 * @author huangzl   
	 *
	 * @date 2015年4月15日下午2:24:20
	 */
	List<Map<String, Object>> getLabelExportData(Map<String, Object> param);
	
	/**
	 * 还款计划表生成
	 * @param loanId 借款ID
	 * @param loanAmount 放款金额
	 * @param investAnnualRate 投资人利息
	 * @param loanTime 放款日期
	 */
	boolean initRepayPlan(LoanDo loanDo);
	
	LoanPersonDo getLoanInfoForSbByLoanId(Long loanId);
	
	LoanPersonDo getLoanInfoForFkByOrderCode(String orderCode);
	
	Map<String,Object> getBankAccountForHF(String idNo);
	Map<String,Object> getBankAccountForTL(String idNo);
	
	List<Map<String,Object>> getSbNameForHF(String orderCode);
	List<Map<String,Object>> getSbNameForTL(String orderCode);

	PageDo<LoanDo> getLoanListPage(Map<String, Object> param,
			PageDo<LoanDo> page);

	int updateLoan(LoanDo loanDo);

	LoanPersonDo getLoanPersonDetail(Long loanId);

	LoanDo getDetailLoanByLoanId(Long loanId);
	/**
	 * -- 汇付卡信息
	 * @auther liminglmf
	 * @date 2015年5月27日
	 * @param userId
	 * @return 
	 */
	List<LoanUserBankDo> managerGetTbcInfo(Long userId);
	/**
	 * -- 通联卡信息
	 * @auther liminglmf
	 * @date 2015年5月27日
	 * @param userId
	 * @return 
	 */
	List<LoanUserBankDo> managerGetTdbcInfo(Long userId);

	List<LoanCheckedDo> getLoanCheckedByLoanId(
			Long loanId, String checkType);

	int updateLoanChecked(LoanCheckedDo loanCheckedDo);

	int createLoanChecked(LoanCheckedDo loanCheckedDo);

	LoanDo getLoanforUpdate(Map<String, Object> param);

	/**
	 * 获取渠道列表
	 * @return
	 */
	List<Map<String, Object>> getChannelTypeList();
	
	/**
	 * 订单信息传到小贷
	 * @auther liminglmf
	 * @date 2015年7月3日
	 * @param loan
	 * @return String[] 第一个返回 "true"/"false"<br>
	 *  当false 时 可取的 第二个参数错误信息
	 */
	String[] sendOrderToXiaoDai(LoanDo loan);

	/**
	 * 
	 * @param ownerName 业主真实姓名
	 * @param ownerIDCardNo 业主身份证号
	 * @param cname 小区名称
	 * @param houseNo 房号
	 * @param loanId 订单ID
	 */
	public void callColorHouseCheck(String ownerName, String ownerIDCardNo,
			String cname, String houseNo, Long loanId);
	
	/**
	 * 根据订单ID查询联系人信息
	 * @auther liminglmf
	 * @date 2015年7月13日
	 * @param loanId
	 * @return
	 */
	List<LoanRelationDo> getLoanRelationListByLoanId(Long loanId);
}
