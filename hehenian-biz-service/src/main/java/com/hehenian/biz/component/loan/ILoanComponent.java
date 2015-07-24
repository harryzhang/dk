package com.hehenian.biz.component.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanChannelDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo.LoanStatus;
import com.hehenian.biz.common.loan.dataobject.LoanDo.ProcessStep;
import com.hehenian.biz.common.loan.dataobject.LoanProxyCheckDo;

/**
 * 
 * @author xiexiangmf
 *
 */
public interface ILoanComponent {
    /**
     * 新增借款申请信息
     * 
     * @param loanDetailDo
     * @return
     * @date: 2014年12月10日下午6:59:12
     */
    Long addLoanDo(LoanDo loanDo);

    /**
     * 修改借款申请信息
     * 
     * @return
     * @date: 2014年12月11日上午9:51:54
     */
    void updateLoanDo(LoanDo loanDo);

    /**
     * 修改借款申请借款的状态
     * 
     * @param loanId
     * @param loanStatus
     * @return
     */
    int changeLoanStatus(LoanDo loanDo);

    /**
     * 根据身份证号查询借款申请信息
     * 
     * @param idNo
     * @return
     */
    LoanDo getByIdNo(String idNo);

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
     * E贷款的个人中心的统计数据
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

	/**
	 * 获取下一个步骤
	 * @param newLoanDo
	 * @return
	 */
	ProcessStep getNextStep(LoanDo newLoanDo);

	/**
	 * 获取下个状态
	 * @param currentStatus
	 * @param currentStep
	 * @param applayAmount
	 * @return
	 */
	LoanStatus getNextStatus(LoanStatus currentStatus, ProcessStep currentStep,
			Double applayAmount);

	/**
	 * 查询小区
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> getAreaList(Map<String,Object> paramMap);
	/** 更新小区房价*/
	int updateHousePrice(Map<String, Object> parameterMap);
	/** 保存小区房价*/
	int saveHousePrice(Map<String, Object> parameterMap);
	/** 根据身份证号码查询*/
	LoanDo getByIdNoGroup(String idNo);

   
}
