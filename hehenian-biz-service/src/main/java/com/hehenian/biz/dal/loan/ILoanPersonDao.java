/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.loan
 * @Title: ILoanPersonDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:44:20
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:44:20
 */
public interface ILoanPersonDao {
    /**
     * 修改借款人申请信息
     * 
     * @return
     */
    int updateLoanPerson(LoanPersonDo loanPersonDo);

    /**
     * 新增借款人申请信息
     * 
     * @return
     */
    int addLoanPerson(LoanPersonDo loanPersonDo);

    /**
     * 查询借款人信息
     * 
     * @param searchItems
     * @return
     */
    public List<LoanPersonDo> queryLoanPersons(Map<String, Object> searchItems);
    /**
     * 查询借款人信息
     *  @author wangt  
     * @param searchItems
     * @return
     */
    public List<LoanPersonDo> queryLoanPersonsByApp(Map<String, Object> searchItems);
    
    /**
     * 查询借款还款信息
     * @author wangt  
     *
     * 2015年3月30日 下午5:46:00 
     * @param searchItems
     * @return
     */
    public List<LoanPersonDo> queryLoanBorrowByApp(Map<String, Object> searchItems);

    
    /**
     * 根据ID查询表的记录
     * 
     * @return
     */
    LoanPersonDo getLoanPersonByIds(@Param("loanId") Long loanId);

    /**
     * 根据借款申请ID查询借款人信息
     * 
     * @param loanId
     * @return
     * @author: liuzgmf
     * @date: 2015年1月26日下午4:02:19
     */
    LoanPersonDo getByLoanId(Long loanId);

	int getTotalCount(Map<String, Object> searchItems);
	
	/**
	 * 查询合约签订总记录
	 * @param searchItems
	 * @return
	 */
	int getAuditedTotalCount(Map<String, Object> searchItems);
	/**
	 * 合约签订查询
	 */
	public List<LoanPersonDo> queryLoanAuditeds(Map<String, Object> searchItems);

	
	LoanPersonDo initTreatyData(Map<String, Object> searchItems);
	
	/**
	 * 查询预期收益
	 * @param searchItems
	 * @return
	 */
	Map<String, Object> getYqsl(Map<String, Object> searchItems);
	
	/**
	 * 查询贷后管理总的记录
	 * @param searchItems
	 * @return
	 */
	
	int getManagerTotalCount(Map<String, Object> searchItems);
	
	/**
	 * 贷后管理查询
	 * @param searchItems
	 * @return
	 */
	List<LoanPersonDo> getLoanManager(Map<String, Object> searchItems);
	
	/**
	 * 查询贷后还款详情
	 */
	List<RepaymentDo> getRepayMentByBwId(Long borrowId);
	
	/**
	 * 查询贷后管理预期收益
	 * @param searchItems
	 * @return
	 */
	Map<String, Object> getdLYqsl(Map<String, Object> searchItems);
	
	/**
	 * 业绩查询管理
	 * @param searchItems
	 * @return
	 */
	Map<String, Object> getIncomeManager(Map<String, Object> searchItems);
	
	/**
	 * 业务查询模块-》查询新订单 已拒绝 的订单数量以及总额
	 * @author wangt
	 * @param searchItems
	 * @return
	 */
	Map<String,Object> getSumLoan(Map<String,Object> searchItems);
	
	/**
	 * 业务查询模块-》查询还款中的订单总数
	 * @author wangt
	 * @param searchItems
	 * @return
	 */
	Map<String,Object> getSumBorrowing(Map<String,Object> searchItems);
	
	/**
	 * 业务查询模块-》查询已还款
	 * @author wangt
	 * @param searchItems
	 * @return
	 */
	Map<String,Object> getSumBorrowed(Map<String,Object> searchItems);
	
	/**
	 * 根据loanId 查询loanPerson loan的信息
	 * @author wangt  
	 *
	 * 2015年4月1日 下午3:45:05 
	 * @param loanId
	 * @return
	 */
	LoanPersonDo  getLoanPersonById(Long loanId); 
}
