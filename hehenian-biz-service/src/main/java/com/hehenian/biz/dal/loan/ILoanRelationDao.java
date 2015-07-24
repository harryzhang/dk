/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.loan
 * @Title: ILoanRelationDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:44:57
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.loan;

import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;

/** 
 *  
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:44:57
 */
public interface ILoanRelationDao {
	/**
	 * 修改个人联系信息
	 * @param loanRelationDo
	 */
    void updateLoanRelation(LoanRelationDo loanRelationDo);
    
    /**
     * 新增个人联系信息
     * @param loanRelationDo
     */
    void addLoanRelation(LoanRelationDo loanRelationDo);
    
    
    /**
     * 根据ID查询表的记录
     * @return
     */
    int getCountByIds(Long id);

    /**
     * 根据订单ID删除联系人
     * @param loanId
     * @return
     */
	int deleteByLoanId(Long loanId);
}
