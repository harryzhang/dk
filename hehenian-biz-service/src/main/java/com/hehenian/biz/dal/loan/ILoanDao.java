/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.loan
 * @Title: ILoanDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:43:17
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanDo;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:43:17
 */
public interface ILoanDao {

    int addLoanDo(LoanDo loanDo);
    
    int addLoanDo1(LoanDo loanDo);

    int updateLoanDo(LoanDo loanDo);

    int changeLoanStatus(LoanDo loanDo);

    /**
     * 查询借款申请记录数
     * 
     * @return
     * @author: liuzgmf
     * @date: 2015年1月26日上午11:30:16
     */
    int getLoanQty();

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
     * 
     * @param colorId
     * @return
     * @author: liuzgmf
     * @date: 2015年1月28日上午9:52:20
     */
    List<LoanDo> queryBySourceUserId(String sourceUserId);

    /**
     * 根据个人身份证号查询借款
     * @param userId
     * @return
     */
	List<LoanDo> getMyLoanList(String id);

	/**
	 * 我推荐的
	 * @param mobile
	 * @return
	 */
	List<LoanDo> getMyRefLoanList(String mobile);

	/**
	 * 逾期次数
	 * @param userId
	 * @return
	 */
	int queryLateCount(String id);
	
	/**
	 * 获得小区管家电话
	 * @param cname
	 * @return
	 */
	String getCmoblie(long cname);

	/**
	 * 查询小区的清单
	 * @return
	 */
	List<Map<String, Object>> getAreaList(Map<String,Object> paramMap);
	 /**
	  * 根据条件查询
	  * @param loanDo 
	  */
	List<LoanDo> selectLoanList(LoanDo loanDo);
	/** 查询 小区信息*/
	List<Map<String, Object>> getJBCmobileDo(Map<String, String> params);
	
	/*
	 * 获取小区信息
	 */
	List<Map<String, Object>> getCname(Long cid);
	/** 更新小区房价*/
	int updateHousePrice(Map<String, Object> parameterMap);
	/** 保存小区房价*/
	int saveHousePrice(Map<String, Object> parameterMap);
	/** 根据身份证号码查询*/
	LoanDo getByIdNoGroup(String idNo);
	
	
	
}
