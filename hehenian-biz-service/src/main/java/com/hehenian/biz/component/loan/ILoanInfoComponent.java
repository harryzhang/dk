/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.loan
 * @Title: ILoanInfoComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年4月20日 下午2:51:49
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanInfoDo;

/**
 * 
 * @author: liuzgmf
 * @date 2015年4月20日 下午2:51:49
 */
public interface ILoanInfoComponent {
    /**
     * 新增借款信息
     * 
     * @param loanInfoDoList
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午2:14:28
     */
    int addLoanInfo(List<LoanInfoDo> loanInfoDoList);

    /**
     * 修改借款信息
     * 
     * @param loanInfoDo
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午2:14:28
     */
    int updateLoanInfo(LoanInfoDo loanInfoDo);

    /**
     * 根据查询条件查询借款标的信息
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午3:01:10
     */
    List<LoanInfoDo> queryLoanInfos(Map<String, Object> searchItems);

    /**
     * 根据ID查询借款标的信息
     * 
     * @param loanInfoId
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午3:01:25
     */
    LoanInfoDo getByLoanInfoId(Long loanInfoId);

    /**
     * 根据条件查询借款标的记录数
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午3:59:35
     */
    long countLoanInfo(Map<String, Object> searchItems);

    /**
     * 根据借款标的ID查询借款标的信息
     * 
     * @param loanInfoIdList
     * @return
     * @author: liuzgmf
     * @date: 2015年4月21日下午2:43:35
     */
    List<LoanInfoDo> queryByLoanInfoIds(List<Long> loanInfoIdList);

    /**
     * 新增借款标的信息
     * 
     * @param loanInfoDo
     * @param userId
     * @author: liuzgmf
     * @date: 2015年5月4日下午3:23:21
     */
    void addLoanDetail(LoanInfoDo loanInfoDo, Long userId);
}
