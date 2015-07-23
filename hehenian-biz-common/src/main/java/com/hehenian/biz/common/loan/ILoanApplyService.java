/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.loan
 * @Title: ILoanApplyService.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月26日 上午11:30:10
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanChannelDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanProxyCheckDo;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月26日 上午11:30:10
 */
public interface ILoanApplyService {

    /**
     * 查询借款申请记录数
     * 
     * @return
     * @author: liuzgmf
     * @date: 2015年1月26日上午11:30:16
     */
    int getLoanQty();

    /**
     * 保存借款申请信息
     * 
     * @param loanDo
     * @return
     * @author: liuzgmf
     * @date: 2015年1月26日下午4:08:15
     */
    Long saveLoan(LoanDo loanDo);

    /**
     * 根据借款ID查询借款申请信息
     * 
     * @param loanId
     * @return
     * @author: liuzgmf
     * @date: 2015年1月26日下午4:08:35
     */
    LoanDo getByLoanId(Long loanId);

    /**
     * 根据彩生活用户ID查询借款申请信息
     * @author: liuzgmf
     * @date: 2015年1月28日上午9:52:20
     */
    List<LoanDo> queryBySourceUserId(String sourceUserId);

    /**
     * E贷款的个人中心统计信息
     * @param loanChannelDo
     * @return
     */
	Map<String, Object> getTotalInfoByPerson(LoanChannelDo loanChannelDo);

	String getCmoblie(long cname);

	 /**
	  * 判断是否插入过
	  * @param loanId 
	  */
	List<LoanDo> selectLoanList(LoanDo loanDo);
	
	
	/********************审核API**************************/
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanProxyCheckDo getProxyCheckDoById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanProxyCheckDo> selectLoanProxyCheck(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanProxyCheckById(LoanProxyCheckDo newLoanProxyCheckDo);
	
	/**
	 * 新增
	 */
	public int addLoanProxyCheck(LoanProxyCheckDo newLoanProxyCheckDo);
	/********************END 审核的API*********************/
	/** 查询 小区信息*/
	List<Map<String, Object>> getJBCmobileDo(Map<String, String> params);

	public abstract List<Map<String, Object>> getAreaList(Map<String,Object> paramMap);
	/** 更新小区房价*/
	int updateHousePrice(Map<String, Object> parameterMap);
	/** 保存小区房价*/
	int saveHousePrice(Map<String, Object> parameterMap);
    
}
