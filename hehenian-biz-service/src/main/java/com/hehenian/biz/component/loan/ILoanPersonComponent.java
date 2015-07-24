/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.loan
 * @Title: ILoanDetailComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月11日 上午9:54:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.JobDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;

/**
 * 
 * @author xiexiangmf
 *
 */
public interface ILoanPersonComponent {

    /**
     * 修改借款人的信息
     * 
     * @param loanPersonDoList
     */
    void updateLoanPerson(LoanPersonDo loanPersonDo);

    /**
     * 新增借款人的信息
     * 
     * @param loanPersonDoList
     */
    void addLoanPerson(LoanPersonDo loanPersonDo);

    /**
     * 根据条件查询借款人信息
     * 
     * @param searchItems
     * @return
     */
    List<LoanPersonDo> queryLoanPerson(Map<String, Object> searchItems);
    /**
     * 根据条件查询借款人信息
     * 
     * @param searchItems
     * @return
     */
    List<LoanPersonDo> queryLoanPersonByApp(Map<String, Object> searchItems);

    /**
     * 根据ID查询表的记录
     * 
     * @return
     */
    LoanPersonDo getCountByIds(Long loanId);

    /**
     * 根据条件查询借款人信息
     * 
     * @param searchItems
     * @return
     */
    int getTotalCount(Map<String, Object> searchItems);

    /**
     * 根据条件查询签约的信息
     * 
     * @param searchItems
     * @return
     */
    List<LoanPersonDo> queryLoanAuditeds(Map<String, Object> searchItems);

    /**
     * 根据条件查询签约总记录
     * 
     * @param searchItems
     * @return
     */
    int getAuditedTotalCount(Map<String, Object> searchItems);

    /**
     * 根据借款申请ID查询借款人信息
     * 
     * @param loanId
     * @return
     * @author: liuzgmf
     * @date: 2015年1月26日下午4:02:19
     */
    LoanPersonDo getByLoanId(Long loanId);

    /**
     * 保存借款人信息
     * 
     * @param userId
     * @param loanPersonDo
     * @return
     * @author: liuzgmf
     * @date: 2015年1月26日下午4:06:31
     */
    int saveLoanPerson(LoanPersonDo loanPersonDo);


	LoanPersonDo initTreatyData(Map<String, Object> searchItems);

	/**
	 * 预期收益
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
	 * @author wangt
	 * 更新或者修改借款人职业信息
	 * @param jobDo
	 */
	void saveJobInfo(JobDo jobDo);
	
	/**
	 * @author wangt
	 * 更新借款人的联系人信息
	 * @param loanRelationDoList
	 * @param loanId
	 * @param loanPersonId
	 */
	void updateRelationList(List<LoanRelationDo> loanRelationDoList,long loanId,long loanPersonId);
	
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
	 * 业绩查询  查询还款订单信息
	 * @author wangt  
	 *
	 * 2015年3月30日 下午5:47:08 
	 * @param searchItems
	 * @return
	 */
	List<LoanPersonDo> queryLoanBorrowByApp(Map<String, Object> searchItems);
	
	/***
	 * 根据LoanId 查询 loanPerson loan的信息
	 * @author wangt  
	 *
	 * 2015年4月1日 下午3:46:17 
	 * @param loanId
	 * @return
	 */
	LoanPersonDo  getLoanPersonById(Long loanId);

	public void saveLoanPersonChild(LoanPersonDo loanPersonDo); 

}
